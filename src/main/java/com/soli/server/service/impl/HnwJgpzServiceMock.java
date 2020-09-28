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
import com.jfinal.plugin.activerecord.Page;
import com.lambkit.common.service.BaseServiceMock;

import com.lambkit.common.util.StringUtils;
import com.soli.server.model.HnwJgpz;
import com.soli.server.model.sql.HnwJgpzCriteria;
import com.soli.server.service.HnwJgpzService;

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
public class HnwJgpzServiceMock extends BaseServiceMock<HnwJgpz> implements HnwJgpzService {
    @Override
    public Ret hnwjByGroup() {
        return null;
    }

    @Override
    public Ret hnwjByName(String name, Integer pageNum, Integer pageSize, String time,String address,String type){
        HnwJgpzCriteria sql=HnwJgpz.sql();
        if(StringUtils.isBlank(name)){
            return  Ret.fail("errorMsg","类别不能为空");
        }
        Page<HnwJgpz> page=HnwJgpz.service().dao().paginate(pageNum,pageSize,sql.andTagEqualTo(name).example().setOrderBy("time desc"));
        return Ret.ok("data",page);
    }

    @Override
    public Ret hnwfljgByName(String name, Integer pageNum, Integer pageSize, String time,String address,String type) throws ParseException {
        return null;
    }

    @Override
    public Ret hnwnyjgByName(String name, Integer pageNum, Integer pageSize, String time, String address, String type) throws ParseException {
        return null;
    }

    @Override
    public Ret chemical() {
        return null;
    }
}