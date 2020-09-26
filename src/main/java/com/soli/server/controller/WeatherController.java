package com.soli.server.controller;


import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.StringUtils;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import com.orbitz.okhttp3.OkHttpClient;
import com.orbitz.okhttp3.Request;
import com.orbitz.okhttp3.Response;
import com.soli.server.model.Directory;
import com.soli.server.utils.Co;
import com.soli.server.utils.IssueShpUtils;

import java.io.IOException;
import java.util.*;

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

    public void uploadShp() throws Exception {

        String url = getPara("url");

        if (StringUtils.isBlank(url)) {
            renderJson(Co.fail("msg", "数据路径不能为空"));
            return;
        }

        Kv kv = IssueShpUtils.uploadShp(url);
        Integer code = kv.getInt("code");
        if (code == 400) {
            renderJson(Co.ok("data", kv.get("errorMsg")));
        } else {
            renderJson(Co.ok("data", Ret.ok("msg", "成功")));
        }

    }


    @Clear
    public void list() {
        List<Directory> oneLevelLists = Directory.service().find(Directory.sql().andLevelEqualTo(1).andDelEqualTo("0").example());
        for (Directory directory : oneLevelLists) {
            List<Directory> twoLevelLists = Directory.service().find(Directory.sql().andLevelEqualTo(2).andDelEqualTo("0").andParentIdEqualTo(directory.getId()).example());
            for (Directory dir : twoLevelLists) {
                if (dir.getName().equals("作物价格")) {
                    Map<String,Object> map1=new HashMap<>();
                    Map<String,Object> map2=new HashMap<>();
                    map1.put("name","粮油米面");
                    map2.put("name","种子种苗");
                    List<Map<String,Object>> mapList=new ArrayList<>();
                    mapList.add(map1);
                    mapList.add(map2);
                    dir.put("twoLevelLists",mapList);
                }
            }
            directory.put("twoLevelLists", twoLevelLists);
        }
        renderJson(Co.ok("data", Ret.ok("oneLevelLists", oneLevelLists)));

    }
}

