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

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.soli.lambkit.start.GeoServerConfig;
import com.soli.server.service.DataService;
import com.soli.server.model.Data;
import com.soli.server.utils.*;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.WKTReader;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.soli.server.utils.CodePageUtils.getUserEntity;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-11
 * @since 1.0
 */
public class DataServiceImpl extends LambkitModelServiceImpl<Data> implements DataService {

    private Data DAO = null;

    public Data dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(Data.class);
        }
        return DAO;
    }

    @Override
    public Ret addFeatureForShp(Integer id, JSONObject json, String latlons) {
        UpmsUser user = getUserEntity();
        if (user == null) {
            return Ret.fail("errorMsg", "当前登录用户异常");
        }
        if (id == null) {
            return Ret.fail("errorMsg", "请选择要编辑的矢量数据");
        }
        if (json == null) {
            return Ret.fail("errorMsg", "json不能为空");
        }

        Data data = Data.service().dao().findById(id);
        if (data.getType() != 0) {
            return Ret.fail("errorMsg", "当前数据不可编辑");
        }
        String name = data.getUrl().split(":")[1];
        String webRootPath = PathKit.getWebRootPath();
        try {
            String path = webRootPath + "/d/" + name + "/" + name + ".shp";
            String newName = UUID.randomUUID().toString();
            String newShp = webRootPath + "/d/" + name + "/" + newName + ".shp";
            File file1 = new File(webRootPath + "/d/" + name);
            if (!file1.exists()) {
                file1.mkdir();
            }
            readShp.exportShp(path, newShp, json, latlons);
            File file = new File(webRootPath + "/d/" + name);
            file.delete();
            //重新发布并更新url
            Kv kv = IssueShpUtils.uploadShp(webRootPath + "/d/" + name, name);
            data.setUrl("d:" + name);
            data.setTime(new Date());
            data.setUserid(user.getUserId().intValue());
            data.update();
            return Ret.ok("name", name);
        } catch (Exception e) {
            e.printStackTrace();
            return Ret.fail("errorMsg", e.getMessage());
        }
    }

    @Override
    public Ret searchCalendar(Integer month) {
        if (month == null) {
            return Ret.fail("errorMsg", "请选择月份");
        }
//        int status;
//        if (day < 11 && day > 0) {
//            status = 0;
//        }else if (day < 21 && day > 10) {
//            status = 1;
//        }else {
//            status = 2;
//        }
        List<Record> st0 = Db.find("select * from whxx_shengzhang where month = '" + month + "' and status = '0' ");
        List<Record> st1 = Db.find("select * from whxx_shengzhang where month = '" + month + "' and status = '1' ");
        List<Record> st2 = Db.find("select * from whxx_shengzhang where month = '" + month + "' and status = '2' ");
        return Ret.ok("st0", st0).set("st1", st1).set("st2", st2);
    }

    @Override
    public Ret edit(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择要编辑的矢量数据");
        }
        Data data = Data.service().dao().findById(id);
        if (data == null || data.getInt("isedit") != 1) {
            return Ret.fail("errorMsg", "数据不存在或数据不可编辑");
        }
        //获取该数据dbf
        String url = data.getUrl();
        String[] split = url.split(":");
        String dbfPath = PathKit.getWebRootPath() + "/d/" + split[1] + "/" + split[1] + ".dbf";

        Record record = new Record();
        InputStream fis = null;
        try {
            fis = new FileInputStream(dbfPath);
            DBFReader reader = new DBFReader(fis);
            int fieldsCount = reader.getFieldCount();
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                String key = new String(field.getName().getBytes(reader.getCharset()));
                DBFDataType type = field.getType();
                record.set(key, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        record.set("type", "Polygon");
        return Ret.ok("data", record);
    }

    @Override
    public Ret search(String name, String type, String[] times, String directoryid, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        StringBuffer sql = new StringBuffer("from tr_data where 1 = 1 ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and name like '%" + name + "%' ");
        }
        if (StringUtils.isNotBlank(type)) {
            sql.append(" and type = '" + type + "' ");
        }
        if (times != null && times.length > 1) {
            sql.append(" and time between '" + times[0] + "' and '" + times[1] + "' ");
        }
        if (StringUtils.isNotBlank(directoryid)) {
            sql.append(" and directoryid = '" + directoryid + "' ");
        }
        sql.append(" order by time desc");
        Page<Data> paginate = Data.service().dao().paginate(pageNum, pageSize, "select * ", sql.toString());
        List<Data> list = paginate.getList();
        String webRootPath = PathKit.getWebRootPath();
        for (int i = 0; i < list.size(); i++) {
            Data data = list.get(i);
            Integer type1 = data.getType();
            Kv kv = null;
            if (type1 == 0) {
                kv = readShp.readShpXY(webRootPath + "/d/" + data.getUrl().split(":")[1] + "/" + data.getUrl().split(":")[1] + ".shp");
            } else if (type1 == 1) {
                kv = ReadTiffUtils.getTiffXY(webRootPath + "/d/" + data.getUrl().split(":")[1] + "/" + data.getUrl().split(":")[1] + ".tif");
            }
            if (kv != null) {
                data.put(kv);
            }
        }
        return Ret.ok("page", paginate);
    }

    @Override
    public Ret add(String name, String url, Integer type, Integer directoryid) {
//        JwtUser me = (JwtUser) RequestManager.me().getRequest().getAttribute("me");0
//        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(me.getUserName()).example());
//
//        Data data = new Data();
//        data.setName(name);
//        data.setType(type);
//        data.setDel(0);
//        data.setDirectoryid(directoryid);
//        data.setUrl(url);
//        data.setTime(new Date());
//        data.setUserid(upmsUser.getUserId().intValue());
//        data.save();
        return Ret.ok("msg", "添加成功");
    }

    @Override
    public Ret del(Integer[] ids) {
        if (ids == null) {
            return Ret.fail("errorMsg", "请选择要删除的数据");
        }
        //删除geoserver服务
        GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
        String RESTURL = config.getGeourl();
        String RESTUSER = config.getGeouser();
        String RESTPW = config.getGeopsw();
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        for (int i = 0; i < ids.length; i++) {
            Data data = Data.service().dao().findById(ids[i]);
            if (data != null) {
                data.delete();
                //删除文件
                String webRootPath = PathKit.getWebRootPath();
                File file;
                if (data.getType() == 2) {
                    file = new File(webRootPath + data.getUrl());
                } else {
                    publisher.removeLayer("d", data.getUrl().split(":")[1]);
                    file = new File(webRootPath + "/d/" + data.getUrl().split(":")[1]);
                }
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        return Ret.ok("msg", "删除成功");
    }
}