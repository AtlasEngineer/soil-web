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
package com.atlas.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.atlas.server.model.InsectPests;
import com.atlas.server.model.sql.CatalogueKeepCriteria;
import com.atlas.server.utils.Co;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.CatalogueKeepService;
import com.atlas.server.model.CatalogueKeep;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @since 1.0
 */
public class CatalogueKeepServiceImpl extends LambkitModelServiceImpl<CatalogueKeep> implements CatalogueKeepService {

    private CatalogueKeep DAO = null;

    public CatalogueKeep dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(CatalogueKeep.class);
        }
        return DAO;
    }

    @Override
    public Co all(String token, String type) throws ParseException {

        String time[] = type.split(",");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		Date end=new Date();
        if (time.length > 1) {
            if (StringUtils.isNotBlank(time[1]) && StringUtils.isNotBlank(time[0])) {
                Date start = df.parse(time[0]);
                Date endD = df.parse(time[1]);
                Calendar calendar=new GregorianCalendar();
				calendar.setTime(endD);
				calendar.add(calendar.DATE,1);
				 end=calendar.getTime();
                if (end.before(start)) {
                    return Co.fail("msg", "开始时间不能在结束时间之前");
                }
                int num = getMonth(start, end);
                if (num > 6) {
                    return Co.fail("msg", "时间相隔不能高于半年");
                }
            }
        }

        JwtConfig config = Lambkit.config(JwtConfig.class);
        String tokenPrefix = config.getTokenPrefix();
        String authToken = token.substring(tokenPrefix.length());
        String username = JwtKit.getJwtUser(authToken);
        if (username == null) {
            return null;
        }
        System.out.println("username : " + username);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
        if (upmsUser == null) {
            return null;
        }
        System.out.println("roleName:" + upmsUser.getRealname());


        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * from catalogue_keep k where del=0 and user_id="+upmsUser.getUserId().intValue()+" ");

        if (StringUtils.isNotBlank(type)) {
            if ("1".equals(type)) {
                stringBuffer.append(" and date_format(k.time,'%Y-%m') = date_format(now(),'%Y-%m')");
            }
            if ("3".equals(type)) {
                stringBuffer.append(" and datediff(curdate(),k.time)<90");
            }
        }
        if (time.length > 1) {
            if (StringUtils.isNotBlank(time[0]) && StringUtils.isNotBlank(df.format(end)) && !"null".equals(time[0]) && !"null".equals(df.format(end))) {
                if (time[0].equals(df.format(end))) {
                    stringBuffer.append(" and date_format(k.time'%Y-%m')=date_format('" + time[0] + "','%Y-%m')");
                } else {
                    stringBuffer.append(" and k.time between '" + time[0] + "' and '" + df.format(end) + "'");
                }
            } else if (StringUtils.isNotBlank(time[0]) && !"null".equals(time[0])) {
                stringBuffer.append(" and date_format(k.time,'%Y-%m')=date_format('" + time[0] + "','%Y-%m')");
            } else if (StringUtils.isNotBlank(df.format(end)) && !"null".equals(df.format(end))) {
                stringBuffer.append(" and date_format(k.time,'%Y-%m')=date_format('" + df.format(end) + "','%Y-%m')");
            }
        }

        stringBuffer.append(" ORDER by k.time desc ");
        JSONArray objects = new JSONArray();
        List<CatalogueKeep> catalogueKeeps = CatalogueKeep.service().dao().find(stringBuffer.toString());
        for (CatalogueKeep catalogueKeep : catalogueKeeps) {
            CatalogueKeep keep = CatalogueKeep.service().dao().findById(catalogueKeep.getId());
			com.alibaba.fastjson.JSONObject jb = new com.alibaba.fastjson.JSONObject();
            jb.fluentPut("time", df.format(keep.getTime()));
            List<Record> records = Db.find("select * from catalogue_keep k where del=0 and date_format(k.time,'%Y-%m-%d')=date_format('" + df.format(keep.getTime()) + "','%Y-%m-%d') ");
            jb.fluentPut("records", records);
			if(objects.contains(jb)){
              continue;
			}else {
				objects.add(jb);
			}
        }
        return Co.ok("data", objects);
    }

    @Override
    public Co delete(Integer id[]) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            CatalogueKeep catalogueKeep = CatalogueKeep.service().dao().findById(id[i]);
            catalogueKeep.setDel(1);
            boolean result = catalogueKeep.update();
            if (result) {
                list.add(id[i]);
            }
        }
        if (list.size() > 0) {
            return Co.ok();
        } else {
            return Co.fail();
        }
    }


    public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }
}
