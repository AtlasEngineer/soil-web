package com.atlas.test;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.PathUtils;
import com.soli.server.utils.GzUtils;
import com.soli.server.utils.ReadTiffUtils;
import com.vividsolutions.jts.io.ParseException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.opengis.referencing.operation.TransformException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TifTest {

            @Test
            public void tiffsearch() throws TransformException, ParseException, IOException {
                long start = System.currentTimeMillis();
                for (int i = 0; i < 365; i++) {
                    String path = "C:\\Users\\xiaoxu\\Desktop\\temp1.tif";
                    String wkt = "POLYGON((136.786497350986 130.82272412483,156.023350911984 125.307250012628,173.776795522493 116.071872701915,189.335846577232 103.486447803021,202.077399883442 88.0549926235943,211.49118555002 70.3955014351889,217.20020312251 51.2151962247221,218.975819583804 31.2822040812422,216.74692558039 11.3947954735025,216.746729334699 11.3938829739416,210.979986034486 -6.77727851139229,201.86961238665 -23.5238462198351,189.746721676323 -38.237172113123,175.051916048218 -50.3825056025267,158.319272978622 -59.5184288685214,140.156934419761 -65.3129000342052,121.225004097161 -67.5553211090423,102.211556274876 -66.1641920978144,102.21133626215 -66.164154670402,82.9044521998754 -60.8457555588494,65.0444214751801 -51.7870335840448,49.3475047986195 -39.3512807659411,36.4432131080853 -24.0372219548527,26.8490615903066 -6.45901391313905,20.9498151927365 12.6783848976874,18.9820579661864 32.6074860515584,21.024705071051 52.5290506012832,21.0247426254575 52.5292335359835,21.2391410213249 53.5467249408522,21.2392536316055 53.5472459258984,23.870747609991 63.304623601676,23.8707858506123 63.3047413672583,31.6126931235193 81.0680129712847,43.4733745917556 97.9828786211444,58.5566035741785 112.099556886299,76.2186992442923 122.815655911497,95.7058709086254 129.673829305552,116.186436477067 132.381380925535,136.786317357196 130.822756691606,136.786497350986 130.82272412483))";
                    double cec = ReadTiffUtils.getAltitudeByWkt(wkt, path);
                    System.out.println(i + ":" + cec);
                }
                System.out.println("共耗时" + (System.currentTimeMillis() - start) + "ms");
            }

            public static void main(String[] args) {
                // TODO Auto-generated method stub
                try {
            //解压
            long start = System.currentTimeMillis();
            File file = new File("D:/data/GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872.tar.gz");
            String fileext = PathUtils.getExtensionName(file.getName());
                    System.out.println(fileext);
//            GzUtils.readTarFile(file);
////            GzUtils.deCompressGZipFile("D:/data/GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872.tar.gz","D:/data/GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872");
//
//            System.out.println("解压共耗时" + (System.currentTimeMillis() - start) + "ms");
//            String name = file.getName().substring(0, file.getName().indexOf(".tar.gz"));
//            SAXReader reader = new SAXReader();
//            Document doc = reader.read(new File("D:/data/GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872/GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872.xml"));
//            // 读取指定标签
//            String topLeftLatitude = doc.getRootElement().elementText("TopLeftLatitude");
//            String topLeftLongitude = doc.getRootElement().elementText("TopLeftLongitude");
//            String topRightLatitude = doc.getRootElement().elementText("TopRightLatitude");
//            String topRightLongitude = doc.getRootElement().elementText("TopRightLongitude");
//
//            String bottomRightLatitude = doc.getRootElement().elementText("BottomRightLatitude");
//            String bottomRightLongitude = doc.getRootElement().elementText("BottomRightLongitude");
//            String bottomLeftLatitude = doc.getRootElement().elementText("BottomLeftLatitude");
//            String bottomLeftLongitude = doc.getRootElement().elementText("BottomLeftLongitude");

//            System.out.println(topLeftLatitude);
//            System.out.println(topLeftLongitude);
//            System.out.println(topRightLatitude);
//            System.out.println(topRightLongitude);
//
//            System.out.println(bottomRightLatitude);
//            System.out.println(bottomRightLongitude);
//            System.out.println(bottomLeftLatitude);
//            System.out.println(bottomLeftLongitude);
            System.out.println("共耗时" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
