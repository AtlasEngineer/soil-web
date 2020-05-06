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

import com.atlas.server.model.InsectPests;
import com.atlas.server.model.InsectSpecies;
import com.atlas.server.model.SpeciesPests;
import com.atlas.server.model.sql.InsectSpeciesCriteria;
import com.google.common.base.Joiner;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.CatalogueService;
import com.atlas.server.model.Catalogue;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public class CatalogueServiceImpl extends LambkitModelServiceImpl<Catalogue> implements CatalogueService {
	
	private Catalogue DAO = null;
	
	public Catalogue dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Catalogue.class);
		}
		return DAO;
	}

	@Override
	public Page all(Integer pageNum, Integer pageSize,String name) {
		if (pageSize == null) {
			pageSize = 10;
		}
		if (pageNum == null) {
			pageNum = 1;
		}
		InsectSpeciesCriteria sql=new InsectSpeciesCriteria();

		if(StringUtils.isNotBlank(name)){
			sql.andNameLike("%"+name+"%");
		}
		Page<InsectSpecies> page=InsectSpecies.service().dao().paginate(pageNum,pageSize,sql.example());
		return page;
	}

	@Override
	public Page all(Integer id,Integer pageNum, Integer pageSize,Integer type,String name) {

		if (pageSize == null) {
			pageSize = 10;
		}
		if (pageNum == null) {
			pageNum = 1;
		}
		Page<Record> page=null;
		if(type!=null&&type==0){//病害
			if(StringUtils.isNotBlank(name)){
				page= Db.paginate(pageNum,pageSize,"select p.id,p.`name`,p.image","from at_species_pests s LEFT JOIN at_insect_pests p on s.pests_id=p.id where s.species_id="+id+" and p.type=0 and p.`name` LIKE '%"+name+"%' ");
			}else {
				page= Db.paginate(pageNum,pageSize,"select p.id,p.`name`,p.image","from at_species_pests s LEFT JOIN at_insect_pests p on s.pests_id=p.id where s.species_id="+id+" and p.type=0");
			}
		}
		if(type!=null&&type==1){ //虫害
			if(StringUtils.isNotBlank(name)){
				page= Db.paginate(pageNum,pageSize,"select p.id,p.`name`,p.image","from at_species_pests s LEFT JOIN at_insect_pests p on s.pests_id=p.id where s.species_id="+id+" and p.type=1 and p.`name` LIKE '%"+name+"%' ");
			}else {
				page= Db.paginate(pageNum,pageSize,"select p.id,p.`name`,p.image","from at_species_pests s LEFT JOIN at_insect_pests p on s.pests_id=p.id where s.species_id="+id+" and p.type=1");
			}
		}
		return page;
	}


	@Override
	public InsectPests insectPestsbyId(Integer id,String token) {
		InsectPests pests=InsectPests.service().dao().findById(id);
		if (!"null".equals(token)&&StringUtils.isNotBlank(token)) {
			JwtConfig config = Lambkit.config(JwtConfig.class);
			String tokenPrefix = config.getTokenPrefix();
			String authToken = token.substring(tokenPrefix.length());
			String username = JwtKit.getJwtUser(authToken);
			System.out.println("username : " + username);
			UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());

			Integer status = Db.queryInt("select status from news_collection c where c.user_id=" + upmsUser.getUserId() + " and c.news_id='" + id + "'"); //0收藏 1取消收藏
			if (null == status) {
				pests.put("is_Collection", false);
			} else {
				if (status > 0) {
					pests.put("is_Collection", false);
				} else {
					pests.put("is_Collection", true);
				}
			}
		}else {
			pests.put("is_Collection", false);
		}
		List<Record> list=Db.find("select i.`name` from at_species_pests s LEFT JOIN at_insect_pests p on s.pests_id=p.id left join  at_insect_species i on s.species_id=i.id where s.pests_id=1561 and p.type=0");
		List<Record> imgList = Db.find(" SELECT c.url from at_pests_sample c   where c.pests_id="+id+" and c.del=0 LIMIT 3");
		List<String> stringList=new ArrayList<>();
		for (Record record:list){
			stringList.add(record.get("name"));
		}
		String str = Joiner.on(",").join(stringList);
		pests.put("host",str);
		pests.put("imgList",imgList);
		return pests;
	}
}
