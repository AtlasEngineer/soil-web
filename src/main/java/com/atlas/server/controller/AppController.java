package com.atlas.server.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atlas.lambkit.start.BaiDuConfig;
import com.atlas.server.model.Catalogue;
import com.atlas.server.model.CatalogueSample;
import com.atlas.server.utils.AiDiscenerUtils;
import com.atlas.server.utils.Co;
import com.atlas.server.utils.ZipUtils;
import com.baidu.aip.imagesearch.AipImageSearch;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.lambkit.Lambkit;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Clear(JwtTokenInterceptor.class)
public class AppController extends LambkitController {

    //百度的连接信息
    private static BaiDuConfig config = Lambkit.config(BaiDuConfig.class);
    //百度接口调用连接对象-植物识别
    private static AipImageSearch client = new AipImageSearch(config.getAPP_ID(), config.getAPI_KEY(), config.getSECRET_KEY());

    //百度接口调用连接对象-病虫害识别
    private static AipImageSearch client_B = new AipImageSearch(config.getB_APP_ID(), config.getB_API_KEY(), config.getB_SECRET_KEY());

    /**
     * @return void
     * @Author queer
     * @Description //TODO app识别
     * @Date 12:59 2020/2/18
     * @Param []
     **/
    public void discernByPhone() {
        UploadFile uf = getFile("file", "image");
        if (uf == null) {
            renderJson(Co.fail("msg", "请选择图片"));
            return;
        }
        File yfile = uf.getFile();
        String lon = getPara("lon");
        String lat = getPara("lat");
        String ip = getPara("ip");
        String rootPath = PathKit.getWebRootPath() + "/upload/";
        String path = rootPath + yfile.getName();
        File newFile = new File(path);
        yfile.renameTo(newFile);
        String url = "upload/"+yfile.getName();
        try {
            //python的识别结果
            Ret ret1 = AiDiscenerUtils.searchFloraPy(ip +url);
            //百度识别结果
            Ret ret2 = AiDiscenerUtils.searchFlora("/"+url);

            if (!"ok".equals(ret1.getStr("state"))) {
                renderJson(Co.fail("msg", ret1));
            }
            if (!"ok".equals(ret2.getStr("state"))) {
                renderJson(Co.fail("msg", ret2));
            }
            //解析再合并后添加到识别记录

            //校验
            if(!"ok".equals(ret1.getStr("state"))){
                renderJson(ret1);
                return;
            }
            if(!"ok".equals(ret2.getStr("state"))){
                renderJson(ret2);
                return;
            }

            //1、合并
            String list1 = ret1.getStr("list");
            String list2 = ret2.getStr("list");

            JSONArray array1 = JSONArray.parseArray(list1);
            JSONArray array2 = JSONArray.parseArray(list2);
            Iterator<Object> iterator1 = array1.iterator();
            while (iterator1.hasNext()) {
                JSONObject jb = (JSONObject) iterator1.next();
                array2.add(jb);
            }

            //2、解析处理
            List<JSONObject> list = JSONArray.parseArray(array2.toJSONString(), JSONObject.class);
            Collections.sort(list, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    Double a = o1.getDouble("sim");
                    Double b = o2.getDouble("sim");
                    if (a > b) {
                        return -1;
                    } else if(a == b) {
                        return 0;
                    } else
                        return 1;
                }
            });
            JSONArray jsonArray = JSONArray.parseArray(list.toString());
            List<String> species = new ArrayList<>();
            Iterator<Object> iterator2 = jsonArray.iterator();
            while (iterator2.hasNext()) {
                JSONObject jb = (JSONObject) iterator2.next();
                String id = jb.getString("id");
                if (species.contains(id)) {
                    iterator2.remove();
                }else{
                    species.add(id);
                }
            }
            //3、添加待审核样本
            String p = "/eatalogue/w/";
            String[] split = url.split("/");
            String filename = split[split.length-1];
            System.out.println(filename);
            File file = new File(PathKit.getWebRootPath() + "/upload/"+filename);
            String s = PathKit.getWebRootPath() + p + file.getName();
            FileUtils.copyFile(file, new File(s));
            CatalogueSample sample = new CatalogueSample();
            sample.setDel(0);
            String s2 = new Date().getTime() + "" + (int) ((Math.random() * 9 + 1) * 100000);
            sample.setId(s2);
            sample.setType(1); //识别待审核
            sample.setTime(new Date());
            sample.setLon(lon);
            sample.setLat(lat);
            sample.setName(file.getName());
            sample.setUrl(p + file.getName());
            sample.setStatus(1);//0 导入,1识别,2.上报
            sample.save();
            //4、添加识别记录
//            String sql = "INSERT INTO \"catalogue_keep\" (\"name\" , \"url\" , \"user_id\" ,\"time\",\"sample_id\",\"status\")  values('" + fileNew.getName() + "','" + p + fileNew.getName() + "',1,'" + new Date() + "','" + sample.getId() + "',1)";
//            int msg = Db.update(sql);
            renderJson(Ret.ok("data", jsonArray));
        } catch (IOException e) {
            e.printStackTrace();
            renderJson(Co.fail("msg", e.getMessage()));
        }
    }


        /**
     * @Description: 添加植物样本
     * @Author: yangxueyang
     * @Date: 2019/9/25
     */
    @Params({
            @Param(name = "filepath", description = "图片路径", required = true, dataType = "String"),
            @Param(name = "id", description = "种类id", required = false, dataType = "String"),
            @Param(name = "prantId", description = "父id", required = false, dataType = "String"),
    })
    @ApiOperation(url = "/cern/addEatalogue", tag = "/cern", httpMethod = "get", description = "添加纲目科属种样本")
    public void addEatalogue() throws IOException {
        String id = getPara("id");//种类id
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        String s ="D:/upload/sample/" + id; //现在改为种类id为文件夹得名字

            List<String> baiDu = new ArrayList<>();  //上传百度失败的图片
            HashMap<String, String> options = new HashMap<String, String>();
            // 文件标签, 这个先不填, 实际项目时, 根据实际需求来
            Catalogue catalogue = Catalogue.service().dao().findFirst(Catalogue.sql().andIdEqualTo(id).example());
            options.put("tags", "5");
            //  images 目录下的base目录训练图片, test是测试图片, 这里把训练图片全部入库
            File images = new File(s);
            // 获取目录下的所有图片
            File[] fi = images.listFiles();
            for (File f : fi) {
                if (ZipUtils.checkFileSizeIsLimit(f.length(), 3, "M")) {
                    System.out.println("小于3m啊啊啊啊啊啊");
                } else {
                    System.out.println(f.getName() + "大于3m啊啊啊啊啊啊");
                    String path = rootPath + "/upload/" + f.getName();
                    ZipUtils.yasuoImage(path, f);
                }

                String fileext = PathUtils.getExtensionName(f.getName());
                String name = f.getName().split("\\.")[0];
                if ("jpg".equalsIgnoreCase(fileext) || "png".equalsIgnoreCase(fileext) || "bmp".equalsIgnoreCase(fileext)) {
                    // 数据格式为json, 可以定义多种属性, 这里以name为例, 所有图片都是花草, 加上文件名方便识别
                    options.put("brief", "{\"name\":\"" + f.getName() + "\",\"id\":\"" + id + "\",\"url\":\"" + "/eatalogue/sample/" + id + "/" + f.getName() + "\"}");
                    // 上传图片入库
                    org.json.JSONObject res = client.similarAdd(f.getAbsolutePath(), options);
                    // 打印上传结果
                    System.out.println(res.toString(2));//不是格式
                    if (StringUtils.isNotBlank(JSON.parseObject(res.toString()).getString("error_msg"))) {
                        //上传百度图库报错了
                        baiDu.add(f.getName());
                    } else {
                        CatalogueSample catalogueSample = CatalogueSample.service().dao().findById(name);
                        catalogueSample.setContSign(JSON.parseObject(res.toString()).getString("cont_sign"));
                        boolean result = catalogueSample.update();
                        if (result) {
                            System.out.println(f.getName() + "修改成功");
                        } else {
                            System.out.println(f.getName() + "修改失败");
                        }
                    }
                } else {
                    //移动到错误的文件夹   应该没有
                    System.out.println(f.getName()+"文件格式不正确");
                }
            }
            if ( baiDu.size() != 0) {
                renderJson(Co.ok("baiDu", baiDu));
            } else {
                renderJson(Co.ok("msg", "添加样本成功,所有图片添加成功"));
            }
    }



    /**
     * @Description: 添加病虫害样本
     * @Author: yangxueyang
     * @Date: 2019/9/25
     */
    @Params({
            @Param(name = "filepath", description = "图片路径", required = true, dataType = "String"),
            @Param(name = "id", description = "种类id", required = false, dataType = "String"),
    })
    @ApiOperation(url = "/cern/addDiseases", tag = "/cern", httpMethod = "get", description = "添加病虫害样本")
    public void addDiseases () throws IOException {
        String filepath = getPara("filepath");//zip路径
        String id = getPara("id");//种类id

        System.out.println(filepath);
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        File file = new File(rootPath + filepath);
        System.out.println("file:" + file.getAbsolutePath());
        System.out.println(file.getName());
        if (file == null) {
            renderJson("msg", "找不到该文件");
            return;
        }
        String name = file.getName().split("\\.")[0];
        //解压后文件夹
        String s = rootPath + "/eatalogue/diseases/" + id; //现在改为种类id为文件夹得名字
        String p = "/eatalogue/diseases/" + name;
        System.out.println("file.getPath():" + file.getPath());
        try {
            ZipUtils.decompress(file.getPath(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("s" + s);
        File files = new File(s);
        File fileS = null;
        String[] fileList = files.list();
        for (String y : fileList) {
            File file2 = new File(s + File.separator + y);
            if (file2.isDirectory()) {
                System.out.println("file2.getName()" + file2.getName());
                s = s + File.separator + file2.getName();
                p = p + "/" + file2.getName();
                System.out.println("新的s为路径:" + s);
                fileS = new File(s);
            } else if (file2.isFile()) {
            }
        }
        if (fileS != null) { //如果他是文件夹的话
            fileList = fileS.list();
        }
        List<String> list = new ArrayList<>();
        List<String> noPass = new ArrayList<>(); //上传本地失败的图片
        List<String> baiDu = new ArrayList<>();  //上传百度失败的图片
        File file1 = null;
        int i = 0;
        File file01 = null;

        for (String y : fileList) {
            file1 = new File(s + "/" + y);
            System.out.println("file1:" + file1.getName());
            String fileext = PathUtils.getExtensionName(file1.getName());
            if (file1.isFile()) {
                if ("jpg".equalsIgnoreCase(fileext) || "png".equalsIgnoreCase(fileext) || "bmp".equalsIgnoreCase(fileext)) {
                    String s1 = file1.getName().split("\\.")[1];
                    //String s2 = file1.getName().split("\\.")[0];   //待定
                    // String s2 = new Date().getTime()+(int)((Math.random()*9+1)*100000)+"";//重命名文件下的文件
                    String s2 = new Date().getTime() + "" + (int) ((Math.random() * 9 + 1) * 100000);
                    file01 = new File(s + File.separator + s2 + "." + s1);
                    System.out.println("file01:" + file01.getName());
                    file1.renameTo(file01);
                    String path = file01.getPath();
                    System.out.println("path" + path);
                    list.add(file01.getPath());
                    i++;
                } else { //如果不是这三种格式的文件的图片 添加到这里并且删除
                    noPass.add(file1.getName());
                }
            }
        }
        System.out.println("成功的i:" + i);
        String newZip = rootPath + "/eatalogue/diseases/" + name + ".zip";
        try {
            int result_msg = ZipUtils.compress(list, newZip, false);
            if (result_msg > 1) {
                boolean rse = new File(newZip).delete();
                if (rse) {
                    System.out.println("新的文件夹压缩包删除成功");
                } else {
                    System.out.println("新的文件夹压缩包删除失败");
                }
            } else {
                System.out.println("新的文件夹压缩包复制失败");
            }
            HashMap<String, String> options = new HashMap<String, String>();
            // 文件标签, 这个先不填, 实际项目时, 根据实际需求来
            Catalogue catalogue = Catalogue.service().dao().findFirst(Catalogue.sql().andIdEqualTo(id).example());
            //  images 目录下的base目录训练图片, test是测试图片, 这里把训练图片全部入库
            File images = new File(s);
            // 获取目录下的所有图片
            File[] fi = images.listFiles();
            for (File f : fi) {
                if (ZipUtils.checkFileSizeIsLimit(f.length(), 3, "M")) {
                    //System.out.println("小于3m啊啊啊啊啊啊");
                } else {
                    //System.out.println(f.getName() + "大于3m啊啊啊啊啊啊");
                    String path = rootPath + "/upload/" + f.getName();
                    ZipUtils.yasuoImage(path, f);
                }
                //System.out.println("f.length()" + f.length());
                // 数据格式为json, 可以定义多种属性, 这里以name为例, 所有图片都是花草, 加上文件名方便识别
                options.put("brief", "{\"name\":\"" + f.getName() + "\",\"id\":\"" + id + "\",\"url\":\"" + "/eatalogue/sample/" + id + "/" + f.getName() + "\"}");
                // 上传图片入库
                org.json.JSONObject res = client.similarAdd(f.getAbsolutePath(), options);
                // 打印上传结果
                System.out.println(res.toString(2));
                String fileext = PathUtils.getExtensionName(f.getName());
                if ("jpg".equalsIgnoreCase(fileext) || "png".equalsIgnoreCase(fileext) || "bmp".equalsIgnoreCase(fileext)) { //不是格式
                    if (StringUtils.isNotBlank(JSON.parseObject(res.toString()).getString("error_msg"))) {
                        //上传百度图库报错了
                        baiDu.add(f.getName());
                        boolean boo = f.delete();
                        if (boo) {
                            System.out.println(f.getName() + "的图片上传百度图片失败，删除本地的图片");
                        }
                    } else {
                        CatalogueSample catalogueSample = new CatalogueSample();
                        catalogueSample.setId(f.getName().split("\\.")[0]);
                        catalogueSample.setName(f.getName());
                        catalogueSample.setBrief("{name:" + f.getName() + ",id:" + id + ",url:" + "/eatalogue/diseases/" + id + "/" + f.getName() + "}");
                        catalogueSample.setCatalogueId(id);
                        catalogueSample.setUrl("/eatalogue/diseases/" + id + "/" + f.getName());
                        catalogueSample.setContSign(JSON.parseObject(res.toString()).getString("cont_sign"));
                        catalogueSample.setTime(new Date());
                        catalogueSample.setType(0);
                        catalogueSample.setStatus(0);//0 导入,1识别,2.上报
                        boolean result = catalogueSample.save();
                        if (result) {
                            System.out.println(f.getName() + "添加数据库成功");
                        } else {
                            System.out.println(f.getName() + "添加数据库失败");
                        }
                    }
                } else {
                    boolean boo = f.delete();
                    if (boo) {
                        System.out.println("名为" + f.getName() + "的文件上传格式不正确删除成功");
                    } else {
                        System.out.println("名为" + f.getName() + "的文件上传格式不正确删除失败");
                    }
                }
            }
            if (noPass.size() != 0 || baiDu.size() != 0) {
                renderJson(Co.ok("data", noPass).set("baiDu", baiDu).set("msg", "部分添加样本成功,请查看上传本地失败的图片"));
            } else {
                renderJson(Co.ok("msg", "添加样本成功,所有图片添加成功"));
            }
            if (fileS != null) {
                ZipUtils.copyFolder(s, rootPath + "/eatalogue/diseases/" + id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            boolean result = ZipUtils.delete(s);
            if (result) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        }
    }


}
