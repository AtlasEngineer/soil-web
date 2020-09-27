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
import com.soli.server.model.Tiankuai;
import com.soli.server.service.TiankuaiService;

public class TiankuaiDirective extends LambkitDirective {

	@Override
	public void onRender(Env env, Scope scope, Writer writer) {
		// TODO Auto-generated method stub
		String gid = getPara("gid", scope);
		String name = getPara("name", scope);
		String layer = getPara("layer", scope);
		String area = getPara("area", scope);
		String oid = getPara("oid_", scope);
		String shapeArea = getPara("shape_area", scope);
		String id = getPara("id", scope);
		String geom = getPara("geom", scope);
		String type = getPara("type", scope);
		String dkName = getPara("dk_name", scope);
		String dkAddress = getPara("dk_address", scope);
		String dkUrl = getPara("dk_url", scope);
		String dkBeginTime = getPara("dk_begin_time", scope);
		String dkEndTime = getPara("dk_end_time", scope);
		String dkFarmland = getPara("dk_farmland", scope);
		String dkPerimeter = getPara("dk_perimeter", scope);
		String dkFarm = getPara("dk_farm", scope);
		String dkAltitude = getPara("dk_altitude", scope);
		String dkSlope = getPara("dk_slope", scope);
		String dkGrowers = getPara("dk_growers", scope);
		String dkPhone = getPara("dk_phone", scope);
		String dkPerson = getPara("dk_person", scope);
		String dkFertilizer = getPara("dk_fertilizer", scope);
		String dkUsername = getPara("dk_username", scope);
		String dkUserId = getPara("dk_user_id", scope);
		String del = getPara("del", scope);
		int pagenum = getParaToInt("pagenum", scope, 0);
		int pagesize = getParaToInt("pagesize", scope, 0);
		String wheresql = getPara("sql", null);
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
		
		String orderby = getPara("orderby", scope, null);
		
		if(StrKit.notBlank(orderby)) {
			sql += " order by " + orderby;
		}
		
		TiankuaiService service = Tiankuai.service();
		
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
