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

import com.atlas.server.model.Catalogue;
import com.atlas.server.service.CatalogueService;
import com.lambkit.common.util.StringUtils;
import com.lambkit.common.service.ServiceKit;
import com.lambkit.web.tag.LambkitTemplateModel;
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
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
/**
 * catalogue标签<br>
 * 参数：{id:主键}
 * 返回值：{entity:catalogue信息}
 * @author lambkit
 */
public class CatalogueMarker extends LambkitTemplateModel {

	@Override
	public void onRender(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String id = get(params, "id");
		String name = get(params, "name");
		String ename = get(params, "ename");
		String pinyin = get(params, "pinyin");
		String about = get(params, "about");
		String features = get(params, "features");
		String purpose = get(params, "purpose");
		String del = get(params, "del");
		String cId = get(params, "c_id");
		String botanyType = get(params, "botany_type");
		String image = get(params, "image");
		String address = get(params, "address");
		String value = get(params, "value");
		int pagenum = getInt(params, "pagenum", 0);
		int pagesize = getInt(params, "pagesize", 0);
		String wheresql = get(params, "sql", null);
		String sql = " from catalogue where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(id)) sql += " and id like '%" + id + "%'";//varchar
			if(StringUtils.hasText(name)) sql += " and name like '%" + name + "%'";//varchar
			if(StringUtils.hasText(ename)) sql += " and ename like '%" + ename + "%'";//varchar
			if(StringUtils.hasText(pinyin)) sql += " and pinyin like '%" + pinyin + "%'";//varchar
			if(StringUtils.hasText(about)) sql += " and about like '%" + about + "%'";//varchar
			if(StringUtils.hasText(features)) sql += " and features like '%" + features + "%'";//varchar
			if(StringUtils.hasText(purpose)) sql += " and purpose like '%" + purpose + "%'";//varchar
			if(StringUtils.hasText(del)) sql += " and del=" + del;//smallint
			if(StringUtils.hasText(cId)) sql += " and c_id=" + cId;//int
			if(StringUtils.hasText(botanyType)) sql += " and botany_type=" + botanyType;//int
			if(StringUtils.hasText(image)) sql += " and image like '%" + image + "%'";//varchar
			if(StringUtils.hasText(address)) sql += " and address like '%" + address + "%'";//varchar
			if(StringUtils.hasText(value)) sql += " and value like '%" + value + "%'";//varchar
		} else {
			sql += wheresql;
		}
		
		String orderby = get(params, "orderby", null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		CatalogueService service = Catalogue.service();
		
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
