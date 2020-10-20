package com.soli.server.controller;

import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import com.soli.server.utils.Co;
import com.soli.server.utils.readShp;

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

}



