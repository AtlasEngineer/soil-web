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
package com.soli.server.service.impl;

import com.jfinal.kit.Ret;
import com.lambkit.common.service.BaseServiceMock;

import com.soli.server.model.Data;
import com.soli.server.service.DataService;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-11
 * @version 1.0
 * @since 1.0
 */
public class DataServiceMock extends BaseServiceMock<Data> implements DataService {
    @Override
    public Ret searchCalendar(Integer month) {
        return null;
    }

    @Override
    public Ret edit(Integer id) {
        return null;
    }

    @Override
    public Ret search(String name, String type, String[] times, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Ret add(String name, String url, Integer type, Integer directoryid) {
        return null;
    }

    @Override
    public Ret del(Integer[] ids) {
        return null;
    }
}