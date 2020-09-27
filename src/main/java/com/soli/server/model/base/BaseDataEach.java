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
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseDataEach<M extends BaseDataEach<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "tr_data_each";
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
	public java.util.Date getDataTime() {
		return this.get("data_time");
	}

	public void setDataTime(java.util.Date dataTime) {
		this.set("data_time", dataTime);
	}
	public java.util.Date getTime() {
		return this.get("time");
	}

	public void setTime(java.util.Date time) {
		this.set("time", time);
	}
	public java.lang.Integer getUserid() {
		return this.get("userid");
	}

	public void setUserid(java.lang.Integer userid) {
		this.set("userid", userid);
	}
	public java.lang.String getUrl() {
		return this.get("url");
	}

	public void setUrl(java.lang.String url) {
		this.set("url", url);
	}
	public java.lang.Integer getType() {
		return this.get("type");
	}

	public void setType(java.lang.Integer type) {
		this.set("type", type);
	}
	public java.lang.Integer getDataId() {
		return this.get("data_id");
	}

	public void setDataId(java.lang.Integer dataId) {
		this.set("data_id", dataId);
	}
}
