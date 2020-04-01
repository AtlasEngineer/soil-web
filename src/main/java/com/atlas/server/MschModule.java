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
import com.atlas.server.model.AtBotany;
import com.atlas.server.model.AtBotanyType;
import com.atlas.server.model.Catalogue;
import com.atlas.server.model.CatalogueSample;
import com.atlas.server.service.AtBotanyService;
import com.atlas.server.service.AtBotanyTypeService;
import com.atlas.server.service.CatalogueService;
import com.atlas.server.service.CatalogueSampleService;
import com.atlas.server.service.impl.AtBotanyServiceImpl;
import com.atlas.server.service.impl.AtBotanyServiceMock;
import com.atlas.server.service.impl.AtBotanyTypeServiceImpl;
import com.atlas.server.service.impl.AtBotanyTypeServiceMock;
import com.atlas.server.service.impl.CatalogueServiceImpl;
import com.atlas.server.service.impl.CatalogueServiceMock;
import com.atlas.server.service.impl.CatalogueSampleServiceImpl;
import com.atlas.server.service.impl.CatalogueSampleServiceMock;
import com.atlas.server.web.tag.AtBotanyDirective;
import com.atlas.server.web.tag.AtBotanyTypeDirective;
import com.atlas.server.web.tag.CatalogueDirective;
import com.atlas.server.web.tag.CatalogueSampleDirective;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-01
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
		if("server".equals(getConfig().getDbconfig())) {
			registerLocalService();
		} else if("client".equals(getConfig().getDbconfig())) {
			registerRemoteService();
		} 
	}
	
	public void mapping(ActiveRecordPluginWrapper arp) {
		arp.addMapping("at_botany", "id", AtBotany.class);
		arp.addMapping("at_botany_type", "id", AtBotanyType.class);
		arp.addMapping("catalogue", "c_id", Catalogue.class);
		arp.addMapping("catalogue_sample", "id", CatalogueSample.class);
	}
	
	public void addTag(LambkitModule lk) {
		lk.addTag("atBotany", new AtBotanyDirective());
		lk.addTag("atBotanyType", new AtBotanyTypeDirective());
		lk.addTag("catalogue", new CatalogueDirective());
		lk.addTag("catalogueSample", new CatalogueSampleDirective());
	}
			
	public void registerLocalService() {
		registerLocalService(getRpcGroup(), getRpcVersion(), getRpcPort());
	}
	
	public void registerLocalService(String group, String version, int port) {
		ServiceManager.me().mapping(AtBotanyService.class, AtBotanyServiceImpl.class, AtBotanyServiceMock.class, group, version, port);
		ServiceManager.me().mapping(AtBotanyTypeService.class, AtBotanyTypeServiceImpl.class, AtBotanyTypeServiceMock.class, group, version, port);
		ServiceManager.me().mapping(CatalogueService.class, CatalogueServiceImpl.class, CatalogueServiceMock.class, group, version, port);
		ServiceManager.me().mapping(CatalogueSampleService.class, CatalogueSampleServiceImpl.class, CatalogueSampleServiceMock.class, group, version, port);
	}
	
	public void registerRemoteService() {
		registerRemoteService(getRpcGroup(), getRpcVersion(), getRpcPort());
	}
	
	public void registerRemoteService(String group, String version, int port) {
		ServiceManager.me().remote(AtBotanyService.class, AtBotanyServiceMock.class, group, version, port);
		ServiceManager.me().remote(AtBotanyTypeService.class, AtBotanyTypeServiceMock.class, group, version, port);
		ServiceManager.me().remote(CatalogueService.class, CatalogueServiceMock.class, group, version, port);
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

