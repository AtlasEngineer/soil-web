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

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.module.upms.rpc.model.UpmsUserRole;
import com.lambkit.plugin.jwt.impl.JwtUser;
import com.lambkit.web.RequestManager;
import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.soli.server.service.DataService;
import com.soli.server.model.Data;
import com.soli.server.utils.readShp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Ret edit(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择要编辑的矢量数据");
        }
        Data data = Data.service().dao().findById(id);
        if (data == null || data.getType() != 0) {
			return Ret.fail("errorMsg", "数据不存在或数据类型错误");
        }
        //获取该数据dbf
		String url = data.getUrl();
		String[] split = url.split(":");
		String dbfPath = PathKit.getWebRootPath()+"/d/"+split[1]+"/"+split[1]+".dbf";

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
				record.set(key,type);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return Ret.ok("data",record);
    }

    @Override
    public Ret search(String name, String type, String[] times, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        StringBuffer sql = new StringBuffer("from tr_data where 1 = 1 ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and name like %" + name + "% ");
        }
        if (StringUtils.isNotBlank(type)) {
            sql.append(" and type = '" + type + "' ");
        }
        if (times.length > 1) {
            sql.append(" and timme between '" + times[0] + "' and '" + times[1] + "' ");
        }
        sql.append(" order by time desc");
        Page<Data> paginate = Data.service().dao().paginate(pageNum, pageSize, "select * ", sql.toString());
        return Ret.ok("data", paginate);
    }

    @Override
    public Ret add(String name, String url, Integer type, Integer directoryid) {
        JwtUser me = (JwtUser) RequestManager.me().getRequest().getAttribute("me");
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(me.getUserName()).example());

        Data data = new Data();
        data.setName(name);
        data.setType(type);
        data.setDel(0);
        data.setDirectoryid(directoryid);
        data.setUrl(url);
        data.setTime(new Date());
        data.setUserid(upmsUser.getUserId().intValue());
        data.save();
        return Ret.ok("msg", "添加成功");
    }

    @Override
    public Ret del(Integer[] ids) {
        if (ids == null) {
            return Ret.fail("errorMsg", "请选择要删除的数据");
        }
        for (int i = 0; i < ids.length; i++) {
            Data data = Data.service().dao().findById(ids[i]);
            if (data != null) {
                data.delete();
            }
        }
        return Ret.ok("msg", "删除成功");
    }
}
