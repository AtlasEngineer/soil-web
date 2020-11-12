package com.soli.server.controller;

import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.StringUtils;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import com.soli.server.utils.Co;
import com.soli.server.utils.readShp;

import java.util.Date;

@Clear(JwtTokenInterceptor.class)
public class IndexController extends LambkitController {


    @Clear
    public void index() {
        renderText("Hello,土壤");
    }


    public void text() throws Exception {
        Record record= Db.findFirst("select ST_Contains(geom, st_geometryfromtext('POINT(\" + lon + \" \" + lat + \")',4326)) = 't'");
        Double f=readShp.getAltitude(110.115,26.275,"F:\\admin\\soil-web\\src\\main\\webapp\\d\\tif-3283348a-8486-4955-9481-0922b3ec13f6\\tif-3283348a-8486-4955-9481-0922b3ec13f6.tif");
        renderJson(Co.ok("data",f));
    }

    public void searchDiseasesAdd() {
        String crop = getPara("crop");
        String type = getPara("type");
        String period = getPara("period");
        String name = getPara("name");
        String about = getPara("about");
        String feature= getPara("feature");
        String way= getPara("way");
        String condition= getPara("condition");
        String symptom= getPara("symptom");
        String grow= getPara("grow");
        String harm= getPara("harm");
        String methon = getPara("methon");
        if (StringUtils.isBlank(type)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "类型不能为空")));
            return;
        }
        if (StringUtils.isBlank(period)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "时期不能为空")));
            return;
        }
        Boolean success = false;

        Record record = new Record();
        record.set("type",type);
        record.set("period",period);
        record.set("del",0);
        record.set("create_time",new Date());
        if ("病害".equals(type)) {
            record.set("diseases_name",name);
            record.set("diseases_condition",condition);
            record.set("diseases_methon",methon);
            record.set("diseases_symptom",symptom);
            record.set("diseases_feature",feature);
            record.set("diseases_way",way);
            record.set("crop",crop);
            success = Db.save("tr_diseases_diseases", record);
        }
        if ("虫害".equals(type)) {
            record.set("pests_name",name);
            record.set("pests_features",feature);
            record.set("pests_grow",grow);
            record.set("pests_harm",harm);
            record.set("pests_methon",methon);
            record.set("pests_about",about);
            record.set("crop",crop);
            success = Db.save("tr_diseases_pests", record);
        }
        if ("草害".equals(type)) {
            record.set("grass_name",name);
            record.set("grass_about",about);
            record.set("grass_methon",methon);
            record.set("grass_harm",harm);
            record.set("grass_feature",feature);
            record.set("crop",crop);
            success = Db.save("tr_diseases_grass",record);
        }
        if (success){
            renderJson(Co.ok("data", Ret.ok("msg", "添加成功")));
            return;
        }else {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "添加失败")));
            return;
        }
    }



    public void searchDiseasesUpdate() {
        Integer id = getParaToInt("id");
        String crop = getPara("crop");
        String type = getPara("type");
        String period = getPara("period");
        String name = getPara("name");
        String about = getPara("about");
        String feature= getPara("feature");
        String way= getPara("way");
        String condition= getPara("condition");
        String symptom= getPara("symptom");
        String grow= getPara("grow");
        String harm= getPara("harm");
        String methon = getPara("methon");

        if (StringUtils.isBlank(type)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "类型不能为空")));
            return;
        }
        if (StringUtils.isBlank(period)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "时期不能为空")));
            return;
        }
        Boolean success = false;
        if (id == null){
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "id不能为空")));
            return;
        }

        if ("病害".equals(type)) {
            Record record = Db.findById("tr_diseases_diseases",id);
            record.set("diseases_name",name);
            record.set("diseases_condition",condition);
            record.set("diseases_methon",methon);
            record.set("diseases_symptom",symptom);
            record.set("diseases_feature",feature);
            record.set("diseases_way",way);
            record.set("crop",crop);
            record.set("type",type);
            record.set("period",period);
            success = Db.update("tr_diseases_diseases", record);
        }
        if ("虫害".equals(type)) {
            Record record = Db.findById("tr_diseases_pests",id);
            record.set("pests_name",name);
            record.set("pests_features",feature);
            record.set("pests_grow",grow);
            record.set("pests_harm",harm);
            record.set("pests_methon",methon);
            record.set("pests_about",about);
            record.set("crop",crop);
            record.set("type",type);
            record.set("period",period);
            success = Db.update("tr_diseases_pests", record);
        }
        if ("草害".equals(type)) {
            Record record = Db.findById("tr_diseases_grass",id);
            record.set("grass_name",name);
            record.set("grass_about",about);
            record.set("grass_methon",methon);
            record.set("grass_harm",harm);
            record.set("grass_feature",feature);
            record.set("crop",crop);
            record.set("type",type);
            record.set("period",period);
            success = Db.update("tr_diseases_grass",record);
        }
        if (success){
            renderJson(Co.ok("data", Ret.ok("msg", "修改成功")));
            return;
        }else {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "修改失败")));
            return;
        }
    }

}



