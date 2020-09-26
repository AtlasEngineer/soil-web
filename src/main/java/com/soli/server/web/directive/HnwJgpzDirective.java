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
package com.soli.server.web.directive;

import com.jfinal.kit.StrKit;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import com.lambkit.common.util.StringUtils;
import com.lambkit.web.directive.LambkitDirective;
import com.soli.server.model.HnwJgpz;
import com.soli.server.service.HnwJgpzService;

public class HnwJgpzDirective extends LambkitDirective {

	@Override
	public void onRender(Env env, Scope scope, Writer writer) {
		// TODO Auto-generated method stub
		String category = getPara("category", scope);
		String tag = getPara("tag", scope);
		String product = getPara("product", scope);
		String place = getPara("place", scope);
		String price = getPara("price", scope);
		String status = getPara("status", scope);
		String upTime = getPara("up_time", scope);
		String url = getPara("url", scope);
		String id = getPara("id", scope);
		int pagenum = getParaToInt("pagenum", scope, 0);
		int pagesize = getParaToInt("pagesize", scope, 0);
		String wheresql = getPara("sql", null);
		String sql = " from hnw_jgpz where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(category)) sql += " and category like '%" + category + "%'";//varchar
			if(StringUtils.hasText(tag)) sql += " and tag like '%" + tag + "%'";//varchar
			if(StringUtils.hasText(product)) sql += " and product like '%" + product + "%'";//varchar
			if(StringUtils.hasText(place)) sql += " and place like '%" + place + "%'";//varchar
			if(StringUtils.hasText(price)) sql += " and price like '%" + price + "%'";//varchar
			if(StringUtils.hasText(status)) sql += " and status like '%" + status + "%'";//varchar
			if(StringUtils.hasText(upTime)) sql += " and up_time like '%" + upTime + "%'";//varchar
			if(StringUtils.hasText(url)) sql += " and url like '%" + url + "%'";//varchar
			if(StringUtils.hasText(id)) sql += " and id like '%" + id + "%'";//bigserial
		} else {
			sql += wheresql;
		}
		
		String orderby = getPara("orderby", scope, null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		HnwJgpzService service = HnwJgpz.service();
		
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
