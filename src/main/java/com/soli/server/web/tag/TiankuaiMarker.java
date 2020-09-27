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

import com.soli.server.model.Tiankuai;
import com.soli.server.service.TiankuaiService;
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
 * tr_tiankuai标签<br>
 * 参数：{id:主键}
 * 返回值：{entity:tr_tiankuai信息}
 * @author lambkit
 */
public class TiankuaiMarker extends LambkitTemplateModel {

	@Override
	public void onRender(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String gid = get(params, "gid");
		String name = get(params, "name");
		String layer = get(params, "layer");
		String area = get(params, "area");
		String oid = get(params, "oid_");
		String shapeArea = get(params, "shape_area");
		String id = get(params, "id");
		String geom = get(params, "geom");
		String type = get(params, "type");
		String dkName = get(params, "dk_name");
		String dkAddress = get(params, "dk_address");
		String dkUrl = get(params, "dk_url");
		String dkBeginTime = get(params, "dk_begin_time");
		String dkEndTime = get(params, "dk_end_time");
		String dkFarmland = get(params, "dk_farmland");
		String dkPerimeter = get(params, "dk_perimeter");
		String dkFarm = get(params, "dk_farm");
		String dkAltitude = get(params, "dk_altitude");
		String dkSlope = get(params, "dk_slope");
		String dkGrowers = get(params, "dk_growers");
		String dkPhone = get(params, "dk_phone");
		String dkPerson = get(params, "dk_person");
		String dkFertilizer = get(params, "dk_fertilizer");
		String dkUsername = get(params, "dk_username");
		String dkUserId = get(params, "dk_user_id");
		String del = get(params, "del");
		int pagenum = getInt(params, "pagenum", 0);
		int pagesize = getInt(params, "pagesize", 0);
		String wheresql = get(params, "sql", null);
		String sql = " from tr_tiankuai where "; 
		if(wheresql == null) {
			sql += " 1=1 ";
			if(StringUtils.hasText(gid)) sql += " and gid like '%" + gid + "%'";//serial
			if(StringUtils.hasText(name)) sql += " and name like '%" + name + "%'";//varchar
			if(StringUtils.hasText(layer)) sql += " and layer like '%" + layer + "%'";//varchar
			if(StringUtils.hasText(area)) sql += " and area like '%" + area + "%'";//numeric
			if(StringUtils.hasText(oid)) sql += " and oid_=" + oid;//int4
			if(StringUtils.hasText(shapeArea)) sql += " and shape_area like '%" + shapeArea + "%'";//numeric
			if(StringUtils.hasText(id)) sql += " and id like '%" + id + "%'";//numeric
			if(StringUtils.hasText(geom)) sql += " and geom like '%" + geom + "%'";//geometry
			if(StringUtils.hasText(type)) sql += " and type like '%" + type + "%'";//varchar
			if(StringUtils.hasText(dkName)) sql += " and dk_name like '%" + dkName + "%'";//varchar
			if(StringUtils.hasText(dkAddress)) sql += " and dk_address like '%" + dkAddress + "%'";//varchar
			if(StringUtils.hasText(dkUrl)) sql += " and dk_url like '%" + dkUrl + "%'";//varchar
			if(StringUtils.hasText(dkBeginTime)) sql += " and dk_begin_time like '%" + dkBeginTime + "%'";//timestamp
			if(StringUtils.hasText(dkEndTime)) sql += " and dk_end_time like '%" + dkEndTime + "%'";//timestamp
			if(StringUtils.hasText(dkFarmland)) sql += " and dk_farmland like '%" + dkFarmland + "%'";//varchar
			if(StringUtils.hasText(dkPerimeter)) sql += " and dk_perimeter like '%" + dkPerimeter + "%'";//varchar
			if(StringUtils.hasText(dkFarm)) sql += " and dk_farm like '%" + dkFarm + "%'";//varchar
			if(StringUtils.hasText(dkAltitude)) sql += " and dk_altitude like '%" + dkAltitude + "%'";//varchar
			if(StringUtils.hasText(dkSlope)) sql += " and dk_slope like '%" + dkSlope + "%'";//varchar
			if(StringUtils.hasText(dkGrowers)) sql += " and dk_growers like '%" + dkGrowers + "%'";//varchar
			if(StringUtils.hasText(dkPhone)) sql += " and dk_phone like '%" + dkPhone + "%'";//varchar
			if(StringUtils.hasText(dkPerson)) sql += " and dk_person like '%" + dkPerson + "%'";//varchar
			if(StringUtils.hasText(dkFertilizer)) sql += " and dk_fertilizer like '%" + dkFertilizer + "%'";//text
			if(StringUtils.hasText(dkUsername)) sql += " and dk_username like '%" + dkUsername + "%'";//varchar
			if(StringUtils.hasText(dkUserId)) sql += " and dk_user_id=" + dkUserId;//int4
			if(StringUtils.hasText(del)) sql += " and del=" + del;//int4
		} else {
			sql += wheresql;
		}
		
		String orderby = get(params, "orderby", null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		TiankuaiService service = Tiankuai.service();
		
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
