package com.atlas.test;

import com.alibaba.fastjson.JSONObject;
import com.soli.server.utils.GeometryRelated;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import it.geosolutions.jaiext.range.NoDataContainer;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.resources.coverage.CoverageUtilities;
import org.junit.Test;
import org.opengis.coverage.ColorInterpretation;
import org.opengis.coverage.PaletteInterpretation;
import org.opengis.coverage.SampleDimension;
import org.opengis.coverage.SampleDimensionType;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Envelope;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValue;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.cs.CoordinateSystem;
import org.opengis.util.InternationalString;

import javax.measure.unit.Unit;
import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Test1 {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testTif() throws Exception {
        String path = "C:\\Users\\xiaoxu\\Desktop\\1002\\GST010_7E3797.tif";
        String writePath = "C:\\Users\\xiaoxu\\Desktop\\1002\\writePath.tif";
        String latlons = "111 33,119 33,117 30,110 30,111 33";

        GeoTiffReader reader = new GeoTiffReader(new File(path));
        GridCoverage2D coverage = reader.read(null);
        RenderedImage sourceImage = coverage.getRenderedImage();
        TiledImage tiledImage = new TiledImage(sourceImage, true);
        Raster sourceRaster = sourceImage.getData();
//            WritableRaster writableRaster = tiledImage.copyData();
        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
        //波段数量
        int numBands = sourceRaster.getNumBands();
        List<Double> lons = new ArrayList<>();
        List<Double> lats = new ArrayList<>();

        String[] split = latlons.split(",");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            lons.add(Double.parseDouble(s.split(" ")[0]));
            lats.add(Double.parseDouble(s.split(" ")[1]));
        }
        Double min_lon = Collections.min(lons);
        Double max_lon = Collections.max(lons);
        Double min_lat = Collections.min(lats);
        Double max_lat = Collections.max(lats);
        // 通过地理坐标获取行列号
        DirectPosition position1 = new DirectPosition2D(crs, max_lon, max_lat);
        Point2D point2d1 = coverage.getGridGeometry().worldToGrid(position1);
        int max_x = (int) point2d1.getX();
        int max_y = (int) point2d1.getY();
        DirectPosition position2 = new DirectPosition2D(crs, min_lon, min_lat);
        Point2D point2d2 = coverage.getGridGeometry().worldToGrid(position2);
        int min_x = (int) point2d2.getX();
        int min_y = (int) point2d2.getY();

        Map<?, ?> props = coverage.getProperties();
        NoDataContainer nodata = (NoDataContainer) props.get(NoDataContainer.GC_NODATA);

        //获取float[][] data
        Double noDataValues = sampleDimension.getNoDataValues()[0];
        System.out.println(noDataValues);
        System.out.println(max_x - min_x);
        System.out.println(min_y - max_y);
        float[][] data = new float[min_y - max_y][max_x - min_x];
        for (int i = min_x; i < max_x; i++) {
            for (int j = max_y; j < min_y; j++) {
                GridCoordinates2D coord = new GridCoordinates2D(i, j);
                DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                double lon = tmpPos.getCoordinate()[0];
                double lat = tmpPos.getCoordinate()[1];
                boolean iscontains = GeometryRelated.withinGeo(lon, lat, "POLYGON((" + latlons + "))");
                if (iscontains) {
                    //面内，赋值像素值
                    float[] sss = (float[]) coverage.evaluate(tmpPos);
                    float s = sss[0];
                    if (Math.abs(s - noDataValues.floatValue()) > 1e-6) {
                        data[j - max_y][i - min_x] = s;
                    } else {
                        data[j - max_y][i - min_x] = noDataValues.floatValue();
                    }
                } else {
                    data[j - max_y][i - min_x] = noDataValues.floatValue();
                }
            }
        }
        //导出tiff
        ReferencedEnvelope envelope = new ReferencedEnvelope(min_lon, max_lon, min_lat, max_lat, crs);
        exportTIFF(writePath, data, envelope, noDataValues.floatValue());
    }

    private void exportTIFF(String outputPath, float[][] data, Envelope env, float nodate) throws Exception {
        GridCoverageFactory factory = new GridCoverageFactory();
        HashMap properties = new HashMap<>();
        CoverageUtilities.setNoDataProperty(properties, new NoDataContainer(nodate));
        GridCoverage2D outputCoverage = factory.create("test", data, env);
        GridCoverage2D nodata = factory.create("test", outputCoverage.getRenderedImage(), outputCoverage.getEnvelope(), outputCoverage.getSampleDimensions(), null, properties);

        GeoTiffWriter writer = new GeoTiffWriter(new File(outputPath));
        writer.write(nodata, null);
        writer.dispose();
    }


    @Test
    public void testABS() {
        float a = (float) 1.0E36;
        float b = (float) 9.999999616903162E35;
        if (Math.abs(a - b) < 1e-6) {
            System.out.println(true);
        }
    }

    @Test
    public void testbe() throws ParseException {
        int year = 2020;
        int m = 1;//月份计数
        while (m < 13) {
            int month = m;
            Calendar cal = Calendar.getInstance();//获得当前日期对象
            cal.clear();//清除信息
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);//1月从0开始
            cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天

            System.out.println("##########___" + sdf.format(cal.getTime()));

            int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            System.out.println("$$________" + count);

            for (int j = 0; j <= (count - 2); ) {
                cal.add(Calendar.DAY_OF_MONTH, +1);
                j++;
                System.out.println(sdf.format(cal.getTime()));
            }
            m++;
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date dBegin = sdf.parse("2019-10-20");
//        Date dEnd = sdf.parse("2020-10-20");
////            List lDate = new ArrayList();
////            lDate.add(dBegin);
//            Calendar calBegin = Calendar.getInstance();
//            // 使用给定的 Date 设置此 Calendar 的时间
//            calBegin.setTime(dBegin);
//            Calendar calEnd = Calendar.getInstance();
//            // 使用给定的 Date 设置此 Calendar 的时间
//            calEnd.setTime(dEnd);
//            // 测试此日期是否在指定日期之后
//            while (dEnd.after(calBegin.getTime())) {
//                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
//                calBegin.add(Calendar.DAY_OF_MONTH, 1);
//                Date time = calBegin.getTime();
//                System.out.println(sdf.format(time));
//            }
//            return lDate;
    }

    @Test
    public void tets() {
        {
            int year = 2013;
            int m = 1;//月份计数
            while (m < 13) {
                int month = m;
                Calendar cal = Calendar.getInstance();//获得当前日期对象
                cal.clear();//清除信息
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month - 1);//1月从0开始
                cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天

                System.out.println("##########___" + sdf.format(cal.getTime()));

                int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                System.out.println("$$________" + count);

                for (int j = 0; j <= (count - 2); ) {
                    cal.add(Calendar.DAY_OF_MONTH, +1);
                    j++;
                    System.out.println(sdf.format(cal.getTime()));
                }
                m++;
            }
        }
    }


    /**
     * 某一年某个月的每一天
     */
    public static List<String> getMonthFullDay(int year, int month, int day) {
        List<String> fullDayList = new ArrayList<String>();
        if (day <= 0) day = 1;
        Calendar cal = Calendar.getInstance();// 获得当前日期对象
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);// 1月从0开始
        cal.set(Calendar.DAY_OF_MONTH, day);// 设置为1号,当前日期既为本月第一天
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 0; j <= (count - 1); ) {
            if (sdf.format(cal.getTime()).equals(getLastDay(year, month)))
                break;
            cal.add(Calendar.DAY_OF_MONTH, j == 0 ? +0 : +1);
            j++;
            fullDayList.add(sdf.format(cal.getTime()));
        }
        return fullDayList;
    }

    public static String getLastDay(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return sdf.format(cal.getTime());
    }

    public static void main(String[] args) {
        try {
            String path = "C:\\Users\\xiaoxu\\Desktop\\ld_province\\ld_province.shp";
            String filename = "C:\\Users\\xiaoxu\\Desktop\\ld_province2\\d-" + UUID.randomUUID().toString() + ".shp";
//        Object buffer = buffer(path, filename);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sdm", "111");
            jsonObject.put("PNAME", "省份");
            exportShp(path, filename, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean exportShp(String path, String shpPath, JSONObject jsonObject) throws Exception {
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
            if ("the_geom".equals(name)) {
                featureBuilder.add(polygon);
            } else {
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
            if (!iterator.hasNext()) {
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
