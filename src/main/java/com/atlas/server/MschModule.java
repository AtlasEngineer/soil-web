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
package com.atlas.server;

import com.jfinal.config.Routes;
import com.jfinal.kit.StrKit;
import com.lambkit.Lambkit;
import com.lambkit.common.service.ServiceManager;
import com.lambkit.core.rpc.RpcConfig;
import com.lambkit.db.datasource.ActiveRecordPluginWrapper;
import com.lambkit.module.LambkitModule;

import com.atlas.server.MschConfig;
import com.atlas.server.model.BotanyType;
import com.atlas.server.model.Catalogue;
import com.atlas.server.model.CatalogueKeep;
import com.atlas.server.model.CatalogueSample;
import com.atlas.server.service.BotanyTypeService;
import com.atlas.server.service.CatalogueService;
import com.atlas.server.service.CatalogueKeepService;
import com.atlas.server.service.CatalogueSampleService;
import com.atlas.server.service.impl.BotanyTypeServiceImpl;
import com.atlas.server.service.impl.BotanyTypeServiceMock;
import com.atlas.server.service.impl.CatalogueServiceImpl;
import com.atlas.server.service.impl.CatalogueServiceMock;
import com.atlas.server.service.impl.CatalogueKeepServiceImpl;
import com.atlas.server.service.impl.CatalogueKeepServiceMock;
import com.atlas.server.service.impl.CatalogueSampleServiceImpl;
import com.atlas.server.service.impl.CatalogueSampleServiceMock;
import com.atlas.server.web.tag.BotanyTypeMarker;
import com.atlas.server.web.tag.CatalogueMarker;
import com.atlas.server.web.tag.CatalogueKeepMarker;
import com.atlas.server.web.tag.CatalogueSampleMarker;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
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
		arp.addMapping("at_botany_type", "id", BotanyType.class);
		arp.addMapping("catalogue", "c_id", Catalogue.class);
		arp.addMapping("catalogue_keep", "id", CatalogueKeep.class);
		arp.addMapping("catalogue_sample", "id", CatalogueSample.class);
	}

	public void addTag(LambkitModule lk) {
		lk.addTag("botanyType", new BotanyTypeMarker());
		lk.addTag("catalogue", new CatalogueMarker());
		lk.addTag("catalogueKeep", new CatalogueKeepMarker());
		lk.addTag("catalogueSample", new CatalogueSampleMarker());
	}

	public void registerLocalService() {
		registerLocalService(getRpcGroup(), getRpcVersion(), getRpcPort());
	}

	public void registerLocalService(String group, String version, int port) {
		ServiceManager.me().mapping(BotanyTypeService.class, BotanyTypeServiceImpl.class, BotanyTypeServiceMock.class, group, version, port);
		ServiceManager.me().mapping(CatalogueService.class, CatalogueServiceImpl.class, CatalogueServiceMock.class, group, version, port);
		ServiceManager.me().mapping(CatalogueKeepService.class, CatalogueKeepServiceImpl.class, CatalogueKeepServiceMock.class, group, version, port);
		ServiceManager.me().mapping(CatalogueSampleService.class, CatalogueSampleServiceImpl.class, CatalogueSampleServiceMock.class, group, version, port);
	}

	public void registerRemoteService() {
		registerRemoteService(getRpcGroup(), getRpcVersion(), getRpcPort());
	}

	public void registerRemoteService(String group, String version, int port) {
		ServiceManager.me().remote(BotanyTypeService.class, BotanyTypeServiceMock.class, group, version, port);
		ServiceManager.me().remote(CatalogueService.class, CatalogueServiceMock.class, group, version, port);
		ServiceManager.me().remote(CatalogueKeepService.class, CatalogueKeepServiceMock.class, group, version, port);
		ServiceManager.me().remote(CatalogueSampleService.class, CatalogueSampleServiceMock.class, group, version, port);
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

