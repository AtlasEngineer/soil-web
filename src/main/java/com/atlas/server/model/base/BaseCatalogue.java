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

import com.lambkit.common.model.BaseModel;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-01
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseCatalogue<M extends BaseCatalogue<M>> extends BaseModel<M> implements IBean {

	public String getTableName() {
		return "catalogue";
	}
    
	public java.lang.String getId() {
		return this.get("id");
	}

	public void setId(java.lang.String id) {
		this.set("id", id);
	}
	public java.lang.String getName() {
		return this.get("name");
	}

	public void setName(java.lang.String name) {
		this.set("name", name);
	}
	public java.lang.String getEname() {
		return this.get("ename");
	}

	public void setEname(java.lang.String ename) {
		this.set("ename", ename);
	}
	public java.lang.String getPinyin() {
		return this.get("pinyin");
	}

	public void setPinyin(java.lang.String pinyin) {
		this.set("pinyin", pinyin);
	}
	public java.lang.String getAbout() {
		return this.get("about");
	}

	public void setAbout(java.lang.String about) {
		this.set("about", about);
	}
	public java.lang.String getFeatures() {
		return this.get("features");
	}

	public void setFeatures(java.lang.String features) {
		this.set("features", features);
	}
	public java.lang.String getPurpose() {
		return this.get("purpose");
	}

	public void setPurpose(java.lang.String purpose) {
		this.set("purpose", purpose);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.lang.Integer getCId() {
		return this.get("c_id");
	}

	public void setCId(java.lang.Integer cId) {
		this.set("c_id", cId);
	}
	public java.lang.Integer getBotanyType() {
		return this.get("botany_type");
	}

	public void setBotanyType(java.lang.Integer botanyType) {
		this.set("botany_type", botanyType);
	}
}
