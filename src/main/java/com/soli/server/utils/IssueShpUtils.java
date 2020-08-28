package com.soli.server.utils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.mapfinal.resource.shapefile.Shapefile;
import com.soli.lambkit.start.GeoServerConfig;
import com.soli.server.utils.CodePageUtils;
import com.soli.server.utils.PublicShp;
import com.soli.server.utils.TestShapeFile;
import com.soli.server.utils.readShp;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssueShpUtils {


    public static Kv uploadShp(String filepath) throws Exception {
        System.out.println(filepath);
        File file = new File(PathKit.getWebRootPath() + filepath);
        System.out.println("file:" + file.getAbsolutePath());
        System.out.println(file.getName());
        if (file == null) {
            return Kv.by("errorMsg", "找不到该文件").set("code", 400);
        }
        String name = file.getName().split("\\.")[0];

        //解压后文件夹
        String s = PathKit.getWebRootPath() + "/d/" + name;
        try {
            com.atlas.server.utils.ZipUtils.decompress(file.getPath(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("s" + s);
        File files = new File(s);
        String shpPath = null;
        String shxPath = null;
        String dbfPath = null;
        String prjPath = null;
        String[] fileList = files.list();
        List<String> list = new ArrayList<>();
        for (String y : fileList) {
            File file1 = new File(s + "/" + y);
            System.out.println("file1:" + file1.getName());
            if (file1.isFile()) {
//                String s1 = file1.getName().split("\\.")[1];
                String[] split = file1.getName().split("\\.");
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < split.length; i++) {
                    System.out.println("saaa:"+split[i]);
                    sb.append(split[i]).append(".");
                }
                String s1 = sb.toString().substring(0,sb.length()-1);
                File file01 = new File(PathKit.getWebRootPath() + "/d/" + name + "/" + name + "." + s1);
                System.out.println("file01:" + file01.getName());
                file1.renameTo(file01);
                String path = file01.getPath();
                System.out.println("path"+path);
                list.add(file01.getPath());
                if("shp".equals(s1)){
                    shpPath = file01.getPath();
                }
                if("shx".equals(s1)){
                    shxPath = file01.getPath();
                }
                if("dbf".equals(s1)){
                    dbfPath = file01.getPath();
                }
                if("prj".equals(s1)){
                    prjPath = file01.getPath();
                }
            }
        }
        if(shpPath==null){
            return Kv.by("errorMsg", "上传压缩文件没有shp").set("code", 400);
        }
        if(shxPath==null){
            return Kv.by("errorMsg", "上传压缩文件没有shx").set("code", 400);
        }
        if(dbfPath==null){
            return Kv.by("errorMsg", "上传压缩文件没有dbf").set("code", 400);
        }
        if(prjPath==null){
            return Kv.by("errorMsg", "上传压缩文件没有prj").set("code", 400);
        }
        String newZip = PathKit.getWebRootPath() + "/d/" + name + ".zip";
//        ZipUtils.compress(s, newZip);
        try {
            com.atlas.server.utils.ZipUtils.compress(list,newZip,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        //if(config==null) return false;
        String RESTURL = config.getGeourl();//http://localhost:8080/geoserver
        String RESTUSER = config.getGeouser();//admin
        String RESTPW = config.getGeopsw();//"Q7P0XFU$2YRQaq08";

        String workspace = "d";//jsus;
        System.out.println("RESTURL:" + RESTURL);
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        GeoServerRESTReader geoServerRESTReader = new GeoServerRESTReader(RESTURL,RESTUSER,RESTPW);
        boolean b = false;
        System.out.println("new zip:" + newZip);

        GeoServerRESTPublisher.UploadMethod method = GeoServerRESTPublisher.UploadMethod.EXTERNAL;

        String codePage = CodePageUtils.getCodePage(s+"/"+name+".dbf");
        System.out.println("codePage:"+codePage);

        NameValuePair[] storeParams = new NameValuePair[1];
        storeParams[0] = new NameValuePair("charset", codePage);

        URI shapeFile = new URI(String.format("file:%s", s));

        try {
            b = publisher.publishShp(workspace, name, storeParams, name, method, shapeFile, "EPSG:4326", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (b) {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            String sql = "insert into tr_geolist (name,datatype,url,dt_time) values('" + name + "','shp','" + name + "','" + time + "')";
            Db.update(sql);
            return Kv.by("errorMsg", "发布成功").set("code", 200);
        } else {
            return Kv.by("errorMsg", "发布失败").set("code", 400);
        }
    }

    /**
     * @return com.jfinal.kit.Kv
     * @Author queer
     * @Description //TODO 自动发布shp到geoserver
     * @Date 17:04 2019/11/30
     * @Param [upfile]
     **/
    public static Kv uploadShpOld(String filepath) {
        File file = new File(PathKit.getWebRootPath() + filepath);
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("ddHHmmss");
        String st = StringUtils.getRandString16();
        //获取String类型的时间
        String ss = df.format(date) + "y";
        String filename = file.getName();
        int dot = filename.lastIndexOf('.');
        String f = filename.substring(0, dot);
        System.out.println("filename：" + filename);
        String delfilename = filename;
        JSONObject json = new JSONObject();
        if (filename != null && !filename.equals("")) {
            //hz 代表上传文件的后缀名 如 zip,rar,xls等
            String hz = filename.substring(filename.lastIndexOf(".") + 1).trim().toLowerCase();
            filename = ss + st + "." + hz;
            //filename=filename.substring(0, filename.lastIndexOf("."))+"."+filename.substring(filename.lastIndexOf("." ) + 1).trim().toLowerCase();
            /**
             * 新保存的位置
             */
            String path = PathKit.getWebRootPath();
            String newPath = null;
            if (!hz.equals("zip") && !hz.equals("xls") && !hz.equals("xlsx") && !hz.equals("csv")) {
                File delFile = new File(path + "/upload/" + delfilename);
                if (delFile.exists()) {
                    delFile.delete();
                }
                return Kv.by("msg", "压缩包格式请改为zip格式,excel请改为xls,xlsx格式").set("code", 400);
            }
            if (!hz.equals("zip")) {
                if (hz.equals("xls") || hz.equals("xlsx")) {
                    newPath = "/upload/exc/" + ss + st + "/";
                } else {
                    newPath = "/upload/exc/";//自定义目录  用于存放图片,文件
                }
            } else {
                newPath = "/upload/zip/";//自定义目录  用于存放压缩包
            }
            json.put("fileUrl", newPath + filename);
            System.out.println(newPath + filename);
            /**
             * 没有则新建目录
             */
            File floder = new File(path + newPath);
            if (!floder.exists()) {
                floder.mkdirs();
            }
            /**
             * 保存新文件
             */
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                File savePath = new File(path + newPath + filename);
                if (!savePath.isDirectory()) savePath.createNewFile();
                fis = new FileInputStream(file);
                fos = new FileOutputStream(savePath);
                byte[] bt = new byte[300];
                while (fis.read(bt, 0, 300) != -1) {
                    fos.write(bt, 0, 300);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fis) fis.close();
                    if (null != fos) fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /*判断sheet名称，如果以数字开头则提示“Sheet名称不能以数字开头”
             * 让用户修改sheet名称
             */
            if (hz.equals("xls")) {
                FileInputStream readFile;
                HSSFWorkbook wb;
                try {
                    readFile = new FileInputStream(new File(path + newPath + filename));
                    wb = new HSSFWorkbook(readFile);
                    HSSFSheet s = wb.getSheetAt(0);
                    Pattern pattern = Pattern.compile("[0-9]*");
                    String name = s.getSheetName();
                    Matcher isNum = pattern.matcher(name.charAt(0) + "");
                    if (isNum.matches()) {
                        File ft = new File(path + newPath + filename);
                        if (ft.exists()) {
                            ft.delete();
                        }
                        File delFile = new File(path + "/upload/" + delfilename);
                        if (delFile.exists()) {
                            delFile.delete();
                        }
                        return Kv.by("msg", "Sheet名称不能以数字开头").set("code", 400);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String str = newPath + filename;
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String sql = "insert into kk_geolist (name,datatype,url,dt_time) values('" + f + "','" + hz + "','" + str + "','" + time + "')";
                Db.update(sql);
                /*判断sheet名称，如果以数字开头则提示“Sheet名称不能以数字开头”
                 * 让用户修改sheet名称
                 */
            } else if (hz.equals("xlsx")) {
                FileInputStream readFile;
                XSSFWorkbook wb;
                try {
                    readFile = new FileInputStream(new File(path + newPath + filename));
                    wb = new XSSFWorkbook(readFile);
                    XSSFSheet s = wb.getSheetAt(0);
                    Pattern pattern = Pattern.compile("[0-9]*");
                    String name = s.getSheetName();
                    Matcher isNum = pattern.matcher(name.charAt(0) + "");
                    if (isNum.matches()) {
                        File ft = new File(path + newPath + filename);
                        if (ft.exists()) {
                            ft.delete();
                        }
                        File delFile = new File(path + "/upload/" + delfilename);
                        if (delFile.exists()) {
                            delFile.delete();
                        }
                        return Kv.by("msg", "Sheet名称不能以数字开头").set("code", 400);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String str = newPath + filename;
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String sql = "insert into kk_geolist (name,datatype,url,dt_time) values('" + f + "','" + hz + "','" + str + "','" + time + "')";
                Db.update(sql);
            } else if (hz.equals("zip")) {
                String str = newPath + filename;
                PublicShp geoService = new PublicShp();
                TestShapeFile shp = new TestShapeFile();
                try {
                    com.atlas.server.utils.ZipUtils.decompress(path + newPath + filename);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                File files = new File(path + newPath);
                System.out.println("路径为" + files.getAbsolutePath());
                File temp = null;
                String[] fileList = files.list();
                System.out.println("压缩包的文件数量为:" + fileList.length);
                if (fileList.length < 1) {
                    Map<String, String> map = new HashMap<String, String>();
                    File delFile = new File(path + "/upload/" + delfilename);
                    if (delFile.exists()) {
                        delFile.delete();
                    }
                    for (String y : fileList) {
                        temp = new File(path + newPath + y);
                        if (temp.isFile()) {
                            temp.delete();
                        }
                    }
                    return Kv.by("msg", "压缩包里面的文件缺失").set("code", 400);
                }
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String sql = "insert into kk_geolist (name,datatype,url,dt_time) values('" + f + "','shp','" + str + "','" + time + "')";
                Db.update(sql);
                String sqle = "select id from kk_geolist where url='" + str + "'";
                Record m = Db.findFirst(sqle);
                int dots = filename.lastIndexOf('.');
                String fer = filename.substring(0, dots);
                String fileSeperator = File.separator;//自动区分系统斜杠
                String jsus = "jsus";
                System.out.println("文件路啊啊啊啊啊啊啊：" + path + fileSeperator + jsus + fileSeperator + jsus + fileSeperator + fer + "/");
                String shpfile = shp.unZip(new File(path + newPath + filename), path + fileSeperator + jsus + fileSeperator + jsus + fileSeperator + fer + "/");
                readShp rs = new readShp();
                String checktype = rs.checktype("jsus:" + fer);
                String sld;
                if (checktype.equals("Polygon")) {
                    sld = "polygon";
                } else if (checktype.equals("Point")) {
                    sld = "point";
                } else {
                    sld = "line";
                }
                System.out.println("sld：" + sld);
                boolean flag = geoService.publishPg(fer, fer, fer, m.get("id"), "EPSG:4326", sld);
                System.out.println(flag + "发布状态");
                if (!flag) {
                    for (String y : fileList) {
                        temp = new File(path + newPath + y);
                        if (temp.isFile()) {
                            temp.delete();
                        }
                    }
                    File delFile = new File(path + "/upload/" + delfilename);
                    if (delFile.exists()) {
                        delFile.delete();
                    }
                    String sqld = "delete from kk_geolist where id='" + m.get("id") + "'";
                    Db.update(sqld);
                    String shppath = new File(shpfile).getParent();
                    System.out.println(shppath);
                    try {
                        PathUtils.deleteDirectory(new File(shppath));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return Kv.by("msg", "发布失败").set("code", 400);
                }
                for (String y : fileList) {
                    temp = new File(path + newPath + y);
                    if (temp.isFile()) {
                        temp.delete();
                    }
                }
            } else {
                String str = newPath + filename;
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String sql = "insert into kk_geolist (name,datatype,url,dt_time) values('" + f + "','" + hz + "','" + str + "','" + time + "')";
                int update = Db.update(sql);
            }
            /**
             * 删除原压缩包，JFinal默认上传文件路径为 /upload（自动创建）
             */
            File delFile = new File(path + "/upload/" + delfilename);
            if (delFile.exists()) {
                delFile.delete();
            }
            return Kv.by("msg", "发布成功").set("code", 200);
        } else {
            return Kv.by("msg", "找不到文件").set("code", 400);
        }
    }

}
