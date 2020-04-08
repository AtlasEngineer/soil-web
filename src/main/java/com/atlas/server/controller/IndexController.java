package com.atlas.server.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atlas.lambkit.start.BaiDuConfig;
import com.atlas.server.model.*;
import com.atlas.server.utils.Co;
import com.baidu.aip.imagesearch.AipImageSearch;
import com.jfinal.aop.Clear;
import com.jfinal.json.JFinalJson;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.lambkit.Lambkit;
import com.lambkit.common.util.DateTimeUtils;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.module.upms.rpc.model.UpmsUserRole;
import com.lambkit.plugin.jwt.IJwtAble;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Clear(JwtTokenInterceptor.class)
public class IndexController extends LambkitController {

    //百度的连接信息
    private static BaiDuConfig config = Lambkit.config(BaiDuConfig.class);
    //百度接口调用连接对象
    private static AipImageSearch client = new AipImageSearch(config.getAPP_ID(), config.getAPI_KEY(), config.getSECRET_KEY());

    /**
     * @return void
     * @Author queer
     * @Description //TODO 跳转签到页面
     * @Date 16:26 2020/4/1
     * @Param []
     **/
    public void sign() {
        String username = getPara("username");
        if (StrKit.isBlank(username)) {
            setAttr("code", 0);
            setAttr("msg", "username is null");
        } else {
            UpmsUser upmsUser = UpmsManager.me().getUpmsApiService().selectUpmsUserByUsername(username);
            if (upmsUser != null) {
                //当期时间
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DATE);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                setAttr("year", year);
                setAttr("month", month);
                setAttr("day", day);
                //签到情况
                Sign sign = Sign.service().today(upmsUser.getUserId(), null);
                if (sign != null) {
                    setAttr("code", 1);
                    setAttr("msg", "signed");
                } else {
                    sign = new Sign();
                    sign.setUserid(upmsUser.getUserId().intValue());
                    sign.setCreated(DateTimeUtils.getCurrentTime());
                    boolean flag = sign.save();
                    if (flag) {
                        Integral.service().addCredit(upmsUser.getUserId(), 1, "sign", "签到", sign.getId());
                        setAttr("cnum", 5);
                        setAttr("code", 2);
                    } else {
                        setAttr("code", 3);
                    }
                }
                //总积分
                setAttr("credit", 55);
                setAttr("signMonth", sign.service().currentMonth(upmsUser.getUserId(), null));
            } else {
                setAttr("code", 0);
                setAttr("msg", "username not exist");
            }
        }
        render("sign.html");
    }


    public void list() {
        List<Record> list = Db.find("select * from at_botany_type ");
        renderJson(Co.ok("data", list));
    }


    public void listById() {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderJson(Co.fail("msg", "id不能为空"));
            return;
        }
        Integer pageNum = getParaToInt("pageNum", 1);
        Integer pageSize = getParaToInt("pageSize", 10);

        Page<Record> page = Db.paginate(pageNum, pageSize, "select *", "from catalogue where botany_type=" + id + " ORDER BY ISNULL(image),image ASC");
        Integer p = pageNum - 1;
        List<Record> list = Db.find("select * from catalogue where botany_type=" + id + " ORDER BY ISNULL(image) OR image='',image,c_id desc limit " + p + "," + pageSize + "");
        List<Record> listWithoutDuplicates = list.stream().distinct().collect(Collectors.toList());
        page.setList(listWithoutDuplicates);
        renderJson(Co.ok("data", page));
    }


    public void searchCatalogueById() {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderJson(Co.fail("msg", "id不能为空"));
            return;
        }
        Record record = Db.findFirst("SELECT * from catalogue c where c.c_id=" + id + "");
        List<Record> list = Db.find("SELECT * from catalogue_sample c where c.catalogue_id='" + record.getStr("id") + "' LIMIT 5;");
        record.set("list", list);
        renderJson(Co.ok("data", record));
    }


    //    /**
//     * @Description: 添加纲目科属种样本
//     * @Author: yangxueyang
//     * @Date: 2019/9/25
//     */
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
//            options.put("tags", "5");
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
//                    options.put("brief", "{\"name\":" + f.getName() + ",\"id\":" + id + ",\"url\":" + "/eatalogue/sample/" + id + "/" + f.getName() + "}");
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
    public void bulkAdd() {
        UploadFile file = getFile("file");

        File tempXlsFile = file.getFile();
        String fileext = PathUtils.getExtensionName(tempXlsFile.getName());
        if (!"xlsx".equals(fileext)) {
            tempXlsFile.delete();
            renderJson(Co.fail("msg", "文件格式不正确，请查看模板文件格式"));
            return;
        }
        // 从excel中读取文件
        Sheet sheet = new Sheet(1, 1);
        String name;
        List<String> ab = new ArrayList<>();
        try {
            List<Object> list = EasyExcelFactory.read(new FileInputStream(tempXlsFile.getPath()), sheet);
            for (Object obj : list) {
                if (obj != null) {
                    ArrayList<String> arrayList = (ArrayList<String>) obj;
                    name = arrayList.get(0);
                    if ("null".equals(name) || StringUtils.isBlank(name)) {
                        continue;
                    }
                    Timestamp currentTime = DateTimeUtils.getCurrentTime();
                    Record r1 = Db.findFirst("select * FROM at_insect_species c  where c.name='" + name + "' ");
                    if (r1 == null) {
                        String sql = "INSERT INTO at_insect_species (name,time) VALUES " +
                                "('" + name + "','" + currentTime + "')";
                        Db.update(sql);
                    } else {
                        ab.add(name);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            renderJson(Co.fail("msg", "读取文件失败"));
            return;
        }
        tempXlsFile.delete();
        renderJson(Co.ok("msg", "导入完成").set("ab", ab));
    }

    public void text() {
        UploadFile file = getFile("file");

        File tempXlsFile = file.getFile();
        String fileext = PathUtils.getExtensionName(tempXlsFile.getName());
        if (!"xlsx".equals(fileext)) {
            tempXlsFile.delete();
            renderJson(Co.fail("msg", "文件格式不正确，请查看模板文件格式"));
            return;
        }
        // 从excel中读取文件
        Sheet sheet = new Sheet(1, 1);
        String name;
        String text;
        Map<String, String> map = new HashMap<>();

        try {
            List<Object> list = EasyExcelFactory.read(new FileInputStream(tempXlsFile.getPath()), sheet);
            for (Object obj : list) {
                if (obj != null) {
                    ArrayList<String> arrayList = (ArrayList<String>) obj;
                    name = arrayList.get(0);
                    text = arrayList.get(1);
                    if ("null".equals(name) || StringUtils.isBlank(name)) {
                        continue;
                    }
                    if ("null".equals(text) || StringUtils.isBlank(text)) {
                        continue;
                    }
                    Timestamp currentTime = DateTimeUtils.getCurrentTime();
                    Record r1 = Db.findFirst("select * FROM at_insect_species c  where c.name='" + name + "' ");
                    Record r2 = Db.findFirst("select * FROM at_insect_pests c  where c.name='" + text + "' ");

                    Record record = Db.findFirst("select * FROM at_species_pests c  where c.pests_id=" + r2.getInt("id") + " and c.species_id=" + r1.getInt("id") + " ");
                    if (record != null) {
                        map.put(name, text);
                        continue;
                    } else {
                        String sql = "INSERT INTO at_species_pests (pests_id,species_id) VALUES " +
                                "(" + r2.getInt("id") + "," + r1.getInt("id") + ")";
                        Db.update(sql);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            renderJson(Co.fail("msg", "读取文件失败"));
            return;
        }
        tempXlsFile.delete();
        renderJson(Co.ok("msg", "导入完成").set("map", map));
    }
    public void search(){
        List<AtBotany> records=AtBotany.service().dao().findAll();
        renderJson(Co.ok("data",records));
    }

}
