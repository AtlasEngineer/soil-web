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
<<<<<<< HEAD
=======
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
>>>>>>> 7720cf1528ad709053b1dfbcbc1f5397c8302220
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

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
<<<<<<< HEAD
	public Ret getCamera(Integer id) {

		return null;
=======
	public Ret all(Integer id) {
		if(id==null){
			return  Ret.fail("errorMsg","id不能为空");
		}
		Record tiankuai = Db.findFirst("select geom  from tr_tiankuai where id = ?", id);

		if(tiankuai==null){
			return  Ret.fail("errorMsg","未查到地块");
		}

		List<Map<String,Object>> mapList=new ArrayList<>();

		List<Record> list=Db.find("select * from tr_camera");
		for (Record record:list){
            Record result=Db.findFirst("SELECT st_x('"+record.getStr("geom")+"') as lon,st_y('"+record.getStr("geom")+"') as lat ,ST_Contains((SELECT geom FROM tr_tiankuai WHERE id = "+id+"), (SELECT geom FROM tr_camera WHERE gid = "+record.getInt("gid")+"));");
			System.out.println(result.getStr("st_contains"));
            if("t".equals(result.getStr("st_contains"))){
				Map<String,Object> map=new HashMap<>();
				map.put("lon",result.getStr("lon"));
				map.put("lat",result.getStr("lat"));
				mapList.add(map);
			}
		}

		return Ret.ok("data",mapList);
>>>>>>> 7720cf1528ad709053b1dfbcbc1f5397c8302220
	}
}
