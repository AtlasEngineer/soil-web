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
public abstract class BaseSpeciesPests<M extends BaseSpeciesPests<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "at_species_pests";
	}
    
	public java.lang.Integer getId() {
		return this.get("id");
	}

	public void setId(java.lang.Integer id) {
		this.set("id", id);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.lang.Integer getPestsId() {
		return this.get("pests_id");
	}

	public void setPestsId(java.lang.Integer pestsId) {
		this.set("pests_id", pestsId);
	}
	public java.lang.Integer getSpeciesId() {
		return this.get("species_id");
	}

	public void setSpeciesId(java.lang.Integer speciesId) {
		this.set("species_id", speciesId);
	}
}
