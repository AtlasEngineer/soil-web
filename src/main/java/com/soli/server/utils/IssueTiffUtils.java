package com.soli.server.utils;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.lambkit.Lambkit;
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

import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
            return Kv.by("msg", "发布成功").set("code", 200).set("sld", sld.get("msg"));
        } else {
            return Kv.by("msg", "发布失败").set("code", 400);
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
        String[] fileList = files.list();
        List<String> list = new ArrayList<>();
        for (String y : fileList) {
            File file1 = new File(s + "/" + y);
            System.out.println("file1:" + file1.getName());
            if (file1.isFile()) {
                String s1 = file1.getName().split("\\.")[1];
                File file01 = new File(PathKit.getWebRootPath() + "/d/" + name + "/" + name + "." + s1);
                System.out.println("file01:" + file01.getName());
                file1.renameTo(file01);
                String path1 = file01.getPath();
                System.out.println("path" + path1);
                list.add(file01.getPath());
                if ("tiff".equals(s1) || "tif".equals(s1)) {
                    tifPath = file01.getPath();
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
                Kv sld = createSld(tiffFile, name);
                Integer code = sld.getInt("code");
                if (code == 200) {
                    String sql1 = "update ld_geoconfig set xml = '?' where name = '?'";
                    Db.update(sql1, sld.getStr("path"), name);
                }
                return Kv.by("msg", "发布成功").set("code", 200).set("sld", sld.get("msg"));
            } else {
                return Kv.by("msg", "发布失败").set("code", 400);
            }
        } else {
            return Kv.by("msg", "该数据已存在").set("code", 400);
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
            return Kv.by("msg", "生成sld成功:"+path).set("code", "200").set("path", path);
        } catch (Exception e) {
            e.printStackTrace();
            return Kv.by("msg", "生成sld失败:"+e.getMessage()).set("code", "500").set("Exception", e.getMessage());
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
