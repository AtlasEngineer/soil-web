package com.atlas.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atlas.lambkit.start.BaiDuConfig;
import com.atlas.server.utils.Co;
import com.baidu.aip.imagesearch.AipImageSearch;
import com.jfinal.aop.Clear;
import com.jfinal.json.JFinalJson;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.lambkit.Lambkit;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Clear(JwtTokenInterceptor.class)
public class IndexController extends LambkitController {

    //百度的连接信息
    private static BaiDuConfig config = Lambkit.config(BaiDuConfig.class);
    //百度接口调用连接对象
    private static AipImageSearch client = new AipImageSearch(config.getAPP_ID(), config.getAPI_KEY(), config.getSECRET_KEY());


    public void list(){
        List<Object> types = new ArrayList<>();
        types.add("aaa");
        types.add("bbb");
        renderJson(Co.ok("data", types));
    }


    /**
     * @Description: 添加纲目科属种样本
     * @Author: yangxueyang
     * @Date: 2019/9/25
     */
//    @Params({
//            @Param(name = "filepath", description = "图片路径", required = true, dataType = "String"),
//            @Param(name = "id", description = "种类id", required = false, dataType = "String"),
//            @Param(name = "prantId", description = "父id", required = false, dataType = "String"),
//    })
//    @ApiOperation(url = "/cern/addEatalogue", tag = "/cern", httpMethod = "get", description = "添加纲目科属种样本")
//    public void addEatalogue() throws IOException {
//        String id = getPara("id");//种类id
//        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
//        String s ="D:/upload/sample/" + id; //现在改为种类id为文件夹得名字
//
//            List<String> baiDu = new ArrayList<>();  //上传百度失败的图片
//            HashMap<String, String> options = new HashMap<String, String>();
//            // 文件标签, 这个先不填, 实际项目时, 根据实际需求来
//            Catalogue catalogue = Catalogue.service().dao().findFirst(Catalogue.sql().andIdEqualTo(id).example());
//            options.put("tags", catalogue.getLv());
//            //  images 目录下的base目录训练图片, test是测试图片, 这里把训练图片全部入库
//            File images = new File(s);
//            // 获取目录下的所有图片
//            File[] fi = images.listFiles();
//            for (File f : fi) {
//                if (ZipUtils.checkFileSizeIsLimit(f.length(), 3, "M")) {
//                    System.out.println("小于3m啊啊啊啊啊啊");
//                } else {
//                    System.out.println(f.getName() + "大于3m啊啊啊啊啊啊");
//                    String path = rootPath + "/upload/" + f.getName();
//                    ZipUtils.yasuoImage(path, f);
//                }
//
//                String fileext = PathUtils.getExtensionName(f.getName());
//                String name = f.getName().split("\\.")[0];
//                if ("jpg".equalsIgnoreCase(fileext) || "png".equalsIgnoreCase(fileext) || "bmp".equalsIgnoreCase(fileext)) {
//                    // 数据格式为json, 可以定义多种属性, 这里以name为例, 所有图片都是花草, 加上文件名方便识别
//                    options.put("brief", "{\"name\":" + f.getName() + ",\"id\":" + id + ",\"prantId\":" + catalogue.getParentid() + ",\"url\":" + "/eatalogue/sample/" + id + "/" + f.getName() + "}");
//                    // 上传图片入库
//                    JSONObject res = client.similarAdd(f.getAbsolutePath(), options);
//                    // 打印上传结果
//                    System.out.println(res.toString(2));//不是格式
//                    if (StringUtils.isNotBlank(JSON.parseObject(res.toString()).getString("error_msg"))) {
//                        //上传百度图库报错了
//                        baiDu.add(f.getName());
//                    } else {
//                        CatalogueSample catalogueSample = CatalogueSample.service().dao().findById(name);
//                        catalogueSample.setContSign(JSON.parseObject(res.toString()).getString("cont_sign"));
//                        boolean result = catalogueSample.update();
//                        if (result) {
//                            System.out.println(f.getName() + "修改成功");
//                        } else {
//                            System.out.println(f.getName() + "修改失败");
//                        }
//                    }
//                } else {
//                    //移动到错误的文件夹   应该没有
//                    System.out.println(f.getName()+"文件格式不正确");
//                }
//            }
//            if ( baiDu.size() != 0) {
//                renderJson(Co.ok("baiDu", baiDu));
//            } else {
//                renderJson(Co.ok("msg", "添加样本成功,所有图片添加成功"));
//            }
//    }


//    public void list(){
//        List<AtBotanyType> types=AtBotanyType.service().dao().findAll();
//        renderJson(Co.ok("data", types));
//    }
//
//    public void listById(){
//        Integer id=getParaToInt("id");
//        if(id==null){
//            renderJson(Co.fail("msg", "id为空"));
//            return;
//        }
//        List<Catalogue> types=Catalogue.service().dao().find(Catalogue.sql().andBotanyTypeEqualTo(id).example());
//        renderJson(Co.ok("data", types));
//    }
//
//
//
//    public void search() throws UnsupportedEncodingException {
//        //getFile一定放在第一个参数去获取，否则都获取不到参数
//        UploadFile uf = getFile("file", "image");
//        File file = uf.getFile();
//        String lon = getPara("lon");
//        String lat = getPara("lat");
//        String fileext = PathUtils.getExtensionName(file.getName());
//        System.out.println("上传时文件名：" + file.getName());
//        String rootPath = PathKit.getWebRootPath() + "/eatalogue/w/";
//        String s2 = new Date().getTime() + "" + (int) ((Math.random() * 9 + 1) * 100000);
//        String filename = s2 + "." + fileext;
//        String image = "";
//        String p = "/eatalogue/w/";
//        if (!"jpg".equals(fileext) && !"png".equals(fileext) && !"gif".equals(fileext) && !"jpeg".equals(fileext)) {
//            file.delete();
//            renderJson(Co.fail("msg", "图片格式不正确"));
//            return;
//        } else {
//            boolean b = file.renameTo(new File(rootPath + filename));
//            if (!b) {
//                renderJson(Co.fail("msg", "重命名失败"));
//                return;
//            } else {
//                System.out.println(filename);
//                image = File.separator + "eatalogue" + File.separator + "w" + File.separator + filename;
//                System.out.println(image);
//                String root = PathKit.getWebRootPath().replace("\\", "/");
//                JSONObject res = null;
//                HashMap<String, String> options = new HashMap<String, String>();
//                // 页码, 这里从第一页开始, 也就是展示相似度最高的两张图片
//                options.put("pn", "0");
//                // 条数, 只显示两条
//                options.put("rn", "10");
//                // 进行相似查询,
//                String images = root + image;
//                System.out.println("图片的地址为哎哎哎啊" + images);
//                res = client.similarSearch(images, options);//直接输入路径进行查询
//                // 打印上传结果
//                System.out.println(res.toString(2));
//                if (StringUtils.isNotBlank(JSON.parseObject(res.toString()).getString("error_msg"))) {
//                    renderJson(Co.fail("msg", "识别失败").set("data", JSON.parseObject(res.toString()).getString("error_msg")));
//                    return;
//                }
//                JSONArray jsonArray = null;
//                Catalogue flora = null;
//                String result = "";
//                String id = "";
//                com.alibaba.fastjson.JSONObject jsonObject = null;
//                if (StringUtils.isNotBlank(res.toString(2))) {
//                    result = JSON.parseObject(res.toString(2)).getString("result");
//                    jsonArray = JSONArray.parseArray(result);
//                    for (int x = 0; x < jsonArray.size(); x++) {
//                        System.out.println("name__________name:" + jsonArray.getJSONObject(x).get("brief"));
//                        System.out.println(JSON.parseObject(jsonArray.getJSONObject(x).get("brief").toString()).getString("id"));
//                        id = JSON.parseObject(jsonArray.getJSONObject(x).get("brief").toString()).getString("id");
//                        flora = Catalogue.service().dao().findFirst(Catalogue.sql().andIdEqualTo(id).example());
//                        flora.put("result", JSONArray.parseArray(result));
//                        flora.put("photo", image);
//                        String text = JFinalJson.getJson().toJson(flora);
//                        jsonObject = com.alibaba.fastjson.JSONObject.parseObject(text);
//                    }
//                }
//                CatalogueSample sample = new CatalogueSample();
//                sample.setBrief("{\"name\":\"" + filename + "\",\"url\":" + image + "\"}");
//                sample.setContSign(JSON.parseObject(res.toString()).getString("cont_sign"));
//                sample.setDel(0);
//                sample.setId(s2);
//                sample.setType(1); //识别待审核
//                sample.setTime(new Date());
//                sample.setName(filename);
//                sample.setUrl(image);
//                sample.setStatus(1);//0 导入,1识别,2.上报
//                sample.set("lat", lat);
//                sample.set("lon", lon);
//                boolean t = sample.save();
//                if (t) {
//                    //1 暂时写死  用户id，从用户信息中获取
//                    String sql = "INSERT INTO \"catalogue_keep\" (\"name\" , \"url\" , \"user_id\" ,\"time\",\"sample_id\",\"status\")  values('" + filename + "','" + p + filename + "',1,'" + new Date() + "','" + sample.getId() + "',1)";
//                    int msg = Db.update(sql);
//                    if (msg == 1) {
//                        renderJson(Co.ok("data", jsonObject));
//                    } else {
//                        renderJson(Co.fail("msg", "识别失败"));
//                    }
//                }
//            }
//        }
//
//    }


}
