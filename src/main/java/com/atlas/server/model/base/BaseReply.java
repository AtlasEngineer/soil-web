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
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseReply<M extends BaseReply<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_reply";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.Integer getTorrid() {
		return this.get("torrid");
	}

	public void setTorrid(java.lang.Integer torrid) {
		this.set("torrid", torrid);
	}
	public java.lang.Integer getTorr() {
		return this.get("torr");
	}

	public void setTorr(java.lang.Integer torr) {
		this.set("torr", torr);
	}
	public java.lang.Integer getUid() {
		return this.get("uid");
	}

	public void setUid(java.lang.Integer uid) {
		this.set("uid", uid);
	}
	public java.lang.Integer getTouid() {
		return this.get("touid");
	}

	public void setTouid(java.lang.Integer touid) {
		this.set("touid", touid);
	}
	public java.lang.String getUname() {
		return this.get("uname");
	}

	public void setUname(java.lang.String uname) {
		this.set("uname", uname);
	}
	public java.lang.String getTouname() {
		return this.get("touname");
	}

	public void setTouname(java.lang.String touname) {
		this.set("touname", touname);
	}
	public java.lang.String getUheadurl() {
		return this.get("uheadurl");
	}

	public void setUheadurl(java.lang.String uheadurl) {
		this.set("uheadurl", uheadurl);
	}
	public java.lang.String getRcontent() {
		return this.get("rcontent");
	}

	public void setRcontent(java.lang.String rcontent) {
		this.set("rcontent", rcontent);
	}
	public java.util.Date getRtime() {
		return this.get("rtime");
	}

	public void setRtime(java.util.Date rtime) {
		this.set("rtime", rtime);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
}
