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

import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.DateTimeUtils;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.SignService;
import com.atlas.server.model.Sign;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.model.UpmsUser;

import java.util.Calendar;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-03
 * @version 1.0
 * @since 1.0
 */
public class SignServiceImpl extends LambkitModelServiceImpl<Sign> implements SignService {
	
	private Sign DAO = null;
	
	public Sign dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Sign.class);
		}
		return DAO;
	}
	@Override
	public Sign today(Long userId, String sway) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ").append(getTableName()).append(" WHERE ").append(" userid=? and ");
		sb.append(" TO_CHAR(created, 'YYYY-MM-DD') = TO_CHAR(now(), 'YYYY-MM-DD')");
		boolean f = StrKit.notBlank(sway);
		if(f) {
			sb.append(" and type=?");
			return dao().findFirst(sb.toString(), userId, sway);
		} else {
			return dao().findFirst(sb.toString(), userId);
		}
	}

	@Override
	public List<Sign> currentMonth(Long userId, String sway) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *, TO_CHAR(created, 'DD') as d FROM ");
		sb.append(getTableName());
		sb.append(" WHERE ").append(" userid=? and ");
		sb.append(" TO_CHAR(created, 'YYYY-MM') = TO_CHAR(now(), 'YYYY-MM') ");
		boolean f = StrKit.notBlank(sway);
		if(f) {
			sb.append(" and type=?");
			return dao().find(sb.toString(), userId, sway);
		} else {
			return dao().find(sb.toString(), userId);
		}
	}
}
