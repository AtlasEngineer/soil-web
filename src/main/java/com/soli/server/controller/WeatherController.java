package com.soli.server.controller;


import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.StringUtils;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.auth.AuthManager;
import com.lambkit.plugin.auth.IUser;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.RequestManager;
import com.lambkit.web.controller.LambkitController;
import com.orbitz.okhttp3.OkHttpClient;
import com.orbitz.okhttp3.Request;
import com.orbitz.okhttp3.Response;
import com.soli.server.model.*;
import com.soli.server.utils.Co;
import com.soli.server.utils.IssueShpUtils;
import org.opengis.geometry.Geometry;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.soli.server.utils.CodePageUtils.getUserEntity;

@Clear(JwtTokenInterceptor.class)
public class WeatherController extends LambkitController {


    public void station() {
        Integer time = getParaToInt("time");
        Integer type = getParaToInt("type");
        Ret res = Ret.ok();
        if (time != null) {
            StringBuilder yj = new StringBuilder();
            yj.append("select * from qx_weather where now() - time < '");
            yj.append(time).append(" hours' ");
            res.set("yj", Db.find(yj.toString()));

            StringBuilder yjtype = new StringBuilder();
            yjtype.append("SELECT signallevel || '-' || codename as wtype, count(*) as num FROM qx_weather where now() - time < '");
            yjtype.append(time).append(" hours' GROUP BY wtype");
            List<Record> yjtypeList = Db.find(yjtype.toString());
            String[] types = {"大风", "寒潮", "干旱", "暴雨", "森林火险", "雷电", "雷雨大风", "高温", "冰雹", "大雾",
                    "干热风", "沙尘暴", "暴雪", "道路结冰", "霾", "台风"};
            List<String> typesList = Arrays.asList(types);
            List<Record> list = new ArrayList<>();
            for (int i = 0; i < yjtypeList.size(); i++) {
                String colo = "";
                Integer num = 0;
                Record record = yjtypeList.get(i);
                String wtype = record.getStr("wtype");
                String[] split = wtype.split("-");
                if (!typesList.contains(split[1])) {
                    Integer rNum = record.getInt("num");
                    num += rNum;
                    colo = split[0];
                    StringBuilder sb = new StringBuilder();
                    sb.append(colo).append("-").append("其他");
                    record.set("wtype", sb.toString()).set("num", num);
                    list.add(record);
                    yjtypeList.remove(i);
                }
            }
            //存放最后数据
            List<Record> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Record record1 = list.get(i);
                String str1 = record1.getStr("wtype");
                String[] split1 = str1.split("-");
                if (list1.size() == 0) {
                    //直接添加
                    list1.add(record1);
                }
                for (int j = 0; j < list1.size(); j++) {
                    Record record2 = list1.get(j);
                    String str2 = record2.getStr("wtype");
                    String[] split2 = str2.split("-");
                    if (split1[0].equals(split2[0])) {
                        Integer int1 = record1.getInt("num");
                        Integer int2 = record2.getInt("num");
                        record2.set("num", int1 + int2);
                    } else {
                        //直接添加
                        list1.add(record1);
                    }
                }
            }
            for (Record record : list1) {
                yjtypeList.add(record);
            }
            res.set("yjtype", yjtypeList);
        }
        if (type != null) {
            StringBuilder img = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            img.append("select * from qx_img where img_type='");
            sb.append("select *, ");
            switch (type) {
                case 0:
                    img.append("yb_tem");
                    //sb.append("tem as value");
                    break;
                case 1:
                    img.append("yb_rain");
                    //sb.append("pre_1h as value");
                    break;
                case 2:
                    img.append("tem");
                    sb.append("tem as value, qw_color as color");
                    break;
                case 3:
                    img.append("pre");
                    sb.append("prs as value, qy_color as color");
                    break;
                case 4:
                    img.append("humidity");
                    sb.append("rhu as value, js_color as color");
                    break;
                case 5:
                    sb.append("win_s_inst_max as value");
                    break;
                case 6:
                    img.append("rain");
                    sb.append("pre_1h as value, sd_color as color");
                    break;
            }
            img.append("' order by dttime desc");
            sb.append(" from qx_station ");
            if (type > 1) {
                res.set("station", Db.find(sb.toString() + " where time = (select max(time) from qx_station)"));
                res.set("stations", Db.find(sb.toString() + " where status='1' order by time desc"));
            }
            res.set("img", Db.findFirst(img.toString()));
        }
        renderJson(Co.ok("data", res));
    }


    //获取天气
    public void getWeather() {
        String lon = getPara("lon");
        String lat = getPara("lat");

        if (StringUtils.isBlank(lon)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "lon不能为空")));
            return;
        }

        if (StringUtils.isBlank(lat)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "lat不能为空")));
            return;
        }

        Record pg = Db.findFirst("select pname,cname from tr_city where ST_Contains(geom, st_geometryfromtext('POINT(" + lon + " " + lat + ")',4326))");
        System.out.println(pg); //{pname:北京市, cname:北京}


        if (pg == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "未查询到相关信息")));
            return;
        }

        String cname = pg.getStr("cname");

        String url_ptah = "https://tianqiapi.com/api?version=v1&appid=66734781&appsecret=xX1MZFk2&city=" + cname + "";

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url_ptah)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            renderJson(Co.ok("data", Ret.ok("data", JSON.parseObject(response.body().string()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Clear
    public void list() {
        List<Directory> oneLevelLists = Directory.service().find(Directory.sql().andLevelEqualTo(1).andDelEqualTo("0").example());
        for (Directory directory : oneLevelLists) {
            List<Data> oneLevelData=Data.service().dao().find(Data.sql().andDirectoryidEqualTo(directory.getId()).andDelEqualTo(0).example());
            List<Directory> twoLevelLists = Directory.service().find(Directory.sql().andLevelEqualTo(2).andDelEqualTo("0").andParentIdEqualTo(directory.getId()).example());
            for (Directory dir : twoLevelLists) {
                List<Data> twoLevelData=Data.service().dao().find(Data.sql().andDirectoryidEqualTo(dir.getId()).andDelEqualTo(0).example());
                dir.put("oneLevelData", twoLevelData);
            }
            List list = new ArrayList() ;
            Iterator it1 = twoLevelLists.iterator() ;
            while(it1.hasNext()) {
                list.add(it1.next()) ;
            }
            Iterator it2 = oneLevelData.iterator() ;
            while(it2.hasNext()) {
                list.add(it2.next()) ;
            }
            directory.put("oneLevelData",list);
        }
        renderJson(Co.ok("data", Ret.ok("oneLevelLists", oneLevelLists)));

    }

    /**
     * 添加田间操作记录
     */
    public void addOperationRecord() {

        String name = getPara("name");
        String type = getPara("type");
        String img = getPara("img");
        String content = getPara("content");

        if (StringUtils.isBlank(name)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "名称不能为空")));
            return;
        }
        if (StringUtils.isBlank(type)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "类型不能为空")));
            return;
        }
        if (StringUtils.isBlank(img)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "图片不能为空")));
            return;
        }
        if (StringUtils.isBlank(content)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "内容不能为空")));
            return;
        }
        String serverSessionId = this.getRequest().getHeader("Authorization");
        String code = UpmsManager.me().getCache().getSession(serverSessionId);

        String username = null;
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data", Ret.fail("errorMsg", "请重新登陆")));
            return;
        }
        System.out.println("name : " + username);
        UpmsUser upmsUser = getUserEntity();
        if (upmsUser == null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "获取用户信息错误")));
            return;
        }

        String imgs[] = img.split(",");

        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setName(name);
        operationRecord.setTime(new Date());
        operationRecord.setContent(content);
        operationRecord.setDel(0);
        operationRecord.setType(type);
        operationRecord.setUserId(upmsUser.getUserId().intValue());
        operationRecord.setUsername(upmsUser.getRealname());
        boolean result = operationRecord.save();
        if (result) {
            for (String s : imgs) {
                OperationRecordImg operationRecordImg = new OperationRecordImg();
                operationRecordImg.setUrl(s);
                operationRecordImg.setOperationId(operationRecord.getId().intValue());
                operationRecordImg.save();
            }
            renderJson(Co.ok("data", Ret.ok()));
            return;
        } else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "添加失败")));
            return;
        }

    }


    /**
     * 修改田间操作记录
     */
    public void updateOperationRecord() {

        String name = getPara("name");
        String type = getPara("type");
        String img = getPara("img");
        String content = getPara("content");
        Integer id = getInt("id");

        if (id == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "id不能为空")));
            return;
        }
        List<OperationRecordImg> operationRecordImgs = OperationRecordImg.service().dao().find(OperationRecordImg.sql().andOperationIdEqualTo(id).example());
        for (OperationRecordImg operationRecordImg : operationRecordImgs) {
            operationRecordImg.delete();

        }
        if (StringUtils.isBlank(name)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "名称不能为空")));
            return;
        }
        if (StringUtils.isBlank(type)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "类型不能为空")));
            return;
        }
        if (StringUtils.isBlank(img)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "图片不能为空")));
            return;
        }
        if (StringUtils.isBlank(content)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "内容不能为空")));
            return;
        }
        String serverSessionId = this.getRequest().getHeader("Authorization");
        String code = UpmsManager.me().getCache().getSession(serverSessionId);

        String username = null;
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data", Ret.fail("errorMsg", "请重新登陆")));
            return;
        }
        System.out.println("name : " + username);
        UpmsUser upmsUser = getUserEntity();
        if (upmsUser == null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "获取用户信息错误")));
            return;
        }

        String imgs[] = img.split(",");

        OperationRecord operationRecord = OperationRecord.service().dao().findById(id);
        operationRecord.setName(name);
        operationRecord.setTime(new Date());
        operationRecord.setContent(content);
        operationRecord.setDel(0);
        operationRecord.setType(type);
        operationRecord.setUserId(upmsUser.getUserId().intValue());
        operationRecord.setUsername(upmsUser.getRealname());
        boolean result = operationRecord.update();
        if (result) {
            for (String s : imgs) {
                OperationRecordImg operationRecordImg = new OperationRecordImg();
                operationRecordImg.setUrl(s);
                operationRecordImg.setOperationId(operationRecord.getId().intValue());
                operationRecordImg.save();
            }
            renderJson(Co.ok("data", Ret.ok()));
            return;
        } else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "添加失败")));
            return;
        }

    }




    /**
     * 添加地块
     */
    public void addDK() throws ParseException {

        String name = getPara("dk_name");//地块名称
        String type = getPara("type");//作物
        String address = getPara("dk_address");//地址
        String url = getPara("dk_url");//图片
        String dk_begin_time = getPara("dk_begin_time");//时间
        String dk_end_time = getPara("dk_end_time");//时间
        String farmland = getPara("dk_farmland");//田亩
        String perimeter = getPara("dk_perimeter");//周长
        String farm = getPara("dk_farm");//农场
        String altitude = getPara("dk_altitude");//海拔
        String slope = getPara("dk_slope");//坡度
        String growers = getPara("dk_growers");//种植户
        String phone = getPara("dk_phone");//联系方式
        String person = getPara("dk_person");//负责人
        String fertilizer = getPara("dk_fertilizer");//施肥建议
        String dk_type = getPara("dk_type");//土地类型
        String density = getPara("dk_density");//密度
        String irrigation=getPara("dk_irrigation");//灌溉方式
        String latlons=getPara("latlons"); //地块经纬度
        String geom_type=getPara("name");//面的类型


        String serverSessionId = this.getRequest().getHeader("Authorization");
        String code = UpmsManager.me().getCache().getSession(serverSessionId);

        String username = null;
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data", Ret.fail("errorMsg", "请重新登陆")));
            return;
        }
        System.out.println("name : " + username);
        UpmsUser upmsUser = getUserEntity();
        if (upmsUser == null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "获取用户信息错误")));
            return;
        }

        if (StringUtils.isBlank(name)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "地块名称不能为空")));
            return;
        }
        if (StringUtils.isBlank(latlons)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "latlons不能为空")));
            return;
        }
        if (StringUtils.isBlank(type)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "作物类型不能为空")));
            return;
        }
        if (StringUtils.isBlank(address)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "地址不能为空")));
            return;
        }
        if (StringUtils.isBlank(url)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "url不能为空")));
            return;
        }

        if (StringUtils.isBlank(latlons)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "latlons不能为空")));
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Integer record=Db.queryInt("SELECT max(gid) FROM tr_tiankuai");

        Date date=new Date();
        System.out.println(latlons);
        int num=Db.update("insert into \"tr_tiankuai\"(id,\"type\", \"dk_name\", \"name\", \"dk_address\", \"dk_url\", \"dk_begin_time\", \"dk_end_time\", " +
                "\"dk_farmland\", \"dk_perimeter\", \"dk_farm\", \"dk_altitude\", \"dk_slope\", \"dk_growers\", \"dk_phone\", \"dk_person\", \"dk_fertilizer\", \"dk_user_id\", \"dk_username\", \"dk_time\", \"del\", \"dk_type\", \"dk_density\", \"dk_irrigation\", \"geom\") " +
                "values(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'SRID=4326;MULTIPOLYGON((("+latlons+")))')",record,type,name,geom_type,address,url,sdf.parse(dk_begin_time),sdf.parse(dk_end_time),farmland,perimeter,farm,altitude,slope,growers,phone,person,fertilizer,upmsUser.getUserId(),upmsUser.getRealname(),date,0,dk_type,density,irrigation);
        if (num>0) {
            renderJson(Co.ok("data", Ret.ok()));
            return;
        } else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "添加失败")));
            return;
        }

    }


    /**
     * 编辑地块
     */
    public void updateDK() throws ParseException {

        String name = getPara("dk_name");//地块名称
        String type = getPara("type");//作物
        String address = getPara("dk_address");//地址
        String url = getPara("dk_url");//图片
        String dk_begin_time = getPara("dk_begin_time");//时间
        String dk_end_time = getPara("dk_end_time");//时间
        String farmland = getPara("dk_farmland");//田亩
        String perimeter = getPara("dk_perimeter");//周长
        String farm = getPara("dk_farm");//农场
        String altitude = getPara("dk_altitude");//海拔
        String slope = getPara("dk_slope");//坡度
        String growers = getPara("dk_growers");//种植户
        String phone = getPara("dk_phone");//联系方式
        String person = getPara("dk_person");//负责人
        String fertilizer = getPara("dk_fertilizer");//施肥建议
        String dk_type = getPara("dk_type");//土地类型
        String density = getPara("dk_density");//密度
        String irrigation=getPara("dk_irrigation");//灌溉方式
        String latlons=getPara("latlons"); //地块经纬度
        Integer id=getParaToInt("id");//地块id
        String geom_type=getPara("name");//面的类型

        if(id==null){
            this.renderJson(Co.ok("data", Ret.fail("errorMsg", "id为空")));
            return;
        }


        String serverSessionId = this.getRequest().getHeader("Authorization");
        String code = UpmsManager.me().getCache().getSession(serverSessionId);

        String username = null;
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data", Ret.fail("errorMsg", "请重新登陆")));
            return;
        }
        System.out.println("name : " + username);
        UpmsUser upmsUser = getUserEntity();
        if (upmsUser == null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "获取用户信息错误")));
            return;
        }

        if (StringUtils.isBlank(name)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "地块名称不能为空")));
            return;
        }
        if (StringUtils.isBlank(type)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "作物类型不能为空")));
            return;
        }
        if (StringUtils.isBlank(address)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "地址不能为空")));
            return;
        }
        if (StringUtils.isBlank(url)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "url不能为空")));
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Tiankuai tiankuai=Tiankuai.service().dao().findById(id);
        if(tiankuai==null){
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "未查到")));
            return;
        }
        tiankuai.setType(type);
        tiankuai.setName(geom_type);
        tiankuai.setDkName(name);
        tiankuai.setDkAddress(address);
        tiankuai.setDkUrl(url);
        tiankuai.setDkBeginTime(sdf.parse(dk_end_time));
        tiankuai.setDkEndTime(sdf.parse(dk_begin_time));
        tiankuai.setDkFarmland(farmland);
        tiankuai.setDkPerimeter(perimeter);
        tiankuai.setDkFarm(farm);
        tiankuai.setDkAltitude(altitude);
        tiankuai.setDkSlope(slope);
        tiankuai.setDkGrowers(growers);
        tiankuai.setDkPhone(phone);
        tiankuai.setDkPerson(person);
        tiankuai.setDkFertilizer(fertilizer);
        tiankuai.setDkUserId(upmsUser.getUserId().intValue());
        tiankuai.setDkUsername(upmsUser.getRealname());
        tiankuai.setDkTime(new Date());
        tiankuai.setDel(0);
        tiankuai.setDkType(dk_type);
        tiankuai.setDkDensity(density);
        tiankuai.setDkIrrigation(irrigation);
        boolean result=tiankuai.update();
        if (result) {
            renderJson(Co.ok("data", Ret.ok()));
            return;
        } else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "修改失败")));
            return;
        }

    }



}

