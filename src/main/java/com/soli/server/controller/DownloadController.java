package com.soli.server.controller;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;
import com.soli.server.model.Data;
import com.soli.server.model.DataEach;
import com.soli.server.utils.Co;
import com.soli.server.utils.ExcelExportUtil;
import com.soli.server.utils.ZipCompressor;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Clear(JwtTokenInterceptor.class)
public class DownloadController extends LambkitController {

    /**
     * excel数据上传模板
     */
    public void getTemplate() {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "id不能为空")));
            return;
        }
        Data data = Data.service().dao().findById(id);
        Record first = Db.findFirst("select * from tr_data_templates where dataid = ?", data.getId());
        if (first == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "该数据没有模板")));
            return;
        }
        String root = PathKit.getWebRootPath().replace("\\", "/");
        File file = new File(root + first.get("url"));
        renderFile(file);
    }

    /**
     * excel列表中数据提取
     */
    public void DataExcelDownload() {
        Integer id = getParaToInt("id");
        String ids = getPara("ids");
        if (id == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请选择要提取的数据类型")));
            return;
        }
        if (ids == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请选择要提取的数据")));
            return;
        }
        Data data = Data.service().dao().findById(id);
        if (data == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "该数据不存在")));
            return;
        }
        //表格
        String tableName = data.getUrl();
        String substring = ids.substring(1, ids.length() - 1);
        if (tableName.contains("hnw_")) {
            StringBuffer sql = new StringBuffer("select product,place,price,status,up_time from " + tableName + " where id in (" + substring + ") ");
            if ("hnw_jgpz".equals(tableName)) {
                sql.append(" and category = '" + data.getName() + "' ");
            }
            List<Record> records = Db.find(sql.toString());
            if (records.size() == 0) {
                renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "没有当前当前时间的数据")));
                return;
            }
            Map<String, String> titleData = new LinkedHashMap<>();//标题，后面用到
            titleData.put("product", "产品/品种");
            titleData.put("place", "所在产地");
            titleData.put("price", "价格");
            titleData.put("status", "升/降");
            titleData.put("up_time", "时间");
            File file = new File(ExcelExportUtil.getTitle());
            file = ExcelExportUtil.saveFile(titleData, records, file);
            renderFile(file);
        } else {
            //动态表格
            Record table = Db.findFirst("select * from sys_tableconfig where tbname = ? ", tableName);
            List<Record> fields = Db.find("select fldname,fldcnn from sys_fieldconfig where fldtbid = ? ", table.getInt("tbid"));
            StringBuffer sql = new StringBuffer("select ");
            for (int i = 0; i < fields.size(); i++) {
                Record field = fields.get(i);
                if ("id".equals(field.get("fldname"))) {
                    continue;
                }
                sql.append(field.getStr("fldname") + ",");
            }
            String s = sql.toString();
            String sqlStr = s.substring(0, s.length() - 1);
            List<Record> records = Db.find(sqlStr + " from " + tableName + " where id in (" + substring + ") ");

            Map<String, String> titleData = new LinkedHashMap<>();//标题，后面用到
            for (Record field : fields) {
                if ("id".equals(field.getStr("fldname"))) {
                    continue;
                }
                titleData.put(field.getStr("fldname"), field.getStr("fldcnn"));
            }
            File file = new File(ExcelExportUtil.getTitle());
            file = ExcelExportUtil.saveFile(titleData, records, file);
            renderFile(file);
        }
    }

    /**
     * 数据提取
     */
    public void DataEachDownload() {
        Integer id = getParaToInt("id");
        Date time = getParaToDate("time");
        if (id == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请选择要提取的数据")));
            return;
        }
        if (time == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请选择要提取的数据时间")));
            return;
        }
        Data data = Data.service().dao().findById(id);
        if (data == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "该数据不存在")));
            return;
        }
        if (data.getType() == 2) {
            //表格
            String tableName = data.getUrl();
            if (tableName.contains("hnw_")) {
                StringBuffer sql = new StringBuffer("select product,place,price,status,up_time from " + tableName + " where up_time = '" + time + "' ");
                if ("hnw_jgpz".equals(tableName)) {
                    sql.append(" and category = '" + data.getName() + "' ");
                }
                List<Record> records = Db.find(sql.toString());
                if (records.size() == 0) {
                    renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "没有当前当前时间的数据")));
                    return;
                }
                Map<String, String> titleData = new LinkedHashMap<>();//标题，后面用到
                titleData.put("product", "产品/品种");
                titleData.put("place", "所在产地");
                titleData.put("price", "价格");
                titleData.put("status", "升/降");
                titleData.put("up_time", "时间");
                File file = new File(ExcelExportUtil.getTitle());
                file = ExcelExportUtil.saveFile(titleData, records, file);
                renderFile(file);
                return;
            } else {
                //动态表格
                Record table = Db.findFirst("select * from sys_tableconfig where tbname = ? ", tableName);
                List<Record> fields = Db.find("select fldname,fldcnn from sys_fieldconfig where fldtbid = ? ", table.getInt("tbid"));
                StringBuffer sql = new StringBuffer("select ");
                for (int i = 0; i < fields.size(); i++) {
                    Record field = fields.get(i);
                    if ("id".equals(field.get("fldname"))) {
                        continue;
                    }
                    sql.append(field.getStr("fldname") + ",");
                }
                String s = sql.toString();
                String sqlStr = s.substring(0, s.length() - 1);
                List<Record> records = Db.find(sqlStr + " from " + tableName);


                Map<String, String> titleData = new LinkedHashMap<>();//标题，后面用到
                for (Record field : fields) {
                    if ("id".equals(field.getStr("fldname"))) {
                        continue;
                    }
                    titleData.put(field.getStr("fldname"), field.getStr("fldcnn"));
                }
                File file = new File(ExcelExportUtil.getTitle());
                file = ExcelExportUtil.saveFile(titleData, records, file);
                renderFile(file);
                return;
            }
        } else {
            //空间数据
            List<DataEach> dataEaches = DataEach.service().dao().find(DataEach.sql().andTimeEqualTo(time).andDataIdEqualTo(id).example());
            if (dataEaches.size() == 0) {
                renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "没有当前当前时间的数据")));
                return;
            }
            //打包压缩
            String webRootPath = PathKit.getWebRootPath();
            List<String> sourceFilePaths = new ArrayList<String>();
            for (DataEach dataEach : dataEaches) {
                sourceFilePaths.add(webRootPath + "/d/" + dataEach.getUrl().split(":")[1]);
            }
            //指定打包到哪个zip（绝对路径）如果压缩包创建出来了，但是里面没有文件，是因为数据库有数据，但是没有该数据的文件file
            String zipTempFilePath = webRootPath + "/d/数据提取记录/" + System.currentTimeMillis() + ".zip";
            //调用压缩
            ZipCompressor zc = new ZipCompressor(zipTempFilePath);
            zc.compress(sourceFilePaths);
            renderFile(new File(zipTempFilePath));
            return;
        }
    }

    /**
     * @param filePaths        需要压缩的文件地址列表（绝对路径）
     * @param zipFilePath      需要压缩到哪个zip文件（无需创建这样一个zip，只需要指定一个全路径）
     * @param keepDirStructure 压缩后目录是否保持原目录结构
     * @return int   压缩成功的文件个数
     * @throws IOException
     * @Title: compress
     * @Description: TODO
     */
    public static int compress(List<String> filePaths, String zipFilePath, Boolean keepDirStructure) throws IOException {
        byte[] buf = new byte[1024];
        File zipFile = new File(zipFilePath);
        //zip文件不存在，则创建文件，用于压缩
        if (!zipFile.exists())
            zipFile.createNewFile();
        int fileCount = 0;//记录压缩了几个文件？
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < filePaths.size(); i++) {
                String relativePath = filePaths.get(i);
                if (StringUtils.isEmpty(relativePath)) {
                    continue;
                }
                File sourceFile = new File(relativePath);//绝对路径找到file
                if (sourceFile == null || !sourceFile.exists()) {
                    continue;
                }

                FileInputStream fis = new FileInputStream(sourceFile);
                if (keepDirStructure != null && keepDirStructure) {
                    //保持目录结构
                    zos.putNextEntry(new ZipEntry(relativePath));
                } else {
                    //直接放到压缩包的根目录
                    zos.putNextEntry(new ZipEntry(sourceFile.getName()));
                }
                //System.out.println("压缩当前文件："+sourceFile.getName());
                int len;
                while ((len = fis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                fis.close();
                fileCount++;
            }
            zos.close();
            //System.out.println("压缩完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileCount;
    }
}
