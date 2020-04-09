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
		String commentId = getPara("comment_id", scope);
		String replyId = getPara("reply_id", scope);
		String content = getPara("content", scope);
		String del = getPara("del", scope);
		String replyType = getPara("reply_type", scope);
		String fromUid = getPara("from_uid", scope);
		String toUid = getPara("to_uid", scope);
		String time = getPara("time", scope);
		int pagenum = getParaToInt("pagenum", scope, 0);
		int pagesize = getParaToInt("pagesize", scope, 0);
		String wheresql = getPara("sql", null);
		String sql = " from at_reply where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(id)) sql += " and id=" + id;//int
			if(StringUtils.hasText(commentId)) sql += " and comment_id=" + commentId;//int
			if(StringUtils.hasText(replyId)) sql += " and reply_id=" + replyId;//int
			if(StringUtils.hasText(content)) sql += " and content=" + content;//int
			if(StringUtils.hasText(del)) sql += " and del=" + del;//int
			if(StringUtils.hasText(replyType)) sql += " and reply_type like '%" + replyType + "%'";//varchar
			if(StringUtils.hasText(fromUid)) sql += " and from_uid=" + fromUid;//int
			if(StringUtils.hasText(toUid)) sql += " and to_uid=" + toUid;//int
			if(StringUtils.hasText(time)) sql += " and time like '%" + time + "%'";//timestamp
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
