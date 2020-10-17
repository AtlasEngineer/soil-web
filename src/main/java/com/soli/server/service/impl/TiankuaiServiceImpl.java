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

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.soli.server.model.Data;
import com.soli.server.model.DataEach;
import com.soli.server.service.TiankuaiService;
import com.soli.server.model.Tiankuai;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.soli.server.utils.ReadTiffUtils.getTiffXY;

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
	public Ret searchLanlats(String lanlats,Integer id) {
		if(StringUtils.isBlank(lanlats)){
			return  Ret.fail("errorMsg","lanlats为空");
		}
		if(id==null){
			return  Ret.fail("errorMsg","id为空");
		}
		Data data=Data.service().dao().findFirst(Data.sql().andIdEqualTo(id).andDelEqualTo(0).example());
		if(data==null){
			return  Ret.fail("errorMsg","未查到");
		}
		if(data.getType()!=0){
			return  Ret.fail("errorMsg","数据类型只能查询空间数据");
		}
		DataEach dataEach=DataEach.service().dao().findFirst(DataEach.sql().andDataIdEqualTo(data.getId()).example());



		return null;
	}


	@Override
	public Ret compoundQuery(Integer countyId, Integer type, String time) {
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
		WKTReader reader = new WKTReader( geometryFactory );
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime ,endTime ;
		List<Record> resultList = new ArrayList<>();
		/**
		 * 	查询语句 开始时间条件没加------~~~~！！！！！
		 * 	！！！！
		 * 	！！！
		 */
		try {
			if (StringUtils.isNotBlank(time)){
				String[] split = time.split(",");
					startTime = simpleDateFormat.parse(split[0]);
					endTime = simpleDateFormat.parse(split[1]);
			}

			Record first = Db.findFirst("SELECT st_astext(geom) as geom FROM tr_ch_county WHERE gid = "+countyId);
			String geometryStr = first.getStr("geom");
			Geometry countyGeom = reader.read(geometryStr);
			/* shp  文件类型	  */
			if (type == 0){
				List<Record> records = Db.find("SELECT * FROM tr_data_each WHERE type = 0 ");
				for (Record record : records) {
					/* geometry 与  shp 比较 */

					if (true){
						resultList.add(record);
					}
				}
			}
			/*	tif	  文件类型    */
			else if (type == 1){
				List<Record> records = Db.find("SELECT * FROM tr_data_each WHERE type = 1 ");
				for (Record	record: records) {
					String url = record.getStr("url");
					if (url.startsWith("d:")){
						/* 截取 d: 后面的 文件名*/
						url = url.substring(2,url.length());
						String rootPath = PathKit.getWebRootPath() + "/d/";

						Kv tiffXY = getTiffXY(rootPath + url + "/" + url + ".tif");
						double minX = tiffXY.getNumber("minX").doubleValue();
						double minY = tiffXY.getNumber("minY").doubleValue();
						double maxX = tiffXY.getNumber("maxX").doubleValue();
						double maxY = tiffXY.getNumber("maxY").doubleValue();
						/* 将tif 包围盒转成 一个面进行比较 */
						String tifGeomStr = "POLYGON(("+minX+" "+maxY+","+maxX+" "+maxY+","+minX+" "+minY+","+minX+" "+maxY+","+minX+" "+maxY+"))";
						Geometry tifGeom = reader.read(tifGeomStr);
						/*	如果相交	  */
						if (countyGeom.intersects(tifGeom)){
							resultList.add(record);
						}
					}
				}
				return Ret.ok("data",resultList);
			}else {
				return Ret.fail("errorMsg","暂不支持此类型文件查询");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return Ret.ok("data",resultList);
	}
}
