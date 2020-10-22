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

import java.io.UnsupportedEncodingException;
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
	public Ret hnwjByName(String name, Integer pageNum, Integer pageSize, String time,String address,String type) throws ParseException, UnsupportedEncodingException {

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
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(time!=null&&time.length()!=0){
			sql.andUpTimeEqualTo(sdf.parse(time));
		}

		if("经济作物".equals(name)){
			Record new_time=Db.findFirst("SELECT up_time from hnw_jgpz where category='经济作物' ORDER BY up_time desc LIMIT 1");
			Page<HnwJgpz> page=HnwJgpz.service().dao().paginate(pageNum,pageSize,sql.andCategoryEqualTo(name).andUpTimeEqualTo(sdf.parse(new_time.getStr("up_time"))).example().setOrderBy("up_time desc"));
			return Ret.ok("data",page);
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

	@Override
	public Ret chemical() {
		List<Record> records=Db.find("select * from tr_chemical_fertilizer");
		return Ret.ok("data",records);
	}

	@Override
	public Ret hnwj(String name, Integer pageNum, Integer pageSize,Integer type,Integer id) throws ParseException, UnsupportedEncodingException {
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}

		if(id==null){
			return  Ret.fail("errorMsg","地块id不能为空");
		}

		Record record=Db.findFirst("select * from tr_ch_county WHERE ST_Contains(geom,(SELECT geom FROM tr_tiankuai WHERE id = "+id+")) ");
		if(record==null){
			return  Ret.fail("errorMsg","暂无数据");
		}

		StringBuffer stringBuffer=new StringBuffer();

		stringBuffer.append("from hnw_jgpz where 1=1 ");

		if(StringUtils.isBlank(name)){
			return  Ret.fail("errorMsg","类别不能为空");
		}else {
			stringBuffer.append(" and product = '"+name+"'");
		}

		if(type==null){
			return  Ret.fail("errorMsg","时间不能为空");
		}else {
			if(type==0){
				//七天
				stringBuffer.append("  and up_time BETWEEN (SELECT now() - INTERVAL '7 day') AND (SELECT now())");
			}else {
				//一个月
				stringBuffer.append(" and \"up_time\" BETWEEN (select now() - interval '1 MONTH')  and (select NOW() THISMONTH)");
			}
		}
		stringBuffer.append(" and place like '%"+record.getStr("cname")+record.getStr("name")+"%'");

		stringBuffer.append(" GROUP BY  up_time,price,place");
		stringBuffer.append(" order BY  up_time ");
		Page<HnwJgpz> page=HnwJgpz.service().dao().paginate(pageNum,pageSize,"select place,price,up_time",stringBuffer.toString());

        Record avg=Db.findFirst("select avg(to_number((substr(price,0,(char_length(price)-2))), '999.99')) as num from  hnw_jgpz n where  product = '"+name+"' and place like '%"+record.getStr("cname")+record.getStr("name")+"%' and up_time >= CURRENT_DATE - 1");

		Record max=Db.findFirst("select max(to_number((substr(price,0,(char_length(price)-2))), '999.99')) as num from  hnw_jgpz n where  product = '"+name+"' and place like '%"+record.getStr("cname")+record.getStr("name")+"%' and up_time >= CURRENT_DATE - 1");

		Record min=Db.findFirst("select min(to_number((substr(price,0,(char_length(price)-2))), '999.99')) as num from  hnw_jgpz n where  product = '"+name+"' and place like '%"+record.getStr("cname")+record.getStr("name")+"%' and up_time >= CURRENT_DATE - 1");

		if(StringUtils.isBlank(avg.getStr("num"))){
			return Ret.ok("data",page).set("avg","").set("max","").set("min","");
		}else {
			return Ret.ok("data",page).set("avg",avg.getStr("num").substring(0,4)).set("max",max.getStr("num")).set("min",min.getStr("num"));

		}

	}

	@Override
	public Ret hnwj(String name) throws ParseException, UnsupportedEncodingException {
		List<Record> records=null;

		if(StringUtils.isBlank(name)){
			records=Db.find("select product from hnw_jgpz  GROUP BY product");
		}else {
			records=Db.find("select product from hnw_jgpz where  product like '%"+name+"%' GROUP BY product");
		}

		return Ret.ok("data",records);
	}
}
