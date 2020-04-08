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

import com.atlas.server.model.CatalogueSample;
import com.atlas.server.service.CatalogueSampleService;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import com.lambkit.common.util.StringUtils;

import com.lambkit.web.directive.LambkitDirective;

public class CatalogueSampleDirective extends LambkitDirective {

	@Override
	public void onRender(Env env, Scope scope, Writer writer) {
		// TODO Auto-generated method stub
		String id = getPara("id", scope);
		String name = getPara("name", scope);
		String brief = getPara("brief", scope);
		String catalogueId = getPara("catalogue_id", scope);
		String url = getPara("url", scope);
		String del = getPara("del", scope);
		String contSign = getPara("cont_sign", scope);
		String lon = getPara("lon", scope);
		String lat = getPara("lat", scope);
		String time = getPara("time", scope);
		String type = getPara("type", scope);
		String status = getPara("status", scope);
		int pagenum = getParaToInt("pagenum", scope, 0);
		int pagesize = getParaToInt("pagesize", scope, 0);
		String wheresql = getPara("sql", null);
		String sql = " from catalogue_sample where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(id)) sql += " and id like '%" + id + "%'";//varchar
			if(StringUtils.hasText(name)) sql += " and name like '%" + name + "%'";//varchar
			if(StringUtils.hasText(brief)) sql += " and brief like '%" + brief + "%'";//varchar
			if(StringUtils.hasText(catalogueId)) sql += " and catalogue_id like '%" + catalogueId + "%'";//varchar
			if(StringUtils.hasText(url)) sql += " and url like '%" + url + "%'";//varchar
			if(StringUtils.hasText(del)) sql += " and del=" + del;//int
			if(StringUtils.hasText(contSign)) sql += " and cont_sign like '%" + contSign + "%'";//varchar
			if(StringUtils.hasText(lon)) sql += " and lon like '%" + lon + "%'";//varchar
			if(StringUtils.hasText(lat)) sql += " and lat like '%" + lat + "%'";//varchar
			if(StringUtils.hasText(time)) sql += " and time like '%" + time + "%'";//datetime
			if(StringUtils.hasText(type)) sql += " and type=" + type;//smallint
			if(StringUtils.hasText(status)) sql += " and status=" + status;//smallint
		} else {
			sql += wheresql;
		}
		
		String orderby = getPara("orderby", scope, null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		CatalogueSampleService service = CatalogueSample.service();
		
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
