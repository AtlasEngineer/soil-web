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
public abstract class BaseInsectPests<M extends BaseInsectPests<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_insect_pests";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.String getName() {
		return this.get("name");
	}

	public void setName(java.lang.String name) {
		this.set("name", name);
	}
	public java.lang.String getFeatures() {
		return this.get("features");
	}

	public void setFeatures(java.lang.String features) {
		this.set("features", features);
	}
	public java.lang.String getHost() {
		return this.get("host");
	}

	public void setHost(java.lang.String host) {
		this.set("host", host);
	}
	public java.lang.String getAddress() {
		return this.get("address");
	}

	public void setAddress(java.lang.String address) {
		this.set("address", address);
	}
	public java.lang.String getHarm() {
		return this.get("harm");
	}

	public void setHarm(java.lang.String harm) {
		this.set("harm", harm);
	}
	public java.lang.String getMethod() {
		return this.get("method");
	}

	public void setMethod(java.lang.String method) {
		this.set("method", method);
	}
	public java.lang.Integer getType() {
		return this.get("type");
	}

	public void setType(java.lang.Integer type) {
		this.set("type", type);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.util.Date getTime() {
		return this.get("time");
	}

	public void setTime(java.util.Date time) {
		this.set("time", time);
	}
	public java.lang.String getImage() {
		return this.get("image");
	}

	public void setImage(java.lang.String image) {
		this.set("image", image);
	}
}
