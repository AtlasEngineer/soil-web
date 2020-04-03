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
package com.atlas.server.model.base;

import com.jfinal.plugin.activerecord.IBean;

import com.lambkit.common.model.LambkitModel;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-03
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseSign<M extends BaseSign<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_sign";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.Integer getUserid() {
		return this.get("userid");
	}

	public void setUserid(java.lang.Integer userid) {
		this.set("userid", userid);
	}
	public java.math.BigDecimal getLng() {
		return this.get("lng");
	}

	public void setLng(java.math.BigDecimal lng) {
		this.set("lng", lng);
	}
	public java.math.BigDecimal getLat() {
		return this.get("lat");
	}

	public void setLat(java.math.BigDecimal lat) {
		this.set("lat", lat);
	}
	public java.lang.String getType() {
		return this.get("type");
	}

	public void setType(java.lang.String type) {
		this.set("type", type);
	}
	public java.lang.String getOrigin() {
		return this.get("origin");
	}

	public void setOrigin(java.lang.String origin) {
		this.set("origin", origin);
	}
	public java.lang.String getAddress() {
		return this.get("address");
	}

	public void setAddress(java.lang.String address) {
		this.set("address", address);
	}
	public java.util.Date getCreated() {
		return this.get("created");
	}

	public void setCreated(java.util.Date created) {
		this.set("created", created);
	}
}
