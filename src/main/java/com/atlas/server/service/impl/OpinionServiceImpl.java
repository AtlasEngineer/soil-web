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
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.OpinionService;
import com.atlas.server.model.Opinion;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.web.RequestManager;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-27
 * @version 1.0
 * @since 1.0
 */
public class OpinionServiceImpl extends LambkitModelServiceImpl<Opinion> implements OpinionService {
	
	private Opinion DAO = null;
	
	public Opinion dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Opinion.class);
		}
		return DAO;
	}

	@Override
	public Co addOpinion(Opinion opinion) {

		boolean result=opinion.save();
		if(result){
			return Co.ok();
		}else {
			return Co.fail();
		}

	}
}
