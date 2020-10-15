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
import com.soli.server.model.HnwJgpz;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-26
 * @version 1.0
 * @since 1.0
 */
public interface HnwJgpzService extends LambkitService<HnwJgpz> {

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.hnwjByGroup",useLogin = false)
    public Ret hnwjByGroup();

    //作物价格
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.hnwjByName",useLogin = false)
    public Ret hnwjByName(String name, Integer pageNum, Integer pageSize, String time,String address,String type) throws ParseException, UnsupportedEncodingException;

    //肥料价格
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.hnwfljgByName",useLogin = false)
    public Ret hnwfljgByName(String name, Integer pageNum, Integer pageSize, String time,String address,String type) throws ParseException;


    //农药价格
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.hnwnyjgByName",useLogin = false)
    public Ret hnwnyjgByName(String name, Integer pageNum, Integer pageSize, String time, String address, String type) throws ParseException;


    //化肥施肥量
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.chemical",useLogin = false)
    public Ret chemical();


    //详情中的作物价格
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.hnwj",useLogin = false)
    public Ret hnwj(String name, Integer pageNum, Integer pageSize,Integer type,Integer id) throws ParseException, UnsupportedEncodingException;


    //详情中的作物种类
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.name",useLogin = false)
    public Ret hnwj(String name) throws ParseException, UnsupportedEncodingException;




}
