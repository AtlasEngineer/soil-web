/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soli.server.service.impl;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.soli.server.model.DataEach;
import com.soli.server.service.TiankuaiService;
import com.soli.server.model.Tiankuai;
import com.soli.server.utils.readShp;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.opengis.filter.Filter;

import java.io.IOException;
import java.util.*;

import static com.soli.server.utils.ReadTiffUtils.getTiffXY;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @since 1.0
 */
public class TiankuaiServiceImpl extends LambkitModelServiceImpl<Tiankuai> implements TiankuaiService {

    private Tiankuai DAO = null;

    @Override
	public Tiankuai dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(Tiankuai.class);
        }
        return DAO;
    }

    @Override
    public Ret all(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "id不能为空");
        }
        Record tiankuai = Db.findFirst("select geom  from tr_tiankuai where id = ?", id);

        if (tiankuai == null) {
            return Ret.fail("errorMsg", "未查到地块");
        }

        List<Record> list = Db.find("select *,st_x(geom) as lon,st_y(geom) as lat from tr_camera WHERE ST_Contains((SELECT geom FROM tr_tiankuai WHERE id = " + id + "),geom)");

        return Ret.ok("data", list);
    }

    @Override
    public Ret searchDiseases(String type, String period,String crop) {

        if (StringUtils.isBlank(type)) {
            return Ret.fail("errorMsg", "类型不能为空");
        }
        if (StringUtils.isBlank(period)) {
            return Ret.fail("errorMsg", "时期不能为空");
        }
        String select = "";
        if ("病害".equals(type)) {
            select = "SELECT * from tr_diseases_diseases ";
        }
        if ("虫害".equals(type)) {
            select = "SELECT * from tr_diseases_pests ";
        }
        if ("草害".equals(type)) {
            select = "SELECT * from tr_diseases_grass ";
        }

        List<Record> page = Db.find( select+" where del=0 and type=? and period =? and crop = ? ORDER BY create_time desc",type,period,crop);

        return Ret.ok("data",page);
    }

    @Override
    public Ret searchDiseasesDel(String type, Integer id) {
        Record record = new Record() ;
        Boolean success = false;
        if ("病害".equals(type)) {
           record = Db.findById("tr_diseases_diseases",id);
            record.set("del", 1);
            success = Db.update("tr_diseases_diseases",record);
        }
        if ("虫害".equals(type)) {
            record = Db.findById("tr_diseases_pests",id);
            record.set("del", 1);
            success = Db.update("tr_diseases_pests",record);
        }
        if ("草害".equals(type)) {
            record = Db.findById("tr_diseases_grass",id);
            record.set("del", 1);
            success = Db.update("tr_diseases_grass",record);
        }
        if (success){
            return Ret.ok("msg","删除成功");
        }else {
            return Ret.fail("errorMsg", "删除失败");
        }
    }


    @Override
    public Ret compoundQuery(Integer countyId, Integer type, String time[]) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        WKTReader reader = new WKTReader(geometryFactory);
        List<Record> resultList = new ArrayList<>();
        try {
            Record first = Db.findFirst("SELECT st_astext(geom) as geom FROM tr_ch_county WHERE gid = " + countyId);
            String geometryStr = first.getStr("geom");
            Geometry countyGeom = reader.read(geometryStr);
            /* shp  文件类型	  */
            if (type == 0) {
                List<Record> records = Db.find("SELECT * FROM tr_data_each WHERE type = 0 and time between '"+time[0]+"' and '"+time[1]+"' ");
                for (Record record : records) {
                    /* geometry 与  shp 比较 */
                    String url = record.getStr("url");
                    if (url.startsWith("d:")) {
                        /* 截取 d: 后面的 文件名*/
                        url = url.substring(2, url.length());
                        String rootPath = PathKit.getWebRootPath() + "/d/";
                        SimpleFeatureSource shpStore = readShp.getShpStore(rootPath + url + "/" + url + ".shp");
                        /* 将tif 包围盒转成 一个面进行比较 */
                        boolean flag = queryFieldBy(first, shpStore);
                        if (flag) {
                            resultList.add(record);
                        }
                    }
                }
            }
            if (type == 1) {
                List<Record> records = Db.find("SELECT * FROM tr_data_each WHERE type in (1,5) and time between '"+time[0]+"' and '"+time[1]+"' ");
                for (Record record : records) {
                    String url = record.getStr("url");
                    if (url.startsWith("d:")) {
                        /* 截取 d: 后面的 文件名*/
                        url = url.substring(2, url.length());
                        String rootPath = PathKit.getWebRootPath() + "/d/";

                        Kv tiffXY = getTiffXY(rootPath + url + "/" + url + ".tif");
                        double minX = tiffXY.getNumber("minX").doubleValue();
                        double minY = tiffXY.getNumber("minY").doubleValue();
                        double maxX = tiffXY.getNumber("maxX").doubleValue();
                        double maxY = tiffXY.getNumber("maxY").doubleValue();
                        /* 将tif 包围盒转成 一个面进行比较 */
                        String tifGeomStr = "POLYGON((" + minX + " " + maxY + "," + maxX + " " + maxY + "," + minX + " " + minY + "," + minX + " " + maxY + "," + minX + " " + maxY + "))";
                        Geometry tifGeom = reader.read(tifGeomStr);
                        /*	如果相交	  */
                        if (countyGeom.intersects(tifGeom)) {
                            resultList.add(record);
                        }
                    }
                }
                return Ret.ok("data", resultList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Ret.ok("data", resultList);
    }

    @Override
    public Ret compoundQueryBySpot(Double longitude, Double latitude) throws IOException, CQLException, ParseException {
        List<DataEach> dataEaches = new ArrayList<>();
        List<DataEach> dataEachList=DataEach.service().dao().findAll();
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        WKTReader reader = new WKTReader(geometryFactory);
        for (DataEach dataEach:dataEachList) {
            String url = dataEach.getStr("url");
            if (dataEach.getType() == 0) {
                if (url.startsWith("d:")) {
                    /* 截取 d: 后面的 文件名*/
                    url = url.substring(2, url.length());
                    String rootPath = PathKit.getWebRootPath() + "/d/";
                    SimpleFeatureSource shpStore = readShp.getShpStore(rootPath + url + "/" + url + ".shp");
                    /* 将tif 包围盒转成 一个面进行比较 */
                    boolean flag = queryField(longitude, latitude, shpStore);
                    if (flag) {
                        dataEaches.add(dataEach);
                    }
                }
            }
            if (dataEach.getType() == 1 || dataEach.getType() == 5) {
                if (url.startsWith("d:")) {
                    /* 截取 d: 后面的 文件名*/
                    url = url.substring(2, url.length());
                    String rootPath = PathKit.getWebRootPath() + "/d/";
                    /* 将tif 包围盒转成 一个面进行比较 */
                    Kv tiffXY = getTiffXY(rootPath + url + "/" + url + ".tif");
                    double minX = tiffXY.getNumber("minX").doubleValue();
                    double minY = tiffXY.getNumber("minY").doubleValue();
                    double maxX = tiffXY.getNumber("maxX").doubleValue();
                    double maxY = tiffXY.getNumber("maxY").doubleValue();
                    /* 将tif 包围盒转成 一个面进行比较 */
                    String tifGeomStr = "POLYGON((" + minX + " " + maxY + "," + maxX + " " + maxY + "," + minX + " " + minY + "," + minX + " " + maxY + "," + minX + " " + maxY + "))";
                    Geometry tifGeom = reader.read(tifGeomStr);
                    Record record = Db.findFirst("select ST_Contains(st_geometryfromtext('" + tifGeom + "',4326), st_geometryfromtext('POINT(" + longitude + " " + latitude + ")',4326)) as num");
					if (record.getBoolean("num")) {
                        dataEaches.add(dataEach);
                    }
                }
            }
			if (dataEach.getType() == 3 || dataEach.getType() == 4 || dataEach.getType() == 5) {
				String tifGeomStr = "POLYGON((" + Double.valueOf(dataEach.getStr("topLeftLongitude")) + " " + Double.valueOf(dataEach.getStr("topLeftLatitude")) + "," + Double.valueOf(dataEach.getStr("topRightLongitude")) + " " + Double.valueOf(dataEach.getStr("topRightLatitude")) + "," + Double.valueOf(dataEach.getStr("bottomRightLongitude")) + " " + Double.valueOf(dataEach.getStr("bottomRightLatitude")) + "," + Double.valueOf(dataEach.getStr("bottomLeftLongitude")) + " " + Double.valueOf(dataEach.getStr("bottomLeftLatitude")) + "," + Double.valueOf(dataEach.getStr("topLeftLongitude")) + " " + Double.valueOf(dataEach.getStr("topLeftLatitude")) + "))";
				Geometry tifGeom = reader.read(tifGeomStr);
                Record record = Db.findFirst("select ST_Contains(st_geometryfromtext('" + tifGeom + "',4326), st_geometryfromtext('POINT(" + longitude + " " + latitude + ")',4326)) as num");
                if (record.getBoolean("num")) {
                    dataEaches.add(dataEach);
                }
            }
        }
        return Ret.ok("data", dataEaches);
    }

	@Override
	public Ret compoundQueryByNoodles(String latlons) throws IOException, CQLException, ParseException {
		List<DataEach> dataEaches = new ArrayList<>();
		List<DataEach> dataEachList=DataEach.service().dao().findAll();
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
		WKTReader reader = new WKTReader(geometryFactory);
		for (DataEach dataEach:dataEachList) {
			String url = dataEach.getStr("url");
			if (dataEach.getType() == 0) {
				if (url.startsWith("d:")) {
					/* 截取 d: 后面的 文件名*/
					url = url.substring(2, url.length());
					String rootPath = PathKit.getWebRootPath() + "/d/";
					SimpleFeatureSource shpStore = readShp.getShpStore(rootPath + url + "/" + url + ".shp");
					/* 将tif 包围盒转成 一个面进行比较 */
                    Record record=Db.findFirst("select st_astext(st_geometryfromtext('POLYGON (("+latlons+"))',4326)) as geom");
					boolean flag = queryField(record,shpStore);
					if (flag) {
						dataEaches.add(dataEach);
					}
				}
			}
			if (dataEach.getType() == 1) {
				if (url.startsWith("d:")) {
					/* 截取 d: 后面的 文件名*/
					url = url.substring(2, url.length());
					String rootPath = PathKit.getWebRootPath() + "/d/";
					/* 将tif 包围盒转成 一个面进行比较 */
					Kv tiffXY = getTiffXY(rootPath + url + "/" + url + ".tif");
					double minX = tiffXY.getNumber("minX").doubleValue();
					double minY = tiffXY.getNumber("minY").doubleValue();
					double maxX = tiffXY.getNumber("maxX").doubleValue();
					double maxY = tiffXY.getNumber("maxY").doubleValue();
					/* 将tif 包围盒转成 一个面进行比较 */
					String tifGeomStr = "POLYGON((" + minX + " " + maxY + "," + maxX + " " + maxY + "," + minX + " " + minY + "," + minX + " " + maxY + "," + minX + " " + maxY + "))";
					Geometry tifGeom = reader.read(tifGeomStr);
					Record record = Db.findFirst("select ST_Contains(st_geometryfromtext('" + tifGeom + "',4326),st_geometryfromtext('POLYGON (("+latlons+"))',4326)) as num");
					if (record.getBoolean("num")) {
						dataEaches.add(dataEach);
					}
				}
			}
			if (dataEach.getType() == 3 || dataEach.getType() == 4 || dataEach.getType() == 5) {
				String tifGeomStr = "POLYGON((" + Double.valueOf(dataEach.getStr("topLeftLongitude")) + " " + Double.valueOf(dataEach.getStr("topLeftLatitude")) + "," + Double.valueOf(dataEach.getStr("topRightLongitude")) + " " + Double.valueOf(dataEach.getStr("topRightLatitude")) + "," + Double.valueOf(dataEach.getStr("bottomRightLongitude")) + " " + Double.valueOf(dataEach.getStr("bottomRightLatitude")) + "," + Double.valueOf(dataEach.getStr("bottomLeftLongitude")) + " " + Double.valueOf(dataEach.getStr("bottomLeftLatitude")) + "," + Double.valueOf(dataEach.getStr("topLeftLongitude")) + " " + Double.valueOf(dataEach.getStr("topLeftLatitude")) + "))";
				Geometry tifGeom = reader.read(tifGeomStr);
				Record record = Db.findFirst("select ST_Contains(st_geometryfromtext('" + tifGeom + "',4326), st_geometryfromtext('POLYGON (("+latlons+"))',4326)) as num");
				if (record.getBoolean("num")) {
					dataEaches.add(dataEach);
				}
			}
		}
		return Ret.ok("data", dataEaches);
	}

    @Override
    public Ret uav() {
        List<DataEach> eachList = DataEach.service().dao().find(DataEach.sql().andDataIdEqualTo(88).example().setOrderBy("data_time desc"));
        return  Ret.ok("data",eachList);
    }

    /**
     * 面查询
     *
     * @param latlons
     * @param featureSource
     * @return
     * @throws IOException
     * @throws CQLException
     */
    public static boolean queryField(Record latlons, SimpleFeatureSource featureSource) throws IOException, CQLException {
        //final Filter filter = CQL.toFilter( "area < 40" );
        Filter filter = ECQL.toFilter("INTERSECTS(the_geom,"+latlons.getStr("geom")+")");
        SimpleFeatureCollection features = featureSource.getFeatures(filter);
        if (features.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 面查询
     *
     * @param latlons
     * @param featureSource
     * @return
     * @throws IOException
     * @throws CQLException
     */
    public static boolean queryFieldBy(Record latlons, SimpleFeatureSource featureSource) throws IOException, CQLException {
        //final Filter filter = CQL.toFilter( "area < 40" );
        Filter filter = ECQL.toFilter("INTERSECTS(the_geom,"+latlons.getStr("geom")+")");
        SimpleFeatureCollection features = featureSource.getFeatures(filter);
        if (features.size() != 0) {
            return true;
        } else {
            return false;
        }
    }




    /**
     * 点查询
     *
     * @param featureSource
     * @return
     * @throws IOException
     * @throws CQLException
     */
    public static boolean queryField(Double longitude, Double latitude, SimpleFeatureSource featureSource) throws IOException, CQLException {
        //final Filter filter = CQL.toFilter( "area < 40" );
        Filter filter = ECQL.toFilter("CONTAINS(the_geom, POINT(" + longitude + " " + latitude + "))");
        SimpleFeatureCollection features = featureSource.getFeatures(filter);
        System.out.println(features.size());
        if (features.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

}
