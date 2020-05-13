package com.atlas.server.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atlas.lambkit.start.BaiDuConfig;
import com.atlas.server.model.*;
import com.atlas.server.model.sql.CatalogueCriteria;
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
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.upload.UploadFile;
import com.lambkit.Lambkit;
import com.lambkit.common.util.DateTimeUtils;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.core.cache.impl.RedisCacheImpl;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.module.upms.rpc.model.UpmsUserRole;
import com.lambkit.plugin.jwt.IJwtAble;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.RequestManager;
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
        List<Record> list = Db.find("select * from at_botany_type");
        renderJson(Co.ok("data", list));
    }


    public void listById() {
        Integer id = getParaToInt("id");
        String name=getPara("name");
        if (id == null) {
            renderJson(Co.fail("msg", "id不能为空"));
            return;
        }
        Integer pageNum = getParaToInt("pageNum", 1);
        Integer pageSize = getParaToInt("pageSize", 10);
        CatalogueCriteria sql=new CatalogueCriteria();

        if(StringUtils.isNotBlank(name)){
            sql.andNameLike("%"+name+"%");
        }
            sql.andBotanyTypeEqualTo(id);
            sql.andImageIsNotNull();
        Page<Catalogue> page = Catalogue.service().dao().paginate(pageNum, pageSize, sql.example());
        renderJson(Co.ok("data", page));
    }



    public void searchCatalogueById() {
        String id = getPara("id");
        if (StringUtils.isBlank(id)) {
            renderJson(Co.fail("msg", "id不能为空"));
            return;
        }
        Record record = Db.findFirst("SELECT * from catalogue c where c.id='" + id + "'");
        List<Record> list = Db.find("SELECT * from catalogue_sample c where c.catalogue_id='" + record.getStr("id") + "' LIMIT 5;");
        record.set("list", list);
        String token = getPara("token");
        if (!"null".equals(token)&&StringUtils.isNotBlank(token)) {
            JwtConfig config = Lambkit.config(JwtConfig.class);
            String tokenPrefix = config.getTokenPrefix();
            String authToken = token.substring(tokenPrefix.length());
            String username = JwtKit.getJwtUser(authToken);
            System.out.println("username : " + username);
            UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());

            Integer status = Db.queryInt("select status from news_collection c where c.user_id=" + upmsUser.getUserId() + " and c.news_id='" + id + "'"); //0收藏 1取消收藏
            if (null == status) {
                record.set("is_Collection", false);
            } else {
                if (status > 0) {
                    record.set("is_Collection", false);
                } else {
                    record.set("is_Collection", true);
                }
            }
        }else {
            record.set("is_Collection", false);
        }
        renderJson(Co.ok("data", record));
    }

    //测试添加病虫害列表
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
        List<String> you = new ArrayList<>();
        List<String> wu = new ArrayList<>();
        try {
            List<Object> list = EasyExcelFactory.read(new FileInputStream(tempXlsFile.getPath()), sheet);
            for (Object obj : list) {
                if (obj != null) {
                    ArrayList<String> arrayList = (ArrayList<String>) obj;
                    name = arrayList.get(0);
                    if ("null".equals(name) || StringUtils.isBlank(name)) {
                        continue;
                    }
                    InsectPests insectPests=InsectPests.service().dao().findFirst(InsectPests.sql().andNameEqualTo(name).example());
                    if (insectPests == null) {
                        wu.add(name);
//                        String sql = "INSERT INTO at_insect_species (name,time) VALUES " +
//                                "('" + name + "','" + currentTime + "')";
//                        Db.update(sql);
                    } else {
                        you.add(name);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            renderJson(Co.fail("msg", "读取文件失败"));
            return;
        }
        tempXlsFile.delete();
        renderJson(Co.ok("msg", "导入完成").set("you", you).set("wu",wu));
    }
    //导入病虫害列表
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



    public void text123(){
        List<Catalogue> records=Catalogue.service().dao().findAll();

        for (Catalogue catalogue:records){
            Catalogue cata=Catalogue.service().dao().findById(catalogue.getCId());
            CatalogueSample catalogueSample=CatalogueSample.service().dao().findFirst(CatalogueSample.sql().andCatalogueIdEqualTo(cata.getId()).example());
            if(catalogueSample!=null){
                cata.setImage(catalogueSample.getUrl());
                cata.update();
            }

        }
          renderJson(Co.ok());
    }

    //百度的连接信息
    private static BaiDuConfig config = Lambkit.config(BaiDuConfig.class);
    private static AipImageSearch client = new AipImageSearch(config.getAPP_ID(), config.getAPI_KEY(), config.getSECRET_KEY());


     //删除样本
    public void test(){
        List<String> cata=new ArrayList<>();

        List<Catalogue> catalogues=Catalogue.service().dao().find("select name,count(*) from catalogue  group by name having count(name)>1");

        for (Catalogue catalogue:catalogues){
            Catalogue catalogueList=Catalogue.service().dao().findFirst(Catalogue.sql().andBotanyTypeEqualTo(5).andNameEqualTo(catalogue.getName()).example());
            catalogueList.delete();
        }

//        List<CatalogueSample> catalogueSamples=CatalogueSample.service().dao().find("SELECT * FROM catalogue_sample WHERE DATEDIFF(time,NOW())=0");
//        // 传入可选参数调用接口
//        HashMap<String, String> options = new HashMap<String, String>();
//
//        for (CatalogueSample catalogueSample:catalogueSamples){
//            if(StringUtils.isNotBlank(catalogueSample.getContSign())){
//                JSONObject res = client.similarDeleteBySign(catalogueSample.getContSign(),options);
//                System.out.println(res.toString(2));
//            }
//        }
        renderJson(Co.ok());
    }


}
