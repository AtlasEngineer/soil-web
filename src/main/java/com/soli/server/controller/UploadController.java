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
import com.soli.server.utils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.soli.server.utils.CodePageUtils.getUserEntity;

@Clear(JwtTokenInterceptor.class)
public class UploadController extends LambkitController {

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
            } else if (type == 1) {
                filename = "tif-" + filename;
            } else if (type == 3) {
                filename = "GF-" + UUID.randomUUID().toString() + ".tar.gz";
            } else if (type == 4) {
                filename = "SB-" + filename;
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
            if (type == 1 || type == 0) {
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
                            type = 1;
                            tifPath = file01.getPath();
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
                if (type == 1) {
                    if (tifPath == null) {
                        renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "上传压缩文件没有tif")));
                        return;
                    }
                    //发布tiff
                    try {
                        kv = IssueTiffUtils.uploadTiff(tifPath, name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (kv.getInt("code") != 200) {
                    renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", kv.get("msg"))));
                    return;
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
            } else if (type == 4) {
                //哨兵数据

            }
            DataEach dataEach = new DataEach();
            if (dataName == null) {
                dataEach.setName(yname);
            } else {
                dataEach.setName(dataName);
            }
            dataEach.setType(type);
            dataEach.setDataId(id);
            dataEach.setDataTime(data_time);
            dataEach.setTime(new Date());
            dataEach.setUserid(user.getUserId().intValue());
            if (type == 0 || type == 1) {
                dataEach.setUrl("d:" + name);
            } else if (type == 2) {
                dataEach.setUrl("/upload/datafile/" + filename);
            } else if (type == 3) {
                dataEach.setUrl("/d/" + name + "/" + name + ".jpg");
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
                    }else if (type1 == 3){
                        kv1= ReadTiffUtils.getXmlLatlons(root + "/d/" + name + "/" + name + ".xml");
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
            String prjPath = null;
            String[] fileList = files.list();
            if(fileList==null||fileList.length==0){
                renderJson(Co.ok("data", Ret.fail("errorMsg", "文件格式不正确,压缩包必须为一级目录")));
                return;
            }
            if(fileList.length>7){
                renderJson(Co.ok("data", Ret.fail("errorMsg", "文件格式不正确,文件数量不对")));
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
                    if ("prj".equals(s1)) {
                        prjPath = file01.getPath();
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
            if (prjPath == null) {
                files.delete();
                fileZip.delete();
                this.renderJson(Co.ok("data", Ret.fail("errorMsg", "上传压缩文件没有prj")));
                return;
            }
            //发布shp
            try {
              Kv  kv = IssueShpUtils.uploadShp(rootPath + uuid, uuid);
              System.out.println(kv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Kv kv = readShp.readShpXY(rootPath + uuid + "/"+uuid + ".shp");
            kv.put("url","d:"+uuid);
            renderJson(Co.ok("data", Ret.ok("data",kv)));
        } else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "重命名失败")));

        }
    }

}
