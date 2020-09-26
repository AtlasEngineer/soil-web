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
package com.soli.server.model.base;

import com.jfinal.plugin.activerecord.IBean;

import com.lambkit.common.model.LambkitModel;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-26
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseHnwJgpz<M extends BaseHnwJgpz<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "hnw_jgpz";
	}
    
	public java.lang.String getCategory() {
		return this.get("category");
	}

	public void setCategory(java.lang.String category) {
		this.set("category", category);
	}
	public java.lang.String getTag() {
		return this.get("tag");
	}

	public void setTag(java.lang.String tag) {
		this.set("tag", tag);
	}
	public java.lang.String getProduct() {
		return this.get("product");
	}

	public void setProduct(java.lang.String product) {
		this.set("product", product);
	}
	public java.lang.String getPlace() {
		return this.get("place");
	}

	public void setPlace(java.lang.String place) {
		this.set("place", place);
	}
	public java.lang.String getPrice() {
		return this.get("price");
	}

	public void setPrice(java.lang.String price) {
		this.set("price", price);
	}
	public java.lang.String getStatus() {
		return this.get("status");
	}

	public void setStatus(java.lang.String status) {
		this.set("status", status);
	}
	public java.util.Date getUpTime() {
		return this.get("up_time");
	}

	public void setUpTime(java.util.Date upTime) {
		this.set("up_time", upTime);
	}
	public java.lang.String getUrl() {
		return this.get("url");
	}

	public void setUrl(java.lang.String url) {
		this.set("url", url);
	}
	public java.lang.Long getId() {
		return this.get("id");
	}

	public void setId(java.lang.Long id) {
		this.set("id", id);
	}
}
