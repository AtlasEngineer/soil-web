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

import com.atlas.server.model.Reply;
import com.atlas.server.service.ReplyService;
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
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
/**
 * at_reply标签<br>
 * 参数：{id:主键}
 * 返回值：{entity:at_reply信息}
 * @author lambkit
 */
public class ReplyMarker extends LambkitTemplateModel {

	@Override
	public void onRender(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String id = get(params, "id");
		String torrid = get(params, "torrid");
		String torr = get(params, "torr");
		String uid = get(params, "uid");
		String touid = get(params, "touid");
		String uname = get(params, "uname");
		String touname = get(params, "touname");
		String uheadurl = get(params, "uheadurl");
		String rcontent = get(params, "rcontent");
		String rtime = get(params, "rtime");
		String del = get(params, "del");
		int pagenum = getInt(params, "pagenum", 0);
		int pagesize = getInt(params, "pagesize", 0);
		String wheresql = get(params, "sql", null);
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
		
		String orderby = get(params, "orderby", null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		ReplyService service = Reply.service();
		
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
