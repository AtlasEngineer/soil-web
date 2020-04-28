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
 * @date 2020-04-27
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseOpinion<M extends BaseOpinion<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_opinion";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.String getContent() {
		return this.get("content");
	}

	public void setContent(java.lang.String content) {
		this.set("content", content);
	}
	public java.lang.String getPhone() {
		return this.get("phone");
	}

	public void setPhone(java.lang.String phone) {
		this.set("phone", phone);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.lang.Integer getUserId() {
		return this.get("user_id");
	}

	public void setUserId(java.lang.Integer userId) {
		this.set("user_id", userId);
	}
}
