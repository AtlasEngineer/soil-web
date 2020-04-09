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

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.BotanyTypeService;
import com.atlas.server.model.BotanyType;
import com.lambkit.core.cache.impl.RedisCacheImpl;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.web.RequestManager;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public class BotanyTypeServiceImpl extends LambkitModelServiceImpl<BotanyType> implements BotanyTypeService {
	
	private BotanyType DAO = null;
	
	public BotanyType dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(BotanyType.class);
		}
		return DAO;
	}

	@Override
	public List<Record> all() {
		List<Record> list= Db.find("select * from news where del=0");
		return list;
	}

	@Override
	public Record searchNewsById(Integer id,String ip) {

		String token = RequestManager.me().getRequest().getHeader("Authorization");

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
		RedisCacheImpl redis = new RedisCacheImpl();
		Integer t_id=redis.get("news",upmsUser.getUserId());
		Record record= Db.findFirst("select * from news where del=0 and id="+id+"");
		if(t_id!=null){

			return  record;
		}else {
			redis.put("news", upmsUser.getUserId(),id);

			Integer num=record.getInt("volume");
			num++;
			String sql="update news set volume="+num+" where id="+id+" ";
			Integer result=Db.update(sql);

			if(result>0){
				return record;
			}else {
				return  null;
			}
		}









	}
}
