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

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-09
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseReply<M extends BaseReply<M>> extends LambkitModel<M> implements IBean {

	public List<M> next;

	public List<M> getNext() {
		return next;
	}

	public void setNext(List<M> next) {
		this.next = next;
	}

	public String getTableName() {
		return "at_reply";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.Integer getCommentId() {
		return this.get("comment_id");
	}

	public void setCommentId(java.lang.Integer commentId) {
		this.set("comment_id", commentId);
	}
	public java.lang.Integer getReplyId() {
		return this.get("reply_id");
	}

	public void setReplyId(java.lang.Integer replyId) {
		this.set("reply_id", replyId);
	}
	public java.lang.Integer getContent() {
		return this.get("content");
	}

	public void setContent(java.lang.Integer content) {
		this.set("content", content);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.lang.String getReplyType() {
		return this.get("reply_type");
	}

	public void setReplyType(java.lang.String replyType) {
		this.set("reply_type", replyType);
	}
	public java.lang.Integer getFromUid() {
		return this.get("from_uid");
	}

	public void setFromUid(java.lang.Integer fromUid) {
		this.set("from_uid", fromUid);
	}
	public java.lang.Integer getToUid() {
		return this.get("to_uid");
	}

	public void setToUid(java.lang.Integer toUid) {
		this.set("to_uid", toUid);
	}
	public java.util.Date getTime() {
		return this.get("time");
	}

	public void setTime(java.util.Date time) {
		this.set("time", time);
	}
}
