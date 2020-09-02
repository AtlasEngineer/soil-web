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
package com.soli.server;

import com.jfinal.config.Routes;
import com.jfinal.kit.StrKit;
import com.lambkit.Lambkit;
import com.lambkit.common.service.ServiceManager;
import com.lambkit.core.rpc.RpcConfig;
import com.lambkit.db.datasource.ActiveRecordPluginWrapper;
import com.lambkit.module.LambkitModule;

import com.soli.server.MschConfig;
import com.soli.server.model.Geolist;
import com.soli.server.service.GeolistService;
import com.soli.server.service.impl.GeolistServiceImpl;
import com.soli.server.service.impl.GeolistServiceMock;
import com.soli.server.web.tag.GeolistMarker;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-08-28
 * @version 1.0
 * @since 1.0
 */
public class MschModule extends LambkitModule  {

	@Override
	public void configMapping(ActiveRecordPluginWrapper arp) {
		if(StrKit.isBlank(getConfig().getDbconfig())) {
			mapping(arp);
		}
	}

	@Override
	public void configMapping(String name, ActiveRecordPluginWrapper arp) {
		super.configMapping(name, arp);
		if(StrKit.notBlank(name) && name.equals(getConfig().getDbconfig())) {
			mapping(arp);
		}
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		addTag(this);
		if("server".equals(getConfig().getServerType())) {
			registerLocalService();
		} else if("client".equals(getConfig().getServerType())) {
			registerRemoteService();
		}
	}

	public void mapping(ActiveRecordPluginWrapper arp) {
		arp.addMapping("tr_geolist", "id", Geolist.class);
	}

	public void addTag(LambkitModule lk) {
		lk.addTag("geolist", new GeolistMarker());
	}

	public void registerLocalService() {
		registerLocalService(getRpcGroup(), getRpcVersion(), getRpcPort());
	}

	public void registerLocalService(String group, String version, int port) {
		ServiceManager.me().mapping(GeolistService.class, GeolistServiceImpl.class, GeolistServiceMock.class, group, version, port);
	}

	public void registerRemoteService() {
		registerRemoteService(getRpcGroup(), getRpcVersion(), getRpcPort());
	}

	public void registerRemoteService(String group, String version, int port) {
		ServiceManager.me().remote(GeolistService.class, GeolistServiceMock.class, group, version, port);
	}

	public int getRpcPort() {
		return Lambkit.config(RpcConfig.class).getDefaultPort();
	}
	public String getRpcGroup() {
		return Lambkit.config(RpcConfig.class).getDefaultGroup();
	}
	public String getRpcVersion() {
		return getConfig().getVersion();
	}
	public MschConfig getConfig() {
		return Lambkit.config(MschConfig.class);
	}
}

