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

import com.atlas.server.model.sql.CatalogueKeepCriteria;
import com.jfinal.plugin.activerecord.Page;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.CatalogueKeepService;
import com.atlas.server.model.CatalogueKeep;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public class CatalogueKeepServiceImpl extends LambkitModelServiceImpl<CatalogueKeep> implements CatalogueKeepService {
	
	private CatalogueKeep DAO = null;
	
	public CatalogueKeep dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(CatalogueKeep.class);
		}
		return DAO;
	}

	@Override
	public Page all(Integer pageNum, Integer pageSize) {
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = 10;
		}
		CatalogueKeepCriteria sql=new CatalogueKeepCriteria();
		Page page=CatalogueKeep.service().dao().paginate(pageNum,pageSize,sql.example());
		return page;
	}
}
