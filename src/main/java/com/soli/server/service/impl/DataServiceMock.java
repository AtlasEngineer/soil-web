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

import com.alibaba.fastjson.JSONObject;
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
    public Ret getTkRemoteData(Integer id) {
        return null;
    }
    @Override
    public Ret getLastData(Integer id) {
        return null;
    }

    @Override
    public Ret getExcelDateNames(Integer id) {
        return null;
    }

    @Override
    public Ret deleteExcelDate(Integer id, Integer[] ids) {
        return null;
    }

    @Override
    public Ret deleteEachDate(Integer[] ids) {
        return null;
    }

    @Override
    public Ret getExcelDate(Integer id, Integer pageNum, Integer pageSize, String name, String address, String[] times) {
        return null;
    }

    public Ret getEach(Integer id) {
        return null;
    }

    @Override
    public Ret getTkAllCenter(String type) {
        return null;
    }

    @Override
    public Ret getTkCenterAndJson(Integer id) {
        return null;
    }

    @Override
    public Ret searchSoilAttribute(Integer id) {
        return null;
    }

    @Override
    public Ret searchTkByType() {
        return null;
    }

    @Override
    public Ret addFeatureForShp(Integer id, JSONObject json,String latlons) {
        return null;
    }

    @Override
    public Ret searchCalendar(Integer month) {
        return null;
    }

    @Override
    public Ret edit(Integer id) {
        return null;
    }

    @Override
    public Ret search(String name, Integer type, String[] times, Integer directoryid, Integer pageNum, Integer pageSize) {
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