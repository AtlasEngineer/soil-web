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
package com.atlas.server.web.tag;

import java.io.IOException;
import java.util.Map;

import com.atlas.server.model.CatalogueSample;
import com.atlas.server.service.CatalogueSampleService;
import com.lambkit.common.util.StringUtils;
import com.lambkit.common.service.ServiceKit;
import com.lambkit.web.tag.base.BaseDirectiveModel;
import com.jfinal.kit.StrKit;
import com.jfinal.render.FreeMarkerRender;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-01
 * @version 1.0
 * @since 1.0
 */
/**
 * catalogue_sample标签<br>
 * 参数：{id:主键}
 * 返回值：{entity:catalogue_sample信息}
 * @author lambkit
 */
public class CatalogueSampleDirective extends BaseDirectiveModel {

	@Override
	public void executeMe(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String id = get(params, "id");
		String name = get(params, "name");
		String brief = get(params, "brief");
		String catalogueId = get(params, "catalogue_id");
		String url = get(params, "url");
		String del = get(params, "del");
		String contSign = get(params, "cont_sign");
		String lon = get(params, "lon");
		String lat = get(params, "lat");
		String time = get(params, "time");
		String type = get(params, "type");
		String parentId = get(params, "parent_id");
		String status = get(params, "status");
		String botanyType = get(params, "botany_type");
		int pagenum = getInt(params, "pagenum", 0);
		int pagesize = getInt(params, "pagesize", 0);
		String wheresql = get(params, "sql", null);
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
			if(StringUtils.hasText(parentId)) sql += " and parent_id like '%" + parentId + "%'";//varchar
			if(StringUtils.hasText(status)) sql += " and status=" + status;//smallint
			if(StringUtils.hasText(botanyType)) sql += " and botany_type=" + botanyType;//int
		} else {
			sql += wheresql;
		}
		
		String orderby = get(params, "orderby", null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		CatalogueSampleService service = CatalogueSample.service();
		
		String tagEntityKeyname = get(params, "key", "entity");
		if(pagenum==0) {
			env.setVariable(tagEntityKeyname, FreeMarkerRender.getConfiguration().getObjectWrapper().wrap(
					service.dao().findFirst("select *" + sql)));
		} else {
			if(pagesize==0) {
				env.setVariable(tagEntityKeyname, FreeMarkerRender.getConfiguration().getObjectWrapper().wrap(
						service.dao().find("select *" + sql)));
			} else {
				env.setVariable(tagEntityKeyname, FreeMarkerRender.getConfiguration().getObjectWrapper().wrap(
						service.dao().paginate(pagenum, pagesize, "select *", sql)));
			}
		}
        body.render(env.getOut());
	}
}
