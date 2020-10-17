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
import com.soli.server.model.Tiankuai;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public interface TiankuaiService extends LambkitService<Tiankuai> {

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.camera",useLogin = false)
    public Ret all(Integer id);


    /**
     * 数据查询面查询
     * @param id
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.lanlats",useLogin = false)
    public Ret searchLanlats(String lanlats,Integer id);




}
