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

import com.atlas.server.model.Answer;
import com.atlas.server.utils.ReplayNode;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.ReplyService;
import com.atlas.server.model.Reply;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.web.RequestManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
public class ReplyServiceImpl extends LambkitModelServiceImpl<Reply> implements ReplyService {
	
	private Reply DAO = null;
	
	public Reply dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Reply.class);
		}
		return DAO;
	}

	@Override
	public Reply addReply(Reply reply) {
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
		reply.setUid(upmsUser.getUserId().intValue());
		reply.setUname(upmsUser.getRealname());
		reply.setRtime(new Date());
		boolean result=reply.save();
		if(result){
			return reply;
		}else {
			return null;
		}
	}

	@Override
	public List<Reply> allByTid(Integer tid) {
		List<Reply> replies=Reply.service().dao().find(Reply.sql().andTorridEqualTo(tid).andTorrEqualTo(1).example());
		return replies;
	}

	@Override
	public List<Reply> allByRid(Integer rid) {
		List<Reply> replies=Reply.service().dao().find(Reply.sql().andTorridEqualTo(rid).andTorrEqualTo(0).example());
		return replies;
	}

}
