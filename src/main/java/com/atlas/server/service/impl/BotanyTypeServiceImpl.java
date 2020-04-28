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

import com.atlas.server.utils.Co;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
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
	public Page all(Integer pageNum, Integer pageSize) {

		if(pageNum==null||pageSize==null){
			pageNum=1;
			pageSize=5;
		}
		Page page=Db.paginate(pageNum,pageSize,"select *","from news where del=0");

		return page;
	}

	@Override
	public Record searchNewsById(Integer id) {

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
		Integer status=Db.queryInt("select status from news_collection c where c.user_id="+upmsUser.getUserId()+" and c.news_id="+id+""); //0收藏 1取消收藏
		if(status>0){
			record.set("is_Collection",false);
		}else{
			record.set("is_Collection",true);
		}
		if(t_id!=null){  //在redis里存在用户看过该文章直接返回详情
			return  record;
		}else {    //先加入redis，在返回详情
			redis.put("news", upmsUser.getUserId(),id,60*20);
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

	@Override
	public Co addNews( Integer news_id, Integer status) {
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
		String sql="";

		Record record=Db.findFirst("select status from news_collection c where c.user_id="+upmsUser.getUserId()+" and c.news_id="+news_id+"");
		if (record==null){
			sql="insert into news_collection (user_id,news_id,status) value ("+upmsUser.getUserId()+","+news_id+","+status+") ";
		}else {
			sql="UPDATE  news_collection SET status="+status+" WHERE user_id="+upmsUser.getUserId()+" and news_id="+news_id+"";
		}
       Integer result=Db.update(sql);
		if(result>0){
			return  Co.ok("msg","成功");
		}else {
			return  Co.fail("msg","失败");
		}

	}
}
