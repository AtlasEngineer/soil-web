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
 * @date 2020-09-19
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseDirectory<M extends BaseDirectory<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "tr_directory";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.Integer getParentId() {
		return this.get("parent_id");
	}

	public void setParentId(java.lang.Integer parentId) {
		this.set("parent_id", parentId);
	}
	public java.lang.Integer getLevel() {
		return this.get("level");
	}

	public void setLevel(java.lang.Integer level) {
		this.set("level", level);
	}
	public java.lang.String getName() {
		return this.get("name");
	}

	public void setName(java.lang.String name) {
		this.set("name", name);
	}
	public java.util.Date getCreateTime() {
		return this.get("create_time");
	}

	public void setCreateTime(java.util.Date createTime) {
		this.set("create_time", createTime);
	}
	public java.lang.String getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.String del) {
		this.set("del", del);
	}
}
