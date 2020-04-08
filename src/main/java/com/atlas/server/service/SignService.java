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

import com.jfinal.kit.Ret;
import com.lambkit.common.service.LambkitService;

import com.atlas.server.model.Sign;

import java.util.List;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-03
 * @since 1.0
 */
public interface SignService extends LambkitService<Sign> {

    /**
     * 当日签到
     * @param userId
     * @param sway
     * @return
     */
    Sign today(Long userId, String sway);

    /**
     * 本月签到
     * @param userId
     * @param sway
     * @return
     */
    List<Sign> currentMonth(Long userId, String sway);
}