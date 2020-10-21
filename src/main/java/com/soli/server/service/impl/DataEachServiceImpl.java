/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soli.server.service.impl;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

import com.soli.server.service.DataEachService;
import com.soli.server.model.DataEach;
import com.soli.server.utils.ReadTiffUtils;
import com.vividsolutions.jts.io.ParseException;
import org.opengis.referencing.operation.TransformException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @since 1.0
 */
public class DataEachServiceImpl extends LambkitModelServiceImpl<DataEach> implements DataEachService {

    private DataEach DAO = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DataEach dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(DataEach.class);
        }
        return DAO;
    }

    @Override
    public Ret getYears() {
        //获取最近五年年份
        Date date = new Date();
        List<Integer> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Integer year = Integer.valueOf(simpleDateFormat.format(date));
        for (int i = 0; i < 5; i++) {
            list.add(year - i);
        }
        return Ret.ok("list", list);
    }

    @Override
    public Ret searchJwAndJy(Integer id, Integer year, Integer type) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择地块");
        }
        if (year == null) {
            return Ret.fail("errorMsg", "请选择年份");
        }
        String table_name;
        if (type == 1) {
            table_name = "tr_tk_temperature";
        } else if (type == 0) {
            table_name = "tr_tk_humidity";
        } else {
            return Ret.fail("errorMsg", "请选择积温或积雨");
        }
        //获取当前时间往前一年的时间
        List<Date> times = getTimes(year);
        Record record10 = new Record();
        Record record40 = new Record();
        List<Double> values = new ArrayList<>();
        for (Date date : times) {
            Record rec10 = Db.findFirst("SELECT value FROM " + table_name + " where type = 10 and tk_id = ? and time = ?", id, date);
            Record rec40 = Db.findFirst("SELECT value FROM " + table_name + " where type = 40 and tk_id = ? and time = ?", id, date);
            if (rec10 == null) {
                record10.set(sdf.format(date), 0);
            } else {
                record10.set(sdf.format(date), Double.valueOf(rec10.getStr("value")));
                values.add(Double.valueOf(rec10.getStr("value")));
            }
            if (rec40 == null) {
                record40.set(sdf.format(date), 0);
            } else {
                record40.set(sdf.format(date), Double.valueOf(rec40.getStr("value")));
                values.add(Double.valueOf(rec10.getStr("value")));
            }
        }
        Double max = Collections.max(values);
        return Ret.ok("list10", record10).set("list40", record40).set("max", max);
    }

    public List<Date> getTimes(int year) {
        int m = 1;//月份计数
        List<Date> lDate = new ArrayList();
        Date date = new Date();
        while (m < 13) {
            int month = m;
            Calendar cal = Calendar.getInstance();//获得当前日期对象
            cal.clear();//清除信息
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);//1月从0开始
            cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天

            lDate.add(cal.getTime());
            int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int j = 0; j <= (count - 2); j++) {
                cal.add(Calendar.DAY_OF_MONTH, +1);
                if (cal.getTime().after(date)) {
                    return lDate;
                }
                lDate.add(cal.getTime());
            }
            m++;
        }
        return lDate;
    }

    @Override
    public Ret updateTkTiff(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择地块");
        }
        //1、获取地块wkt
        Record wktRec = Db.findFirst("select st_astext(geom) as wkt from tr_tiankuai where id = ?", id);
        String wkt = wktRec.getStr("wkt");
        //2、分获取积温、积雨日期列表，遍历读取像素值保存
        String root = PathKit.getWebRootPath().replace("\\", "/");
        //10cm土壤温度48
        //10cm土壤湿度47
        //40cm土壤温度92
        //10-40cm土壤湿度94
        //积温98
        //积雨95
        List<Record> records = Db.find("select * from tr_data_each where data_id in (48,47,92,94,95,98)");
        for (Record record : records) {
            String url = record.getStr("url").split(":")[1];
            readQwAndSd(wkt, root + "/d/" + url + "/" + url + ".tif", record.getInt("id"), record.getDate("data_time"));
        }
        return Ret.ok();
    }

    //更新所有地块的温度或湿度数据
    public static Kv readQwAndSd(String wkt, String path, Integer id, Date time) {
        String tableName = null;
        Integer height = null;
        if (id == 48) {
            tableName = "tr_tk_temperature";
            height = 10;
        } else if (id == 47) {
            tableName = "tr_tk_humidity";
            height = 10;
        } else if (id == 92) {
            tableName = "tr_tk_temperature";
            height = 40;
        } else if (id == 94) {
            tableName = "tr_tk_humidity";
            height = 40;
        } else if (id == 98) {
            tableName = "tr_tk_accumulated";
            height = 0;
        } else if (id == 95) {
            tableName = "tr_tk_eroded";
            height = 0;
        }
        Kv kv = Kv.create();
        //2、分获取积温、积雨日期列表，遍历读取像素值保存
        //读取当前日期文件两个数据像素值
        try {
            double v10 = ReadTiffUtils.getAltitudeByWkt(wkt, path);
            Record first = Db.findFirst("select * from " + tableName + " where tk_id = '" + id + "' and time = '" + time + "' and type = '" + height + "' ");
            if (first == null) {
                Db.update("insert into " + tableName + "(tk_id,value,time,type) values('" + id + "','" + v10 + "','" + time + "','" + height + "')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kv;
    }


    public static Kv updateTkTif(File file, String wkt, String tableName, Integer id) {
        Kv kv = new Kv();
        File[] files = file.listFiles();
        //3、遍历时间文件夹
        for (File timeFile : files) {
            //读取10和40的数据
            String path10 = null;
            String path40 = null;
            File[] fileList = timeFile.listFiles();
            for (File file1 : fileList) {
                System.out.println("file1:" + file1.getName());
                if (file1.isFile()) {
                    String[] split = file1.getName().split("\\.");
                    StringBuffer sb = new StringBuffer();
                    for (int i = 1; i < split.length; i++) {
                        sb.append(split[i]).append(".");
                    }
                    String s1 = sb.toString().substring(0, sb.length() - 1);
                    if ("tif".equals(s1)) {
                        String name = file1.getName();
                        if (name.contains("10")) {
                            path10 = file1.getAbsolutePath();
                        } else if (name.contains("40")) {
                            path40 = file1.getAbsolutePath();
                        }
                    }
                }
            }
            //读取当前日期文件两个数据像素值
            try {
                if (path10 != null) {
                    double v10 = ReadTiffUtils.getAltitudeByWkt(wkt, path10);
                    Record first = Db.findFirst("select * from " + tableName + " where tk_id = '" + id + "' and time = '" + timeFile.getName() + "' and type = 10");
                    if (first == null) {
                        Db.update("insert into " + tableName + "(tk_id,value,time,type) values('" + id + "','" + v10 + "','" + timeFile.getName() + "',10)");
                        kv.set(timeFile.getName() + ",10", v10);
                    } else {
                        kv.set(timeFile.getName() + ",10", "数据已存在");
                    }
                }
                if (path40 != null) {
                    double v40 = ReadTiffUtils.getAltitudeByWkt(wkt, path40);
                    Record first = Db.findFirst("select * from " + tableName + " where tk_id = '" + id + "' and time = '" + timeFile.getName() + "' and type = 40");
                    if (first == null) {
                        Db.update("insert into " + tableName + "(tk_id,value,time,type) values('" + id + "','" + v40 + "','" + timeFile.getName() + "',40)");
                        kv.set(timeFile.getName() + ",40", v40);
                    } else {
                        kv.set(timeFile.getName() + ",40", "数据已存在");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                kv.set(timeFile.getName(), e.getMessage());
            }
        }
        return kv;
    }
}
