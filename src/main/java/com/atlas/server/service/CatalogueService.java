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
package com.atlas.server.service;

import com.atlas.server.model.InsectPests;
import com.atlas.server.model.InsectSpecies;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitService;

import com.atlas.server.model.Catalogue;
import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public interface CatalogueService extends LambkitService<Catalogue> {

    //查询所有作物
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "insectSpecies.all")
    public Page all(Integer pageNum, Integer pageSize,String name);


    //查询某个植物所得的病
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "insectPests.all.byId")
    public Page all(Integer id,Integer pageNum, Integer pageSize,Integer type,String name);



    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.insectPestsById")
    public InsectPests insectPestsbyId(Integer id,String token);




}
