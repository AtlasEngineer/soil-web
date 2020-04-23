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
package com.atlas.server.web.directive;

import com.atlas.server.model.Reply;
import com.atlas.server.service.ReplyService;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import com.lambkit.common.util.StringUtils;
import com.lambkit.web.directive.LambkitDirective;

public class ReplyDirective extends LambkitDirective {

	@Override
	public void onRender(Env env, Scope scope, Writer writer) {
		// TODO Auto-generated method stub
		String id = getPara("id", scope);
		String torrid = getPara("torrid", scope);
		String torr = getPara("torr", scope);
		String uid = getPara("uid", scope);
		String touid = getPara("touid", scope);
		String uname = getPara("uname", scope);
		String touname = getPara("touname", scope);
		String uheadurl = getPara("uheadurl", scope);
		String rcontent = getPara("rcontent", scope);
		String rtime = getPara("rtime", scope);
		String del = getPara("del", scope);
		int pagenum = getParaToInt("pagenum", scope, 0);
		int pagesize = getParaToInt("pagesize", scope, 0);
		String wheresql = getPara("sql", null);
		String sql = " from at_reply where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(id)) sql += " and id=" + id;//int
			if(StringUtils.hasText(torrid)) sql += " and torrid=" + torrid;//int
			if(StringUtils.hasText(torr)) sql += " and torr=" + torr;//int
			if(StringUtils.hasText(uid)) sql += " and uid=" + uid;//int
			if(StringUtils.hasText(touid)) sql += " and touid=" + touid;//int
			if(StringUtils.hasText(uname)) sql += " and uname like '%" + uname + "%'";//varchar
			if(StringUtils.hasText(touname)) sql += " and touname like '%" + touname + "%'";//varchar
			if(StringUtils.hasText(uheadurl)) sql += " and uheadurl like '%" + uheadurl + "%'";//varchar
			if(StringUtils.hasText(rcontent)) sql += " and rcontent like '%" + rcontent + "%'";//varchar
			if(StringUtils.hasText(rtime)) sql += " and rtime like '%" + rtime + "%'";//timestamp
			if(StringUtils.hasText(del)) sql += " and del=" + del;//int
		} else {
			sql += wheresql;
		}
		
		String orderby = getPara("orderby", scope, null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		ReplyService service = Reply.service();
		
		String tagEntityKeyname = getPara("key", scope, "entity");
		if(pagenum==0) {
			scope.set(tagEntityKeyname, service.dao().findFirst("select *" + sql));
		} else {
			if(pagesize==0) {
				scope.set(tagEntityKeyname, service.dao().find("select *" + sql));
			} else {
				scope.set(tagEntityKeyname, service.dao().paginate(pagenum, pagesize, "select *", sql));
			}
		}
        renderBody(env, scope, writer);
	}

}
