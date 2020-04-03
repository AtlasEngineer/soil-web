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
public abstract class BaseIntegral<M extends BaseIntegral<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_integral";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.Long getUserid() {
		return this.get("userid");
	}

	public void setUserid(java.lang.Long userid) {
		this.set("userid", userid);
	}
	public java.lang.Integer getScore() {
		return this.get("score");
	}

	public void setScore(java.lang.Integer score) {
		this.set("score", score);
	}
	public java.lang.String getType() {
		return this.get("type");
	}

	public void setType(java.lang.String type) {
		this.set("type", type);
	}
	public java.util.Date getCreated() {
		return this.get("created");
	}

	public void setCreated(java.util.Date created) {
		this.set("created", created);
	}
	public java.lang.String getOrigin() {
		return this.get("origin");
	}

	public void setOrigin(java.lang.String origin) {
		this.set("origin", origin);
	}
	public java.lang.Integer getOrid() {
		return this.get("orid");
	}

	public void setOrid(java.lang.Integer orid) {
		this.set("orid", orid);
	}
}
