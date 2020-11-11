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

import com.soli.server.model.DataEach;
import com.soli.server.model.Tiankuai;
import com.soli.server.service.TiankuaiService;
import com.vividsolutions.jts.io.ParseException;
import org.geotools.filter.text.cql2.CQLException;

import java.io.IOException;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @since 1.0
 */
public class TiankuaiServiceMock extends BaseServiceMock<Tiankuai> implements TiankuaiService {
    @Override
    public Ret all(Integer id) {
        return null;
    }

    @Override
    public Ret searchDiseases(String type, String period) {
        return null;
    }

    @Override
    public Ret searchDiseasesAdd(String type, String period, String name, String about, String feature, String way, String condition, String symptom, String grow, String harm, String methon) {
        return null;
    }

    @Override
    public Ret searchDiseasesDel(String type, Integer id) {
        return null;
    }

    @Override
    public Ret compoundQuery(Integer countyId, Integer type, String time[]) {
        return null;
    }

    @Override
    public Ret compoundQueryBySpot(Double longitude, Double latitude) {
        return null;
    }

    @Override
    public Ret compoundQueryByNoodles(String latlons) throws IOException, CQLException, ParseException {
        return null;
    }

    @Override
    public Ret uav() {
        return null;
    }
}