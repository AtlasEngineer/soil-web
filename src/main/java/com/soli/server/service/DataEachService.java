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

import com.jfinal.kit.Ret;
import com.lambkit.common.service.LambkitService;

import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;
import com.soli.server.model.DataEach;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public interface DataEachService extends LambkitService<DataEach> {

    /**
     * 获取年份列表
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.getYears",useLogin = false)
    public Ret getYears();

    /**
     * 查询指定地块的，一年的积温积雨统计
     * @param id
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.searchAccumulatedAndEroded",useLogin = false)
    public Ret searchAccumulatedAndEroded(Integer id,Integer year);

    /**
     * 查询指定地块的，一年的温度湿度统计
     * @param id
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.searchJwAndJy",useLogin = false)
    public Ret searchJwAndJy(Integer id,Integer year,Integer type);

    /**
     * 存取新添地块的所有时间的温度和湿度
     * @param id
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "data.updateTkTiff",useLogin = false)
    public Ret updateTkTiff(Integer id);

}
