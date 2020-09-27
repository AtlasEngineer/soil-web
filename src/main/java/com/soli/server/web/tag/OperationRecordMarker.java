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
package com.soli.server.web.tag;

import java.io.IOException;
import java.util.Map;

import com.soli.server.model.OperationRecord;
import com.soli.server.service.OperationRecordService;
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
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
/**
 * tr_operation_record标签<br>
 * 参数：{id:主键}
 * 返回值：{entity:tr_operation_record信息}
 * @author lambkit
 */
public class OperationRecordMarker extends LambkitTemplateModel {

	@Override
	public void onRender(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String id = get(params, "id");
		String time = get(params, "time");
		String name = get(params, "name");
		String username = get(params, "username");
		String userId = get(params, "user_id");
		String type = get(params, "type");
		String content = get(params, "content");
		String del = get(params, "del");
		int pagenum = getInt(params, "pagenum", 0);
		int pagesize = getInt(params, "pagesize", 0);
		String wheresql = get(params, "sql", null);
		String sql = " from tr_operation_record where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(id)) sql += " and id like '%" + id + "%'";//bigserial
			if(StringUtils.hasText(time)) sql += " and time like '%" + time + "%'";//timestamp
			if(StringUtils.hasText(name)) sql += " and name like '%" + name + "%'";//varchar
			if(StringUtils.hasText(username)) sql += " and username like '%" + username + "%'";//varchar
			if(StringUtils.hasText(userId)) sql += " and user_id=" + userId;//int4
			if(StringUtils.hasText(type)) sql += " and type like '%" + type + "%'";//varchar
			if(StringUtils.hasText(content)) sql += " and content like '%" + content + "%'";//text
			if(StringUtils.hasText(del)) sql += " and del=" + del;//int2
		} else {
			sql += wheresql;
		}
		
		String orderby = get(params, "orderby", null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		OperationRecordService service = OperationRecord.service();
		
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
