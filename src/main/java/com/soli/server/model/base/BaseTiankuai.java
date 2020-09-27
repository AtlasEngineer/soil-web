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
public abstract class BaseTiankuai<M extends BaseTiankuai<M>> extends LambkitModel<M> implements IBean {

	public String getTableName() {
		return "tr_tiankuai";
	}
    
	public java.lang.Integer getGid() {
		return this.get("gid");
	}

	public void setGid(java.lang.Integer gid) {
		this.set("gid", gid);
	}
	public java.lang.String getName() {
		return this.get("name");
	}

	public void setName(java.lang.String name) {
		this.set("name", name);
	}
	public java.lang.String getLayer() {
		return this.get("layer");
	}

	public void setLayer(java.lang.String layer) {
		this.set("layer", layer);
	}
	public java.math.BigDecimal getArea() {
		return this.get("area");
	}

	public void setArea(java.math.BigDecimal area) {
		this.set("area", area);
	}
	public java.lang.Integer getOid() {
		return this.get("oid_");
	}

	public void setOid(java.lang.Integer oid) {
		this.set("oid_", oid);
	}
	public java.math.BigDecimal getShapeArea() {
		return this.get("shape_area");
	}

	public void setShapeArea(java.math.BigDecimal shapeArea) {
		this.set("shape_area", shapeArea);
	}
	public java.math.BigDecimal getId() {
		return this.get("id");
	}

	public void setId(java.math.BigDecimal id) {
		this.set("id", id);
	}
	public java.lang.String getGeom() {
		return this.get("geom");
	}

	public void setGeom(java.lang.String geom) {
		this.set("geom", geom);
	}
	public java.lang.String getType() {
		return this.get("type");
	}

	public void setType(java.lang.String type) {
		this.set("type", type);
	}
	public java.lang.String getDkName() {
		return this.get("dk_name");
	}

	public void setDkName(java.lang.String dkName) {
		this.set("dk_name", dkName);
	}
	public java.lang.String getDkAddress() {
		return this.get("dk_address");
	}

	public void setDkAddress(java.lang.String dkAddress) {
		this.set("dk_address", dkAddress);
	}
	public java.lang.String getDkUrl() {
		return this.get("dk_url");
	}

	public void setDkUrl(java.lang.String dkUrl) {
		this.set("dk_url", dkUrl);
	}
	public java.util.Date getDkBeginTime() {
		return this.get("dk_begin_time");
	}

	public void setDkBeginTime(java.util.Date dkBeginTime) {
		this.set("dk_begin_time", dkBeginTime);
	}
	public java.util.Date getDkEndTime() {
		return this.get("dk_end_time");
	}

	public void setDkEndTime(java.util.Date dkEndTime) {
		this.set("dk_end_time", dkEndTime);
	}
	public java.lang.String getDkFarmland() {
		return this.get("dk_farmland");
	}

	public void setDkFarmland(java.lang.String dkFarmland) {
		this.set("dk_farmland", dkFarmland);
	}
	public java.lang.String getDkPerimeter() {
		return this.get("dk_perimeter");
	}

	public void setDkPerimeter(java.lang.String dkPerimeter) {
		this.set("dk_perimeter", dkPerimeter);
	}
	public java.lang.String getDkFarm() {
		return this.get("dk_farm");
	}

	public void setDkFarm(java.lang.String dkFarm) {
		this.set("dk_farm", dkFarm);
	}
	public java.lang.String getDkAltitude() {
		return this.get("dk_altitude");
	}

	public void setDkAltitude(java.lang.String dkAltitude) {
		this.set("dk_altitude", dkAltitude);
	}
	public java.lang.String getDkSlope() {
		return this.get("dk_slope");
	}

	public void setDkSlope(java.lang.String dkSlope) {
		this.set("dk_slope", dkSlope);
	}
	public java.lang.String getDkGrowers() {
		return this.get("dk_growers");
	}

	public void setDkGrowers(java.lang.String dkGrowers) {
		this.set("dk_growers", dkGrowers);
	}
	public java.lang.String getDkPhone() {
		return this.get("dk_phone");
	}

	public void setDkPhone(java.lang.String dkPhone) {
		this.set("dk_phone", dkPhone);
	}
	public java.lang.String getDkPerson() {
		return this.get("dk_person");
	}

	public void setDkPerson(java.lang.String dkPerson) {
		this.set("dk_person", dkPerson);
	}
	public java.lang.String getDkFertilizer() {
		return this.get("dk_fertilizer");
	}

	public void setDkFertilizer(java.lang.String dkFertilizer) {
		this.set("dk_fertilizer", dkFertilizer);
	}
	public java.lang.String getDkUsername() {
		return this.get("dk_username");
	}

	public void setDkUsername(java.lang.String dkUsername) {
		this.set("dk_username", dkUsername);
	}
	public java.lang.Integer getDkUserId() {
		return this.get("dk_user_id");
	}

	public void setDkUserId(java.lang.Integer dkUserId) {
		this.set("dk_user_id", dkUserId);
	}
	public java.lang.Integer getDel() {
		return this.get("del");
	}

	public void setDel(java.lang.Integer del) {
		this.set("del", del);
	}
	public java.util.Date getDkTime() {
		return this.get("dk_time");
	}

	public void setDkTime(java.util.Date dkTime) {
		this.set("dk_time", dkTime);
	}
	public java.lang.String getDkType() {
		return this.get("dk_type");
	}

	public void setDkType(java.lang.String dkType) {
		this.set("dk_type", dkType);
	}
	public java.lang.String getDkDensity() {
		return this.get("dk_density");
	}

	public void setDkDensity(java.lang.String dkDensity) {
		this.set("dk_density", dkDensity);
	}
	public java.lang.String getDkIrrigation() {
		return this.get("dk_irrigation");
	}

	public void setDkIrrigation(java.lang.String dkIrrigation) {
		this.set("dk_irrigation", dkIrrigation);
	}
}
