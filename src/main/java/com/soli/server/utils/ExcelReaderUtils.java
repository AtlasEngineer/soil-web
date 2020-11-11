package com.soli.server.utils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelReaderUtils {

    public static String way(String path, List<Record> records, String table_name) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(path));
        //获取一张表
        Sheet sheet = workbook.getSheetAt(0);
        Row row1 = sheet.getRow(0);
        int lastCellNum1 = row1.getLastCellNum();

        StringBuffer sql_ex = new StringBuffer();
        sql_ex.append(" (");
        // (列1, 列2,...) VALUES (值1, 值2,....)
        for (int j = 0; j < lastCellNum1; j++) {
            Cell cell = row1.getCell(j);//取得第j列数据
            if (cell == null) {
                continue;
            }
            cell.setCellType(CellType.STRING);
            String key = cell.getStringCellValue();
            sql_ex.append(key + ",");
        }
        String substring = sql_ex.substring(0, sql_ex.length() - 1);
        String s1 = substring + ") VALUES (";
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {//跳过第一行，取得其他行数据
            StringBuffer sql = new StringBuffer();
            sql.append(" insert into " + table_name + s1);
            Row row = sheet.getRow(i);//取得第i行数据
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);//取得第j列数据
                if (cell == null) {
                    continue;
                }
                if (cell.getCellTypeEnum() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    Date value = cell.getDateCellValue();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                    sql.append("'" + ft.format(value) + "',");
                } else {
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    sql.append("'" + value + "',");
                }
            }
            String substring1 = sql.substring(0, sql.length() - 1);
            int update = Db.update(substring1 + ")");
        }
        return sql_ex.toString();
    }

    public static int productUpload(File file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        //获取一张表
        Sheet sheet = workbook.getSheetAt(0);
        StringBuffer sql_ex = new StringBuffer();
        sql_ex.append(" ( product,phenology,create_time,del)  VALUES ( ");
        int suceessCount = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {//跳过第一行，取得其他行数据
            StringBuffer sql = new StringBuffer();
            sql.append(" INSERT INTO tr_product" + sql_ex.toString());
            Row row = sheet.getRow(i);//取得第i行数据
            if (StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                continue;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);//取得第j列数据
                if (cell == null) {
                    continue;
                }
                String value = cell.getStringCellValue();
                sql.append("'" + value + "',");
            }
            sql.append("now(),");
            sql.append("0");
            int update = Db.update(sql.toString() + ")");
            suceessCount+=update;
        }
        return suceessCount;
    }
}