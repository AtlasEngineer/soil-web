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
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BasePestsSample<M extends BasePestsSample<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_pests_sample";
	}
    
	public java.lang.String getId() {
		return this.get("id");
	}

	public void setId(java.lang.String id) {
		this.set("id", id);
	}
	public java.lang.Integer getPestsId() {
		return this.get("pests_id");
	}

	public void setPestsId(java.lang.Integer pestsId) {
		this.set("pests_id", pestsId);
	}
	public java.lang.String getUrl() {
		return this.get("url");
	}

	public void setUrl(java.lang.String url) {
		this.set("url", url);
	}
	public java.lang.String getBrief() {
		return this.get("brief");
	}

	public void setBrief(java.lang.String brief) {
		this.set("brief", brief);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.lang.String getContSign() {
		return this.get("cont_sign");
	}

	public void setContSign(java.lang.String contSign) {
		this.set("cont_sign", contSign);
	}
	public java.lang.Double getLon() {
		return this.get("lon");
	}

	public void setLon(java.lang.Double lon) {
		this.set("lon", lon);
	}
	public java.lang.String getLat() {
		return this.get("lat");
	}

	public void setLat(java.lang.String lat) {
		this.set("lat", lat);
	}
	public java.util.Date getTime() {
		return this.get("time");
	}

	public void setTime(java.util.Date time) {
		this.set("time", time);
	}
	public java.lang.Integer getType() {
		return this.get("type");
	}

	public void setType(java.lang.Integer type) {
		this.set("type", type);
	}
	public java.lang.Integer getStatus() {
		return this.get("status");
	}

	public void setStatus(java.lang.Integer status) {
		this.set("status", status);
	}
	public java.lang.String getName() {
		return this.get("name");
	}

	public void setName(java.lang.String name) {
		this.set("name", name);
	}
}
