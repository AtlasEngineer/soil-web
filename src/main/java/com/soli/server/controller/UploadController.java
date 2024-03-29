package com.soli.server.controller;


import com.jfinal.aop.Clear;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import com.soli.server.model.Data;
import com.soli.server.model.DataEach;
import com.soli.server.service.impl.DataEachServiceImpl;
import com.soli.server.utils.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.soli.server.utils.CodePageUtils.getUserEntity;

@Clear(JwtTokenInterceptor.class)
public class UploadController extends LambkitController {


    @Clear
    @ApiOperation(url = "/upload/uploadProductExcel", tag = "/upload", httpMethod = "post", description = "上传数据")
    public void uploadProductExcel() {
        UploadFile uf = getFile();
        File file = uf.getFile();
        String name = file.getName();
        if (file == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "未获取到excel")));
            return;
        } else if ((!name.endsWith("xls")) && (!name.endsWith("xlsx"))) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "只能导入excel格式数据")));
            return;
        } else {
            try {
                ExcelReaderUtils.productUpload(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        renderJson(Co.ok("data", Co.by("state", "ok").set("msg", "保存成功")));
        return;
    }


    /**
     * @return void
     * @Author queer
     * @Description //TODO 上传发布数据（0：shp,1：tiff，2：word，excle等）
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @Clear
    @ApiOperation(url = "/upload/uploadData", tag = "/upload", httpMethod = "post", description = "上传数据")
    public void uploadData() {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile();
        File file = uf.getFile();

        String serverSessionId = this.getRequest().getHeader("Authorization");
        String code = UpmsManager.me().getCache().getSession(serverSessionId);
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data", Ret.fail("errorMsg", "请重新登录")));
            return;
        }
        UpmsUser user = getUserEntity();

        if (user == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "当前登录用户异常")));
            return;
        }

        Integer id = getParaToInt("id");
        String dataName = getPara("name");
        Date data_time = getParaToDate("data_time");
        if (id == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请选择要查看的数据")));
            return;
        }
        Data data = Data.service().dao().findById(id);
        Integer type = data.getType();
        if (type != 2) {
            if (StringUtils.isBlank(dataName)) {
                renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请输入数据名称")));
                return;
            }
            if (data_time == null) {
                renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请选择数据时间")));
                return;
            }
        }
        String yname = file.getName();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/datafile/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
//        if (file.length() > 104857600) {
//            file.delete();
//            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "文件大小不能大于100MB")));
//            return;
//        } else
        if (!"zip".equals(fileext) && (type == 1 || type == 0 || type == 4)) {
            file.delete();
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "文件格式不正确,支持zip文件上传")));
            return;
        } else if (!"csv".equals(fileext) && !"xlsx".equals(fileext) && !"xls".equals(fileext) && type == 2) {
            file.delete();
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "文件格式不正确,支持xlsx、xls、csv文件上传")));
            return;
        } else if (!"gz".equals(fileext) && type == 3) {
            file.delete();
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "文件格式不正确,支持tar.gz文件")));
            return;
        } else {
            //重命名
            if (type == 0) {
                filename = "shp-" + filename;
            } else if (type == 1 || type == 5) {
                filename = "tif-" + filename;
            } else if (type == 3) {
                filename = "GF-" + UUID.randomUUID().toString() + ".tar.gz";
            } else if (type == 4) {
                if (id == 82) {
                    //landset
                    filename = "landset-" + filename;
                } else {
                    filename = "SB1-" + filename;
                }
            } else if (type == 6) {
                filename = "SB2-" + filename;
            }
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "重命名失败")));
                return;
            }
            String root = PathKit.getWebRootPath().replace("\\", "/");
            File file1 = new File(rootPath + filename);
            if (file1 == null) {
                renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "找不到该文件")));
                return;
            }
            String name = filename.split("\\.")[0];
            //发布
            Kv kv = null;
            if (type == 1 || type == 0 || type == 5) {
                //解压后文件夹
                String s = root + "/d/" + name;
                try {
                    ZipUtils.decompress(file1.getPath(), s);
                } catch (Exception e) {
                    e.printStackTrace();
                    renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "解压错误")));
                    return;
                }
                //判断数据type
                File files = new File(s);
                String[] fileList = files.list();
                //0 shp,1 tiff,2文档
                String shpPath = null;
                String shxPath = null;
                String dbfPath = null;
                String prjPath = null;
                String tifPath = null;
                String tifAuxPath = null;
                String tifDbfPath = null;
                for (String y : fileList) {
                    File file2 = new File(s + "/" + y);
                    if (file2.isFile()) {
                        String[] split = file2.getName().split("\\.");//尾椎集合
                        StringBuffer sb = new StringBuffer();
                        for (int i = 1; i < split.length; i++) {
                            sb.append(split[i] + ".");
                        }
                        String s1 = sb.toString().substring(0, sb.length() - 1);
                        File file01 = new File(root + "/d/" + name + "/" + name + "." + s1);
                        file2.renameTo(file01);
                        if (type == 1 || type == 5) {
                            if ("tif".equals(s1) || "tiff".equals(s1)) {
                                tifPath = file01.getPath();
                            }
                            if ("tif.vat.dbf".equals(s1) || "tif.vat.dbf".equals(s1)) {
                                tifDbfPath = file01.getPath();
                            }
                            if ("tif.aux.xml".equals(s1) || "tif.aux.xml".equals(s1)) {
                                tifAuxPath = file01.getPath();
                            }
                        } else if (type == 0) {
                            if ("shp".equals(s1)) {
                                shpPath = file01.getPath();
                            }
                            if ("shx".equals(s1)) {
                                shxPath = file01.getPath();
                            }
                            if ("dbf".equals(s1)) {
                                dbfPath = file01.getPath();
                            }
                            if ("prj".equals(s1)) {
                                prjPath = file01.getPath();
                            }
                        }
                    }
                }
                if (type == 0) {
                    if (shpPath == null) {
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "上传压缩文件没有shp")));
                        return;
                    }
                    if (shxPath == null) {
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "上传压缩文件没有shx")));
                        return;
                    }
                    if (dbfPath == null) {
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "上传压缩文件没有dbf")));
                        return;
                    }
                    if (prjPath == null) {
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "上传压缩文件没有prj")));
                        return;
                    }
                    //发布shp
                    try {
                        kv = IssueShpUtils.uploadShp(s, name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (type == 1 || type == 5) {
                    if (tifPath == null) {
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "上传压缩文件没有tif")));
                        return;
                    }
                    //发布tiff
                    try {
                        Integer sldType = 0;
                        File sldParams = null;
                        if (tifAuxPath != null) {
                            sldType = 2;
                            sldParams = new File(tifAuxPath);
                        }
                        if (tifDbfPath != null) {
                            sldType = 1;
                            sldParams = new File(tifDbfPath);
                        }
                        kv = IssueTiffUtils.uploadTiff(tifPath, name, sldType, sldParams, data.getId());
                        if (kv.getInt("code") != 200) {
                            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", kv.get("msg"))));
                            return;
                        }
                        if ("无人机数据".equals(data.getName())) {
                            //添加最大最小坐标
                            Kv tiffXY = ReadTiffUtils.getTiffXY(tifPath);
                            kv.set(tiffXY);
                        } else if ("10cm土壤温度".equals(data.getName())) {
                            Kv readQwAndSd = readQwAndSd(tifPath, "tr_tk_temperature", data_time, 10);
                            kv.set(readQwAndSd);
                        } else if ("0-10cm土壤湿度".equals(data.getName())) {
                            Kv readQwAndSd = readQwAndSd(tifPath, "tr_tk_humidity", data_time, 10);
                            kv.set(readQwAndSd);
                        } else if ("40cm土壤温度".equals(data.getName())) {
                            Kv readQwAndSd = readQwAndSd(tifPath, "tr_tk_temperature", data_time, 40);
                            kv.set(readQwAndSd);
                        } else if ("10-40cm土壤湿度".equals(data.getName())) {
                            Kv readQwAndSd = readQwAndSd(tifPath, "tr_tk_humidity", data_time, 40);
                            kv.set(readQwAndSd);
                        } else if ("积温".equals(data.getName())) {
                            Kv readQwAndSd = readQwAndSd(tifPath, "tr_tk_accumulated", data_time, 0);
                            kv.set(readQwAndSd);
                        } else if ("积雨".equals(data.getName())) {
                            Kv readQwAndSd = readQwAndSd(tifPath, "tr_tk_eroded", data_time, 0);
                            kv.set(readQwAndSd);
                        }
                        if (id == 79 || id == 44 || id == 45 || id == 30 || id == 46 || id == 47 || id == 94 || id == 95 || id == 28 || id == 48 || id == 92 || id == 98) {
                            //气象数据重新生成sld、灌溉面积
                            Kv sld = IssueTiffUtils.createFixedSld(id, name);
                            if (sld.getInt("code") != 200) {
                                kv = Kv.by("msg", "发布失败，请检查数据坐标系等信息").set("code", 409);
                            } else {
                                kv = Kv.by("msg", "发布成功").set("code", 200).set("sld", sld.get("msg"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请检查数据坐标系等信息").set("code", 409)));
                        return;
                    }
                }
            } else if (type == 2) {
                //发布表格到数据库
                String s = rootPath + filename;
                if ("xlsx".equals(fileext) || "xls".equals(fileext)) {
                    //获取数据库表字段属性
                    List<Record> records = Db.find("SELECT COLUMN_NAME as name,ordinal_position as no,is_nullable as isnull,character_maximum_length as length,udt_name as type " +
                            " FROM information_schema.COLUMNS AS C  WHERE TABLE_NAME = '" + data.getUrl() + "'");
                    try {
                        ExcelReaderUtils.way(s, records, data.getUrl());
                    } catch (Exception e) {
                        e.printStackTrace();
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "写入" + fileext + "数据失败")));
                        return;
                    }
                } else {
                    //csv数据直接sql导入
                    try {
                        Db.update("COPY " + data.getUrl() + "(category,tag,product,place,price,status,up_time,url) from '" + s + "' WITH CSV  HEADER");
                    } catch (Exception e) {
                        e.printStackTrace();
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "写入csv数据失败")));
                        return;
                    }
                }
            } else if (type == 3) {
                //高分数据
                GzUtils.readTarFile(file);
                name = file.getName().substring(0, file.getName().indexOf(".tar.gz"));
            } else if (type == 6) {
                //哨兵2数据
                //解压后文件夹
                String s = root + "/d/" + name;
                try {
                    ZipUtils.decompress(file1.getPath(), s);
                } catch (Exception e) {
                    e.printStackTrace();
                    renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "解压错误")));
                    return;
                }

            }
            DataEach dataEach = new DataEach();
            if (dataName == null) {
                dataEach.setName(yname);
            } else {
                dataEach.setName(dataName);
            }
            if (type == 1 && kv.getStr("sld").contains("波段数量")) {
                //多波段tiff没有配色sld
                dataEach.setType(5);
            } else {
                dataEach.setType(type);
            }
            if ("无人机数据".equals(data.getName())) {
                dataEach.set("topLeftLongitude", kv.getNumber("maxX"));
                dataEach.set("topLeftLatitude", kv.getNumber("maxY"));
                dataEach.set("topRightLongitude", kv.getNumber("minX"));
                dataEach.set("topRightLatitude", kv.getNumber("maxY"));
                dataEach.set("bottomRightLongitude", kv.getNumber("minX"));
                dataEach.set("bottomRightLatitude", kv.getNumber("minY"));
                dataEach.set("bottomLeftLongitude", kv.getNumber("maxX"));
                dataEach.set("bottomLeftLatitude", kv.getNumber("minY"));
            }
            dataEach.setDataId(id);
            dataEach.setDataTime(data_time);
            dataEach.setTime(new Date());
            dataEach.setUserid(user.getUserId().intValue());
            if (type == 0 || type == 1 || type == 5) {
                dataEach.setUrl("d:" + name);
            } else if (type == 2) {
                dataEach.setUrl("/upload/datafile/" + filename);
            } else if (type == 3) {
                dataEach.setUrl("/d/" + name + "/" + name + ".jpg");
            } else if (type == 6) {
                dataEach.setUrl("/d/" + name + "/INSPIRE.xml");
            }
            boolean save = dataEach.save();
            if (save) {
                List<DataEach> dataEaches = DataEach.service().dao().find(DataEach.sql().andDataIdEqualTo(id).example().setOrderBy("data_time desc"));
                String webRootPath = PathKit.getWebRootPath();
                for (int i = 0; i < dataEaches.size(); i++) {
                    DataEach dataEach1 = dataEaches.get(i);
                    Integer type1 = data.getType();
                    Kv kv1 = null;
                    if (type1 == 0) {
                        kv1 = readShp.readShpXY(webRootPath + "/d/" + dataEach1.getUrl().split(":")[1] + "/" + dataEach1.getUrl().split(":")[1] + ".shp");
                    } else if (type1 == 1) {
                        kv1 = ReadTiffUtils.getTiffXY(webRootPath + "/d/" + dataEach1.getUrl().split(":")[1] + "/" + dataEach1.getUrl().split(":")[1] + ".tif");
                    } else if (type1 == 3) {
                        kv1 = ReadTiffUtils.getXmlLatlons(root + "/d/" + name + "/" + name + ".xml");
                    } else if (type1 == 6) {
                        SAXReader reader = new SAXReader();
                        Document doc = null;
                        try {
                            doc = reader.read(new File(root + "/d/" + name + "/INSPIRE.xml"));
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        // 读取指定标签
                        Element identificationInfo = doc.getRootElement().element("identificationInfo");
                        Element MD_DataIdentification = identificationInfo.element("MD_DataIdentification");
                        Element abstractElement = MD_DataIdentification.element("abstract");
                        String latlons = abstractElement.elementText("CharacterString");
                        kv1.set("latlons", latlons);
                    }
                    if (kv1 != null) {
                        dataEach1.put(kv1);
                    }
                }
                if (kv != null && kv.get("sld") != null) {
                    renderJson(Co.ok("data", Co.by("state", "ok").set("sld", kv.get("sld")).set("list", dataEaches)));
                } else {
                    renderJson(Co.ok("data", Co.by("state", "ok").set("list", dataEaches)));
                }
            } else {
                renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "保存失败")));
            }
        }
    }

    //更新所有地块的温度或湿度数据
    public static Kv readQwAndSd(String filePath, String tableName, Date time, int height) {
        //1、获取地块wkt
        List<Record> records = Db.find("select id,st_astext(geom) as wkt,dk_name from tr_tiankuai");
        Kv kv = Kv.create();
        for (Record record : records) {
            //2、分获取积温、积雨日期列表，遍历读取像素值保存
            String wkt = record.get("wkt");
            Integer id = record.getInt("id");
            String dk_name = record.getStr("dk_name");
            //读取当前日期文件两个数据像素值
            try {
                double v10 = ReadTiffUtils.getAltitudeByWkt(wkt, filePath);
                Record first = Db.findFirst("select * from " + tableName + " where tk_id = '" + id + "' and time = '" + time + "' and type = '" + height + "' ");
                if (first == null) {
                    Db.update("insert into " + tableName + "(tk_id,value,time,type) values('" + id + "','" + v10 + "','" + time + "','" + height + "')");
                    kv.set(dk_name + "," + height, v10);
                }
            } catch (Exception e) {
                e.printStackTrace();
                kv.set(dk_name, e.getMessage());
            }
        }
        return kv;
    }


    /**
     * @Description: 上传tiff压缩文件
     * @Author: yangxueyang
     * @Date: 2019/8/28
     */
    public void uploadTifZip() {
        UploadFile uf = getFile();
        File file = uf.getFile();
        String root = PathKit.getWebRootPath().replace("\\", "/");
        String fileext = PathUtils.getExtensionName(file.getName());
        String name = UUID.randomUUID().toString();
        String filename = name + "." + "zip";
        if (file.length() > 104857600) {
            file.delete();
            renderJson(Co.ok("data", Ret.fail("errorMsg", "文件大小不能大于100MB")));
            return;
        } else if (!"zip".equals(fileext)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "压缩包只支持zip格式")));
            file.delete();
            return;
        } else {
            File newfile = new File(root + "/upload/" + filename);
            boolean b = file.renameTo(newfile);
            if (!b) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "重命名失败")));
            } else {
                //解压后文件夹
                String s = root + "/d/" + name;
                try {
                    ZipUtils.decompress(newfile.getPath(), s);
                } catch (Exception e) {
                    e.printStackTrace();
                    renderJson(Co.ok("data", Ret.fail("errorMsg", "解压错误")));
                    return;
                }
                File files = new File(s);
                String[] fileList = files.list();
                String tifPath = null;
                for (String y : fileList) {
                    File file2 = new File(s + "/" + y);
                    if (file2.isFile()) {
                        String[] split = file2.getName().split("\\.");//尾椎集合
                        StringBuffer sb = new StringBuffer();
                        for (int i = 1; i < split.length; i++) {
                            sb.append(split[i] + ".");
                        }
                        String s1 = sb.toString().substring(0, sb.length() - 1);
                        File file01 = new File(root + "/d/" + name + "/" + name + "." + s1);
                        file2.renameTo(file01);
                        if ("tif".equals(s1) || "tiff".equals(s1)) {
                            tifPath = file01.getPath();
                        }
                    }
                }
                if (StringUtils.isBlank(tifPath)) {
                    renderJson(Co.ok("data", Ret.fail("errorMsg", "压缩包没有tif文件")));
                    return;
                }
                renderJson(Co.ok("data", Ret.ok("url", "/d/" + name + "/" + name + ".tif").set("yname", file.getName())));
            }
        }
    }


    /**
     * @Description: 上传压缩文件
     * @Author: yangxueyang
     * @Date: 2019/8/28
     */
    public void uploadZip() {
        UploadFile uf = getFile();
        File file = uf.getFile();
        String rootPath = PathKit.getWebRootPath() + "/upload/zip/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + "zip";

        if (!"zip".equals(fileext)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "图片格式不正确")));
            file.delete();
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Co.fail("errorMsg", "重命名失败")));
            } else {
                renderJson(Co.ok("data", Co.ok("url", "/upload/zip/" + filename).set("yname", file.getName())));
            }
        }

    }

    /**
     * @return void
     * @Author queer
     * @Description //TODO
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @ApiOperation(url = "/upload/updateImg", tag = "/upload", httpMethod = "post", description = "图片上传")
    public void updateImg() {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile("file", "image");
        File file = uf.getFile();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/question/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
        if (!"jpg".equals(fileext) && !"png".equals(fileext) && !"gif".equals(fileext) && !"jpeg".equals(fileext)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "图片格式不正确")));
            file.delete();
            return;
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Co.fail("errorMsg", "重命名失败")));
            } else {
                renderJson(Co.ok("data", Co.ok("url", "/upload/question/" + filename).set("yname", file.getName())));
            }
        }
    }

    /**
     * @return void
     * @Author queer
     * @Description //TODO
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @ApiOperation(url = "/upload/updateHeadPortrait", tag = "/upload", httpMethod = "post", description = "图片上传")
    public void updateHeadPortrait() {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile("file", "image");
        File file = uf.getFile();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
        if (!"jpg".equals(fileext) && !"png".equals(fileext) && !"gif".equals(fileext) && !"jpeg".equals(fileext)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "图片格式不正确")));
            file.delete();
            return;
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Co.fail("errorMsg", "重命名失败")));
            } else {
                renderJson(Co.ok("data", Co.ok("url", "/upload/" + filename).set("yname", file.getName())));
            }
        }
    }

    /**
     * @return void
     * @Author queer
     * @Description //TODO
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @ApiOperation(url = "/upload/updateDK", tag = "/upload", httpMethod = "post", description = "图片上传")
    public void updateDK() {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile("file", "image");
        File file = uf.getFile();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/dk/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
        if (!"jpg".equals(fileext) && !"png".equals(fileext) && !"gif".equals(fileext) && !"jpeg".equals(fileext)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "图片格式不正确")));
            file.delete();
            return;
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "重命名失败")));
            } else {
                renderJson(Co.ok("data", Ret.ok("url", "/upload/dk/" + filename).set("yname", file.getName())));
            }
        }
    }


    /**
     * @return void
     * @Author queer
     * @Description //TODO
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @ApiOperation(url = "/upload/updateOperationRecord", tag = "/upload", httpMethod = "post", description = "图片上传")
    public void updateOperationRecord() {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile("file", "image");
        File file = uf.getFile();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/operation/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
        if (!"jpg".equals(fileext) && !"png".equals(fileext) && !"gif".equals(fileext) && !"jpeg".equals(fileext)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "图片格式不正确")));
            file.delete();
            return;
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "重命名失败")));
            } else {
                renderJson(Co.ok("data", Ret.ok("url", "/upload/operation/" + filename).set("yname", file.getName())));
            }
        }
    }


    /**
     * @return void
     * @Author queer
     * @Description //TODO
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @ApiOperation(url = "/upload/updatePDF", tag = "/upload", httpMethod = "post", description = "图片上传")
    public void updatePDF() {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile("file", "pdf");
        File file = uf.getFile();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/operation/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
        if (!"pdf".equals(fileext) && !"PDF".equals(fileext)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "pdf文件格式不正确")));
            file.delete();
            return;
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "重命名失败")));
            } else {
                renderJson(Co.ok("data", Ret.ok("url", "/upload/operation/" + filename).set("yname", file.getName())));
            }
        }
    }

    /**
     * @return void
     * @Author queer
     * @Description //TODO
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @Clear
    @ApiOperation(url = "/upload/uploadShp", tag = "/upload", httpMethod = "post", description = "上传shp文件")
    public void uploadShp() throws Exception {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile("file", "image");
        File file = uf.getFile();

        String rootPath = PathKit.getWebRootPath().replace("\\", "/") + "/upload/data/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = uuid + "." + fileext;
        if (!"zip".equals(fileext)) {
            file.delete();
            renderJson(Co.ok("data", Ret.fail("errorMsg", "文件格式不正确")));
        }
        File fileZip = new File(rootPath + filename);
        boolean b = file.renameTo(fileZip);
        if (b) {
            ZipUtils.decompress(rootPath + filename, rootPath + uuid);
            File files = new File(rootPath + uuid);
            String shpPath = null;
            String shxPath = null;
            String dbfPath = null;
            String[] fileList = files.list();
            if (fileList == null || fileList.length == 0) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "文件格式不正确,压缩文件必须为一级目录")));
                return;
            }
            if (fileList.length < 3) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "文件格式不正确,文件数量不对,必须文件:.shp、.shx、.dbf")));
                return;
            }
            List<String> list = new ArrayList<>();
            for (String y : fileList) {
                File file1 = new File(rootPath + uuid + "/" + y);
                System.out.println("file1:" + file1.getName());
                if (file1.isFile()) {
//                String s1 = file1.getName().split("\\.")[1];
                    String[] split = file1.getName().split("\\.");
                    StringBuffer sb = new StringBuffer();
                    for (int i = 1; i < split.length; i++) {
                        System.out.println("saaa:" + split[i]);
                        sb.append(split[i]).append(".");
                    }
                    String s1 = sb.toString().substring(0, sb.length() - 1);
                    File file01 = new File(PathKit.getWebRootPath().replace("\\", "/") + "/upload/data/" + uuid + "/" + uuid + "." + s1);
                    System.out.println("file01:" + file01.getName());
                    file1.renameTo(file01);
                    String path = file01.getPath();
                    System.out.println("path" + path);
                    list.add(file01.getPath());
                    if ("shp".equals(s1)) {
                        shpPath = file01.getPath();
                    }
                    if ("shx".equals(s1)) {
                        shxPath = file01.getPath();
                    }
                    if ("dbf".equals(s1)) {
                        dbfPath = file01.getPath();
                    }
                }
            }
            if (shpPath == null) {
                files.delete();
                fileZip.delete();
                this.renderJson(Co.ok("data", Ret.fail("errorMsg", "上传压缩文件没有shp")));
                return;
            }
            if (shxPath == null) {
                files.delete();
                fileZip.delete();
                this.renderJson(Co.ok("data", Ret.fail("errorMsg", "上传压缩文件没有shx")));
                return;
            }
            if (dbfPath == null) {
                files.delete();
                fileZip.delete();
                this.renderJson(Co.ok("data", Ret.fail("errorMsg", "上传压缩文件没有dbf")));
                return;
            }
            //发布shp
            try {
                Kv kv = IssueShpUtils.uploadShp(rootPath + uuid, uuid);
                System.out.println(kv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Kv kv = readShp.readShpXY(rootPath + uuid + "/" + uuid + ".shp");
            kv.put("url", "d:" + uuid);
            renderJson(Co.ok("data", Ret.ok("data", kv)));
        } else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "重命名失败")));
        }
    }

}
