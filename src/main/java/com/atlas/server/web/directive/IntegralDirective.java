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

import com.atlas.server.model.Integral;
import com.atlas.server.service.IntegralService;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import com.lambkit.common.util.StringUtils;
import com.lambkit.web.directive.LambkitDirective;

public class IntegralDirective extends LambkitDirective {

	@Override
	public void onRender(Env env, Scope scope, Writer writer) {
		// TODO Auto-generated method stub
		String id = getPara("id", scope);
		String userid = getPara("userid", scope);
		String score = getPara("score", scope);
		String type = getPara("type", scope);
		String created = getPara("created", scope);
		String origin = getPara("origin", scope);
		String orid = getPara("orid", scope);
		int pagenum = getParaToInt("pagenum", scope, 0);
		int pagesize = getParaToInt("pagesize", scope, 0);
		String wheresql = getPara("sql", null);
		String sql = " from at_integral where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(id)) sql += " and id=" + id;//int
			if(StringUtils.hasText(userid)) sql += " and userid=" + userid;//bigint
			if(StringUtils.hasText(score)) sql += " and score=" + score;//int
			if(StringUtils.hasText(type)) sql += " and type like '%" + type + "%'";//varchar
			if(StringUtils.hasText(created)) sql += " and created like '%" + created + "%'";//datetime
			if(StringUtils.hasText(origin)) sql += " and origin like '%" + origin + "%'";//varchar
			if(StringUtils.hasText(orid)) sql += " and orid=" + orid;//int
		} else {
			sql += wheresql;
		}
		
		String orderby = getPara("orderby", scope, null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		IntegralService service = Integral.service();
		
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
