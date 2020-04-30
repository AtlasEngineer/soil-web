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
 * @date 2020-04-29
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BasePestsKeep<M extends BasePestsKeep<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_pests_keep";
	}
    
	public java.lang.Long getId() {
		return this.get("id");
	}

	public void setId(java.lang.Long id) {
		this.set("id", id);
	}
	public java.lang.String getName() {
		return this.get("name");
	}

	public void setName(java.lang.String name) {
		this.set("name", name);
	}
	public java.lang.String getUrl() {
		return this.get("url");
	}

	public void setUrl(java.lang.String url) {
		this.set("url", url);
	}
	public java.lang.Integer getUserId() {
		return this.get("user_id");
	}

	public void setUserId(java.lang.Integer userId) {
		this.set("user_id", userId);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.lang.String getPestsId() {
		return this.get("pests_id");
	}

	public void setPestsId(java.lang.String pestsId) {
		this.set("pests_id", pestsId);
	}
	public java.util.Date getTime() {
		return this.get("time");
	}

	public void setTime(java.util.Date time) {
		this.set("time", time);
	}
	public java.lang.String getSampleId() {
		return this.get("sample_id");
	}

	public void setSampleId(java.lang.String sampleId) {
		this.set("sample_id", sampleId);
	}
	public java.lang.Integer getStatus() {
		return this.get("status");
	}

	public void setStatus(java.lang.Integer status) {
		this.set("status", status);
	}
}
