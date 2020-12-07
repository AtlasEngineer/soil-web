package com.soli.server.controller;

import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.StringUtils;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import com.lambkit.web.render.XmlFileRender;
import com.soli.server.utils.Co;
import com.soli.server.utils.readShp;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
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

    public void getNdviSld(){
        String storename = getPara("name");
        System.out.println("create new sld...");
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("StyledLayerDescriptor");
        root.addAttribute("version", "1.0.0");
        root.addAttribute("xmlns", "http://www.opengis.net/sld");
//            root.add(new Namespace("sld", "http://www.opengis.net/sld"));
        root.add(new Namespace("ogc", "http://www.opengis.net/ogc"));
        root.add(new Namespace("xlink", "http://www.w3.org/1999/xlink"));
        root.add(new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
        root.add(new Namespace("schemaLocation", "http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));
//            root.add(new Namespace("schemaLocation", "http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));

        Element row_nl = root.addElement("NamedLayer");
        row_nl.addElement("Name").addText("d:" + storename);
        Element row_nl_us = row_nl.addElement("UserStyle");
        row_nl_us.addElement("Name").addText("default");
        Element row_nl_us_f = row_nl_us.addElement("FeatureTypeStyle");
        Element row_nl_us_r = row_nl_us_f.addElement("Rule");

        Element row_nl_us_r_p = row_nl_us_r.addElement("RasterSymbolizer");
        // <Opacity>1.0</Opacity>
        row_nl_us_r_p.addElement("Opacity").addText("1.0");
        Element row_nl_us_r_c_p = row_nl_us_r_p.addElement("ColorMap");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f20000").addAttribute("quantity", String.valueOf(-1)).addAttribute("label", "-1");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f57300").addAttribute("quantity", String.valueOf(-0.75)).addAttribute("label", "-0.75");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f4a100").addAttribute("quantity", String.valueOf(-0.5)).addAttribute("label", "-0.5");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f4c900").addAttribute("quantity", String.valueOf(-0.25)).addAttribute("label", "-0.25");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#daf400").addAttribute("quantity", String.valueOf(0)).addAttribute("label", "0");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9fe900").addAttribute("quantity", String.valueOf(0.25)).addAttribute("label", "0.25");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#71c000").addAttribute("quantity", String.valueOf(0.5)).addAttribute("label", "0.5");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#509b00").addAttribute("quantity", String.valueOf(0.75)).addAttribute("label", "0.75");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#317400").addAttribute("quantity", String.valueOf(1)).addAttribute("label", "1");

        String xmlinfo = formatXML(document, "GB2312");// document.asXML();//
        render(new XmlFileRender(xmlinfo));
    }

    /**
     * 格式化XML文档
     *
     * @param document xml文档
     * @param charset  字符串的编码
     * @return 格式化后XML字符串
     */
    @Clear
    private String formatXML(Document document, String charset) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw, format);
        try {
            xw.write(document);
            xw.flush();
            xw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
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



