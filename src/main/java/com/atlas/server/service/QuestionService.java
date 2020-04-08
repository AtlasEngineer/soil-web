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

import com.jfinal.plugin.activerecord.Page;
import com.lambkit.common.service.LambkitService;

import com.atlas.server.model.Question;
import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public interface QuestionService extends LambkitService<Question> {

    //查询所有问题
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "question.all")
    public Page all(Integer pageNum, Integer pageSize);


    //查询自己所有问题
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "question.allByUser")
    public Page allByUser(Integer pageNum, Integer pageSize);


    //增加问题
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "add.question")
    public boolean addQuestion(Question question);


    //查询单个问题
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.questionById")
    public Question questionById(Integer id);







}
