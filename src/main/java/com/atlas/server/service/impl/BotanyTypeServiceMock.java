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

import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.BaseServiceMock;

import com.atlas.server.model.BotanyType;
import com.atlas.server.service.BotanyTypeService;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public class BotanyTypeServiceMock extends BaseServiceMock<BotanyType> implements BotanyTypeService {
    @Override
    public List<Record> all() {
        return null;
    }

    @Override
    public Record searchNewsById(Integer id,String ip) {
        return null;
    }
}