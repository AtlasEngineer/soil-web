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

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.LambkitConfig;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.soli.lambkit.start.GeoServerConfig;
import com.soli.server.model.DataEach;
import com.soli.server.service.DataService;
import com.soli.server.model.Data;
import com.soli.server.utils.*;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.WKTReader;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import org.apache.poi.ss.formula.functions.T;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.data.DataSourceException;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.jts.ReferencedEnvelope;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.soli.server.utils.CodePageUtils.getUserEntity;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-11
 * @since 1.0
 */
public class DataServiceImpl extends LambkitModelServiceImpl<Data> implements DataService {

    private Data DAO = null;

    public Data dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(Data.class);
        }
        return DAO;
    }

    @Override
    public Ret getProvince(Double lat, Double lon) {
        Record first = Db.findFirst("select * from tr_ch_county where ST_Contains(geom,st_geometryfromtext('POINT(" + lon + " " + lat + ")',4326))");
        return Ret.ok("data",first);
    }

    public Ret updateNDVI(List<Integer> ids) {
        if (ids.size() == 0) {
            return Ret.fail("errorMsg", "请选择更新的数据");
        }
        //获取最新的landset
        List<DataEach> dataEachs = DataEach.service().dao().find(DataEach.sql().andDataIdEqualTo(82).andIdIn(ids).example().setOrderBy("data_time desc"));
        List<NDVIModel> ndviModels = new ArrayList<>();
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (DataEach dataEach : dataEachs) {
            File directory = new File(rootPath + dataEach.getUrl());
            File[] files = directory.listFiles();
            File b5File = null;
            File b4File = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.getName().contains("_B5")) {
                    b5File = file;
                } else if (file.getName().contains("_B4")) {
                    b4File = file;
                }
            }
            if (b4File == null) {
                return Ret.fail("errorMsg", "缺少红外波段数据");
            }
            if (b5File == null) {
                return Ret.fail("errorMsg", "缺少近红外波段数据");
            }
            //获取与当前landset数据相交地的包围盒
//        List<Record> tks = Db.find("SELECT gid,st_astext(geom) FROM tr_tiankuai ORDER BY gid");
            List<Record> tks = Db.find("SELECT T.gid,st_astext(ST_Transform(T.geom,32650)) as geom FROM " +
                    " tr_data_each e LEFT JOIN tr_tiankuai T ON ST_Intersects ( T.geom,st_geometryfromtext (concat_ws ( '','POLYGON((', " +
                    "  concat_ws ( ',', " +
                    " concat_ws ( ' ', e.\"topLeftLongitude\", e.\"topLeftLatitude\" ), " +
                    " concat_ws ( ' ', e.\"topRightLongitude\", e.\"topRightLatitude\" ), " +
                    " concat_ws ( ' ', e.\"bottomRightLongitude\", e.\"bottomRightLatitude\" ), " +
                    " concat_ws ( ' ', e.\"bottomLeftLongitude\", e.\"bottomLeftLatitude\" ), " +
                    " concat_ws ( ' ', e.\"topLeftLongitude\", e.\"topLeftLatitude\" )),'))'), 4326 ))" +
                    " where e.id = '" + dataEach.getId() + "' and T.del = 0 ");
            for (Record tk : tks) {
                //获取wkt
                String writePath = "/ndvi/" + tk.getInt("gid") + "_" + sdf.format(dataEach.getDataTime()) + directory.getName() + ".tif";
                String geom = tk.getStr("geom");
                if (geom.contains("MULTIPOLYGON")) {
                    geom = geom.substring(15, geom.length() - 3);
                } else if (geom.contains("POLYGON")) {
                    geom = geom.substring(9, geom.length() - 2);
                }
                //获取田块与landset相同分辨率的tif
                try {
                    //获取ndvi
                    Kv kv1 = ReadTiffUtils.getNDVIParams(geom, b4File);
//                    if (!kv1.getBoolean("intersec")) {
//                        //地块与tiff不相交
//                        continue;
//                    }
                    float[][] ndviParams = (float[][]) kv1.get("data");
                    Kv kv2 = ReadTiffUtils.getNDVIParams(geom, b5File);
                    float[][] ndviParams1 = (float[][]) kv2.get("data");

                    Kv floatData = ReadTiffUtils.getFloatData(geom, b4File, rootPath + writePath);
                    float[][] data = (float[][]) floatData.get("data");
//                    ReferencedEnvelope envelope = (ReferencedEnvelope) floatData.get("envelope");
//                    int x = 0;
//                    int y = 0;
//                    int z = 0;
                    //ndvi = B5-B4/B5+B4   B4是红，B5是近红 正常结果范围在-1到1之间
                    for (int i = 0; i < ndviParams.length; i++) {
                        for (int j = 0; j < ndviParams[i].length; j++) {
                            float b4 = ndviParams[i][j];
                            float b5 = ndviParams1[i][j];
                            double add = Arith.add(b5, b4);
                            if (add != 0) {
                                Double div = Arith.div(Arith.sub(b5, b4), add);
                                if (div == -1 || div == 1) {
//                                    x++;
                                    data[i][j] = 0;
                                }else{
//                                    z++;
                                    data[i][j] = div.floatValue();
                                }
                            } else {
//                                y++;
//                                System.out.println(b4+"    "+b5);
                                data[i][j] = 0;
                            }
                        }
                    }
//                    System.out.println("+-1的数量:"+x);
//                    System.out.println("0的数量:"+y);
//                    System.out.println("value的数量:"+z);
                    NDVIModel ndviModel = new NDVIModel();
                    ndviModel.setName(directory.getName());
                    ndviModel.setData(data);
                    ndviModel.setGeom(geom);
                    ndviModel.setTk_id(tk.getInt("gid"));
                    ndviModel.setPath(writePath);
                    ndviModel.setData_time(dataEach.getDataTime());
                    ndviModels.add(ndviModel);
//                    try {
//                        ReadTiffUtils.writerTif(ndviModel.getGeom(), data, rootPath + "/ndvi2" + ndviModel.getPath(), ndviModel.getNoData());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //并和相交的田块tif----同一数据时间和同一地块
        List<Integer> list = new ArrayList<>();
        for (NDVIModel ndviModel : ndviModels) {
            if (list.contains(ndviModel.getTk_id())) {
                continue;
            }
            float[][] data1 = ndviModel.getData();
            float[][] data = new float[data1.length][data1[0].length];
            for (NDVIModel ndviModel2 : ndviModels) {
                if (ndviModel == ndviModel2) {
                    list.add(ndviModel2.getTk_id());
                    continue;
                }
                if (ndviModel.getData_time().equals(ndviModel2.getData_time()) && ndviModel.getTk_id().equals(ndviModel2.getTk_id())) {
                    //需要合并的
                    float[][] data2 = ndviModel2.getData();
                    for (int i = 0; i < data1.length; i++) {
                        for (int j = 0; j < data1[i].length; j++) {
                            float d1 = data1[i][j];
                            float d2 = data2[i][j];
                            if (d1 == 0) {
                                data[i][j] = d2;
                            } else if (d2 == 0) {
                                data[i][j] = d1;
                            } else {
                                //取平均值
                                Double div = Arith.div(Arith.add(d1, d2), 2);
                                if (div == -1 || div == 1) {
                                    data[i][j] = 0;
                                }else{
                                    data[i][j] = div.floatValue();
                                }
                            }
                        }
                    }
                    ndviModel.setPath("/ndvi/ndvi_" + sdf.format(ndviModel.getData_time()) + "_" + ndviModel.getTk_id() + ndviModel.getName() + "_" + ndviModel2.getName() + ".tif");
                    list.add(ndviModel2.getTk_id());
                } else {
                    data = ndviModel.getData();
                }
            }
            //生成tiff
            File file = new File(rootPath + ndviModel.getPath());
            try{
                ReadTiffUtils.writerTif(ndviModel.getGeom(), data, rootPath + ndviModel.getPath(), 0.0);
                //生成缩略tiff
                String s = rootPath + ndviModel.getPath();
                String[] split = s.split(".tif");
                String s1 = split[0] + ".tif";
                //生成缩略图
                ReadTiffUtils.makeThumbsFromTiff(data, s, s1);
                //发布tiff

            } catch (Exception e) {
                e.printStackTrace();
            }
            Db.update("insert into tr_tiankuai_ndvi (tk_id,data_time,path,url) values('" + ndviModel.getTk_id() + "','" + ndviModel.getData_time() + "','" + ndviModel.getPath() + "','d:"+file.getName().split(".tif")[0]+"')");
        }
        return Ret.ok();
    }

    @Override
    public Ret updateNDVI2(List<Integer> ids) {
        if (ids.size() == 0) {
            return Ret.fail("errorMsg", "请选择更新的数据");
        }
        //获取最新的landset
        List<DataEach> dataEachs = DataEach.service().dao().find(DataEach.sql().andDataIdEqualTo(82).andIdIn(ids).example().setOrderBy("data_time desc"));
        List<NDVIModel> ndviModels = new ArrayList<>();
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        for (DataEach dataEach : dataEachs) {
            File directory = new File(rootPath + dataEach.getUrl());
            File[] files = directory.listFiles();
            File b5File = null;
            File b4File = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.getName().contains("_B5")) {
                    b5File = file;
                } else if (file.getName().contains("_B4")) {
                    b4File = file;
                }
            }
            if (b4File == null) {
                return Ret.fail("errorMsg", "缺少红外波段数据");
            }
            if (b5File == null) {
                return Ret.fail("errorMsg", "缺少近红外波段数据");
            }
            //获取与当前landset数据相交地的包围盒
//        List<Record> tks = Db.find("SELECT gid,st_astext(geom) FROM tr_tiankuai ORDER BY gid");
            List<Record> tks = Db.find("SELECT T.gid,st_astext(ST_Transform(T.geom,32650)) as geom FROM " +
                    " tr_data_each e LEFT JOIN tr_tiankuai T ON ST_Intersects ( T.geom, st_geometryfromtext (concat_ws ( '','POLYGON((', " +
                    "  concat_ws ( ',', " +
                    " concat_ws ( ' ', e.\"topLeftLongitude\", e.\"topLeftLatitude\" ), " +
                    " concat_ws ( ' ', e.\"topRightLongitude\", e.\"topRightLatitude\" ), " +
                    " concat_ws ( ' ', e.\"bottomRightLongitude\", e.\"bottomRightLatitude\" ), " +
                    " concat_ws ( ' ', e.\"bottomLeftLongitude\", e.\"bottomLeftLatitude\" ), " +
                    " concat_ws ( ' ', e.\"topLeftLongitude\", e.\"topLeftLatitude\" )),'))'), 4326 ))" +
                    " where e.id = '" + dataEach.getId() + "' and T.del = 0 ");
            for (Record tk : tks) {
                //获取wkt坐标，读取像素值
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String writePath = "/ndvi/ndvi_" + sdf.format(dataEach.getDataTime()) + "_" + tk.getInt("gid") + "_" + directory.getName() + ".tif";
                String geom = tk.getStr("geom");
                if (geom.contains("MULTIPOLYGON")) {
                    geom = geom.substring(15, geom.length() - 3);
                } else if (geom.contains("POLYGON")) {
                    geom = geom.substring(9, geom.length() - 2);
                }
                try {
                    Kv kv1 = ReadTiffUtils.getNDVIParams(geom, b4File);
                    if (!kv1.getBoolean("intersec")) {
                        //地块与tiff不相交
                        continue;
                    }
                    float[][] ndviParams = (float[][]) kv1.get("data");
                    Kv kv2 = ReadTiffUtils.getNDVIParams(geom, b5File);
                    float[][] ndviParams1 = (float[][]) kv2.get("data");
                    //ndvi = B5-B4/B5+B4   B4是红，B5是近红 正常结果范围在-1到1之间
                    float[][] data = new float[ndviParams.length][ndviParams[0].length];
                    for (int i = 0; i < ndviParams.length; i++) {
                        for (int j = 0; j < ndviParams[i].length; j++) {
                            float b4 = ndviParams[i][j];
                            float b5 = ndviParams1[i][j];
                            double add = Arith.add(b5, b4);
                            if (add != 0) {
                                Double div = Arith.div(Arith.sub(b5, b4), add);
                                data[i][j] = div.floatValue();
                            }
                        }
                    }
                    //获取nodata
                    Double noDate = ReadTiffUtils.getNoDate(b4File.getAbsolutePath());

                    NDVIModel ndviModel = new NDVIModel();
                    ndviModel.setName(directory.getName());
                    ndviModel.setNoData(noDate);
                    ndviModel.setData(data);
                    ndviModel.setGeom(geom);
                    ndviModel.setTk_id(tk.getInt("gid"));
                    ndviModel.setPath(writePath);
                    ndviModel.setData_time(dataEach.getDataTime());
                    ndviModels.add(ndviModel);
                    //生成tiff
                    ReadTiffUtils.writerTif(geom, data, rootPath + writePath, noDate);
//                    //保存数据
//                    Db.update("insert into tr_tiankuai_ndvi (tk_id,data_time,url) values('" + tk.getInt("gid") + "','" + dataEach.getDataTime() + "','" + writePath + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //并和相交的田块tif----同一数据时间和同一地块
        List<Integer> list = new ArrayList<>();
        for (NDVIModel ndviModel : ndviModels) {
            if (list.contains(ndviModel.getTk_id())) {
                continue;
            }
            for (NDVIModel ndviModel2 : ndviModels) {
                if (ndviModel.equals(ndviModel2)) {
                    list.add(ndviModel2.getTk_id());
                    continue;
                }
                if (ndviModel.getData_time() == ndviModel2.getData_time() && ndviModel.getTk_id() == ndviModel2.getTk_id()) {
                    //需要合并的
                    String s = rootPath + ndviModel.getPath().split(".tif")[0] + "_" + ndviModel2.getName() + ".tif";
                    TiffOP.mergeTiff(new File(s), new File(rootPath + ndviModel.getPath()), new File(rootPath + ndviModel2.getPath()));
                    ndviModel.setPath(ndviModel.getPath().split(".tif")[0] + "_" + ndviModel2.getName() + ".tif");
                    list.add(ndviModel2.getTk_id());
                }
            }
//            //直接生成tiff
//            if(!isMerge){
//                try {
//                    ReadTiffUtils.writerTif(ndviModel.getGeom(), ndviModel.getData(), rootPath + ndviModel.getPath(), ndviModel.getNoData());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            //保存数据
            Db.update("insert into tr_tiankuai_ndvi (tk_id,data_time,url) values('" + ndviModel.getTk_id() + "','" + ndviModel.getData_time() + "','" + ndviModel.getPath() + "')");
            //发布数据
        }
//        List<Integer> list = new ArrayList<>();
//        for (NDVIModel ndviModel : ndviModels) {
//            if (list.contains(ndviModel.getTk_id())) {
//                continue;
//            }
//            float[][] data = new float[0][];
//            for (NDVIModel ndviModel2 : ndviModels) {
//                if (!ndviModel.equals(ndviModel2) && ndviModel.getData_time().equals(ndviModel2.getData_time()) && ndviModel.getTk_id().equals(ndviModel2.getTk_id())) {
//                    //需要合并的
////                    TiffOP.mergeTiff()
//                    float[][] data1 = ndviModel.getData();
//                    float[][] data2 = ndviModel2.getData();
//                    data = new float[data1.length][data1[0].length];
//                    for (int i = 0; i < data1.length; i++) {
//                        for (int j = 0; j < data1[i].length; j++) {
//                            float d1 = data1[i][j];
//                            float d2 = data2[i][j];
//                            if (d1 == 0) {
//                                data[i][j] = d2;
//                            } else if (d2 == 0) {
//                                data[i][j] = d1;
//                            } else {
//                                //取平均值
//                                Double div = Arith.div(Arith.add(d1, d2), 2);
//                                data[i][j] = div.floatValue();
//                            }
//                        }
//                    }
//                    list.add(ndviModel2.getTk_id());
//                } else {
//                    data = ndviModel.getData();
//                }
//            }
//            //直接生成tiff
//            try {
//                ReadTiffUtils.writerTif(ndviModel.getGeom(), data, rootPath + ndviModel.getPath(), ndviModel.getNoData());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //保存数据
//            Db.update("insert into tr_tiankuai_ndvi (tk_id,data_time,url) values('" + ndviModel.getTk_id() + "','" + ndviModel.getData_time() + "','" + ndviModel.getPath() + "')");
//            //发布数据
//        }
        return Ret.ok();
    }

    @Override
    public Ret getTkRemoteData(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择数据");
        }
        //高分和哨兵1数据,无人机(目前就当无人机数据是正四边形的-刘阳)
        List<Record> gf_sb = Db.find("SELECT e.*,d.name as dir_name FROM " +
                " (((select data_id,max(data_time) from tr_data_each GROUP BY data_id) b " +
                " LEFT JOIN tr_data_each e ON e.data_time = b.max and b.data_id =  e.data_id) " +
                " LEFT JOIN tr_data d ON d.id = e.data_id)" +
                " LEFT JOIN tr_tiankuai T ON ST_Intersects ( T.geom, st_geometryfromtext (concat_ws ( '','POLYGON((', " +
                "  concat_ws ( ',', " +
                " concat_ws ( ' ', e.\"topLeftLongitude\", e.\"topLeftLatitude\" ), " +
                " concat_ws ( ' ', e.\"topRightLongitude\", e.\"topRightLatitude\" ), " +
                " concat_ws ( ' ', e.\"bottomRightLongitude\", e.\"bottomRightLatitude\" ), " +
                " concat_ws ( ' ', e.\"bottomLeftLongitude\", e.\"bottomLeftLatitude\" ), " +
                " concat_ws ( ' ', e.\"topLeftLongitude\", e.\"topLeftLatitude\" )),'))'), 4326 ))" +
                " where (e.TYPE IN ( 3, 4  ) or e.data_id = 88) AND t.id = ?", id);
        //哨兵2
        Record record = Db.findFirst("SELECT e.*,d.name as dir_name FROM ((tr_data_each e LEFT JOIN tr_data d ON d.id = e.data_id) LEFT JOIN tr_tiankuai T " +
                " ON ST_Intersects (T.geom,st_geometryfromtext ( concat_ws ( '', 'POLYGON((', e.latlons, '))' ),4326) ))  " +
                " WHERE e.data_id = 84 AND T.ID = 35 ORDER BY e.data_time DESC");
        if (record != null) {
            gf_sb.add(record);
        }
        //landset待添加

        return Ret.ok("list", gf_sb);
    }

    @Override
    public Ret getLastData(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择数据");
        }
        Data data = Data.service().dao().findById(id);
        //高分、哨兵1，哨兵2-84，landset
        if (data.getType() == 3 || data.getType() == 4 || data.getId() == 84) {
            //获取当前系统时间最近15天的数据
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 15);
            Date today = calendar.getTime();
            List<DataEach> data_time_desc = DataEach.service().dao().find(DataEach.sql().andDataIdEqualTo(data.getId()).andDataTimeBetween(today, new Date()).example().setOrderBy("data_time desc"));
            List<Double> lon = new ArrayList<>();
            List<Double> lat = new ArrayList<>();
            for (DataEach dataEach : data_time_desc) {
                if (data.getId() == 84) {
                    //哨兵2包围盒
                    String latlons = dataEach.getStr("latlons");
                    Record first = Db.findFirst(" select st_xmin(geom),st_ymin(geom),st_xmax(geom),st_ymax(geom) " +
                            "from (SELECT st_envelope(st_geometryfromtext('polygon((" + latlons + "))')) as geom) as a");
                    lon.add(Double.valueOf(first.getDouble("st_xmax")));
                    lon.add(Double.valueOf(first.getDouble("st_xmin")));

                    lat.add(Double.valueOf(first.getDouble("st_ymin")));
                    lat.add(Double.valueOf(first.getDouble("st_ymax")));

                } else {
                    lon.add(Double.valueOf(dataEach.getStr("topLeftLongitude")));
                    lon.add(Double.valueOf(dataEach.getStr("topRightLongitude")));
                    lon.add(Double.valueOf(dataEach.getStr("bottomRightLongitude")));
                    lon.add(Double.valueOf(dataEach.getStr("bottomLeftLongitude")));

                    lat.add(Double.valueOf(dataEach.getStr("topLeftLatitude")));
                    lat.add(Double.valueOf(dataEach.getStr("topRightLatitude")));
                    lat.add(Double.valueOf(dataEach.getStr("bottomRightLatitude")));
                    lat.add(Double.valueOf(dataEach.getStr("bottomLeftLatitude")));
                }
            }
            Double min_lon = null;
            Double max_lon = null;
            Double min_lat = null;
            Double max_lat = null;
            if (lon.size() > 0) {
                min_lon = Collections.min(lon);
                max_lon = Collections.max(lon);
            }
            if (lat.size() > 0) {
                min_lat = Collections.min(lat);
                max_lat = Collections.max(lat);
            }
            if (data_time_desc == null) {
                return Ret.fail("errorMsg", "暂无数据");
            } else {
                return Ret.ok("data", data_time_desc).set("min_lon", min_lon).set("max_lon", max_lon).set("min_lat", min_lat).set("max_lat", max_lat);
            }
        } else if ("无人机数据".equals(data.getName())) {
            //最新一天的所有数据
            DataEach data_time_desc = DataEach.service().dao().findFirst(DataEach.sql().andDataIdEqualTo(data.getId()).example().setOrderBy("data_time desc"));
            if (data_time_desc == null) {
                return Ret.fail("errorMsg", "暂无数据");
            } else {
                List<DataEach> dataEaches = DataEach.service().dao().find(DataEach.sql().andDataIdEqualTo(data.getId()).andDataTimeEqualTo(data_time_desc.getDataTime()).example());
                List<Double> lon = new ArrayList<>();
                List<Double> lat = new ArrayList<>();
                String webRootPath = PathKit.getWebRootPath().replace("\\", "/");
                for (DataEach dataEach : dataEaches) {
//                    Kv tiffXY = ReadTiffUtils.getTiffXY(webRootPath + "/d/" + dataEach.getUrl().split(":")[1] + "/" + dataEach.getUrl().split(":")[1] + ".tif");
//                    lon.add(tiffXY.getNumber("minY").doubleValue());
//                    lon.add(tiffXY.getNumber("maxY").doubleValue());
//
//                    lat.add(tiffXY.getNumber("minX").doubleValue());
//                    lat.add(tiffXY.getNumber("maxX").doubleValue());
                    lon.add(Double.valueOf(dataEach.getStr("topLeftLongitude")));
                    lon.add(Double.valueOf(dataEach.getStr("topRightLongitude")));
                    lon.add(Double.valueOf(dataEach.getStr("bottomRightLongitude")));
                    lon.add(Double.valueOf(dataEach.getStr("bottomLeftLongitude")));

                    lat.add(Double.valueOf(dataEach.getStr("topLeftLatitude")));
                    lat.add(Double.valueOf(dataEach.getStr("topRightLatitude")));
                    lat.add(Double.valueOf(dataEach.getStr("bottomRightLatitude")));
                    lat.add(Double.valueOf(dataEach.getStr("bottomLeftLatitude")));
                }
                Double min_lon = Collections.min(lon);
                Double max_lon = Collections.max(lon);
                Double min_lat = Collections.min(lat);
                Double max_lat = Collections.min(lat);
                return Ret.ok("data", dataEaches).set("min_lon", min_lon).set("max_lon", max_lon).set("min_lat", min_lat).set("max_lat", max_lat);
            }
        } else {
            DataEach data_time_desc = DataEach.service().dao().findFirst(DataEach.sql().andDataIdEqualTo(data.getId()).example().setOrderBy("data_time desc"));
            if (data_time_desc == null) {
                return Ret.fail("errorMsg", "暂无数据");
            } else {
                return Ret.ok("data", data_time_desc);
            }
        }
    }

    @Override
    public Ret getExcelDateNames(Integer id) {
        Data data = Data.service().dao().findById(id);
        if (data != null && data.getType() != 2 && data.getInt("isedit") != 1) {
            return Ret.fail("errorMsg", "该数据不是表格数据");
        }
        String table_name = data.getUrl();
        List<Record> records = Db.find("SELECT DISTINCT(product) FROM " + table_name);
        List<String> strings = new ArrayList<>();
        for (Record record : records) {
            strings.add(record.get("product"));
        }
        return Ret.ok("list", strings);
    }

    @Override
    public Ret deleteExcelDate(Integer id, Integer[] ids) {
        Data data = Data.service().dao().findById(id);
        if (data != null && data.getType() != 2) {
            return Ret.fail("errorMsg", "该数据不是表格数据");
        }
        String table_name = data.getUrl();
        for (int i = 0; i < ids.length; i++) {
            Db.delete("delete from " + table_name + " where id = ? ", ids[i]);
        }
        return Ret.ok("msg", "删除成功");
    }

    @Override
    public Ret deleteEachDate(Integer[] ids) {
        if (ids == null) {
            return Ret.fail("errorMsg", "请选择要删除的数据");
        }
        //删除geoserver服务
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        String RESTURL = config.getGeourl();
        String RESTUSER = config.getGeouser();
        String RESTPW = config.getGeopsw();
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        for (int i = 0; i < ids.length; i++) {
            DataEach dataEach = DataEach.service().dao().findById(ids[i]);
            if (dataEach != null) {
                dataEach.delete();
                //删除文件
                String webRootPath = PathKit.getWebRootPath();
                File file;
                if (dataEach.getType() == 2) {
                    file = new File(webRootPath + dataEach.getUrl());
                } else if (dataEach.getType() == 0 || dataEach.getType() == 1 || dataEach.getType() == 5) {
                    publisher.removeLayer("d", dataEach.getUrl().split(":")[1]);
                    publisher.removeDatastore("d",dataEach.getUrl().split(":")[1]);
                    file = new File(webRootPath + "/d/" + dataEach.getUrl().split(":")[1]);
                } else {
                    File file1 = new File(webRootPath + dataEach.getUrl());
                    file = file1.getParentFile();
                }
                file.delete();
            }
        }
        return Ret.ok("msg", "删除成功");
    }

    @Override
    public Ret getExcelDate(Integer id, Integer pageNum, Integer pageSize, String name, String address, String[] times) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择数据");
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Data data = Data.service().dao().findById(id);
        if (data != null && data.getType() != 2) {
            return Ret.fail("errorMsg", "该数据不是表格数据");
        }
        String table_name = data.getUrl();
        StringBuffer sql = new StringBuffer(" from " + table_name + " where 1=1 ");
        if (table_name.contains("hnw_")) {
            if (StringUtils.isNotBlank(name)) {
                sql.append(" and product = '" + name + "' ");
            }
            if (StringUtils.isNotBlank(address)) {
                sql.append(" and place like '%" + address + "%' ");
            }
            if (times != null && times.length > 1) {
                sql.append(" and up_time between '" + times[0] + "' and '" + times[1] + "' ");
            }
            if ("hnw_jgpz".equals(table_name)) {
                sql.append(" and category = '" + data.getStr("name") + "' ");
            }
            sql.append(" order by up_time desc ");
        }
        Page<Record> page = Db.paginate(pageNum, pageSize, "select * ", sql.toString());
        Record table = Db.findFirst("select * from sys_tableconfig where tbname = ? ", table_name);
        List<Record> fields = Db.find("select fldname,fldcnn from sys_fieldconfig where fldtbid = ? ", table.getInt("tbid"));
        return Ret.ok("page", page).set("fields", fields);
    }

    public Ret getEach(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择数据");
        }
        Data data1 = Data.service().dao().findById(id);
        if (data1.getType() != 0 && data1.getType() != 1 && data1.getType() != 5 && data1.getType() != 3 && data1.getType() != 4) {
            return Ret.fail("errorMsg", "该数据没有期数列表");
        }
        List<DataEach> dataEaches = DataEach.service().dao().find(DataEach.sql().andDataIdEqualTo(id).example().setOrderBy("data_time desc"));
        String webRootPath = PathKit.getWebRootPath().replace("\\", "/");
        for (int i = 0; i < dataEaches.size(); i++) {
            DataEach data = dataEaches.get(i);
            Integer type1 = data.getType();
            Kv kv = null;
            if (type1 == 0) {
                kv = readShp.readShpXY(webRootPath + "/d/" + data.getUrl().split(":")[1] + "/" + data.getUrl().split(":")[1] + ".shp");
            } else if (type1 == 1 || type1 == 5) {
                kv = ReadTiffUtils.getTiffXY(webRootPath + "/d/" + data.getUrl().split(":")[1] + "/" + data.getUrl().split(":")[1] + ".tif");
            } else if (type1 == 3) {
                kv = ReadTiffUtils.getXmlLatlons(webRootPath + data.getUrl().replace("jpg", "xml"));
            }
//            else if (type1 == 4) {
//                SAXReader reader = new SAXReader();
//                Document doc = null;
//                try {
//                    doc = reader.read(new File(webRootPath + data.getUrl()));
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                // 读取指定标签
//                Element identificationInfo = doc.getRootElement().element("identificationInfo");
//                Element MD_DataIdentification = identificationInfo.element("MD_DataIdentification");
//                Element abstractElement = MD_DataIdentification.element("abstract");
//                String latlons = abstractElement.elementText("CharacterString");
//                kv.set("latlons", latlons);
//            }
            if (kv != null) {
                data.put(kv);
            }
        }
        return Ret.ok("list", dataEaches);
    }

    @Override
    public Ret getTkAllCenter(String type) {
//        Record center = Db.findFirst(" SELECT st_x(ST_Centroid(st_union(geom))) as x,st_y(ST_Centroid(st_union(geom))) as y from tr_tiankuai");
        List<Record> center = null;
        if (StringUtils.isNotBlank(type)) {
            center = Db.find(" SELECT dk_name,id,st_x(ST_Centroid(geom)) as x,st_y(ST_Centroid(geom)) as y from tr_tiankuai where del=0 and name=?", type);
        } else {
            center = Db.find(" SELECT dk_name,id,st_x(ST_Centroid(geom)) as x,st_y(ST_Centroid(geom)) as y from tr_tiankuai where del=0");
        }
        return Ret.ok("center", center);
    }

    @Override
    public Ret getTkCenterAndJson(Integer id) {
        Record center = Db.findFirst("select st_x(ST_Centroid(geom)) as x,st_y(ST_Centroid(geom)) as y from tr_tiankuai where id = ?", id);
        Record geojson = Db.findFirst("select st_asgeojson(geom) from tr_tiankuai where id = ?", id);

        Record record = Db.findFirst("select * from tr_tiankuai where id = ?", id);
        return Ret.ok("center", center).set("geojson", geojson).set("record", record);

    }

    @Override
    public Ret searchSoilAttribute(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择地块");
        }
        Record tiankuai = Db.findFirst("select st_astext(geom) as wkt from tr_tiankuai where id = ?", id);
        GeoServerConfig geoServerConfig = new GeoServerConfig();
        String respath = geoServerConfig.getRespath().replace("\\", "/");
        if (tiankuai != null) {
            try {
                String wkt = tiankuai.getStr("wkt");
//                double cec = ReadTiffUtils.getAltitudeByWkt(wkt, respath + "/土壤/CEC.tif");
//                double zlhl = ReadTiffUtils.getAltitudeByWkt(wkt, respath + "/土壤/黏粒/黏粒含量.tif");
//                double slhl = ReadTiffUtils.getAltitudeByWkt(wkt, respath + "/土壤/砂粒/砂粒含量.tif");
                double yjt = ReadTiffUtils.getAltitudeByWkt(wkt, respath + "/土壤/有机碳/有机碳.tif");
                double ph = ReadTiffUtils.getAltitudeByWkt(wkt, respath + "/土壤/PH.tif");
//                double bctrhl = ReadTiffUtils.getAltitudeByWkt(wkt, respath + "/土壤/表层土砾石含量.tif");

                return Ret.ok("data", Ret.by("有机质", yjt).set("PH", ph)
                        //没有数据部分
                        .set("速效氮", yjt).set("速效钾", yjt).set("速效磷", yjt).set("全氮", yjt));
            } catch (Exception e) {
                e.printStackTrace();
                return Ret.fail("errorMsg", "读取像素值错误，请联系管理员");
            }
        } else {
            return Ret.fail("errorMsg", "地块不存在");
        }
    }

    @Override
    public Ret searchTkByType() {
        List<Record> typesNum = Db.find("select type,count(type) from tr_tiankuai GROUP BY type");
        Record numCount = Db.findFirst("select count(1) as count from tr_tiankuai where del=0");
        Record areaCount = Db.findFirst("SELECT sum(st_area(ST_Transform(geom,4527)))*0.0015 as count from tr_tiankuai");
        List<Record> typesArea = Db.find("SELECT type,sum(st_area(ST_Transform(geom,4527)))*0.0015 as area from tr_tiankuai GROUP BY type");
        return Ret.ok("typesNum", typesNum).set("typesArea", typesArea)
                .set("numCount", numCount.getDouble("count"))
                .set("areaCount", areaCount.getDouble("count") * 0.0015);
    }

    @Override
    public Ret addFeatureForShp(Integer id, JSONObject json, String latlons) {
        UpmsUser user = getUserEntity();
        if (user == null) {
            return Ret.fail("errorMsg", "当前登录用户异常");
        }
        if (id == null) {
            return Ret.fail("errorMsg", "请选择要编辑的矢量数据");
        }
        if (json == null) {
            return Ret.fail("errorMsg", "json不能为空");
        }

        Data data = Data.service().dao().findById(id);
        if (data.getType() != 0) {
            return Ret.fail("errorMsg", "当前数据不可编辑");
        }
        String name = data.getUrl().split(":")[1];
        String webRootPath = PathKit.getWebRootPath().replace("\\", "/");
        try {
            String path = webRootPath + "/d/" + name + "/" + name + ".shp";

            String fileDir = webRootPath + "/d/" + name;
            //压缩
            ZipUtils.compress(fileDir, webRootPath + "/upload/datafile/" + name + ".zip");

            readShp.exportShp(path, name, json, latlons);
            //重新发布并更新url
            Kv kv = IssueShpUtils.uploadShp(webRootPath + "/d/" + name, name);
            data.setUrl("d:" + name);
            data.setTime(new Date());
            data.setUserid(user.getUserId().intValue());
            data.update();
            return Ret.ok("name", name);
        } catch (Exception e) {
            e.printStackTrace();
            return Ret.fail("errorMsg", e.getMessage());
        }
    }

    @Override
    public Ret searchCalendar(Integer month) {
        if (month == null) {
            return Ret.fail("errorMsg", "请选择月份");
        }
//        int status;
//        if (day < 11 && day > 0) {
//            status = 0;
//        }else if (day < 21 && day > 10) {
//            status = 1;
//        }else {
//            status = 2;
//        }
        List<Record> st0 = Db.find("select * from whxx_shengzhang where month = '" + month + "' and status = '0' ");
        List<Record> st1 = Db.find("select * from whxx_shengzhang where month = '" + month + "' and status = '1' ");
        List<Record> st2 = Db.find("select * from whxx_shengzhang where month = '" + month + "' and status = '2' ");
        return Ret.ok("st0", st0).set("st1", st1).set("st2", st2);
    }

    @Override
    public Ret edit(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择要编辑的矢量数据");
        }
        Data data = Data.service().dao().findById(id);
        if (data == null || data.getInt("isedit") != 1) {
            return Ret.fail("errorMsg", "数据不存在或数据不可编辑");
        }
        //获取该数据dbf
        String url = data.getUrl();
        String[] split = url.split(":");
        String dbfPath = PathKit.getWebRootPath() + "/d/" + split[1] + "/" + split[1] + ".dbf";

        Record record = new Record();
        InputStream fis = null;
        try {
            fis = new FileInputStream(dbfPath);
            DBFReader reader = new DBFReader(fis);
            int fieldsCount = reader.getFieldCount();
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                String key = new String(field.getName().getBytes(reader.getCharset()));
                DBFDataType type = field.getType();
                record.set(key, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        record.set("type", "Polygon");
        return Ret.ok("data", record);
    }

    @Override
    public Ret search(String name, Integer type, String[] times, Integer directoryid, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        StringBuffer sql = new StringBuffer("from tr_data where 1 = 1 ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and name like '%" + name + "%' ");
        }
        if (type != null) {
            sql.append(" and type = '" + type + "' ");
        }
        if (times != null && times.length > 1) {
            sql.append(" and time between '" + times[0] + "' and '" + times[1] + "' ");
        }
        if (directoryid != null) {
            sql.append(" and directoryid = '" + directoryid + "' ");
        }
        sql.append(" order by time desc");
        Page<Data> paginate = Data.service().dao().paginate(pageNum, pageSize, "select * ", sql.toString());
//        List<Data> list = paginate.getList();
//        String webRootPath = PathKit.getWebRootPath();
//        for (int i = 0; i < list.size(); i++) {
//            Data data = list.get(i);
//            Integer type1 = data.getType();
//            Kv kv = null;
//            if (type1 == 0) {
//                kv = readShp.readShpXY(webRootPath + "/d/" + data.getUrl().split(":")[1] + "/" + data.getUrl().split(":")[1] + ".shp");
//            } else if (type1 == 1) {
//                kv = ReadTiffUtils.getTiffXY(webRootPath + "/d/" + data.getUrl().split(":")[1] + "/" + data.getUrl().split(":")[1] + ".tif");
//            }
//            if (kv != null) {
//                data.put(kv);
//            }
//        }
        return Ret.ok("page", paginate);
    }

    @Override
    public Ret add(String name, String url, Integer type, Integer directoryid) {
//        JwtUser me = (JwtUser) RequestManager.me().getRequest().getAttribute("me");0
//        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(me.getUserName()).example());
//
//        Data data = new Data();
//        data.setName(name);
//        data.setType(type);
//        data.setDel(0);
//        data.setDirectoryid(directoryid);
//        data.setUrl(url);
//        data.setTime(new Date());
//        data.setUserid(upmsUser.getUserId().intValue());
//        data.save();
        return Ret.ok("msg", "添加成功");
    }

    @Override
    public Ret del(Integer[] ids) {
        if (ids == null) {
            return Ret.fail("errorMsg", "请选择要删除的数据");
        }
        //删除geoserver服务
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        String RESTURL = config.getGeourl();
        String RESTUSER = config.getGeouser();
        String RESTPW = config.getGeopsw();
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        for (int i = 0; i < ids.length; i++) {
            Data data = Data.service().dao().findById(ids[i]);
            if (data != null) {
                data.delete();
                //删除文件
                String webRootPath = PathKit.getWebRootPath();
                File file;
                if (data.getType() == 2) {
                    file = new File(webRootPath + data.getUrl());
                } else {
                    publisher.removeLayer("d", data.getUrl().split(":")[1]);
                    file = new File(webRootPath + "/d/" + data.getUrl().split(":")[1]);
                }
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        return Ret.ok("msg", "删除成功");
    }
}