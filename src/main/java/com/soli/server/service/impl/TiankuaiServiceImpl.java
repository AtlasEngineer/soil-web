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
package com.soli.server.service.impl;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.soli.server.model.Data;
import com.soli.server.model.DataEach;
import com.soli.server.service.TiankuaiService;
import com.soli.server.model.Tiankuai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public class TiankuaiServiceImpl extends LambkitModelServiceImpl<Tiankuai> implements TiankuaiService {
	
	private Tiankuai DAO = null;
	
	public Tiankuai dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Tiankuai.class);
		}
		return DAO;
	}

	@Override
	public Ret all(Integer id) {
		if(id==null){
			return  Ret.fail("errorMsg","id不能为空");
		}
		Record tiankuai = Db.findFirst("select geom  from tr_tiankuai where id = ?", id);

		if(tiankuai==null){
			return  Ret.fail("errorMsg","未查到地块");
		}

		List<Record> list=Db.find("select *,st_x(geom) as lon,st_y(geom) as lat from tr_camera WHERE ST_Contains((SELECT geom FROM tr_tiankuai WHERE id = "+id+"),geom)");

		return Ret.ok("data",list);
	}

	@Override
	public Ret searchDiseases(String type,String period,Integer pageNum, Integer pageSize) {

		if(StringUtils.isBlank(type)){
			return  Ret.fail("errorMsg","类型不能为空");
		}
		if(StringUtils.isBlank(period)){
			return  Ret.fail("errorMsg","时期不能为空");
		}
		String select="";
		if("病害".equals(type)){
			select="SELECT diseases_name,diseases_condition,diseases_methon,type,period,id,del,diseases_symptom";
		}
		if("虫害".equals(type)){
			select="SELECT pests_name,pests_features,pests_grow,type,period,id,del,pests_harm,pests_methon";
		}
		if("草害".equals(type)){
			select="SELECT grass_name,grass_about,grass_methon,type,period,id,del";
		}

		Page<Record> page=Db.paginate(pageNum,pageSize,select,"from where del=0 and type='"+type+"'");

		return null;
	}

}
