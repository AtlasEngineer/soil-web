package com.soli.server.utils;


import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.lambkit.Lambkit;

import com.lambkit.common.util.TimeUtils;
import com.lambkit.db.mgr.util.MgrDb;
import com.soli.lambkit.start.GeoServerConfig;
import com.soli.server.model.Geolist;
import com.soli.server.utils.CodePageUtils;
import com.soli.server.utils.MapServerConstants;
import com.soli.server.utils.Public;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import org.apache.commons.httpclient.NameValuePair;
import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.CRS;
import org.geotools.resources.Classes;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.*;
import com.jfinal.plugin.activerecord.Record;

public class IssueShpUtils {

    public static void main(String[] args) {
        Kv kv = null;
        try {
            kv = uploadShp("/src/main/webapp/d/8d84a41d-7f43-463f-a2db-755d34f33847.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(kv);
    }

    public static Kv uploadShp(String s,String name) throws Exception {

        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        String RESTURL = config.getGeourl();//http://localhost:8080/geoserver
        String RESTUSER = config.getGeouser();//admin
        String RESTPW = config.getGeopsw();//"Q7P0XFU$2YRQaq08";

        String workspace = "d";//jsus;

        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        GeoServerRESTReader geoServerRESTReader = new GeoServerRESTReader(RESTURL,RESTUSER,RESTPW);
        boolean b = false;

        GeoServerRESTPublisher.UploadMethod method = GeoServerRESTPublisher.UploadMethod.EXTERNAL;
        //获取dbf编码
        String codePage = CodePageUtils.getCodePage(s+"/"+name+".dbf");
        System.out.println("codePage:"+codePage);


        NameValuePair[] storeParams = new NameValuePair[1];
        storeParams[0] = new NameValuePair("charset", codePage);
        URI shapeFile = new URI(String.format("file:%s", s));

        try {
            b = publisher.publishShp(workspace, name, storeParams, name, method, shapeFile, "EPSG:4326", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (b) {
            return Kv.by("msg", "发布成功").set("code", 200);
        } else {
            return Kv.by("msg", "发布失败").set("code", 400);
        }
    }

    public static Kv uploadShp(String filepath) throws Exception {
        System.out.println(filepath);
        File file = new File(PathKit.getWebRootPath() + filepath);
        System.out.println("file:" + file.getAbsolutePath());
        System.out.println(file.getName());
        if (file == null) {
            return Kv.by("msg", "找不到该文件").set("code", 400);
        }
        String name = file.getName().split("\\.")[0];

        //解压后文件夹
        String s = PathKit.getWebRootPath() + "/d/" + name;
        try {
            ZipUtils.decompress(file.getPath(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("s" + s);
        File files = new File(s);
        String shpPath = null;
        String shxPath = null;
        String dbfPath = null;
        String prjPath = null;
        String[] fileList = files.list();
        List<String> list = new ArrayList<>();
        for (String y : fileList) {
            File file1 = new File(s + "/" + y);
            System.out.println("file1:" + file1.getName());
            if (file1.isFile()) {
//                String s1 = file1.getName().split("\\.")[1];
                String[] split = file1.getName().split("\\.");
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < split.length; i++) {
                    System.out.println("saaa:"+split[i]);
                    sb.append(split[i]).append(".");
                }
                String s1 = sb.toString().substring(0,sb.length()-1);
                File file01 = new File(PathKit.getWebRootPath() + "/d/" + name + "/" + name + "." + s1);
                System.out.println("file01:" + file01.getName());
                file1.renameTo(file01);
                String path = file01.getPath();
                System.out.println("path"+path);
                list.add(file01.getPath());
                if("shp".equals(s1)){
                    shpPath = file01.getPath();
                }
                if("shx".equals(s1)){
                    shxPath = file01.getPath();
                }
                if("dbf".equals(s1)){
                    dbfPath = file01.getPath();
                }
                if("prj".equals(s1)){
                    prjPath = file01.getPath();
                }
            }
        }
        if(shpPath==null){
            return Kv.by("msg", "上传压缩文件没有shp").set("code", 400);
        }
        if(shxPath==null){
            return Kv.by("msg", "上传压缩文件没有shx").set("code", 400);
        }
        if(dbfPath==null){
            return Kv.by("msg", "上传压缩文件没有dbf").set("code", 400);
        }
        if(prjPath==null){
            return Kv.by("msg", "上传压缩文件没有prj").set("code", 400);
        }

        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);

        String RESTURL = config.getGeourl();
        String RESTUSER = config.getGeouser();
        String RESTPW = config.getGeopsw();



        String workspace = "d";//jsus;
        System.out.println("RESTURL:" + RESTURL);
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        GeoServerRESTReader geoServerRESTReader = new GeoServerRESTReader(RESTURL,RESTUSER,RESTPW);
        boolean b = false;

        GeoServerRESTPublisher.UploadMethod method = GeoServerRESTPublisher.UploadMethod.EXTERNAL;


        String codePage = CodePageUtils.getCodePage(s+"/"+name+".dbf");
        System.out.println("codePage:"+codePage);

        //发布到postgis数据库
        //importShpToPG(s+"/"+name+".shp",name, MapServerConstants.TYPE_ADD,codePage,"");

        NameValuePair[] storeParams = new NameValuePair[1];
        storeParams[0] = new NameValuePair("charset", codePage);
        String path = URLEncoder.encode(s,codePage);
        URI shapeFile = new URI(String.format("file:%s", path));
        try {
            b = publisher.publishShp(workspace, name, storeParams, name, method, shapeFile, "EPSG:4326", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (b) {
            Geolist geolist=new Geolist();
            geolist.setDatatype("shp");
            geolist.setName(name);
            geolist.setUrl("d:"+name);
            geolist.setDtTime(new Date());
            geolist.save();
            return Kv.by("msg", "发布成功").set("code", 200);
        } else {
            return Kv.by("msg", "发布失败").set("code", 400);
        }
    }

    public static Kv uploadShpBySurface(String filepath,Integer id) throws Exception {
        System.out.println(filepath);
        File file = new File(PathKit.getWebRootPath() + filepath);
        System.out.println("file:" + file.getAbsolutePath());
        System.out.println(file.getName());
        if (file == null) {
            return Kv.by("msg", "找不到该文件").set("code", 400);
        }
        String name = file.getName().split("\\.")[0];

        //解压后文件夹
        String s = PathKit.getWebRootPath() + "/d/" + name;
        try {
            ZipUtils.decompress(file.getPath(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("s" + s);
        File files = new File(s);
        String shpPath = null;
        String shxPath = null;
        String dbfPath = null;
        String prjPath = null;
        String[] fileList = files.list();
        List<String> list = new ArrayList<>();
        for (String y : fileList) {
            File file1 = new File(s + "/" + y);
            System.out.println("file1:" + file1.getName());
            if (file1.isFile()) {
//                String s1 = file1.getName().split("\\.")[1];
                String[] split = file1.getName().split("\\.");
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < split.length; i++) {
                    System.out.println("saaa:"+split[i]);
                    sb.append(split[i]).append(".");
                }
                String s1 = sb.toString().substring(0,sb.length()-1);
                File file01 = new File(PathKit.getWebRootPath() + "/d/" + name + "/" + name + "." + s1);
                System.out.println("file01:" + file01.getName());
                file1.renameTo(file01);
                String path = file01.getPath();
                System.out.println("path"+path);
                list.add(file01.getPath());
                if("shp".equals(s1)){
                    shpPath = file01.getPath();
                }
                if("shx".equals(s1)){
                    shxPath = file01.getPath();
                }
                if("dbf".equals(s1)){
                    dbfPath = file01.getPath();
                }
                if("prj".equals(s1)){
                    prjPath = file01.getPath();
                }
            }
        }
        if(shpPath==null){
            return Kv.by("msg", "上传压缩文件没有shp").set("code", 400);
        }
        if(shxPath==null){
            return Kv.by("msg", "上传压缩文件没有shx").set("code", 400);
        }
        if(dbfPath==null){
            return Kv.by("msg", "上传压缩文件没有dbf").set("code", 400);
        }
        if(prjPath==null){
            return Kv.by("msg", "上传压缩文件没有prj").set("code", 400);
        }

        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);

        String RESTURL = config.getGeourl();
        String RESTUSER = config.getGeouser();
        String RESTPW = config.getGeopsw();



        String workspace = "d";//jsus;
        System.out.println("RESTURL:" + RESTURL);
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        GeoServerRESTReader geoServerRESTReader = new GeoServerRESTReader(RESTURL,RESTUSER,RESTPW);


        GeoServerRESTPublisher.UploadMethod method = GeoServerRESTPublisher.UploadMethod.EXTERNAL;


        String codePage = CodePageUtils.getCodePage(s+"/"+name+".dbf");
        System.out.println("codePage:"+codePage);

        //发布到postgis数据库
        importShpToPG(s+"/"+name+".shp",name,MapServerConstants.TYPE_ADD,codePage,"");

        //修改完字段发布表
        boolean b= Public.publishPg(name,name,name,id,"EPSG:4326","polygon");
        if (b) {
            return Kv.by("msg", "发布成功").set("code", 200);
        } else {
            return Kv.by("msg", "发布失败").set("code", 400);
        }
    }





    public static void importShpToPG(String shpfile, String tbname, int updateType, String charsetName, String where) throws IOException {
        ShapefileDataStore shpDataStore = null;
        TimeUtils.startTime("read Shp feature");
        try{
            File file = new File(shpfile);
            if(!file.exists()) {
                return;
            }
            if(updateType==MapServerConstants.TYPE_ADD) {
                //新增表格
            } else if(updateType==MapServerConstants.UPDATE_TYPE_COVER) {
                //覆盖更新
                Db.update("delete from " + tbname);
            } else if(updateType==MapServerConstants.UPDATE_TYPE_ADD) {
                //增量更新
            } else if(updateType==MapServerConstants.UPDATE_TYPE_FILTER && StrKit.notBlank(where)) {
                //要素更新
                Db.update("delete from " + tbname + " where " + where);
            } else {
                return;
            }
            shpDataStore = new ShapefileDataStore(file.toURI().toURL());
            //设置字符编码
            Charset charset = Charset.forName(charsetName);
            shpDataStore.setCharset(charset);
            String typeName = shpDataStore.getTypeNames()[0];
            System.out.println("name: " + typeName);
            tbname = StrKit.notBlank(tbname) ? tbname : typeName;
            SimpleFeatureType schema = shpDataStore.getSchema(typeName);
            CoordinateReferenceSystem srs = schema.getCoordinateReferenceSystem();
            System.out.println(srs.toWKT());
            Integer epsg = CRS.lookupEpsgCode(srs, true);
            if(epsg==null) {
                epsg = 4326;
            }
            String wkid=""+epsg;
            System.out.println("wkid:"+wkid);

            SimpleFeatureSource featureSource = null;
            featureSource =  shpDataStore.getFeatureSource(typeName);
            SimpleFeatureCollection result = featureSource.getFeatures();
            System.out.println("feature size: " + result.size());
            SimpleFeatureIterator itertor = result.features();

            StringBuilder insertSql = new StringBuilder();
            StringBuilder createSql = new StringBuilder();
            createSql.append("DROP TABLE IF EXISTS \"public\".\"").append(tbname).append("\";");
            createSql.append("CREATE TABLE \"public\".\"").append(tbname).append("\" (");
            createSql.append("\"gid\" int4 not null,");
            int count=0;//记录总插入数量
            int sqlcount=0;//记录一次批量插入时的插入个数
            int commitcount=10;//记录一次批量插入的上限数
            while (itertor.hasNext())
            {
                SimpleFeature feature = itertor.next();
                Collection<Property> p = feature.getProperties();
                Iterator<Property> it = p.iterator();

                String geometry = "";
                if(sqlcount==0) {
                    insertSql.delete(0, insertSql.length());
                    insertSql.append("BEGIN;");
                }
                sqlcount++;
                String id = feature.getID();
                insertSql.append("insert into \"").append(tbname).append("\" values (").append(Integer.parseInt(id.replace(typeName + ".", ""))).append(",");
                //FeatureId fid = feature.getIdentifier();
                while(it.hasNext()) {
                    Property pro = it.next();
                    /*if(pro.getValue() instanceof Point) {
                    	geometry = "st_geomfromtext('"+((Point)pro.getValue()).toString()+"'";
                    	geometry += epsg==null ? ")" : ","+wkid+")";
                    } else if(pro.getValue() instanceof Polygon) {
                    	geometry = "st_geomfromtext('"+((Polygon)pro.getValue()).toString()+"'";
                    	geometry += epsg==null ? ")" : ","+wkid+")";
                    } else if(pro.getValue() instanceof MultiPolygon) {
                    	geometry = "st_geomfromtext('"+((MultiPolygon)pro.getValue()).toString()+"'";
                    	geometry += epsg==null ? ")" : ","+wkid+")";
                    } else if(pro.getValue() instanceof LineString) {
                    	geometry = "st_geomfromtext('"+((LineString)pro.getValue()).toString()+"'";
                    	geometry += epsg==null ? ")" : ","+wkid+")";*/
                    if(pro.getValue() instanceof Point) {
                        geometry = "st_geomfromtext('"+ pro.getValue().toString()+"'";
                        geometry += epsg==null ? ")" : ","+wkid+")";
                    } else if(pro.getValue() instanceof Polygon) {
                        geometry = "st_geomfromtext('"+pro.getValue().toString()+"'";
                        geometry += epsg==null ? ")" : ","+wkid+")";
                    } else if(pro.getValue() instanceof MultiPolygon) {
                        geometry = "st_geomfromtext('"+pro.getValue().toString()+"'";
                        geometry += epsg==null ? ")" : ","+wkid+")";
                    } else if(pro.getValue() instanceof LineString) {
                        geometry = "st_geomfromtext('"+pro.getValue().toString()+"'";
                        geometry += epsg==null ? ")" : ","+wkid+")";
                    } else {
                        String type = Classes.getShortName(pro.getType().getBinding());
                        if(type.equals("String")) {
                            if(count==0) {
                                createSql.append("\"").append(pro.getName()).append("\" varchar(250) COLLATE \"default\",");
                            }
                            if(pro.getValue()==null) {
                                insertSql.append("null,");
                            } else {
                                insertSql.append("'").append(pro.getValue().toString().replace("'", "\"")).append("',");
                            }

                        } else if(type.equals("Double")) {
                            if(count==0) {
                                createSql.append("\"").append(pro.getName()).append("\" float4,");
                            }
                            insertSql.append(pro.getValue()).append(",");
                        } else if(type.equals("Float")) {
                            if(count==0) {
                                createSql.append("\"").append(pro.getName()).append("\" float4,");
                            }
                            insertSql.append(pro.getValue()).append(",");
                        } else if(type.equals("Integer")) {
                            if(count==0) {
                                createSql.append("\"").append(pro.getName()).append("\" int4,");
                            }
                            insertSql.append(pro.getValue()).append(",");
                        } else {
                            if(count==0) {
                                createSql.append("\"").append(pro.getName()).append("\" varchar(250) COLLATE \"default\",");
                            }
                            insertSql.append("'").append(pro.getValue()).append("',");
                        }
                    }
                }
                insertSql.append(geometry).append(");");
                if(count==0) {
                    createSql.append("\"geom\" public.geometry, PRIMARY KEY (\"gid\")) WITH (OIDS=FALSE);");
                    //System.out.println("createSql: "+ createSql.toString());
                    //执行createSql
                    if(updateType==MapServerConstants.TYPE_ADD) {
                        MgrDb.use().execute(createSql.toString());
                    }
                }
                if(sqlcount==commitcount) {
                    insertSql.append(" COMMIT;");
                    //System.out.println("insertSql: "+ insertSql.toString());
                    //执行insertSql
                    MgrDb.use().execute(insertSql.toString());
                    sqlcount=0;
                }
                count++;
            }
            if(sqlcount!=0) {
                insertSql.append(" COMMIT;");
                //System.out.println("insertSql: "+ insertSql.toString());
                //执行insertSql
                MgrDb.use().execute(insertSql.toString());
            }
            itertor.close();
            TimeUtils.endTime("read Shp feature");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Map<Integer, Object> ji(String name) {
        List<Record> records=Db.find("select * from zjk");
        List<Map<String, Object>> mapList_name = new ArrayList<>();

        for (Record result_name:records){
            List<Record> list_geom = Db.find("SELECT st_area(ST_Intersection(geom,?))/st_area(geom) as number ,gid FROM \"" + name + "\" WHERE ST_Intersects(geom,?) and ST_IsValid(geom)", result_name.getStr("geom"), result_name.getStr("geom"));

            for (Record record : list_geom) {
                if (record != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("gid", record.getInt("gid"));
                    map.put("number", record.getDouble("number"));
                    map.put("name", result_name.getStr("name"));
                    mapList_name.add(map);
                }
            }
        }


        //方法一
        Map<Integer, JSONObject> result = new HashMap<Integer, JSONObject>();
        Map<Integer, Object> order = new HashMap<Integer, Object>();
        for (Map<String, Object> map : mapList_name) {
            Integer id = (Integer) map.get("gid");
            String value = map.get("name").toString();
            Double number = (Double) map.get("number");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", value);
            jsonObject.put("number", number);
            if (result.containsKey(id)) {
                System.out.println("result" + result.get(id).getDouble("number") + "id:" + id);
                System.out.println("map" + map.get("name") + "id:" + id);
                System.out.println(number);
                if (result.get(id).getDouble("number") > number) {
                    order.put(id, value);
                } else {
                    order.put(id, result.get(id).getString("name"));
                }
            }
            result.put(id, jsonObject);
        }


        return order;


    }

}
