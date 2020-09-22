package com.atlas.test;

import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.geotools.data.FeatureWriter;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.Name;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Test1 {

    public static void main(String[] args) {
        try {
            String path = "C:\\Users\\xiaoxu\\Desktop\\ld_province\\ld_province.shp";
            String filename = "C:\\Users\\xiaoxu\\Desktop\\ld_province2\\d-" + UUID.randomUUID().toString() + ".shp";
//        Object buffer = buffer(path, filename);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sdm","111");
            jsonObject.put("PNAME","省份");
            exportShp(path,filename,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean exportShp(String path,String shpPath,JSONObject jsonObject) throws Exception {
        //源文件
        ShapefileDataStore shapeDS = (ShapefileDataStore) new ShapefileDataStoreFactory().createDataStore(new File(path).toURI().toURL());
        SimpleFeatureSource fs = shapeDS.getFeatureSource(shapeDS.getTypeNames()[0]);
        SimpleFeatureCollection featureCollection = fs.getFeatures();
        FeatureIterator<SimpleFeature> iterator = featureCollection.features();
        SimpleFeatureType pgfeaturetype = fs.getSchema();
        //新文件
        File file = new File(shpPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        Map<String, Serializable> params = new HashMap<String, Serializable>();
        params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
        ShapefileDataStore shpDataStore = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);

        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.init(pgfeaturetype);
        typeBuilder.setCRS(CRS.decode("EPSG:4326", true));
        pgfeaturetype = typeBuilder.buildFeatureType();
        shpDataStore.setCharset(shapeDS.getCharset());
        shpDataStore.createSchema(pgfeaturetype);

        //创建新feature
        GeometryFactory geometryFactory = new GeometryFactory();
        GeometryBuilder gb = new GeometryBuilder(geometryFactory);
        //创建一个点geometry
        Polygon polygon = gb.polygon(112, 112, 112, 35, 35, 35, 35, 112);
//        Polygon polygon = geometryFactory.createPolygon(new Coordinate(123, 32));
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(pgfeaturetype);
        //添加的数据一定按照SimpleFeatureType给的字段顺序进行赋值
        //添加geometry属性
        int attributeCount = pgfeaturetype.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String name = pgfeaturetype.getDescriptor(i).getName().toString();
            System.out.println(pgfeaturetype.getDescriptor(i).getName());
            if("the_geom".equals(name)){
                featureBuilder.add(polygon);
            }else{
                featureBuilder.add(jsonObject.get(name));
            }
        }
        SimpleFeature simpleFeature1 = featureBuilder.buildFeature(null);

        FeatureWriter<SimpleFeatureType, SimpleFeature> featureWriter = shpDataStore.getFeatureWriter(shpDataStore.getTypeNames()[0], Transaction.AUTO_COMMIT);
        //添加源数据
        while (iterator.hasNext()) {
            Feature feature = iterator.next();
            SimpleFeature simpleFeature = featureWriter.next();
            simpleFeature.setValue(feature.getProperties());
            featureWriter.write();
            //添加新要素
            if(!iterator.hasNext()){
                SimpleFeature next = featureWriter.next();
                feature.setValue(simpleFeature1.getProperties());
                next.setValue(feature.getProperties());
                featureWriter.write();
            }
        }


        iterator.close();
        featureWriter.close();
        return true;
    }


//    public static Object buffer(String path, String newPath) {
//        List<Map<String, Object>> list = new ArrayList<>();
//        long start = System.currentTimeMillis();
//
////        String webRootPath = PathKit.getWebRootPath();
////        String shpfile = webRootPath + "/d/pointgbk.shp";
////        String buffile = webRootPath + "/d/pointgbkbuffer.shp";
//
//        try {
//            //读取shp文件
//            File file = new File(path);
//            ShapefileDataStore shpDataStore = null;
//            shpDataStore = new ShapefileDataStore(file.toURL());
//            //设置编码
//            Charset charset = Charset.forName("GBK");
//            shpDataStore.setCharset(charset);
//            String typeName = shpDataStore.getTypeNames()[0];
//            SimpleFeatureSource featureSource = null;
//            featureSource = shpDataStore.getFeatureSource(typeName);
//            SimpleFeatureCollection result = featureSource.getFeatures();
//            SimpleFeatureIterator itertor = result.features();
//
////            创建shape文件对象
//            File fileBuf = new File(newPath);
//            Map<String, Serializable> params = new HashMap<String, Serializable>();
//            params.put(ShapefileDataStoreFactory.URLP.key, fileBuf.toURI().toURL());
//            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
////            获取原shp文件字段名
//            SimpleFeatureType sft = featureSource.getSchema();
//            List<AttributeDescriptor> attrs = sft.getAttributeDescriptors();
//
//            //定义图形信息和属性信息
//            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
////            tb.setCRS(DefaultGeographicCRS.WGS84);
//            tb.setName("shapefile");
//            for (int i = 0; i < attrs.size(); i++) {
//                AttributeDescriptor attr = attrs.get(i);
//                String fieldName = attr.getName().toString();
//                if (fieldName == "the_geom") {
//                    tb.add(fieldName, Polygon.class);
//                } else {
//                    tb.add(fieldName, String.class);
//                }
//            }
//            ds.createSchema(tb.buildFeatureType());
//            //设置编码
//            ds.setCharset(charset);
//            //设置Writer，遍历原feature，添加到新feature
//            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = shpDataStore.getFeatureWriter(typeName, Transaction.AUTO_COMMIT);
//
//            while (itertor.hasNext()) {
//                SimpleFeature feature = itertor.next();
//                SimpleFeature featureBuf = writer.next();
//                featureBuf.setAttributes(feature.getAttributes());
//                //Object temp=feature.getAttribute("the_geom");
//                //更新the_geom字段，改为buffer后的polygon
//                Object the_geom = feature.getAttribute("the_geom");
//
//                featureBuf.setAttribute("the_geom", the_geom);
//                Map<String, Object> map = new HashMap<>();
//                map.put("wkt", the_geom.toString());
//                list.add(map);
//            }
//            if (writer.hasNext()) {
//                writer.write();
//            }
//            writer.close();
//            itertor.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("共耗时" + (System.currentTimeMillis() - start) + "ms");
//
//        return list;
//    }

    /**
     * 获取到输出的ds
     *
     * @param targetFilePath
     * @param sourceSchema   指定表映射
     * @return
     * @throws IOException
     */
    public static ShapefileDataStore getOutputDataStore(String targetFilePath, SimpleFeatureType sourceSchema) {
        try {
            File targetFile = new File(targetFilePath);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            Map<String, Serializable> params1 = new HashMap<String, Serializable>();
            params1.put(ShapefileDataStoreFactory.URLP.key, targetFile.toURI().toURL());
            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params1);
            // create new schema
            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setName(sourceSchema.getName());
            builder.setSuperType((SimpleFeatureType) sourceSchema.getSuper());
            //自定义字段名
            builder.add("rowId", Integer.class);
            builder.add("你好", Integer.class);
            builder.add("sId", String.class);
            builder.add("curFld", String.class);
            builder.add("哈哈", String.class);
            builder.add("refLayCode", String.class);
            builder.add("refLayer", String.class);
            builder.add("errDesc", String.class);
            builder.add(sourceSchema.getDescriptor("the_geom"));
            SimpleFeatureType nSchema = builder.buildFeatureType();
            ds.createSchema(SimpleFeatureTypeBuilder.retype(nSchema, DefaultGeographicCRS.WGS84));//将图层表头设置给target shape
            ds.setCharset(ds.getCharset());
            return ds;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        SimpleFeatureCollection polygonCollection = null;
//        SimpleFeatureCollection fcResult = null;
//        final DefaultFeatureCollection found = new DefaultFeatureCollection();
//
//        FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
//        SimpleFeature feature = null;
//
//        Filter polyCheck = null;
//        Filter andFil = null;
//        Filter boundsCheck = null;
//
//        String qryStr = null;
//
//        SimpleFeatureIterator it = polygonCollection.features();
//        try {
//            while (it.hasNext()) {
//                feature = it.next();
//                BoundingBox bounds = feature.getBounds();
//                boundsCheck = ff.bbox(ff.property("the_geom"), bounds);
//
//                Geometry geom = (Geometry) feature.getDefaultGeometry();
//                polyCheck = ff.intersects(ff.property("the_geom"), ff.literal(geom));
//
//                andFil = ff.and(boundsCheck, polyCheck);
//
//                try {
//                    fcResult = featureSource.getFeatures(andFil);
//                    // go through results and copy out the found features
//                    fcResult.accepts(
//                            new FeatureVisitor() {
//                                public void visit(Feature feature) {
//                                    found.add((SimpleFeature) feature);
//                                }
//                            },
//                            null);
//                } catch (IOException e1) {
//                    System.out.println("Unable to run filter for " + feature.getID() + ":" + e1);
//                    continue;
//                }
//            }
//        } finally {
//            it.close();
//        }
//
//    }
}
