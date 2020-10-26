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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssueTiffUtils {

    public static Kv uploadTiff(String tifPath, String name) throws Exception {
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        String geoserverUrl = config.getGeourl();
        String geoserverUsername = config.getGeouser();
        String geoserverPassword = config.getGeopsw();
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
        //发布tiff
        boolean result = geoServerRESTPublisher.publishGeoTIFF(workspace, name, tiffFile);
        if (result) {
            Kv sld = createSld(tiffFile, name);
            if (sld.getInt("code") != 200) {
                return Kv.by("msg", "发布失败，请检查数据坐标系等信息").set("code", 409);
            }else{
                return Kv.by("msg", "发布成功").set("code", 200).set("sld", sld.get("msg"));
            }
        } else {
            return Kv.by("msg", "发布失败，请检查数据坐标系等信息").set("code", 400);
        }
//        else {
//            return Kv.by("msg", "该数据已存在").set("code", 400);
//        }
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
        System.out.println("path:" + path);
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
        String tifDbfPath = null;
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
                if (".tif.vat.dbf".equals(s1) || ".tif.vat.dbf".equals(s1)) {
                    existDbf = true;
                    tifDbfPath = file01.getPath();
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
                    sld = createSld(tiffFile, name);
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
            row_nl_us_r_c_p.addAttribute("type", "values");
            String color = "#ff2626,#ff6c2b,#ffa82b,#ffda2b,#d5ff2b,#a8ff2b,#4eff2b,#2bff9e,#2bffda,#2bf3ff,#2bd5ff,#2bb2ff,#2b8fff,#2b49ff,#762bff,#b22bff,#e42bff,#ff2be9,#ff2bb7,#ff2b76";
            String[] colors = color.split(",");
            for (int i = 0; i < doubles.size(); i++) {
                row_nl_us_r_c_p.addElement("ColorMapEntry").addAttribute("color", colors[i])
                        .addAttribute("quantity", String.valueOf(doubles.get(i))).addAttribute("label", "values");
            }
            //生成style文件
            String rootPath = PathKit.getWebRootPath().replace("\\", "/");
            String path = rootPath + "/sld/" + storename + ".sld";
            saveDocument(document, path, "UTF-8");
            return Kv.by("msg", "生成sld成功:" + path).set("code", "200").set("path", path);
        } catch (Exception e) {
            e.printStackTrace();
            return Kv.by("msg", "生成sld失败:" + e.getMessage()).set("code", "500").set("Exception", e.getMessage());
        }
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
//            TiledImage tiledImage = new TiledImage(sourceImage, true);
            Raster sourceRaster = sourceImage.getData();
//            WritableRaster writableRaster = tiledImage.copyData();
//            CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem2D();

            GridSampleDimension sampleDimension = coverage.getSampleDimension(0);
            //波段数量
            int numBands = sourceRaster.getNumBands();
            if (numBands != 1) {
                return Kv.by("msg", "波段数量:" + numBands).set("code", "400");
            }
            //No Data
            double nodData = sampleDimension.getMinimumValue();
            //获取影像长宽
            int iwidth = sourceImage.getWidth();
            int iheight = sourceImage.getHeight();
            System.out.println("宽：" + iwidth + "----长：" + iheight);
            //获取行列对应的像元值
            double[] adsaf = {0};
            double max = 0;
            double min = 0;
            for (int i = 0; i < iwidth; i++) {
                for (int j = 0; j < iheight; j++) {
                    double[] pixel = sourceRaster.getPixel(i, j, adsaf);
                    double v = pixel[0];
                    if (nodData == v) {
                        continue;
                    }
                    if (i == 0 && j == 0) {
                        max = v;
                        min = v;
                    }
                    if (v > max) {
                        max = v;
                    }
                    if (v < min) {
                        min = v;
                    }
                }
            }
            System.out.println("max:" + max + "---min:" + min);
            double cha = max - min;
            double ji = cha / 4;
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
                        .addAttribute("quantity", String.valueOf(aa[i])).addAttribute("label", "values");
            }
            //生成style文件
            String rootPath = PathKit.getWebRootPath().replace("\\", "/");
            String path = rootPath + "/sld/" + storename + ".sld";
            saveDocument(document, path, "UTF-8");
            return Kv.by("msg", "生成sld成功:" + path).set("code", "200").set("path", path);
        } catch (Exception e) {
            e.printStackTrace();
            return Kv.by("msg", "生成sld失败:" + e.getMessage()).set("code", "400").set("Exception", e.getMessage());
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
