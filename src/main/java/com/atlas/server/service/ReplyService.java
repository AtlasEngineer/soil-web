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

import com.atlas.server.model.Answer;
import com.jfinal.plugin.activerecord.Page;
import com.lambkit.common.service.LambkitService;

import com.atlas.server.model.Reply;
import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
public interface ReplyService extends LambkitService<Reply> {

    //增加回复
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "add.reply")
    public Reply addReply(Reply reply);


    //找到对某个评论的回复
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "reply.allByTid")
    public List<Reply> allByTid(Integer tid);


    //找到对某个回复的所有回复
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "reply.allByRid")
    public List<Reply> allByRid(Integer rid);



}
