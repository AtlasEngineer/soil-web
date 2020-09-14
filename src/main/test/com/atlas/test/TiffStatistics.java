package com.atlas.test;

import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.factory.Hints;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.TransformException;

import com.lambkit.common.util.TimeUtils;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Polygon;

public class TiffStatistics {

    private static GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

    public static void main(String[] args) {
        // 比较包围盒过滤数据
        try {
            String latlons =  "106.69921875000001 35.889050079360935,107.05078125000001 30.600093873550072,113.99414062500001 34.161818161230386,106.69921875000001 35.889050079360935";
            WKTReader reader = new WKTReader(geometryFactory);
            Polygon polygon = (Polygon) reader.read("POLYGON(("+latlons+"))");
            Envelope env = polygon.getEnvelopeInternal();
            long a = System.currentTimeMillis();//获取当前系统时间(毫秒)
            double statistics = geotiffStatistics("C:\\Users\\xiaoxu\\Desktop\\逐年草地盖度\\cover_2000.tif", env, polygon);
            System.out.println("结果：" + statistics);
            System.out.print("程序1执行时间为：");
            System.out.println(System.currentTimeMillis() - a + "毫秒");

            long b = System.currentTimeMillis();//获取当前系统时间(毫秒)
//            double yearWater = readTiffUtils.getAltitudeList(latlons, "C:\\Users\\xiaoxu\\Desktop\\逐年草地盖度\\cover_2000.tif");
//            System.out.println("结果：" + yearWater);
//            System.out.print("程序2执行时间为：");
//            System.out.println(System.currentTimeMillis() - b + "毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * tif图像统计
     *
     * @throws IOException
     * @throws TransformException
     */
    protected static double geotiffStatistics(String tif, Envelope env, Polygon polygon)
            throws IOException, TransformException {
        File file = new File(tif);
        if (file == null || !file.exists()) {
            return 0;
        }
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));

        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        RenderedImage sourceImage = coverage.getRenderedImage();

        // 获取影像长宽
        int iwidth = coverage.getRenderedImage().getWidth();
        int iheight = coverage.getRenderedImage().getHeight();

        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        // No Data
        double nodData = sampleDimension.getMinimumValue();
        // System.out.println("nodData: " + nodData);

        // 像素值集合
        Raster sourceRaster = sourceImage.getData();
        double statistics = 0;
        int nlevel = 1;
        int minW = 0, maxW = iwidth - 1;
        int minH = 0, maxH = iheight - 1;
        int midW = (minW + maxW) / 2;
        int midH = (minH + maxH) / 2;
        statistics += quadStatistics(nlevel, minW, midW, minH, midH, coverage, sourceRaster, nodData, env, polygon);
        statistics += quadStatistics(nlevel, minW, midW, midH + 1, maxH, coverage, sourceRaster, nodData, env, polygon);
        statistics += quadStatistics(nlevel, midW + 1, maxW, minH, midH, coverage, sourceRaster, nodData, env, polygon);
        statistics += quadStatistics(nlevel, midW + 1, maxW, midH + 1, maxH, coverage, sourceRaster, nodData, env,
                polygon);
        return statistics;
    }

    /**
     * 四叉树遍历
     *
     * @param level
     * @param minW
     * @param maxW
     * @param minH
     * @param maxH
     * @param coverage
     * @param sourceRaster
     * @param nodData
     * @param env
     * @param polygon
     * @return
     * @throws TransformException
     */
    protected static double quadStatistics(int level, int minW, int maxW, int minH, int maxH, GridCoverage2D coverage,
                                           Raster sourceRaster, double nodData, Envelope env, Polygon polygon) throws TransformException {
        Envelope envTif = createEnvelope(minW, maxW, minH, maxH, coverage);
        double statistics = 0;
        if (env.intersects(envTif)) {
            if (level == 20 || maxW - minW < 5 || maxH - minH < 5) {
                for (int i = minW; i <= maxW; i++) {
                    for (int j = minH; j <= maxH; j++) {
                        GridCoordinates2D coord = new GridCoordinates2D(i, j);
                        DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                        // 像素坐标转空间坐标
                        double lon = tmpPos.getCoordinate()[0];
                        double lat = tmpPos.getCoordinate()[1];
                        // if(!env.contains(lon, lat)) continue;
                        // System.out.println("Coordinate: " + lon + ", " +
                        // lat);
                        // 判断以x,y为坐标的点point(x,y)是否在geometry表示的Polygon中
                        if (isInPolygon(lon, lat, polygon)) {
                            float ibandvalue = sourceRaster.getSampleFloat(i, j, 0);
                            if (ibandvalue != nodData) {
                                statistics += ibandvalue;
                            }
                        }
                    }
                }
            } else {
                int nlevel = level + 1;
                int midW = (minW + maxW) / 2;
                int midH = (minH + maxH) / 2;
                statistics += quadStatistics(nlevel, minW, midW, minH, midH, coverage, sourceRaster, nodData, env,
                        polygon);
                statistics += quadStatistics(nlevel, minW, midW, midH + 1, maxH, coverage, sourceRaster, nodData, env,
                        polygon);
                statistics += quadStatistics(nlevel, midW + 1, maxW, minH, midH, coverage, sourceRaster, nodData, env,
                        polygon);
                statistics += quadStatistics(nlevel, midW + 1, maxW, midH + 1, maxH, coverage, sourceRaster, nodData,
                        env, polygon);
            }
        }
        return statistics;
    }

    protected static Envelope createEnvelope(int minW, int maxW, int minH, int maxH, GridCoverage2D coverage) {
        Coordinate c1 = getCoordinate(minW, minH, coverage);
        Coordinate c2 = getCoordinate(minW, maxH, coverage);
        Coordinate c3 = getCoordinate(maxW, maxH, coverage);
        Coordinate c4 = getCoordinate(maxW, minH, coverage);
        Envelope env = new Envelope(c1, c3);
        env.expandToInclude(c2);
        env.expandToInclude(c4);
        return env;
    }

    protected static Coordinate getCoordinate(int w, int h, GridCoverage2D coverage) {
        GridCoordinates2D coord1 = new GridCoordinates2D(w, h);
        DirectPosition tmpPos1;
        try {
            tmpPos1 = coverage.getGridGeometry().gridToWorld(coord1);
            // 像素坐标转空间坐标
            double lon = tmpPos1.getCoordinate()[0];
            double lat = tmpPos1.getCoordinate()[1];
            return new Coordinate(lon, lat);
        } catch (TransformException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static boolean isInPolygon(double lon, double lat, Polygon polygon) {
        return polygon.contains(polygon.getFactory().createPoint(new Coordinate(lon, lat)));
    }

}