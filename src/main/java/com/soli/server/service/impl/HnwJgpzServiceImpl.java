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

import com.soli.server.model.sql.HnwJgpzCriteria;
import com.soli.server.service.HnwJgpzService;
import com.soli.server.model.HnwJgpz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-26
 * @version 1.0
 * @since 1.0
 */
public class HnwJgpzServiceImpl extends LambkitModelServiceImpl<HnwJgpz> implements HnwJgpzService {
	
	private HnwJgpz DAO = null;
	
	@Override
	public HnwJgpz dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(HnwJgpz.class);
		}
		return DAO;
	}

	@Override
	public Ret hnwjByGroup() {
		List<HnwJgpz> hnwJgpzList=HnwJgpz.service().dao().find("SELECT category from hnw_jgpz GROUP BY category");
		return Ret.ok("data",hnwJgpzList);
	}

	@Override
	public Ret hnwjByName(String name, Integer pageNum, Integer pageSize, String time,String address,String type) throws ParseException {
		HnwJgpzCriteria sql=HnwJgpz.sql();
		if(StringUtils.isBlank(name)){
			return  Ret.fail("errorMsg","类别不能为空");
		}
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		if(StringUtils.isNotBlank(address)){
			sql.andPlaceLike("%"+address+"%");
		}
		if(StringUtils.isNotBlank(type)){
			sql.andTagLike("%"+type+"%");
		}
		if(time!=null&&time.length()!=0){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			sql.andUpTimeEqualTo(sdf.parse(time));
		}
		Page<HnwJgpz> page=HnwJgpz.service().dao().paginate(pageNum,pageSize,sql.andCategoryEqualTo(name).example().setOrderBy("up_time desc"));
		return Ret.ok("data",page);
	}

	@Override
	public Ret hnwfljgByName(String name, Integer pageNum, Integer pageSize, String time,String address,String type) throws ParseException {

		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("from hnw_fljg where 1=1 ");
		if(StringUtils.isNotBlank(type)){
			stringBuffer.append(" and tag like '%"+type+"%'");
		}
		if(StringUtils.isNotBlank(address)){
			stringBuffer.append(" and place like '%"+address+"%'");
		}
		if (time != null && time.length() > 1) {
			stringBuffer.append(" and up_time = '" + time + "' ");
		}
		stringBuffer.append(" order by up_time desc ");
		Page<Record> page= Db.paginate(pageNum,pageSize,"select *",stringBuffer.toString());
		return Ret.ok("data",page);
	}

	@Override
	public Ret hnwnyjgByName(String name, Integer pageNum, Integer pageSize, String time, String address, String type) throws ParseException {

		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("from hnw_nyjg where 1=1 ");
		if(StringUtils.isNotBlank(type)){
			stringBuffer.append(" and tag like '%"+type+"%'");
		}
		if(StringUtils.isNotBlank(address)){
			stringBuffer.append(" and place like '%"+address+"%'");
		}
		if (time != null && time.length() > 1) {
			stringBuffer.append(" and up_time = '" + time + "' ");
		}
		stringBuffer.append(" order by up_time desc ");
		Page<Record> page= Db.paginate(pageNum,pageSize,"select *",stringBuffer.toString());
		return Ret.ok("data",page);
	}
}
