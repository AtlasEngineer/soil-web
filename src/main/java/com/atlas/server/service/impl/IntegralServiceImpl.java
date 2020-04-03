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

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.DateTimeUtils;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.IntegralService;
import com.atlas.server.model.Integral;
import com.lambkit.module.upms.rpc.model.UpmsUser;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-03
 * @version 1.0
 * @since 1.0
 */
public class IntegralServiceImpl extends LambkitModelServiceImpl<Integral> implements IntegralService {
	
	private Integral DAO = null;
	
	public Integral dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Integral.class);
		}
		return DAO;
	}

	@Override
	public boolean addCredit(Long userId, Integer score, String ctype, String action, Integer actid) {
		Integral integral = new Integral();
		integral.setUserid(userId);
		integral.setScore(score);
		integral.setType(ctype);
		integral.setOrigin(action);
		integral.setOrid(actid);
		return addCredit(integral);
	}

	@Override
	public boolean addCredit(Integral integral) {
		return false;
	}

	@Override
	public List<Integral> currentMonthCredits(Long userId, String ctype, String action) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ").append(getTableName()).append(" WHERE ").append(" userid=? and ");
		sb.append(" TO_CHAR(created, 'YYYY-MM') = TO_CHAR(now(), 'YYYY-MM') ");
		if(StrKit.notBlank(ctype)) {
			sb.append(" and type=?");
		}
		if(StrKit.notBlank(action)) {
			sb.append(" and origin=?");
		}
		return dao().find(sb.toString(), userId);
	}

	@Override
	public Integer currentMonthCredit(Long userId, String ctype, String action) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT sum(score) as credit FROM ").append(getTableName()).append(" WHERE ").append(" userid=? and ");
		sb.append(" TO_CHAR(created, 'YYYY-MM') = TO_CHAR(now(), 'YYYY-MM') ");
		Integer credit = 0;
		if(StrKit.notBlank(ctype)) {
			sb.append(" and type=?");
		}
		if(StrKit.notBlank(action)) {
			sb.append(" and origin=?");
		}
		credit = Db.use(dao().configName()).queryInt(sb.toString(), userId);
		credit = credit==null? 0 : credit;
		return credit;
	}
}
