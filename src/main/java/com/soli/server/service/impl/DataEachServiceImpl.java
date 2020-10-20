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
import java.util.Date;
import java.util.List;

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

    public DataEach dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(DataEach.class);
        }
        return DAO;
    }

    @Override
    public Ret searchJwAndJy(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择地块");
        }
        //获取当前时间往前一年的时间
        Date date = new Date();


        Record record = Db.findFirst("SELECT * FROM tr_tk_humidity where tk_id = ? ", id);
        return Ret.ok("data", record);
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
        //温度
        File file1 = new File(root + "/python/土壤温度/");
        Kv tr_tk_temperature = updateTkTif(file1, wkt, "tr_tk_temperature", id);
        //湿度
        File file2 = new File(root + "/python/土壤湿度/");
        Kv tr_tk_humidity = updateTkTif(file2, wkt, "tr_tk_humidity", id);
        return Ret.ok("温度", tr_tk_temperature).set("湿度", tr_tk_humidity);
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
