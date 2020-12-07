package com.soli.server.utils;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.lambkit.Lambkit;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.soli.lambkit.start.GeoServerConfig;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import it.geosolutions.jaiext.range.NoDataContainer;
import it.geosolutions.jaiext.range.Range;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.image.ImageWorker;
import org.geotools.resources.coverage.CoverageUtilities;
import org.locationtech.jts.geom.Geometry;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import javax.media.jai.TiledImage;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssueTiffUtils {


    public static Kv uploadTiff(String tifPath, String name, Integer sldType, File sldParam, Integer data_id) throws Exception {
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        String geoserverUrl = config.getGeourl();
        String geoserverUsername = config.getGeouser();
        String geoserverPassword = config.getGeopsw();
//        geoserverUrl = "http://127.0.0.1:18999/geoserver";
//        geoserverUsername = "admin";
//        geoserverPassword = "geoserver";
        GeoServerRESTPublisher geoServerRESTPublisher = new GeoServerRESTPublisher(geoserverUrl, geoserverUsername, geoserverPassword);
        GeoServerRESTReader geoServerRESTReader = new GeoServerRESTReader(geoserverUrl, geoserverUsername, geoserverPassword);
        String workspace = "d";

        //获取tiff文件
        File tiffFile = new File(tifPath);
        //判断工作空间是否存在
        List<String> workspacesList = geoServerRESTReader.getWorkspaceNames();
        boolean wsNull = !workspacesList.contains(workspace);
        if (wsNull) {
            geoServerRESTPublisher.createWorkspace(workspace);
        }

        //store是否存在，不存在则新建并发布数据。
//        RESTDataStoreList datastoresList = geoServerRESTReader.getDatastores(workspace);
//        List<String> datastoreNameList = datastoresList.getNames();
//        boolean storeNull = !datastoreNameList.contains(name);
//        if (storeNull) {
        Kv sld;
        if (sldType == 1) {
            sld = createSldByDbf(sldParam, name);
        } else if (sldType == 1) {
            sld = createNDVISld(name);
        } else {
            if (sldType == 2) {
                sld = createSldByAux(tiffFile, sldParam, name);
            } else {
                sld = createSld(tiffFile, name);
            }
        }
//        if (sld.getInt("code") != 200) {
//            Kv.by("msg", "发布失败，请检查数据坐标系等信息").set("code", 409);
//        }
        //发布tiff
        boolean result;
        if (data_id == 79) {
            result = geoServerRESTPublisher.publishExternalGeoTIFF(workspace, name, tiffFile, name, "EPSG:4326", GSResourceEncoder.ProjectionPolicy.NONE, "ggmj");
        } else {
            result = geoServerRESTPublisher.publishGeoTIFF(workspace, name, tiffFile);
        }
        if (result) {
            return Kv.by("msg", "发布成功").set("code", 200).set("sld", sld.get("msg"));
        } else {
            return Kv.by("msg", "发布失败，请检查数据坐标系等信息").set("code", 400);
        }
    }

    private static Kv createNDVISld(String storename) {
        System.out.println("create new sld...");
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("StyledLayerDescriptor");
        root.addAttribute("version", "1.0.0");
        root.addAttribute("xmlns", "http://www.opengis.net/sld");
//            root.add(new Namespace("sld", "http://www.opengis.net/sld"));
        root.add(new Namespace("ogc", "http://www.opengis.net/ogc"));
        root.add(new Namespace("xlink", "http://www.w3.org/1999/xlink"));
        root.add(new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
        root.add(new Namespace("schemaLocation", "http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));
//            root.add(new Namespace("schemaLocation", "http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));

        Element row_nl = root.addElement("NamedLayer");
        row_nl.addElement("Name").addText("d:" + storename);
        Element row_nl_us = row_nl.addElement("UserStyle");
        row_nl_us.addElement("Name").addText("default");
        Element row_nl_us_f = row_nl_us.addElement("FeatureTypeStyle");
        Element row_nl_us_r = row_nl_us_f.addElement("Rule");

        Element row_nl_us_r_p = row_nl_us_r.addElement("RasterSymbolizer");
        // <Opacity>1.0</Opacity>
        row_nl_us_r_p.addElement("Opacity").addText("1.0");
        Element row_nl_us_r_c_p = row_nl_us_r_p.addElement("ColorMap");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f20000").addAttribute("quantity", String.valueOf(-1)).addAttribute("label", "-1");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f57300").addAttribute("quantity", String.valueOf(-0.75)).addAttribute("label", "-0.75");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f4a100").addAttribute("quantity", String.valueOf(-0.5)).addAttribute("label", "-0.5");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f4c900").addAttribute("quantity", String.valueOf(-0.25)).addAttribute("label", "-0.25");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#daf400").addAttribute("quantity", String.valueOf(0)).addAttribute("label", "0");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9fe900").addAttribute("quantity", String.valueOf(0.25)).addAttribute("label", "0.25");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#71c000").addAttribute("quantity", String.valueOf(0.5)).addAttribute("label", "0.5");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#509b00").addAttribute("quantity", String.valueOf(0.75)).addAttribute("label", "0.75");
        row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#317400").addAttribute("quantity", String.valueOf(1)).addAttribute("label", "1");
        //生成style文件
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        String path = rootPath + "/sld/" + storename + ".sld";
        saveDocument(document, path, "UTF-8");
        return Kv.by("msg", "生成sld成功:" + path).set("code", 200).set("path", path);
    }

    public static Kv uploadTiff(String filepath) throws Exception {
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        String geoserverUrl = config.getGeourl();
        String geoserverUsername = config.getGeouser();
        String geoserverPassword = config.getGeopsw();
        GeoServerRESTPublisher geoServerRESTPublisher = new GeoServerRESTPublisher(geoserverUrl, geoserverUsername, geoserverPassword);
        GeoServerRESTReader geoServerRESTReader = new GeoServerRESTReader(geoserverUrl, geoserverUsername, geoserverPassword);

        String workspace = "d";
        String path = PathKit.getWebRootPath().replace("\\", "/") + filepath;
        System.out.println("path:" + filepath);
        File geotiff = new File(path);

        if (geotiff == null) {
            return Kv.by("msg", "找不到该文件").set("code", 400);
        }
        String name = geotiff.getName().split("\\.")[0];

        //解压后文件夹
        String s = PathKit.getWebRootPath().replace("\\", "/") + "/d/" + name;
        try {
            ZipUtils.decompress(geotiff.getPath(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("s" + s);
        File files = new File(s);
        String tifPath = null;
        boolean existDbf = false;
        boolean existAux = false;
        String tifDbfPath = null;
        String tifAuxPath = null;
        String[] fileList = files.list();
        List<String> list = new ArrayList<>();
        for (String y : fileList) {
            File file1 = new File(s + "/" + y);
            System.out.println("file1:" + file1.getName());
            if (file1.isFile()) {
                String[] split = file1.getName().split("\\.");
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < split.length; i++) {
                    System.out.println("saaa:" + split[i]);
                    sb.append(split[i]).append(".");
                }
                String s1 = sb.toString().substring(0, sb.length() - 1);
                File file01 = new File(PathKit.getWebRootPath() + "/d/" + name + "/" + name + "." + s1);
                System.out.println("file01:" + file01.getName());
                file1.renameTo(file01);
                String path1 = file01.getPath();
                System.out.println("path" + path1);
                list.add(file01.getPath());
                if ("tiff".equals(s1) || "tif".equals(s1)) {
                    tifPath = file01.getPath();
                }
                if ("tif.vat.dbf".equals(s1) || "tif.vat.dbf".equals(s1)) {
                    existDbf = true;
                    tifDbfPath = file01.getPath();
                }
                if ("tif.aux.xml".equals(s1) || "tif.aux.xml".equals(s1)) {
                    existAux = true;
                    tifAuxPath = file01.getPath();
                }
            }
        }
        //获取tiff文件
        File tiffFile = new File(tifPath);
        //判断工作空间是否存在
        List<String> workspacesList = geoServerRESTReader.getWorkspaceNames();
        boolean wsNull = !workspacesList.contains(workspace);
        if (wsNull) {
            geoServerRESTPublisher.createWorkspace(workspace);
        }

        //store是否存在，不存在则新建并发布数据。
        RESTDataStoreList datastoresList = geoServerRESTReader.getDatastores(workspace);
        List<String> datastoreNameList = datastoresList.getNames();
        boolean storeNull = !datastoreNameList.contains(name);
        if (storeNull) {
            //发布tiff
            boolean result = geoServerRESTPublisher.publishGeoTIFF(workspace, name, tiffFile);
            if (result) {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String sql = "insert into ld_geoconfig (table,type,time) values('" + name + "','shp','" + time + "')";
                Db.update(sql);
                Kv sld;
                if (existDbf) {
                    sld = createSldByDbf(new File(tifDbfPath), name);
                } else {
                    if (existAux) {
                        sld = createSldByAux(tiffFile, new File(tifAuxPath), name);
                    } else {
                        sld = createSld(tiffFile, name);
                    }
                }
//                Integer code = sld.getInt("code");
//                if (code == 200) {
//                    String sql1 = "update ld_geoconfig set xml = '?' where name = '?'";
//                    Db.update(sql1, sld.getStr("path"), name);
//                }
                return Kv.by("msg", "发布成功").set("code", 200).set("sld", sld.get("msg"));
            } else {
                return Kv.by("msg", "发布失败").set("code", 400);
            }
        } else {
            return Kv.by("msg", "该数据已存在").set("code", 400);
        }
    }

    private static Kv createSldByAux(File tiffile, File auxfile, String name) {
        try {
            GeoTiffReader reader = new GeoTiffReader(tiffile);
            GridCoverage2D coverage = reader.read(null);
            RenderedImage sourceImage = coverage.getRenderedImage();
            Raster sourceRaster = sourceImage.getData();
            //波段数量
            int numBands = sourceRaster.getNumBands();
            if (numBands != 1) {
                return Kv.by("msg", "波段数量:" + numBands).set("code", 400);
            }
            //读取aux
            SAXReader auxReader = new SAXReader();
            Document doc = null;
            try {
                doc = auxReader.read(auxfile);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            // 读取指定标签
            Element PAMRasterBand = doc.getRootElement().element("PAMRasterBand");
            Element metadata = PAMRasterBand.element("Metadata");
            List<Element> mdi = metadata.elements("MDI");
            System.out.println(mdi.size());
            double max = 0.0;
            double min = 0.0;
            for (Element element : mdi) {
                String key = element.attributeValue("key");
                if ("STATISTICS_MINIMUM".equals(key)) {
                    min = Double.valueOf(element.getStringValue());
                }
                if ("STATISTICS_MAXIMUM".equals(key)) {
                    max = Double.valueOf(element.getStringValue());
                }
            }
            System.out.println("max:" + max + "---min:" + min);
            double cha = max - min;
            double ji = Arith.div(cha, 4, 2);
            double[] aa = new double[5];
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    aa[i] = min;
                } else {
                    aa[i] = aa[i - 1] + ji;
                }
            }
            System.out.println("像元值分五级每级差值:" + ji);
            //生成sld
            System.out.println("create new sld...");
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("StyledLayerDescriptor");
            root.addAttribute("version", "1.0.0");
            root.addAttribute("xmlns", "http://www.opengis.net/sld");
//            root.add(new Namespace("sld", "http://www.opengis.net/sld"));
            root.add(new Namespace("ogc", "http://www.opengis.net/ogc"));
            root.add(new Namespace("xlink", "http://www.w3.org/1999/xlink"));
            root.add(new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
            root.add(new Namespace("schemaLocation", "http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));
//            root.add(new Namespace("schemaLocation", "http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));

            Element row_nl = root.addElement("NamedLayer");
            row_nl.addElement("Name").addText("d:" + name);
            Element row_nl_us = row_nl.addElement("UserStyle");
            row_nl_us.addElement("Name").addText("default");
            Element row_nl_us_f = row_nl_us.addElement("FeatureTypeStyle");
            Element row_nl_us_r = row_nl_us_f.addElement("Rule");

            Element row_nl_us_r_p = row_nl_us_r.addElement("RasterSymbolizer");
            // <Opacity>1.0</Opacity>
            row_nl_us_r_p.addElement("Opacity").addText("1.0");
            Element row_nl_us_r_c_p = row_nl_us_r_p.addElement("ColorMap");
            String color = "#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000,#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000";
            String[] colors = color.split(",");
            for (int i = 0; i < aa.length; i++) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", colors[i])
                        .addAttribute("quantity", String.valueOf(aa[i])).addAttribute("label", String.valueOf(aa[i]));
            }
            //生成style文件
            String rootPath = PathKit.getWebRootPath().replace("\\", "/");
            String path = rootPath + "/sld/" + name + ".sld";
            saveDocument(document, path, "UTF-8");
            return Kv.by("msg", "生成sld成功:" + path).set("code", 200).set("path", path);
        } catch (Exception e) {
            e.printStackTrace();
            return Kv.by("msg", "生成sld失败:" + e.getMessage()).set("code", 400).set("Exception", e.getMessage());
        }
    }


    /**
     * 读取dbf
     *
     * @param tifFile
     */
    public static List<Double> readDBF(File tifFile) {
        InputStream fis = null;
        try {
            // 读取文件的输入流
            fis = new FileInputStream(tifFile.getAbsolutePath());
            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);
            reader.setCharactersetName("GBK");
            // System.out.println(reader.getCharset());
            // 调用DBFReader对实例方法得到path文件中字段的个数
            int fieldsCount = reader.getFieldCount();
            // 取出字段信息
            int index = 0;
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                String output = new String(field.getName().getBytes("iso-8859-1"), "GBK");
                if (output.equals("value")) {
                    index = i;
                }
            }
            //配色数据
            List<Double> doubles = new ArrayList<>();
            Object[] rowValues; // 一条条取出path文件中记录
            while ((rowValues = reader.nextRecord()) != null) {
                for (int i = 0; i < rowValues.length; i++) {
                    if (i == index) {
                        doubles.add(((BigDecimal) rowValues[i]).doubleValue());
                    }
                }
            }
            return doubles;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
    }


    /**
     * 根据tif的dbf生成sld
     *
     * @param tifFile
     * @param storename
     * @return
     */
    public static Kv createSldByDbf(File tifFile, String storename) {
        try {
            List<Double> doubles = readDBF(tifFile);
            double max = Collections.max(doubles);
            double min = Collections.min(doubles);
            System.out.println("max:" + max + "---min:" + min);
            double cha = max - min;
            double ji = Arith.div(cha, 4, 2);
            double[] aa = new double[5];
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    aa[i] = min;
                } else {
                    aa[i] = aa[i - 1] + ji;
                }
            }
            System.out.println("像元值分五级每级差值:" + ji);
            //排序
            Collections.sort(doubles);
            //生成sld
            System.out.println("create new sld...");
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("StyledLayerDescriptor");
            root.addAttribute("version", "1.0.0");
            root.addAttribute("xmlns", "http://www.opengis.net/sld");
//            root.add(new Namespace("sld", "http://www.opengis.net/sld"));
            root.add(new Namespace("ogc", "http://www.opengis.net/ogc"));
            root.add(new Namespace("xlink", "http://www.w3.org/1999/xlink"));
            root.add(new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
            root.add(new Namespace("schemaLocation", "http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));
//            root.add(new Namespace("schemaLocation", "http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));

            Element row_nl = root.addElement("NamedLayer");
            row_nl.addElement("Name").addText("d:" + storename);
            Element row_nl_us = row_nl.addElement("UserStyle");
            row_nl_us.addElement("Name").addText("default");
            Element row_nl_us_f = row_nl_us.addElement("FeatureTypeStyle");
            Element row_nl_us_r = row_nl_us_f.addElement("Rule");

            Element row_nl_us_r_p = row_nl_us_r.addElement("RasterSymbolizer");
            // <Opacity>1.0</Opacity>
            row_nl_us_r_p.addElement("Opacity").addText("1.0");
            Element row_nl_us_r_c_p = row_nl_us_r_p.addElement("ColorMap");
            String color = "#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000,#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000";
//            String color = "#ff2626,#ff6c2b,#ffa82b,#ffda2b,#d5ff2b,#a8ff2b,#4eff2b,#2bff9e,#2bffda,#2bf3ff,#2bd5ff,#2bb2ff,#2b8fff,#2b49ff,#762bff,#b22bff,#e42bff,#ff2be9,#ff2bb7,#ff2b76";
            String[] colors = color.split(",");
            for (int i = 0; i < aa.length; i++) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", colors[i])
                        .addAttribute("quantity", String.valueOf(aa[i])).addAttribute("label", String.valueOf(aa[i]));
            }
            //生成style文件
            String rootPath = PathKit.getWebRootPath().replace("\\", "/");
            String path = rootPath + "/sld/" + storename + ".sld";
            saveDocument(document, path, "UTF-8");
            return Kv.by("msg", "生成sld成功:" + path).set("code", 200).set("path", path);
        } catch (Exception e) {
            e.printStackTrace();
            return Kv.by("msg", "生成sld失败:" + e.getMessage()).set("code", 200).set("Exception", e.getMessage());
        }
    }


    public static void main(String[] args) {
        try {
//            uploadTiff("D:\\tools\\apache-tomcat-8.5.41-windows-x64\\apache-tomcat-8.5.41\\webapps\\geoserver\\data\\data\\d\\ChinaEco100\\ChinaEco100.tif"
//                    , "ChinaEco100",2,new File("D:\\tools\\apache-tomcat-8.5.41-windows-x64\\apache-tomcat-8.5.41\\webapps\\geoserver\\data\\data\\d\\ChinaEco100\\ChinaEco100.tif.aux.xml"));
            uploadTiff("D:\\tools\\apache-tomcat-8.5.41-windows-x64\\apache-tomcat-8.5.41\\webapps\\geoserver\\data\\data\\d\\ChinaEco100\\ChinaEco100.tif"
                    , "ChinaEco100", 0, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        double dis = 1e-6;
//        double d1 = 0.0000001d;
//        double d2 = 0d;
//        System.out.println(d1 == d2); //直接判断为flase
//        System.out.println(Math.abs(d1 - d2) < dis);    //允许一定的误差范围，判断结果为true
//        createSld(new File("C:\\Users\\xiaoxu\\Desktop\\1002\\GST010_7E3797.tif"), "aaaa");
    }

    /**
     * @return boolean
     * @Author queer
     * @Description //TODO 单波段tiff创建生成style文件
     * @Date 13:42 2019/12/18
     * @Param [tifFile, storename]
     **/
    public static Kv createSld(File tifFile, String storename) {
        try {
            GeoTiffReader reader = new GeoTiffReader(tifFile);
            GridCoverage2D coverage = reader.read(null);
            RenderedImage sourceImage = coverage.getRenderedImage();
            TiledImage tiledImage = new TiledImage(sourceImage, true);
            Raster sourceRaster = sourceImage.getData();
//            WritableRaster writableRaster = tiledImage.copyData();
//            CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();
            GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
            //波段数量
            int numBands = sourceRaster.getNumBands();
            if (numBands != 1) {
                return Kv.by("msg", "波段数量:" + numBands).set("code", 400);
            }

            NoDataContainer noDataProperty = CoverageUtilities.getNoDataProperty(coverage);
            Range asRange = noDataProperty.getAsRange();
            System.out.println(asRange.getMin());

            //No Data
            Double noData = sampleDimension.getMaximumValue();
            Double noDataValues = sampleDimension.getNoDataValues()[0];
            System.out.println("noData1:" + noDataValues);
            System.out.println("noData2:" + noData);
            //获取影像长宽
            int iwidth = sourceImage.getWidth();
            int iheight = sourceImage.getHeight();
            System.out.println("宽：" + iwidth + "----长：" + iheight);
            //获取行列对应的像元值
            float[] adsaf = {0};
//            List<Float> count = new ArrayList();
            double max = 0;
            double min = 0;
            long l = System.currentTimeMillis();
            for (int i = 0; i < iwidth; i++) {
                for (int j = 0; j < iheight; j++) {
                    sourceRaster.getPixel(i, j, adsaf);
                    float v = adsaf[0];
                    if (Math.abs(v - noData.floatValue()) < 1e-6) {
                        continue;
                    }
                    if (max == 0 && min == 0) {
                        max = v;
                        min = v;
                    } else {
                        if (max < v) {
                            max = v;
                        }
                        if (min > v) {
                            min = v;
                        }
                    }
//                    count.add(adsaf[0]);
                }
            }
            System.out.println("遍历耗时：" + (System.currentTimeMillis() - l) + "ms");
//            double max = Collections.max(count);
//            double min = Collections.min(count);
            System.out.println("max:" + max + "---min:" + min);
            double cha = max - min;
            double ji = Arith.div(cha, 4, 2);
            double[] aa = new double[5];
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    aa[i] = min;
                } else {
                    aa[i] = aa[i - 1] + ji;
                }
            }
            System.out.println("像元值分五级每级差值:" + ji);
            //生成sld
            System.out.println("create new sld...");
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("StyledLayerDescriptor");
            root.addAttribute("version", "1.0.0");
            root.addAttribute("xmlns", "http://www.opengis.net/sld");
//            root.add(new Namespace("sld", "http://www.opengis.net/sld"));
            root.add(new Namespace("ogc", "http://www.opengis.net/ogc"));
            root.add(new Namespace("xlink", "http://www.w3.org/1999/xlink"));
            root.add(new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
            root.add(new Namespace("schemaLocation", "http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));
//            root.add(new Namespace("schemaLocation", "http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));

            Element row_nl = root.addElement("NamedLayer");
            row_nl.addElement("Name").addText("d:" + storename);
            Element row_nl_us = row_nl.addElement("UserStyle");
            row_nl_us.addElement("Name").addText("default");
            Element row_nl_us_f = row_nl_us.addElement("FeatureTypeStyle");
            Element row_nl_us_r = row_nl_us_f.addElement("Rule");

            Element row_nl_us_r_p = row_nl_us_r.addElement("RasterSymbolizer");
            // <Opacity>1.0</Opacity>
            row_nl_us_r_p.addElement("Opacity").addText("1.0");
            Element row_nl_us_r_c_p = row_nl_us_r_p.addElement("ColorMap");
            String color = "#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000,#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000";
            String[] colors = color.split(",");
            for (int i = 0; i < aa.length; i++) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", colors[i])
                        .addAttribute("quantity", String.valueOf(aa[i])).addAttribute("label", String.valueOf(aa[i]));
            }
            //生成style文件
            String rootPath = PathKit.getWebRootPath().replace("\\", "/");
            String path = rootPath + "/sld/" + storename + ".sld";
            saveDocument(document, path, "UTF-8");
            return Kv.by("msg", "生成sld成功:" + path).set("code", 200).set("path", path);
        } catch (Exception e) {
            e.printStackTrace();
            return Kv.by("msg", "生成sld失败:" + e.getMessage()).set("code", 400).set("Exception", e.getMessage());
        }
    }

    /**
     * @return boolean
     * @Author queer
     * @Description //TODO 单波段tiff创建生成style文件
     * @Date 13:42 2019/12/18
     * @Param [tifFile, storename]
     **/
    public static Kv createFixedSld(Integer id, String storename) {
        try {
            System.out.println("create new sld...");
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("StyledLayerDescriptor");
            root.addAttribute("version", "1.0.0");
            root.addAttribute("xmlns", "http://www.opengis.net/sld");
//            root.add(new Namespace("sld", "http://www.opengis.net/sld"));
            root.add(new Namespace("ogc", "http://www.opengis.net/ogc"));
            root.add(new Namespace("xlink", "http://www.w3.org/1999/xlink"));
            root.add(new Namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance"));
            root.add(new Namespace("schemaLocation", "http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));
//            root.add(new Namespace("schemaLocation", "http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"));

            Element row_nl = root.addElement("NamedLayer");
            row_nl.addElement("Name").addText("d:" + storename);
            Element row_nl_us = row_nl.addElement("UserStyle");
            row_nl_us.addElement("Name").addText("default");
            Element row_nl_us_f = row_nl_us.addElement("FeatureTypeStyle");
            Element row_nl_us_r = row_nl_us_f.addElement("Rule");

            Element row_nl_us_r_p = row_nl_us_r.addElement("RasterSymbolizer");
            // <Opacity>1.0</Opacity>
            row_nl_us_r_p.addElement("Opacity").addText("1.0");
            Element row_nl_us_r_c_p = row_nl_us_r_p.addElement("ColorMap");
            String color = "#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000,#AAFFAA,#00FF00,#FFFF00,#FF7F00,#BF7F3F,#000000";
            String[] colors = color.split(",");
            if (id == 79) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#00a2ff").addAttribute("quantity", String.valueOf(1));
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#00a2ff").addAttribute("quantity", String.valueOf(2));
            }
            if (id == 46) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#44cb2d").addAttribute("quantity", String.valueOf(0)).addAttribute("label", "0");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f1ff14").addAttribute("quantity", String.valueOf(1000)).addAttribute("label", "1000");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#fa0200").addAttribute("quantity", String.valueOf(2000)).addAttribute("label", "2000");
            }
            if (id == 28 || id == 48 || id == 92 ) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1e14ff").addAttribute("quantity", String.valueOf(230)).addAttribute("label", "230");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1a56ff").addAttribute("quantity", String.valueOf(240)).addAttribute("label", "240");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14b2ff").addAttribute("quantity", String.valueOf(250)).addAttribute("label", "250");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14f7ff").addAttribute("quantity", String.valueOf(260)).addAttribute("label", "260");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9cff14").addAttribute("quantity", String.valueOf(270)).addAttribute("label", "270");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#3bff14").addAttribute("quantity", String.valueOf(275)).addAttribute("label", "275");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9cff14").addAttribute("quantity", String.valueOf(280)).addAttribute("label", "280");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f7ff14").addAttribute("quantity", String.valueOf(290)).addAttribute("label", "290");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ffb410").addAttribute("quantity", String.valueOf(300)).addAttribute("label", "300");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff500b").addAttribute("quantity", String.valueOf(310)).addAttribute("label", "310");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff0707").addAttribute("quantity", String.valueOf(320)).addAttribute("label", "320");
            }
            if (id == 47 || id == 94) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff0707").addAttribute("quantity", String.valueOf(0.0)).addAttribute("label", "0.0");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff500b").addAttribute("quantity", String.valueOf(0.1)).addAttribute("label", "0.1");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ffb410").addAttribute("quantity", String.valueOf(0.2)).addAttribute("label", "0.2");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f7ff14").addAttribute("quantity", String.valueOf(0.3)).addAttribute("label", "0.3");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9cff14").addAttribute("quantity", String.valueOf(0.4)).addAttribute("label", "0.4");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#3bff14").addAttribute("quantity", String.valueOf(0.5)).addAttribute("label", "0.5");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9cff14").addAttribute("quantity", String.valueOf(0.6)).addAttribute("label", "0.6");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14f7ff").addAttribute("quantity", String.valueOf(0.7)).addAttribute("label", "0.7");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14b2ff").addAttribute("quantity", String.valueOf(0.8)).addAttribute("label", "0.8");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1a56ff").addAttribute("quantity", String.valueOf(0.9)).addAttribute("label", "0.9");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1e14ff").addAttribute("quantity", String.valueOf(1.0)).addAttribute("label", "1.0");
            }
//            if (id == 95) {
//                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#afff14").addAttribute("quantity", String.valueOf(0)).addAttribute("label", "0");
//                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#3bff14").addAttribute("quantity", String.valueOf(500)).addAttribute("label", "500");
//                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14f7ff").addAttribute("quantity", String.valueOf(1000)).addAttribute("label", "1000");
//                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#44b6ff").addAttribute("quantity", String.valueOf(1500)).addAttribute("label", "1500");
//                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1e14ff").addAttribute("quantity", String.valueOf(2000)).addAttribute("label", "2000");
//            }
            if (id == 30 || id == 95) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#afff14").addAttribute("quantity", String.valueOf(0)).addAttribute("label", "0");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#78ff14").addAttribute("quantity", String.valueOf(5)).addAttribute("label", "5");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#3bff14").addAttribute("quantity", String.valueOf(10)).addAttribute("label", "10");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1dff8a").addAttribute("quantity", String.valueOf(15)).addAttribute("label", "15");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14f7ff").addAttribute("quantity", String.valueOf(20)).addAttribute("label", "20");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#2adcff").addAttribute("quantity", String.valueOf(25)).addAttribute("label", "25");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#44b6ff").addAttribute("quantity", String.valueOf(30)).addAttribute("label", "30");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#3f8bff").addAttribute("quantity", String.valueOf(35)).addAttribute("label", "35");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#315fff").addAttribute("quantity", String.valueOf(40)).addAttribute("label", "40");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#2634ff").addAttribute("quantity", String.valueOf(45)).addAttribute("label", "45");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1e14ff").addAttribute("quantity", String.valueOf(50)).addAttribute("label", "50");
            }
            if (id == 44) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1483ff").addAttribute("quantity", String.valueOf(10)).addAttribute("label", "10");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14bcff").addAttribute("quantity", String.valueOf(20)).addAttribute("label", "20");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14f7ff").addAttribute("quantity", String.valueOf(40)).addAttribute("label", "40");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14ff8b").addAttribute("quantity", String.valueOf(60)).addAttribute("label", "60");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1fff14").addAttribute("quantity", String.valueOf(80)).addAttribute("label", "80");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#8bff14").addAttribute("quantity", String.valueOf(100)).addAttribute("label", "100");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f7ff14").addAttribute("quantity", String.valueOf(120)).addAttribute("label", "120");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff850d").addAttribute("quantity", String.valueOf(140)).addAttribute("label", "140");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff0707").addAttribute("quantity", String.valueOf(160)).addAttribute("label", "160");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#b30707").addAttribute("quantity", String.valueOf(180)).addAttribute("label", "180");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#640909").addAttribute("quantity", String.valueOf(200)).addAttribute("label", "200");
            }
            if (id == 45) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff0707").addAttribute("quantity", String.valueOf(0)).addAttribute("label", "0");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ff500b").addAttribute("quantity", String.valueOf(0.005)).addAttribute("label", "0.005");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#ffb410").addAttribute("quantity", String.valueOf(0.01)).addAttribute("label", "0.01");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#f7ff14").addAttribute("quantity", String.valueOf(0.015)).addAttribute("label", "0.015");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9cff14").addAttribute("quantity", String.valueOf(0.02)).addAttribute("label", "0.02");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#3bff14").addAttribute("quantity", String.valueOf(0.025)).addAttribute("label", "0.025");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#9cff14").addAttribute("quantity", String.valueOf(0.03)).addAttribute("label", "0.03");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14f7ff").addAttribute("quantity", String.valueOf(0.035)).addAttribute("label", "0.035");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#14b2ff").addAttribute("quantity", String.valueOf(0.04)).addAttribute("label", "0.04");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1a56ff").addAttribute("quantity", String.valueOf(0.045)).addAttribute("label", "0.045");
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", "#1e14ff").addAttribute("quantity", String.valueOf(0.05)).addAttribute("label", "0.05");
            }
            //生成style文件
            String rootPath = PathKit.getWebRootPath().replace("\\", "/");
            String path = rootPath + "/sld/" + storename + ".sld";
            saveDocument(document, path, "UTF-8");
            return Kv.by("msg", "生成sld成功:" + path).set("code", 200).set("path", path);
        } catch (Exception e) {
            e.printStackTrace();
            return Kv.by("msg", "生成sld失败:" + e.getMessage()).set("code", 400).set("Exception", e.getMessage());
        }
    }

    /**
     * 保存XML文档
     *
     * @param doc
     * @throws IOException
     */
    private static void saveDocument(Document doc, String path, String charset) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        XMLWriter writer;
//        File file=new File(path);
        try {
//            if(!file.exists())
//                file.createNewFile();
            writer = new XMLWriter(new FileOutputStream(path), format);
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
