
package com.soli.server.utils;

import com.drew.imaging.tiff.TiffMetadataReader;
import com.drew.metadata.Metadata;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.sun.media.jai.codec.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import it.geosolutions.jaiext.range.NoDataContainer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.map.*;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.processing.Operations;
import org.geotools.factory.Hints;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.resources.coverage.CoverageUtilities;
import org.geotools.styling.*;
import org.opengis.filter.FilterFactory2;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.media.jai.TiledImage;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;

import static java.lang.Math.pow;

public class ReadTiffUtils {

    public static void main(String[] args) {
        try {
            String latlons = "116.940049242731 36.0735916625155,116.938546823689 36.0683884682009,116.938460971172 36.0558300083306,116.938933160014 36.0555177558914,116.941508735516 36.0555871454294,116.946187697678 36.0562463429891,116.949879355897 36.056662675445,116.954815875609 36.0564892038561,116.955202211935 36.0575300276542,116.956017810844 36.0575300276542,116.955889032069 36.0583973703037,116.955974884585 36.0610687255982,116.956146589619 36.0633930778457,116.955545622002 36.0671049606815,116.943526269659 36.0677640617532,116.944256016051 36.0737304096521,116.943612122176 36.0741119630158,116.940049242731 36.0735916625155";
            getNDVIData(latlons,new File("Z:\\00-项目资料库\\2020年项目\\2020-029-C-土壤数据管理智能平台\\04数据\\高分数据GF6\\GF6_WFV_E117.5_N35.8_20201007_L1A1120041548\\GF6_WFV_E117.5_N35.8_20201007_L1A1120041548-1.tiff"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage coverageImage(GridCoverage2D coverage) {
        MapContent map = new MapContent();
        GtsLayer gtsLayer = new GtsLayer("coverage");
        gtsLayer.addCoverage(coverage);
        int width = coverage.getRenderedImage().getWidth();
        int height = coverage.getRenderedImage().getHeight();
        map.addLayer(gtsLayer.getLayer());
        ReferencedEnvelope mapArea = new ReferencedEnvelope(coverage.getEnvelope());
        map.setViewport(new MapViewport(mapArea));
        // 初始化输出图像
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = bi.getGraphics();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Rectangle rect = new Rectangle(0, 0, width, height);
        if (map != null) {
            // 初始化渲染器
            StreamingRenderer sr = new StreamingRenderer();
            sr.setMapContent(map);
            // 绘制地图
            sr.paint((Graphics2D) g, rect, mapArea);
        }
        map.dispose();
        return bi;
    }

    public static Style createStyle() {
        FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
        StyleFactory sf = CommonFactoryFinder.getStyleFactory();
        RasterSymbolizer sym = sf.getDefaultRasterSymbolizer();
        ColorMap cMap = sf.createColorMap();

        ColorMapEntry color1 = sf.createColorMapEntry();
        color1.setColor(ff.literal("#f20000"));
        color1.setQuantity(ff.literal(-1));
        ColorMapEntry color2 = sf.createColorMapEntry();
        color2.setColor(ff.literal("#f57300"));
        color2.setQuantity(ff.literal(-0.75));
        ColorMapEntry color3 = sf.createColorMapEntry();
        color3.setColor(ff.literal("#f4a100"));
        color3.setQuantity(ff.literal(-0.5));
        ColorMapEntry color4 = sf.createColorMapEntry();
        color4.setColor(ff.literal("#f4c900"));
        color4.setQuantity(ff.literal(-0.25));
        ColorMapEntry color5 = sf.createColorMapEntry();
        color5.setColor(ff.literal("#daf400"));
        color5.setQuantity(ff.literal(0));
        ColorMapEntry color6 = sf.createColorMapEntry();
        color6.setColor(ff.literal("#9fe900"));
        color6.setQuantity(ff.literal(0.25));
        ColorMapEntry color7 = sf.createColorMapEntry();
        color7.setColor(ff.literal("#71c000"));
        color7.setQuantity(ff.literal(0.5));
        ColorMapEntry color8 = sf.createColorMapEntry();
        color8.setColor(ff.literal("#509b00"));
        color8.setQuantity(ff.literal(0.75));
        ColorMapEntry color9 = sf.createColorMapEntry();
        color9.setColor(ff.literal("#317400"));
        color9.setQuantity(ff.literal(1));

        cMap.addColorMapEntry(color1);
        cMap.addColorMapEntry(color2);
        cMap.addColorMapEntry(color3);
        cMap.addColorMapEntry(color4);
        cMap.addColorMapEntry(color5);
        cMap.addColorMapEntry(color6);
        cMap.addColorMapEntry(color7);
        cMap.addColorMapEntry(color8);
        cMap.addColorMapEntry(color9);
        sym.setColorMap(cMap);
        Style style = SLD.wrapSymbolizers(sym);
        return style;
    }

    /**
     * 从tiff生成缩略图
     *
     * @param tifPath
     * @param humbstPath
     * @return
     */
    public static boolean makeThumbsFromTiff(float[][] data, String tifPath, String humbstPath) throws IOException {
        GeoTiffReader reader = new GeoTiffReader(new File(tifPath));
        GridCoverage2D coverage = reader.read(null);
//        GridCoverage2D outputCoverage = scaleCoverage(coverage, 0.8, 0.8);

//        File file = new File(humbstPath);
//        GeoTiffWriter writer = new GeoTiffWriter(file);
//        writer.write(outputCoverage, null);
//        writer.dispose();

        //生成缩略图土壤
        BufferedImage bufferedImage = coverageImage(coverage);
        ImageIO.write(bufferedImage, "png", new File(humbstPath.replace("tif", "png")));
        return true;
    }

    public static void tif2Jpg(String fileAbsolutePath) {
        if (fileAbsolutePath == null || "".equals(fileAbsolutePath.trim())) {
            return;
        }
        if (!new File(fileAbsolutePath).exists()) {
            return;
        }
        FileSeekableStream fileSeekStream = null;
        try {
            fileSeekStream = new FileSeekableStream(fileAbsolutePath);
            TIFFEncodeParam tiffEncodeParam = new TIFFEncodeParam();
            JPEGEncodeParam jpegEncodeParam = new JPEGEncodeParam();
            ImageDecoder dec = ImageCodec.createImageDecoder("tiff", fileSeekStream, null);
            int count = dec.getNumPages();
            tiffEncodeParam.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
            tiffEncodeParam.setLittleEndian(false);
            String filePathPrefix = fileAbsolutePath.substring(0, fileAbsolutePath.lastIndexOf("."));
            for (int i = 0; i < count; i++) {
                RenderedImage renderedImage = dec.decodeAsRenderedImage(i);
                File imgFile = new File(filePathPrefix + "_" + i + ".jpg");
                ParameterBlock pb = new ParameterBlock();
                pb.addSource(renderedImage);
                pb.add(imgFile.toString());
                pb.add("JPEG");
                pb.add(jpegEncodeParam);
                RenderedOp renderedOp = JAI.create("filestore", pb);
                renderedOp.dispose();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fileSeekStream != null) {
                try {
                    fileSeekStream.close();
                } catch (IOException e) {
                }
                fileSeekStream = null;
            }
        }
    }

    public static void formatConverter(String tifFile, String jpgFile) {
        try {
            File input = new File(tifFile);
            File output = new File(jpgFile);
            BufferedImage image = ImageIO.read(input);
            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
//            BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
//            Graphics g = result.getGraphics();
//            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//            result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
            ImageIO.write(result, "png", output);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将 tiff 转换 jpg 格式
     *
     * @param filePath
     * @return
     */
    public static String tiffTuanJPG(String filePath) {
        String format = filePath.substring(filePath.lastIndexOf(".") + 1);
        String turnJpgFile = filePath.replace("TIF", "jpg");
        if (format.equals("TIF")) {
            File fileTiff = new File(turnJpgFile);
            if (fileTiff.exists()) {
                System.out.println("该tiff文件已经转换为 JPG 文件：" + turnJpgFile);
                return turnJpgFile;
            }
            RenderedOp rd = JAI.create("fileload", filePath);//读取iff文件
            OutputStream ops = null;
            try {
                ops = new FileOutputStream(turnJpgFile);
                //文件存储输出流
                JPEGEncodeParam param = new JPEGEncodeParam();
                ImageEncoder image = ImageCodec.createImageEncoder("JPEG", ops, param); //指定输出格式
                image.encode(rd);
                //解析输出流进行输出
                ops.close();
                System.out.println("tiff转换jpg成功:" + filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return turnJpgFile;
    }

    public static Kv getFloatData(String latlons, File file, String writePath) throws Exception {
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, CRS.decode("EPSG:32650")));
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
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
        float[][] data = new float[min_y - max_y][max_x - min_x];
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[i].length; j++) {
//                data[i][j] = 1;
//            }
//        }
//        //导出tiff
        Point maxPoint = projectTransform(max_lat, max_lon, "EPSG:32650", "EPSG:4326");
        Point minPoint = projectTransform(min_lat, min_lon, "EPSG:32650", "EPSG:4326");
        CoordinateReferenceSystem crs84 = CRS.decode("EPSG:4326", false);
        ReferencedEnvelope envelope = new ReferencedEnvelope(minPoint.getX(), maxPoint.getX(), minPoint.getY(), maxPoint.getY(), crs84);
//        exportTIFF(writePath, data, envelope,0);
        return Kv.by("data", data).set("envelope", envelope);
    }

    public static Double getNoDate(String path) throws IOException {
        GeoTiffReader reader = new GeoTiffReader(new File(path));
        GridCoverage2D coverage = reader.read(null);
        GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
        double[] noDataValues = sampleDimension.getNoDataValues();
        if (noDataValues == null) {
            return null;
        } else {
            return noDataValues[0];
        }
    }

    public static void writerTif(String latlons, float[][] data, String writePath, Double noDataValues) throws Exception {
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

        CoordinateReferenceSystem crs = CRS.decode("EPSG:4326", false);

        System.out.println("min:"+min_lon+"  -  "+min_lat);
        System.out.println("max:"+max_lon+"  -  "+max_lat);
        //导出tiff
        ReferencedEnvelope envelope = new ReferencedEnvelope(min_lon, max_lon, min_lat, max_lat, crs);

        if (noDataValues == null) {
            exportTIFF(writePath, data, envelope, 2);
        } else {
            exportTIFF(writePath, data, envelope, noDataValues.floatValue());
        }
    }

    public static GridCoverage2D scaleCoverage(GridCoverage2D coverage, double xScale, double yScale) {
        Operations ops = new Operations(null);
        coverage = (GridCoverage2D) ops.scale(coverage, xScale, yScale, 0, 0);
        return coverage;
    }

    public static void exportTIFF(String outputPath, float[][] data, org.opengis.geometry.Envelope env, float nodate) throws Exception {
        GridCoverageFactory factory = new GridCoverageFactory();

        HashMap properties = new HashMap<>();
        CoverageUtilities.setNoDataProperty(properties, new NoDataContainer(nodate));
        GridCoverage2D outputCoverage = factory.create("test", data, env);
        GridCoverage2D nodata = factory.create("test", outputCoverage.getRenderedImage(), outputCoverage.getEnvelope(), outputCoverage.getSampleDimensions(), null, properties);

        GeoTiffWriter writer = new GeoTiffWriter(new File(outputPath));
        writer.write(nodata, null);
        writer.dispose();
    }

    public static void exportTIFF(String outputPath, float[][] data, org.opengis.geometry.Envelope env) throws Exception {
        GridCoverageFactory factory = new GridCoverageFactory();
        GridCoverage2D outputCoverage = factory.create("test", data, env);
        GeoTiffWriter writer = new GeoTiffWriter(new File(outputPath));
        writer.write(outputCoverage, null);
        writer.dispose();
    }

    /**
     * 导出田块tiff
     *
     * @param outputPath
     * @param coverage
     * @throws Exception
     */
    public static void exportTkTIFF(String latlons, String outputPath, GridCoverage2D coverage) throws Exception {
        GridCoverageFactory factory = new GridCoverageFactory();

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

        Point maxPoint = projectTransform(max_lat, max_lon, "EPSG:32650", "EPSG:4326");
        Point minPoint = projectTransform(min_lat, min_lon, "EPSG:32650", "EPSG:4326");

        CoordinateReferenceSystem crs = CRS.decode("EPSG:4326", false);
        //导出tiff
        ReferencedEnvelope envelope = new ReferencedEnvelope(minPoint.getX(), maxPoint.getX(), minPoint.getY(), maxPoint.getY(), crs);
        GridCoverage2D outputCoverage = factory.create("test", coverage.getRenderedImage(), envelope, null, null, null);

        GeoTiffWriter writer = new GeoTiffWriter(new File(outputPath));
        writer.write(outputCoverage, null);
        writer.dispose();
    }


    public static Kv getNDVIData(String latlons, File file) throws Exception {
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, CRS.decode("EPSG:4326")));

        GeoTiffReader tifReader = new GeoTiffReader(file,tiffHints);

        GridCoverage2D coverage = tifReader.read(null);
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
//        GridCoordinates2D coord = new GridCoordinates2D(5000, 5000);
//        DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
//        int[] sss = (int[]) coverage.evaluate(tmpPos);
//        float b4 = sss[3];//红
//        float b5 = sss[4];//近红

        RenderedImage sourceImage = coverage.getRenderedImage();
        Raster sourceRaster = sourceImage.getData();
        Rectangle bounds = sourceRaster.getBounds();

        //波段数量
        int numBands = sourceRaster.getNumBands();
        if (numBands != 1) {
            return Kv.by("msg", "波段数量:" + numBands).set("code", 400);
        }

        //通过地理坐标获取行列号
        //获取影像长宽
        int iwidth = coverage.getRenderedImage().getWidth();
        int iheight = coverage.getRenderedImage().getHeight();

        DirectPosition position1 = new DirectPosition2D(crs, max_lon, max_lat);
        Point2D point2d1 = coverage.getGridGeometry().worldToGrid(position1);
        int max_x = (int) point2d1.getX();
        int max_y = (int) point2d1.getY();
        DirectPosition position2 = new DirectPosition2D(crs, min_lon, min_lat);
        Point2D point2d2 = coverage.getGridGeometry().worldToGrid(position2);
        int min_x = (int) point2d2.getX();
        int min_y = (int) point2d2.getY();
        if (max_y < 0) {
            max_y = 0;
        }
        if (min_x < 0) {
            min_x = 0;
        }
        if (min_y > iheight) {
            min_y = iheight;
        }
        if (max_x > iwidth) {
            max_x = iwidth;
        }
        float[][] data = new float[min_y - max_y][max_x - min_x];
        System.out.println(Arith.mul((min_y - max_y), (max_x - min_x)));
        Map properties = coverage.getProperties();
        Double noDataValues = (Double) properties.get(NoDataContainer.GC_NODATA);
        if (max_x == min_x && max_y == min_y) {
            //田块在一个像素里
            data = new float[1][1];
            float[] sss = (float[]) coverage.evaluate(position1);
            data[0][0] = sss[0];
        } else {
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
                        float b4 = sss[3];//红
                        float b5 = sss[4];//近红
                        if (noDataValues == null || Math.abs(b4 - noDataValues) > 1e-6) {
                            //ndvi = B5-B4/B5+B4   B4是红，B5是近红 正常结果范围在-1到1之间
                            double add = Arith.add(b5, b4);
                            if (add == 0) {
                                data[j - max_y][i - min_x] = 0;
                            }else{
                                Double div = Arith.div(Arith.sub(b5, b4), add);
                                if (div == -1 || div == 1) {
                                    data[j - max_y][i - min_x] = 0;
                                } else {
                                    data[j - max_y][i - min_x] = div.floatValue();
                                }
                            }
                        } else {
                            if (noDataValues != null) {
                                data[j - max_y][i - min_x] = noDataValues.floatValue();
                            }
                        }
                    } else {
                        if (noDataValues != null) {
                            data[j - max_y][i - min_x] = noDataValues.floatValue();
                        }
                    }
                }
            }
        }
        return Kv.by("data", data).set("intersec", true);
//        return null;
    }


    public static Kv getNDVIParams(String latlons, File file) throws Exception {
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, CRS.decode("EPSG:32650")));
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
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
//        Point maxPoint = projectTransform(max_lat, max_lon, "EPSG:4326", "EPSG:32650");
//        Point minPoint = projectTransform(min_lat, min_lon, "EPSG:4326", "EPSG:32650");
        // 通过地理坐标获取行列号
        //获取影像长宽
        int iwidth = coverage.getRenderedImage().getWidth();
        int iheight = coverage.getRenderedImage().getHeight();

        DirectPosition position1 = new DirectPosition2D(crs, max_lon, max_lat);
        Point2D point2d1 = coverage.getGridGeometry().worldToGrid(position1);
        int max_x = (int) point2d1.getX();
        int max_y = (int) point2d1.getY();
        DirectPosition position2 = new DirectPosition2D(crs, min_lon, min_lat);
        Point2D point2d2 = coverage.getGridGeometry().worldToGrid(position2);
        int min_x = (int) point2d2.getX();
        int min_y = (int) point2d2.getY();
//        if (min_y < 0 && max_x < 0 && max_y > iheight) {
//            return Kv.by("data", null).set("intersec", false);
//        }
        if (max_y < 0) {
            max_y = 0;
        }
        if (min_x < 0) {
            min_x = 0;
        }
        if (min_y > iheight) {
            min_y = iheight;
        }
        if (max_x > iwidth) {
            max_x = iwidth;
        }
        float[][] data = new float[min_y - max_y][max_x - min_x];
        System.out.println(Arith.mul((min_y - max_y), (max_x - min_x)));
//        int x = 0;
//        int y = 0;
        Map properties = coverage.getProperties();
        Double noDataValues = (Double) properties.get(NoDataContainer.GC_NODATA);
        if (max_x == min_x && max_y == min_y) {
            //田块在一个像素里
            data = new float[1][1];
            float[] sss = (float[]) coverage.evaluate(position1);
            data[0][0] = sss[0];
        } else {
            for (int i = min_x; i < max_x; i++) {
                for (int j = max_y; j < min_y; j++) {
                    GridCoordinates2D coord = new GridCoordinates2D(i, j);
                    DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                    double lon = tmpPos.getCoordinate()[0];
                    double lat = tmpPos.getCoordinate()[1];
                    if (i == min_x && j == max_y) {
                        System.out.println(lon + "-" + lat);
                    }
                    boolean iscontains = GeometryRelated.withinGeo(lon, lat, "POLYGON((" + latlons + "))", 32650);
                    if (iscontains) {
                        //面内，赋值像素值
                        int[] sss = (int[]) coverage.evaluate(tmpPos);
                        int s = sss[0];
                        if (noDataValues == null || Math.abs(s - noDataValues) > 1e-6) {
//                            if (s == 0) {
//                                y++;
//                            }
                            data[j - max_y][i - min_x] = s;
                        } else {
                            if (noDataValues != null) {
                                data[j - max_y][i - min_x] = noDataValues.floatValue();
                            }
                        }
                    } else {
//                        x++;
                        if (noDataValues != null) {
                            data[j - max_y][i - min_x] = noDataValues.floatValue();
                        }
                    }
                }
            }
        }
//        System.out.println("面内不是nodata的数量:" + y);
//        System.out.println("不在面内的数量:" + x);
        return Kv.by("data", data).set("intersec", true);
    }


    /**
     * 投影转换， lon=经度，lat=纬度，ESPG格式（例）：EPSG:4610
     */
    public static Point projectTransform(double lon, double lat,
                                         String epsgSource, String epsgTarget) throws FactoryException,
            MismatchedDimensionException, TransformException {
        // 原始坐标点
        // PS:通常逻辑上理解经度应该是横坐标x，纬度是y，可是这里经度要填到y，纬度x，否则会报错
        Point sourcePoint = JtsHelper.createPoint(lat, lon, epsgSource);

        // 定义转换前和转换后的投影，可以用ESPG或者wkt
        // "PROJCS[\"Xian_1980_3_Degree_GK_CM_111E\",GEOGCS[\"GCS_Xian_1980\",DATUM[\"D_Xian_1980\",SPHEROID[\"Xian_1980\",6378140.0,298.257]],PRIMEM[\"Greenwich\",0.0],UNIT[\"Degree\",0.0174532925199433]],PROJECTION[\"Gauss_Kruger\"],PARAMETER[\"False_Easting\",500000.0],PARAMETER[\"False_Northing\",0.0],PARAMETER[\"Central_Meridian\",111.0],PARAMETER[\"Scale_Factor\",1.0],PARAMETER[\"Latitude_Of_Origin\",0.0],UNIT[\"Meter\",1.0]]";
        // CoordinateReferenceSystem mercatroCRS = CRS.parseWKT(strWKTMercator);
        CoordinateReferenceSystem crsSource = CRS.decode(epsgSource);
        CoordinateReferenceSystem crsTarget = CRS.decode(epsgTarget);
        // 投影转换
        MathTransform transform = CRS.findMathTransform(crsSource, crsTarget);
        Point pointTarget = (Point) JTS.transform(sourcePoint, transform);

        return pointTarget;
    }

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

    public static Kv getXmlLatlons(String path) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(new File(path));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // 读取指定标签
        String topLeftLatitude = doc.getRootElement().elementText("TopLeftLatitude");
        String topLeftLongitude = doc.getRootElement().elementText("TopLeftLongitude");
        String topRightLatitude = doc.getRootElement().elementText("TopRightLatitude");
        String topRightLongitude = doc.getRootElement().elementText("TopRightLongitude");

        String bottomRightLatitude = doc.getRootElement().elementText("BottomRightLatitude");
        String bottomRightLongitude = doc.getRootElement().elementText("BottomRightLongitude");
        String bottomLeftLatitude = doc.getRootElement().elementText("BottomLeftLatitude");
        String bottomLeftLongitude = doc.getRootElement().elementText("BottomLeftLongitude");
        return Kv.by("topLeftLatitude", topLeftLatitude).set("topLeftLongitude", topLeftLongitude)
                .set("topRightLatitude", topRightLatitude).set("topRightLongitude", topRightLongitude)
                .set("bottomRightLatitude", bottomRightLatitude).set("bottomRightLongitude", bottomRightLongitude)
                .set("bottomLeftLatitude", bottomLeftLatitude).set("bottomLeftLongitude", bottomLeftLongitude)
                .set("code", 200);
    }

    /**
     * 面查询指定URl 的 tif
     */
    public static double getAltitudeByWkt(String wkt, String url) throws TransformException, IOException, ParseException {
        wkt = wkt.replace(" ZM", "");
        File file = new File(url);
        Hints tiffHints = new Hints();
        tiffHints.add(new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
        tiffHints.add(new Hints(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, DefaultGeographicCRS.WGS84));
        GeoTiffReader tifReader = new GeoTiffReader(file, tiffHints);
        GridCoverage2D coverage = tifReader.read(null);
        // 获取坐标系
        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
//        WKTReader reader = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326));
//        Geometry polygon = reader.read(wkt);

        WKTReader wktReader = new WKTReader();
        Geometry polygon = null;
        try {
            polygon = wktReader.read(wkt);
            polygon.setSRID(4326);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Kv.by("code", 400).set("msg", e.getMessage());
        }

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
        System.out.println(min_x + "至" + max_x);
        System.out.println(max_y + "至" + min_y);
        RenderedImage sourceImage = coverage.getRenderedImage();
        Raster sourceRaster = sourceImage.getData();
        float[] adsaf = {0};
        if (min_x == max_x && max_y == min_y) {
//            GridCoordinates2D coord = new GridCoordinates2D(min_x,max_y);
//            DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
//            System.out.println("tmpPos:" + tmpPos);
            float pix = sourceRaster.getPixel(min_x, max_y, adsaf)[0];
            if (nodData != pix) {
                //加入计算平均值
                sum = sum + pix;
                count++;
            }
        } else {
            for (int i = min_x; i < max_x; i++) {
                for (int j = max_y; j < min_y; j++) {
                    GridCoordinates2D coord = new GridCoordinates2D(i, j);
                    DirectPosition tmpPos = coverage.getGridGeometry().gridToWorld(coord);
                    double lon = tmpPos.getCoordinate()[0];
                    double lat = tmpPos.getCoordinate()[1];
                    boolean iscontains = GeometryRelated.withinGeo(lon, lat, wkt);
                    if (iscontains) {
                        float pix = sourceRaster.getPixel(min_x, max_y, adsaf)[0];
                        if (nodData != pix) {
                            //加入计算平均值
                            sum = sum + pix;
                            count++;
                        }
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
            System.out.println("param:" + param);
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

            coverageMinX = coverageEnvelope.getMinX();
            coverageMaxX = coverageEnvelope.getMaxX();
            coverageMinY = coverageEnvelope.getMinY();
            coverageMaxY = coverageEnvelope.getMaxY();
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
