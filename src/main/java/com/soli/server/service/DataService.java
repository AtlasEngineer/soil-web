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
package com.soli.server.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Ret;
import com.lambkit.common.service.LambkitService;

import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;
import com.soli.server.model.Data;

import java.util.Date;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-11
 * @version 1.0
 * @since 1.0
 */
public interface DataService extends LambkitService<Data> {

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.getLastData",useLogin = false)
    public Ret getLastData(Integer id);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.getExcelDateNames",useLogin = false)
    public Ret getExcelDateNames(Integer id);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.deleteExcelDate",useLogin = false)
    public Ret deleteExcelDate(Integer id,Integer[] ids);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.deleteEachDate",useLogin = false)
    public Ret deleteEachDate(Integer[] ids);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.getExcelDate",useLogin = false)
    public Ret getExcelDate(Integer id,Integer pageNum,Integer pageSize,String name,String address,String[] times);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.getEach",useLogin = false)
    public Ret getEach(Integer id);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.getTkAllCenter",useLogin = false)
    public Ret getTkAllCenter(String type);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.getTkCenterAndJson",useLogin = false)
    public Ret getTkCenterAndJson(Integer id);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.searchSoilAttribute",useLogin = false)
    public Ret searchSoilAttribute(Integer id);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.searchTkByType",useLogin = false)
    public Ret searchTkByType();

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.addFeatureForShp",useLogin = false)
    public Ret addFeatureForShp(Integer id, JSONObject json,String latlons);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.searchCalendar",useLogin = false)
    public Ret searchCalendar(Integer month);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.edit",useLogin = false)
    public Ret edit(Integer id);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.search",useLogin = false)
    public Ret search(String name, Integer type, String[] times,Integer directoryid,Integer pageNum,Integer pageSize);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.add",useLogin = false)
    public Ret add(String name,String url,Integer type,Integer directoryid);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.del",useLogin = false)
    public Ret del(Integer[] ids);

}
