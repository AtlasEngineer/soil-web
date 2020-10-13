package com.soli.server.utils;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: xuzhonghao
 * @create: 2019-08-05 15:30
 */
public class ReadDbfUtils {


    public static void main(String[] args) {
        Integer num=readDBF("C:\\Users\\Administrator\\Desktop\\7034329f-f5f4-4c5a-a148-0722c6529442\\7034329f-f5f4-4c5a-a148-0722c6529442.dbf");
        System.out.println(num);
        /*Text01.writeDBF("D:/test.dbf");*/
    }

    /**
     * 读取dbf
     *
     * @param path
     */
    public static Integer readDBF(String path) {
        InputStream fis = null;
        Integer num=0;
        try {
            // 读取文件的输入流
            fis = new FileInputStream(path);
            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);
            // 解决乱码
            reader.setCharactersetName("GBK");
            // 调用DBFReader对实例方法得到path文件中字段的个数
            int fieldsCount = reader.getFieldCount();
            System.out.println(reader.getRecordCount());
            num=reader.getRecordCount()-1;
            // 取出字段信息
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                System.out.println(field.getName());
            }
            Object[] rowValues;
            // 一条条取出path文件中记录
            while ((rowValues = reader.nextRecord()) != null) {
                for (int i = 0; i < rowValues.length; i++) {
                    DBFField field = reader.getField(i);
                    if(field.getName().equalsIgnoreCase("value")){

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return num;
    }

    /**
     * 写dbf
     * @param path
     */
    public static void writeDBF(String path) {
        OutputStream fos = null;
        try {
            // 定义DBF文件字段
            DBFField[] fields = new DBFField[3];
            // 分别定义各个字段信息，setFieldName和setName作用相同，
            // 只是setFieldName已经不建议使用
            fields[0] = new DBFField();
            // fields[0].setFieldName("emp_code");
            fields[0].setName("semp_Test1");
            fields[0].setDataType(DBFField.FIELD_TYPE_C);
            fields[0].setFieldLength(10);
            fields[0].setDecimalCount(0);
            fields[1] = new DBFField();
            // fields[1].setFieldName("emp_name");
            fields[1].setName("emp_Test2");
            fields[1].setDataType(DBFField.FIELD_TYPE_C);
            fields[1].setFieldLength(20);
            fields[1].setDecimalCount(0);
            fields[2] = new DBFField();
            // fields[2].setFieldName("salary");
            fields[2].setName("emp_Test3");
            fields[2].setDataType(DBFField.FIELD_TYPE_N);
            fields[2].setFieldLength(12);
            fields[2].setDecimalCount(2);
            // DBFWriter writer = new DBFWriter(new File(path));
            // 定义DBFWriter实例用来写DBF文件
            DBFWriter writer = new DBFWriter();
            // 把字段信息写入DBFWriter实例，即定义表结构
            writer.setFields(fields);
            // 一条条的写入记录
            Object[] rowData = new Object[3];
            rowData[0] = "1000";
            rowData[1] = "John";
            rowData[2] = new Double(5000.00);
            writer.addRecord(rowData);
            rowData = new Object[3];
            rowData[0] = "1001";
            rowData[1] = "Lalit";
            rowData[2] = new Double(3400.00);
            writer.addRecord(rowData);
            rowData = new Object[3];
            rowData[0] = "1002";
            rowData[1] = "Rohit";
            rowData[2] = new Double(7350.00);
            writer.addRecord(rowData);
            // 定义输出流，并关联的一个文件
            fos = new FileOutputStream(path);
            // 写入数据
            writer.write(fos);
            // writer.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }



}