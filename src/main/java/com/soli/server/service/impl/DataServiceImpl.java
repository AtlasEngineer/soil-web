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

import com.jfinal.kit.Ret;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.module.upms.rpc.model.UpmsUserRole;
import com.lambkit.plugin.jwt.impl.JwtUser;
import com.lambkit.web.RequestManager;
import com.soli.server.service.DataService;
import com.soli.server.model.Data;

import java.util.Date;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-11
 * @version 1.0
 * @since 1.0
 */
public class DataServiceImpl extends LambkitModelServiceImpl<Data> implements DataService {
	
	private Data DAO = null;
	
	public Data dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Data.class);
		}
		return DAO;
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
		return Ret.ok("msg","添加成功");
	}
}
