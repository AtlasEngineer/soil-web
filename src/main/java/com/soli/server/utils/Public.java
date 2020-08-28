package com.soli.server.utils;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.lambkit.Lambkit;
import com.soli.lambkit.start.GeoServerConfig;
import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher.UploadMethod;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;
import it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import org.apache.commons.httpclient.NameValuePair;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

public class Public {

    private final static Integer SUCCESS=1;
    private final static Integer FAIL=2;
    private final static Integer REPEAT=3;
    public boolean pu(String layername, int id, String epsgCode) {

        String RESTURL = "http://127.0.0.1:8080/geoserver";//localhost http://59.110.20.59:8585/geoserver
        String RESTUSER = "admin";
        String RESTPW = "Q7P0XFU$2YRQaq08";
        String workspace = "jsus";
        String storename = "jsus";
        String resPath = PathKit.getWebRootPath() + "\\src\\main\\webapp\\jsus";
        String uploadMethod = "external";// external
        epsgCode = "EPSG:91901" + id;

        List<String> workspaces = null;
        try {
            GeoServerRESTReader reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTPW);
            GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
            workspaces = reader.getWorkspaceNames();
            if (workspaces.contains(workspace)) {
                UploadMethod method = UploadMethod.EXTERNAL;
                File shpFile = null;
                if (uploadMethod.equals("url")) {
                    method = UploadMethod.URL;
                    String url = "\\\\127.0.0.1:8080\\";
                    shpFile = new File(url + resPath + storename + "\\" + layername + ".zip");
                } else if (uploadMethod.equals("file")) {
                    //zipfile
                    method = UploadMethod.FILE;
                    System.out.println(layername);
                    System.out.println(resPath);
                    shpFile = new File(resPath + File.separator + storename + File.separator + layername + ".shp");
                } else {
                    shpFile = new File(resPath + File.separator + storename + File.separator + layername);
                }
                System.out.println("shpFile :" + shpFile);
                String ul = workspace + ":" + layername;
                /*String sql="update ydyl_datalist set url='"+ul+"' where id='"+id+"'";
                Db.update(sql);*/
                return publisher.publishShp(workspace, storename, (NameValuePair[]) null, layername,
                        method, shpFile.toURI(), "EPSG:4326", epsgCode, ProjectionPolicy.REPROJECT_TO_DECLARED, null);
            }
        } catch (Exception mue) {
            mue.printStackTrace();
        }
        System.out.println("------over-----");
        return false;
    }

    public static boolean publishPg(String gsName, String layerName, String tbname, Integer id, String epsgCode, String defaultStyle) {
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);

        String RESTURL = config.getGeourl();
        System.out.println("RESTURL:" + RESTURL);
        String RESTUSER = config.getGeouser();
        String RESTPW = config.getGeopsw();
        //jsus;
        String workspace = "d";
        //"jsus";
        String storename = "pg";
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
        fte.setProjectionPolicy(GSResourceEncoder.ProjectionPolicy.REPROJECT_TO_DECLARED);
        fte.addKeyword("KEYWORD");
        fte.setTitle(layerName);
        fte.setName(tbname);
        fte.setSRS(epsgCode);
        fte.setNativeCRS(epsgCode);
        GSLayerEncoder layerEncoder = new GSLayerEncoder();
        layerEncoder.setDefaultStyle(defaultStyle);
        String ul = workspace + ":" + layerName;
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String sql = "insert into tr_geolist (name,datatype,url,dt_time) values('" + ul + "','shp','" + gsName + "','" + time + "')";
        Db.update(sql);
        return publisher.publishDBLayer(workspace, storename, fte, layerEncoder);
    }

    public static void main(String arg[]) {
        Public p = new Public();
        boolean pu = p.pu("ad", 1, "");
        System.out.println(pu);
    }


    public static Integer publicPgsql(String table_name,String store_name) throws MalformedURLException {
        //GeoServer的连接配置
        String url = "http://localhost:8080/geoserver";
        String username = "admin";
        String passwd = "geoserver";

        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);


        String ws = "d";     //待创建和发布图层的工作区名称workspace
        //String store_name = "wafangdianshi"; //待创建和发布图层的数据存储名称store
        //String table_name = "wafangdianshi"; // 数据库要发布的表名称,后面图层名称和表名保持一致


        //判断工作区（workspace）是否存在，不存在则创建
        URL u = new URL(url);
        GeoServerRESTManager manager = new GeoServerRESTManager(u, username, passwd);
        GeoServerRESTPublisher publisher = manager.getPublisher();
        List<String> workspaces = manager.getReader().getWorkspaceNames();
        if (!workspaces.contains(ws)) {
            boolean createws = publisher.createWorkspace(ws);
            System.out.println("create ws : " + createws);
        } else {
            System.out.println("workspace已经存在了,ws :" + ws);
        }


        //判断数据存储（datastore）是否已经存在，不存在则创建
        RESTDataStore restStore = manager.getReader().getDatastore(ws, store_name);
        if (restStore == null) {
            GSPostGISDatastoreEncoder store = new GSPostGISDatastoreEncoder(store_name);
            //设置url
            store.setHost(config.getGeourl());
            //设置端口
            store.setPort(5434);
            // 数据库的用户名
            store.setUser(config.getGeouser());
            // 数据库的密码
            store.setPassword(config.getGeopsw());
            // 那个数据库;
            store.setDatabase("zhangjiakou");
            //当前先默认使用public这个schema
            store.setSchema("public");
            // 超时设置
            store.setConnectionTimeout(20);
            //store.setName(schema);
            // 最大连接数
            store.setMaxConnections(20);
            // 最小连接数
            store.setMinConnections(1);
            store.setExposePrimaryKeys(true);
            boolean createStore = manager.getStoreManager().create(ws, store);
            System.out.println("create store : " + createStore);
        } else {
            System.out.println("数据存储已经存在了,store:" + store_name);
        }


        //判断图层是否已经存在，不存在则创建并发布
        RESTLayer layer = manager.getReader().getLayer(ws, table_name);
        if (layer == null) {
            GSFeatureTypeEncoder pds = new GSFeatureTypeEncoder();
            pds.setTitle(table_name);
            pds.setName(table_name);
            pds.setSRS("EPSG:4326");
            GSLayerEncoder layerEncoder = new GSLayerEncoder();
            boolean publish = manager.getPublisher().publishDBLayer(ws, store_name, pds, layerEncoder);
            if (publish){
                return SUCCESS;
            }else {
                return FAIL;
            }
        } else {
            System.out.println("表已经发布过了,table:" + table_name);
            return REPEAT;
        }
    }


}
