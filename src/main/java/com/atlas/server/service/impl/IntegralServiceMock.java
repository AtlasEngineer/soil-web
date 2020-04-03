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
package com.atlas.server.service.impl;

import com.lambkit.common.service.BaseServiceMock;

import com.atlas.server.model.Integral;
import com.atlas.server.service.IntegralService;

import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-03
 * @version 1.0
 * @since 1.0
 */
public class IntegralServiceMock extends BaseServiceMock<Integral> implements IntegralService {
    @Override
    public boolean addCredit(Long userId, Integer score, String ctype, String action, Integer actid) {
        return false;
    }

    @Override
    public boolean addCredit(Integral integral) {
        return false;
    }

    @Override
    public Integer currentMonthCredit(Long userId, String ctype, String action) {
        return null;
    }

    @Override
    public List<Integral> currentMonthCredits(Long userId, String ctype, String action) {
        return null;
    }
}