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
import com.lambkit.common.model.LambkitModel;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;
import com.soli.server.model.Catalogue;
import com.soli.server.service.CatalogueService;
import com.soli.server.service.ProductService;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author ww
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-11-11
 * @version 1.0
 * @since 1.0
 */
public class ProductServiceImpl extends LambkitModelServiceImpl implements ProductService {


	@Override
	public LambkitModel dao() {
		return null;
	}

	@Override
	public Ret getProductList(Integer pagenum, Integer pagesize, String name) {
		String from = " FROM tr_product WHERE del = 0 ";
		if (StringUtils.isNotBlank(name)){
			from += " AND product like '%" + name + "%'";
		}
		from += " ORDER BY create_time desc";
		if (pagenum == 0){
			List<Record> paginate = Db.find("select id,product,phenology", from);
			return Ret.ok("data",paginate);
		}else {
			Page<Record> paginate = Db.paginate(pagenum, pagesize, "select id,product,phenology", from);
			return Ret.ok("data",paginate);
		}
	}

	@Override
	public Ret getProductById(Integer id) {
		Record tr_product = Db.findById("tr_product", id);
		return Ret.ok("data",tr_product);
	}

	@Override
	public Ret addProduct(String name, String phenology) {
		Record tr_product = new Record();
		tr_product.set("product",name);
		tr_product.set("phenology",phenology);
		tr_product.set("create_time",new Date());
		tr_product.set("del", 0);
		boolean tr_product1 = Db.save("tr_product", tr_product);
		if (tr_product1){
			return Ret.ok("msg","添加成功");
		}else {
			return Ret.fail("errorMsg","添加失败");
		}
	}

	@Override
	public Ret updateProduct(Integer id, String name, String phenology) {
		Record tr_product = Db.findById("tr_product", id);
		tr_product.set("product",name);
		tr_product.set("phenology",phenology);
		boolean tr_product1 = Db.update("tr_product", tr_product);
		if (tr_product1){
			return Ret.ok("msg","修改成功");
		}else {
			return Ret.fail("errorMsg","修改失败");
		}
	}

	@Override
	public Ret delProductByIds(String ids) {
		String[] split = ids.split(",");
		if (split.length > 0){
			int update = Db.update("UPDATE tr_product SET del = 1 WHERE id in (" + ids + ")");
		}
		return Ret.ok("msg","删除成功");
	}

	@Override
	public Ret getPhenologyListByName(String name) {
		Record first = Db.findFirst("SELECT id, product, phenology FROM tr_product WHERE product = '" + name + "'");
		if (first != null){
			String phenology = first.getStr("phenology");
			if (StringUtils.isNotBlank(phenology)){
				String[] split = phenology.split(",");
				first.set("pheList",split);
			}
			else {
				first.set("pheList",new String[0]);
			}
		}
		return Ret.ok("msg",first);
	}

}
