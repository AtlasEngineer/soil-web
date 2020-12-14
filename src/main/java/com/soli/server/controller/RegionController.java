package com.soli.server.controller;


import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.web.controller.LambkitController;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.bundle.BundleFeature;
import com.soli.server.utils.BundlerRender;
import com.soli.server.utils.Co;

import java.util.ArrayList;
import java.util.List;

@Clear
public class RegionController extends LambkitController {

    /**
     * @return void
     * @Author queer
     * @Description //TODO 获取省份列表
     * @Date 15:44 2019/12/20
     * @Param []
     **/
    public void getProvince() {
        List<Record> list = Db.find("select gid,pname as label,sdm as value  from tr_ch_province");
        for(Record record:list){
            List<Record> city_list = Db.find("select gid,cname as label,ssdm as value from tr_ch_city where pname = ? order by pyname ", record.getStr("label"));
            record.set("children",city_list);
            for (Record city:city_list){
                List<Record> country_list = Db.find("select gid,name as label,ssdm as value from tr_ch_county where pname = ? and cname = ? order by pyname ", record.getStr("label"), city.getStr("label"));
                city.set("children",country_list);
            }
        }

        renderJson(Co.ok("data", Ret.ok("data",list)));
    }

    /**
     * @return void
     * @Author queer
     * @Description //TODO 根据省份名称获取市列表
     * @Date 15:45 2019/12/20
     * @Param []
     **/
    public void getCity() {
        String pname = getPara("pname");
        if (StringUtils.isBlank(pname)) {
            renderJson(Co.fail("msg", "请选择省份"));
        }
        List<Record> list = Db.find("select cname from tr_ch_city where pname = ? order by pyname ", pname);
        renderJson(Co.ok("data", Ret.ok("data",list)));
    }

    /**
     * @return void
     * @Author queer
     * @Description //TODO 根据省份名称和市名称获取县列表
     * @Date 15:49 2019/12/20
     * @Param []
     **/
    public void getCounty() {
        String pname = getPara("pname");
        String cname = getPara("cname");
        if (StringUtils.isBlank(pname)) {
            renderJson(Co.fail("msg", "请选择省份"));
        }
        if (StringUtils.isBlank(cname)) {
            renderJson(Co.fail("msg", "请选择市区"));
        }
        List<Record> list = Db.find("select name from tr_ch_county where pname = ? and cname = ? order by pyname ", pname, cname);
        renderJson(Co.ok("data", list));
    }


    /**
     * @return void
     * @Author queer
     * @Description //TODO 模糊查询省市县列表
     * @Date 15:49 2019/12/20
     * @Param []
     **/
    public void getCountyByName() {

        String name = getPara("name");

        if (StringUtils.isBlank(name)) {
            renderJson(Co.ok("data", Ret.ok("errorMsg", "请输入名称")));
        }

        List<Record> provinceList = Db.find("select gid, pname as name  , 0 as type from tr_ch_province where pname like '%" + name + "%' ");
        List<Record> cityList = Db.find("select gid, cname as name  , 1 as type from tr_ch_city where cname like '%" + name + "%'");
        List<Record> countyList = Db.find("select gid, name , 2 as type from tr_ch_county where name like '%" + name + "%'");

        List<Record> recordList = new ArrayList<>();
        recordList.addAll(provinceList);
        recordList.addAll(cityList);
        recordList.addAll(countyList);

        List<Record> result=new ArrayList<>();

        for (Record record : recordList) {
            if (!record.getStr("name").equals("市辖区")) {
                result.add(record);
            }
        }

        renderJson(Co.ok("data",Ret.ok("data",result)));
    }



    /**
     * @return void
     * @Author queer
     * @Description //TODO 模糊查询省市县最大最小坐标
     * @Date 15:49 2019/12/20
     * @Param []
     **/
    public void getgemoById() {

        Integer id=getParaToInt("id");
        Integer type=getParaToInt("type");

        if (id==null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请输入id")));
        }
        if (type==null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请输入类型")));
        }
        Record list =null;
        if(type==0){
             list = Db.findFirst("SELECT st_xmin(geom),st_ymin(geom),st_xmax(geom),st_ymax(geom) FROM tr_ch_province  where \"gid\"=?", id);
        }
        if(type==1){
            list = Db.findFirst("SELECT st_xmin(geom),st_ymin(geom),st_xmax(geom),st_ymax(geom) FROM tr_ch_city  where \"gid\"=?", id);
        }
        if(type==2){
            list = Db.findFirst("SELECT st_xmin(geom),st_ymin(geom),st_xmax(geom),st_ymax(geom) FROM tr_ch_county  where \"gid\"=?", id);
        }

        renderJson(Co.ok("data",Ret.ok("data",list)));
    }


    /**
     * @return void
     * @Author queer
     * @Description //TODO 计算多边形的面积
     * @Date 15:49 2019/12/20
     * @Param []
     **/
    public void CalculationArea(){

        String latlons=getPara("latlons");

        if (StringUtils.isBlank(latlons)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请输入经纬度的集合")));
        }
        Record first = Db.findFirst("select st_area(st_transform(ST_SetSRID(ST_GeomFromText('POLYGON (("+latlons+"))'),4326),4527))");

        renderJson(Co.ok("data",Ret.ok("data",first)));

    }
}
