package com.soli.server.utils;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.TimeUtils;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.data.*;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.shapefile.shp.JTSUtilities;
import org.geotools.data.shapefile.shp.ShapeType;
import org.geotools.data.shapefile.shp.ShapefileHeader;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;

import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.*;

public class readShp {

    public static void main(String[] args) {
        //transShape("C:/Users/xuzhonghao/Desktop/North_China_Albers_ge/North_China_Albers_ge.shp", "C:/Users/xuzhonghao/Desktop/newshp/newshp.shp");

    }

    public void addFeatureToShp(String srcfilepath) throws IOException {
        try{
            //定义属性
            final SimpleFeatureType TYPE = DataUtilities.createType("Location",
                    "location:Point," + // <- the geometry attribute: Point type
                            "POIID:String," + // <- a String attribute
                            "MESHID:String," + // a number attribute
                            "OWNER:String"
            );
            SimpleFeatureCollection collection = FeatureCollections.newCollection();
            GeometryFactory geometryFactory = new GeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            double latitude = Double.parseDouble("116.123456789");
            double longitude = Double.parseDouble("39.120001");
            String POIID = "2050003092";
            String MESHID = "0";
            String OWNER = "340881";
            Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            Object[] obj = {point, POIID, MESHID, OWNER};

            SimpleFeatureIterator iterator = collection.features();
            try {
                while( iterator.hasNext() ){
                    SimpleFeature feature = iterator.next();
                    System.out.println( feature.getID() );
                }
            }
            finally {
                iterator.close();
            }
//            SimpleFeature feature = featureBuilder.buildFeature(null, obj);
//            collection.add(feature);
//            feature = featureBuilder.buildFeature(null, obj);
//            collection.add(feature);
            File newFile = new File("D:/newPoi.shp");
            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);
            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.createSchema(TYPE);
            newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

            Transaction transaction = new DefaultTransaction("create");
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transShape(String srcfilepath, String destfilepath) {
        try {
            //源shape文件
            ShapefileDataStore shapeDS = (ShapefileDataStore) new ShapefileDataStoreFactory().createDataStore(new File(srcfilepath).toURI().toURL());
            //创建目标shape文件对象
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            FileDataStoreFactorySpi factory = new ShapefileDataStoreFactory();
            params.put(ShapefileDataStoreFactory.URLP.key, new File(destfilepath).toURI().toURL());
            ShapefileDataStore ds = (ShapefileDataStore) factory.createNewDataStore(params);
            // 设置属性
            SimpleFeatureSource fs = shapeDS.getFeatureSource(shapeDS.getTypeNames()[0]);
            //下面这行还有其他写法，根据源shape文件的simpleFeatureType可以不用retype，而直接用fs.getSchema设置
//            ds.createSchema(SimpleFeatureTypeBuilder.retype(fs.getSchema(), GeographicCRS.));
            ds.createSchema(SimpleFeatureTypeBuilder.retype(fs.getSchema(), DefaultGeographicCRS.WGS84));

            //设置writer
            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);

            //写记录
            SimpleFeatureIterator it = fs.getFeatures().features();
            try {
                while (it.hasNext()) {
                    SimpleFeature f = it.next();
                    SimpleFeature fNew = writer.next();
                    fNew.setAttributes(f.getAttributes());
                    writer.write();
                }
            } finally {
                it.close();
            }
            writer.close();
            ds.dispose();
            shapeDS.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Record> readDBF(String path) {
        List<Record> records = new ArrayList<>();
        InputStream fis = null;
        try {
            // 读取文件的输入流
            fis = new FileInputStream(path);
            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);
//            reader.setCharactersetName(codeString);
            // System.out.println(reader.getCharset());
            // 调用DBFReader对实例方法得到path文件中字段的个数
            int fieldsCount = reader.getFieldCount();
            // 取出字段信息
//            for (int i = 0; i < fieldsCount; i++) {
//                records.add(output);
//            }
            Object[] rowValues; // 一条条取出path文件中记录
            while ((rowValues = reader.nextRecord()) != null) {
                Record record = new Record();
                for (int i = 0; i < rowValues.length; i++) {
                    String val = new String(rowValues[i].toString().getBytes(reader.getCharset()));
                    DBFField field = reader.getField(i);
                    String key = new String(field.getName().getBytes(reader.getCharset()));
                    record.set(key, val);
                }
                records.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return records;
    }

    public static void write(String filepath) {
        try {
            //创建shape文件对象
            File file = new File(filepath);
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
            //定义图形信息和属性信息
            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
            tb.setCRS(DefaultGeographicCRS.WGS84);
            tb.setName("shapefile");
            tb.add("the_geom", Point.class);
            tb.add("POIID", Long.class);
            tb.add("NAMEC", String.class);
            ds.createSchema(tb.buildFeatureType());
            ds.setCharset(Charset.forName("GBK"));
            //设置Writer
            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);
            //写下一条
            SimpleFeature feature = writer.next();
            feature.setAttribute("the_geom", new GeometryFactory().createPoint(new Coordinate(116.123, 39.345)));
            feature.setAttribute("POIID", 1234567890l);
            feature.setAttribute("NAMEC", "某兴趣点1");
            feature = writer.next();
            feature.setAttribute("the_geom", new GeometryFactory().createPoint(new Coordinate(116.456, 39.678)));
            feature.setAttribute("POIID", 1234567891l);
            feature.setAttribute("NAMEC", "某兴趣点2");
            writer.write();
            writer.close();
            ds.dispose();

            //读取刚写完shape文件的图形信息
            ShpFiles shpFiles = new ShpFiles(filepath);
            ShapefileReader reader = new ShapefileReader(shpFiles, false, true, new GeometryFactory(), false);
            try {
                while (reader.hasNext()) {
                    System.out.println(reader.nextRecord().shape());
                }
            } finally {
                reader.close();
            }
        } catch (Exception e) {
        }
    }

    public static Kv readShpXY1(String path) {
        double maxX = 0;
        double maxY = 0;
        double minX = 0;
        double minY = 0;
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        try {
            ShpFiles sf = new ShpFiles(path);
            ShapefileReader r = new ShapefileReader(sf, false, false, new GeometryFactory());
            ShapefileHeader header = r.getHeader();
            maxX = header.maxX();
            maxY = header.maxY();
            minX = header.minX();
            minY = header.minY();
            System.out.println("maxX:" + maxX);
            System.out.println("maxY:" + maxY);
            System.out.println("minX:" + minX);
            System.out.println("minY:" + minY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Kv.by("xy", Kv.by("maxX", maxX).set("maxY", maxY).set("minX", minX).set("minY", minY));
    }


    public static Kv readShpXY(String path) {
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        double maxX = 0;
        double maxY = 0;
        double minX = 0;
        double minY = 0;

        try {
//			//第一种方式，获取shapefile所有同名文件句柄(如果存在读取，否则新建)
//			ShapefileDataStore sds = (ShapefileDataStore) dataStoreFactory.createDataStore(new File(path).toURI().toURL());
//			sds.setCharset(Charset.forName("GBK"));
//
//			//第二种方式，获取shapefile所有同名文件句柄
//			ShapefileDataStore shpDataStore = new ShapefileDataStore(new File(path).toURI().toURL());
//			shpDataStore.setCharset(Charset.forName("GBK"));
//			SimpleFeatureSource featureSource2 = shpDataStore.getFeatureSource();
//			SimpleFeatureIterator itertor2 = featureSource2.getFeatures().features();

            //第三种方式，获取shapefile矢量文件
            ShpFiles sf = new ShpFiles(path);
            ShapefileReader r = new ShapefileReader(sf, false, false, new GeometryFactory());
            ShapefileHeader header = r.getHeader();
            maxX = header.maxX();
            maxY = header.maxY();
            minX = header.minX();
            minY = header.minY();
            System.out.println("maxX:" + maxX);
            System.out.println("maxY:" + maxY);
            System.out.println("minX:" + minX);
            System.out.println("minY:" + minY);
//			while (r.hasNext()) {
//				Geometry shape = (Geometry) r.nextRecord().shape();  //com.vividsolutions.jts.geom.Geometry;
//				System.out.println(shape.toString());
//			}
//			r.close();

//			//获取shp+shx矢量要素文件句柄
//			SimpleFeatureSource featureSource = sds.getFeatureSource();
//			//获取矢量要素
//			SimpleFeatureIterator itertor = featureSource.getFeatures().features();
//
//			while(itertor.hasNext()) {
//				SimpleFeature feature = itertor.next();
//				Iterator<Property> it = feature.getProperties().iterator();
//
//				while(it.hasNext()) {
//					Property pro = it.next();
//					System.out.println(pro);
//				}
//			}
//			itertor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Kv.by("maxX", maxX).set("maxY", maxY).set("minX", minX).set("minY", minY);
    }

    public String checktype(String url) {
        ShapefileDataStore shpDataStore = null;
        TimeUtils.startTime("read Shp feature");
        String resPath = PathKit.getWebRootPath() + "\\jsus";
        url = url.substring(5, 30);
        List<Object> li = new ArrayList<Object>();
        try {
            File file = new File(resPath + "\\jsus\\" + url + "\\" + url + ".shp");
            System.out.println(resPath + "\\jsus\\" + url + "\\" + url + ".shp");
            if (!file.exists())
                return "";
            shpDataStore = new ShapefileDataStore(file.toURI().toURL());
            // 设置字符编码
            Charset charset = Charset.forName("gbk");
            shpDataStore.setCharset(charset);
            String typeName = shpDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = null;
            featureSource = shpDataStore.getFeatureSource(typeName);
            SimpleFeatureCollection result = featureSource.getFeatures();
            SimpleFeatureIterator itertor = result.features();
            // int i=0;
            while (itertor.hasNext()) {
                SimpleFeature feature = itertor.next();
                // FeatureId fid = feature.getIdentifier();
                String id = feature.getID();
                Collection<Property> p = feature.getProperties();
                Iterator<Property> it = p.iterator();
                // int j=0;
                String geom = "";
                ShapeType shpType = null;
                // System.out.print("[id:"+id+"{");
                while (it.hasNext()) {
                    Property pro = it.next();
                    Object objval = pro.getValue();

                    String field = pro.getName().toString();

                    if (field.equals("the_geom") && objval instanceof Geometry) {
                        shpType = JTSUtilities.getShapeType((Geometry) objval, 2);
                    }
                    /*
                     * System.out.print("{name:"+field); System.out.print(",value:"+value);
                     * System.out.print(",type:" +objval.getClass().getName());
                     * System.out.print("}, ");
                     */

                    if (!field.equals("wkt")) {
                        // System.out.println(field+"="+value);
                    } else {
                        String value = pro.getValue().toString();
                        geom = value;
                        // System.out.println(geom);
                    }
                }
                // System.out.println("}]");
                itertor.close();
                TimeUtils.endTime("read Shp feature");
                if (shpType != null) {
                    System.out.println("shp的地图类型是：" + shpType.name);
                    li.add(shpType.name);
                    return li.get(0).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
