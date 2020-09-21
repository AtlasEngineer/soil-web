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
import com.soli.server.model.Directory;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-19
 * @version 1.0
 * @since 1.0
 */
public interface DirectoryService extends LambkitService<Directory> {

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.getDirectoryList",useLogin = false)
    public Ret getDirectoryList(String name, Integer level);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.getSubDirectoriesList",useLogin = false)
    public Ret getSubDirectoriesList(String name,Integer parentId);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.addDirectories",useLogin = false)
    public Ret addDirectories(String level1,String level2,String level3);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.delDirectories",useLogin = false)
    public Ret delDirectories(Integer id);
}
