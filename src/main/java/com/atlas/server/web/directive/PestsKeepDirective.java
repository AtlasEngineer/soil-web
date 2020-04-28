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

import com.atlas.server.model.PestsKeep;
import com.atlas.server.service.PestsKeepService;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import com.lambkit.common.util.StringUtils;
import com.lambkit.web.directive.LambkitDirective;

public class PestsKeepDirective extends LambkitDirective {

	@Override
	public void onRender(Env env, Scope scope, Writer writer) {
		// TODO Auto-generated method stub
		String id = getPara("id", scope);
		String name = getPara("name", scope);
		String url = getPara("url", scope);
		String userId = getPara("user_id", scope);
		String del = getPara("del", scope);
		String pestsId = getPara("pests_id", scope);
		String time = getPara("time", scope);
		String sampleId = getPara("sample_id", scope);
		String status = getPara("status", scope);
		int pagenum = getParaToInt("pagenum", scope, 0);
		int pagesize = getParaToInt("pagesize", scope, 0);
		String wheresql = getPara("sql", null);
		String sql = " from at_pests_keep where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(id)) sql += " and id=" + id;//bigint
			if(StringUtils.hasText(name)) sql += " and name like '%" + name + "%'";//varchar
			if(StringUtils.hasText(url)) sql += " and url like '%" + url + "%'";//varchar
			if(StringUtils.hasText(userId)) sql += " and user_id=" + userId;//int
			if(StringUtils.hasText(del)) sql += " and del=" + del;//smallint
			if(StringUtils.hasText(pestsId)) sql += " and pests_id like '%" + pestsId + "%'";//varchar
			if(StringUtils.hasText(time)) sql += " and time like '%" + time + "%'";//datetime
			if(StringUtils.hasText(sampleId)) sql += " and sample_id=" + sampleId;//bigint
			if(StringUtils.hasText(status)) sql += " and status=" + status;//int
		} else {
			sql += wheresql;
		}
		
		String orderby = getPara("orderby", scope, null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		PestsKeepService service = PestsKeep.service();
		
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
