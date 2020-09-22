
package com.soli.server.utils;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.factory.Hints;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import java.awt.geom.Point2D;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.pow;

public class ReadTiffUtils {

    /**
     * 点查询指定URl 的 tif
     */
    public static double getAltitude(Double lon, Double lat, String url) throws Exception {
        File file = new File(url);
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
        // 构建地理坐标
        DirectPosition position = new DirectPosition2D(crs, lon, lat);
        Object results = coverage.evaluate(position);
        Double objectClass = getObjectClass(results);
        return objectClass;
    }

    /**
     * 面查询指定URl 的 tif
     */
    public static double getAltitudeByWkt(String wkt, String url) throws TransformException, IOException, ParseException {
        File file = new File(url);
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
        List<Double> lons = new ArrayList<>();
        List<Double> lats = new ArrayList<>();

        WKTReader reader = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326));
        Polygon polygon = (Polygon) reader.read(wkt);

        Double min_lon = polygon.getEnvelopeInternal().getMinX();
        Double max_lon = polygon.getEnvelopeInternal().getMaxX();
        Double min_lat = polygon.getEnvelopeInternal().getMinY();
        Double max_lat = polygon.getEnvelopeInternal().getMaxY();
        // 通过地理坐标获取行列号
        DirectPosition position1 = new DirectPosition2D(crs, max_lon, max_lat);
        Point2D point2d1 = coverage.getGridGeometry().worldToGrid(position1);
        int max_x = (int) point2d1.getX();
        int max_y = (int) point2d1.getY();
        DirectPosition position2 = new DirectPosition2D(crs, min_lon, min_lat);
        Point2D point2d2 = coverage.getGridGeometry().worldToGrid(position2);
        int min_x = (int) point2d2.getX();
        int min_y = (int) point2d2.getY();

        double sum = 0;
        int count = 0;
        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        double nodData = sampleDimension.getMinimumValue();
        for (int i = min_x; i < max_x; i++) {
            for (int j = max_y; j < min_y; j++) {
                GridCoordinates2D coord = new GridCoordinates2D(i, j);
                DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                double lon = tmpPos.getCoordinate()[0];
                double lat = tmpPos.getCoordinate()[1];
                boolean iscontains = GeometryRelated.withinGeo(lon, lat, wkt);
                if (iscontains) {
                    Double pix = getObjectClass(coverage.evaluate(tmpPos));
                    if (nodData != pix) {
                        //加入计算平均值
                        sum = sum + pix;
                        count++;
                    }
                }
            }
        }

        if (count == 0) {
            return 0;
        } else {
            return Arith.div(sum, count, 2);
        }


    }

    /**
     * 面查询指定URl 的 tif
     */
    public static double getAltitudeList(String latlons, String url) throws TransformException, IOException, ParseException {
        File file = new File(url);
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
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

        double sum = 0;
        int count = 0;
        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        double nodData = sampleDimension.getMinimumValue();
        for (int i = min_x; i < max_x; i++) {
            for (int j = max_y; j < min_y; j++) {
                GridCoordinates2D coord = new GridCoordinates2D(i, j);
                DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                double lon = tmpPos.getCoordinate()[0];
                double lat = tmpPos.getCoordinate()[1];
                boolean iscontains = GeometryRelated.withinGeo(lon, lat, "POLYGON((" + latlons + "))");
                if (iscontains) {
                    Double pix = getObjectClass(coverage.evaluate(tmpPos));
                    if (nodData != pix) {
                        //加入计算平均值
                        sum = sum + pix;
                        count++;
                    }
                }
            }
        }

        if (count == 0) {
            return 0;
        } else {
            return Arith.div(sum, count, 2);
        }


    }

    public static double getByGeom(String geom, String url) throws Exception {
        File file = new File(url);
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();

        Envelope2D coverageEnvelope = coverage.getEnvelope2D();
        int min_x = (int) coverageEnvelope.getBounds().getMinX();
        int max_x = (int) coverageEnvelope.getBounds().getMaxX();
        int max_y = (int) coverageEnvelope.getBounds().getMinY();
        int min_y = (int) coverageEnvelope.getBounds().getMaxY();

        double sum = 0;
        int count = 0;
        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        double nodData = sampleDimension.getMinimumValue();
        for (int i = min_x; i < max_x; i++) {
            for (int j = max_y; j < min_y; j++) {
                GridCoordinates2D coord = new GridCoordinates2D(i, j);
                DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                double lon = tmpPos.getCoordinate()[0];
                double lat = tmpPos.getCoordinate()[1];
                boolean iscontains = GeometryRelated.withinGeo(lon, lat, geom);
                if (iscontains) {
                    Double pix = getObjectClass(coverage.evaluate(tmpPos));
                    if (nodData != pix) {
                        //加入计算平均值
                        sum = sum + pix;
                        count++;
                    }
                }
            }
        }

        if (count == 0) {
            return 0;
        } else {
            return Arith.div(sum, count, 2);
        }
    }

    public static Double getObjectClass(Object param) {
        Double d = 0.0;
        if ((param instanceof Integer[]) || (param instanceof int[])) {
            int[] b = (int[]) param;
            d = Double.valueOf(b[0]);
        } else if (param instanceof String[]) {
            String[] b = (String[]) param;
            d = Double.valueOf(b[0]);
        } else if (param instanceof Double[] || param instanceof double[]) {
            Double[] b = (Double[]) param;
            d = Double.valueOf(b[0]);
        } else if (param instanceof Float[] || param instanceof float[]) {
            float[] b = (float[]) param;
            d = Double.valueOf(b[0]);
        } else if (param instanceof Long[] || param instanceof long[]) {
            long[] b = (long[]) param;
            d = Double.valueOf(b[0]);
        } else if (param instanceof byte[] || param instanceof Byte[]) {
            byte[] b = (byte[]) param;
            d = Double.valueOf(b[0]);
        }
//        System.out.println("===");
        return d;
    }

    public static double getBioList(String latlons) throws Exception {
        String demPath = PathKit.getWebRootPath() + "/upload/cover/bio_2019.tif";
        File file = new File(demPath);
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        // 默认坐标系EPSG:3857
        // tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM,
        // CRS.decode("EPSG:4326")));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));

        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();

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

        double sum = 0;
        int count = 0;
        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        double nodData = sampleDimension.getMinimumValue();
        for (int i = min_x; i < max_x; i++) {
            for (int j = max_y; j < min_y; j++) {
                GridCoordinates2D coord = new GridCoordinates2D(i, j);
                DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                double lon = tmpPos.getCoordinate()[0];
                double lat = tmpPos.getCoordinate()[1];
//                Record first = Db.findFirst("select ST_Contains( st_geometryfromtext('polygon ((" + latlons + "))',4326) " +
//                        ",st_geometryfromtext('POINT(" + lon + " " + lat + ")',4326)) as iscontains");
//                Boolean iscontains = first.getBoolean("iscontains");
                boolean iscontains = GeometryRelated.withinGeo(lon, lat, "POLYGON((" + latlons + "))");
                if (iscontains) {
                    float[] sss = (float[]) coverage.evaluate(tmpPos);
                    float s = sss[0];
                    if (nodData != s) {
                        //加入计算平均值
                        sum = sum + s;
                        count++;
                    }
                }
            }
        }
        if (count == 0) {
            return 0;
        } else {
            return Arith.div(sum, count, 2);
        }
    }

    public static Kv getTiffXY(String strImageUrl) {
        double coverageMinX = 0.0;
        double coverageMaxX = 0.0;
        double coverageMinY = 0.0;
        double coverageMaxY = 0.0;
        try {
            String ImageUrl = strImageUrl.replace("\\", "\\\\");
            ;
            File ImageFile = new File(ImageUrl);
            System.out.println(ImageUrl);
            System.out.println("f:" + ImageFile.getName());
            GeoTiffReader reader = new GeoTiffReader(ImageFile);
            GridCoverage2D coverage = reader.read(null);
            Envelope2D coverageEnvelope = coverage.getEnvelope2D();

            coverageMinX = coverageEnvelope.getBounds().getMinX();
            coverageMaxX = coverageEnvelope.getBounds().getMaxX();
            coverageMinY = coverageEnvelope.getBounds().getMinY();
            coverageMaxY = coverageEnvelope.getBounds().getMaxY();
            System.out.println("coverageMinX：" + coverageMinX);
            System.out.println("coverageMaxX：" + coverageMaxX);
            System.out.println("coverageMinY：" + coverageMinY);
            System.out.println("coverageMaxY：" + coverageMaxY);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Kv.by("code", 400).set("msg", e.getMessage());
        }
        return Kv.by("minX", coverageMinX).set("maxX", coverageMaxX)
                .set("minY", coverageMinY).set("maxY", coverageMaxY).set("code", 200);
    }

    public static String GetImageExt(String strImageUrl) {
        String strExtent = "";
        try {
            String ImageUrl = strImageUrl.replace("\\", "\\\\");
            ;
            File ImageFile = new File(ImageUrl);
            GeoTiffReader reader = new GeoTiffReader(ImageFile);
            GridCoverage2D coverage = reader.read(null);
            RenderedImage sourceImage = coverage.getRenderedImage();

            CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
            Envelope2D coverageEnvelope = coverage.getEnvelope2D();

            //获取行列对应的像元值
            Raster sourceRaster = sourceImage.getData();


//            float[] adsaf = {0};
//            sourceRaster.getPixel(1500, 800,adsaf);
//            float ibandvalue = sourceRaster.getSampleFloat(0,0,0);

            int ibandcount = coverage.getNumSampleDimensions();
            String[] sampleDimensionNames = new String[ibandcount];
            for (int i = 0; i < ibandcount; i++) {
                GridSampleDimension dim = coverage.getSampleDimension(i);
                sampleDimensionNames[i] = dim.getDescription().toString();
                System.out.println("Description:" + dim.getDescription().toString());
            }

            GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
            System.out.println("bands MaximumValue：" + sampleDimension.getMaximumValue());
            System.out.println("bands MinimumValue：" + sampleDimension.getMinimumValue());

            double coverageMinX = coverageEnvelope.getBounds().getMinX();
            double coverageMaxX = coverageEnvelope.getBounds().getMaxX();
            double coverageMinY = coverageEnvelope.getBounds().getMinY();
            double coverageMaxY = coverageEnvelope.getBounds().getMaxY();
            System.out.println("coverageMinX：" + coverageMinX);
            System.out.println("coverageMaxX：" + coverageMaxX);
            System.out.println("coverageMinY：" + coverageMinY);
            System.out.println("coverageMaxY：" + coverageMaxY);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return strExtent;
    }

    public static List<Map> getAltitudeTifList(Record record, List<String> urls) throws Exception {
        File file = new File(urls.get(0));
        Double sum;
        int count;
        List<Map> result = new ArrayList<>();
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));

        Map map = new HashMap();
        for (int i = 0; i < urls.size(); i++) {
            map.put("sum" + i, 0.0);
            map.put("count" + i, 0);
            map.put("coverage" + i, new GeoTiffReader(new File(urls.get(i)), tiffHints).read(null));
        }
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();

        Double min_lon = record.get("xmin");
        Double max_lon = record.get("xmax");
        Double min_lat = record.get("ymin");
        Double max_lat = record.get("ymax");
        String geom = record.getStr("geom");
        // 通过地理坐标获取行列号
        DirectPosition position1 = new DirectPosition2D(crs, max_lon, max_lat);
        Point2D point2d1 = coverage.getGridGeometry().worldToGrid(position1);
        int max_x = (int) point2d1.getX();
        int max_y = (int) point2d1.getY();
        DirectPosition position2 = new DirectPosition2D(crs, min_lon, min_lat);
        Point2D point2d2 = coverage.getGridGeometry().worldToGrid(position2);
        int min_x = (int) point2d2.getX();
        int min_y = (int) point2d2.getY();

        boolean isContains = true;
        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        double nodData = sampleDimension.getMinimumValue();
        for (int i = min_x; i < max_x; i++) {
            for (int j = max_y; j < min_y; j++) {
                GridCoordinates2D coord = new GridCoordinates2D(i, j);
                DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                double lon = tmpPos.getCoordinate()[0];
                double lat = tmpPos.getCoordinate()[1];
//              Record first = Db.findFirst("select ST_Contains( st_geometryfromtext('polygon ((" + latlons + "))',4326) " +
//                        ",st_geometryfromtext('POINT(" + lon + " " + lat + ")',4326)) as iscontains");
//                Record first = Db.findFirst("select ST_Contains( '" + geom + "' " +
//                        ",st_geometryfromtext('POINT(" + lon + " " + lat + ")',4326)) as iscontains");
//                Boolean iscontains = first.getBoolean("iscontains");
                if (geom.startsWith("MULTIPOLYGON")) {
                    geom = geom.replace("MULTIPOLYGON(", "POLYGON");
                    geom = geom.replace(")))", "))");
                }
                boolean iscontains = GeometryRelated.withinGeo(lon, lat, geom);
                if (iscontains) {
                    for (int k = 0; k < urls.size(); k++) {
                        GridCoverage2D o = (GridCoverage2D) map.get("coverage" + k);
                        float[] sss = (float[]) o.evaluate(tmpPos);
                        float s = sss[0];
                        if (nodData != s) {
                            sum = (Double) map.get("sum" + k);
                            //加入计算平均值
                            sum = sum + s;
                            map.put("sum" + k, sum);
                            count = (int) map.get("count" + k);
                            count = count + 1;
                            map.put("count" + k, count);
                            isContains = false;
                        }
                    }
                }
            }
        }
        if (isContains) {
            return result;
        } else {
            for (int i = 0; i < urls.size(); i++) {
                sum = (Double) map.get("sum" + i);
                count = (int) map.get("count" + i);
                Map resultMap = new HashMap();
                resultMap.put("yaer", 2000 + i);
                resultMap.put("output", Arith.div(sum, count, 2));
                result.add(resultMap);
            }
            return result;
        }
    }

    /**
     * 计算斜率（变化率）
     */
    public static Double slope(List<Double> listx, List<Double> listy) {
        Double sumX = 0.0;
        for (int i = 0; i < listx.size(); i++) {
            sumX += listx.get(i);
        }
        Double ax = Arith.div(sumX, listx.size());
        Double sumY = 0.0;
        for (int i = 0; i < listy.size(); i++) {
            sumY += listx.get(i);
        }
        Double ay = Arith.div(sumX, listx.size());
        Double em = 0.0;
        Double ez = 0.0;
        for (int i = 0; i < listx.size(); i++) {
            em += ((listx.get(i) - ax) * (listy.get(i) - ay));
            ez += pow((listx.get(i) - ax), 2);
        }
        Double result = 0.0;
        if (ez != 0) {
            result = Arith.div(em, ez);
        }
        return result;
    }

}
