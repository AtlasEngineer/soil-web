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
package com.atlas.server.service.impl;

import com.atlas.server.model.InsectPests;
import com.atlas.server.model.InsectSpecies;
import com.jfinal.plugin.activerecord.Page;
import com.lambkit.common.service.BaseServiceMock;

import com.atlas.server.model.Catalogue;
import com.atlas.server.service.CatalogueService;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public class CatalogueServiceMock extends BaseServiceMock<Catalogue> implements CatalogueService {


    @Override
    public Page all(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<InsectPests> all(Integer id) {
        return null;
    }

    @Override
    public InsectPests searchInsectPestsById(Integer id) {
        return null;
    }
}