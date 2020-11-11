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

/**
 * @author ww
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-11-11
 * @version 1.0
 * @since 1.0
 */
public interface ProductService extends LambkitService {

    /**
     * 获取作物及物候列表
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "product.list",useLogin = false)
    public Ret getProductList(Integer pagenum,Integer pagesize,String name);
    /**
     * 获取作物及物候详情
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "product.getById",useLogin = false)
    public Ret getProductById(Integer id);

    /**
     * 获取作物及物候详情
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "product.add",useLogin = false)
    public Ret addProduct(String name,String phenology);

    /**
     * 编辑作物及物候详情
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "product.update",useLogin = false)
    public Ret updateProduct(Integer id,String name,String phenology);

    /**
     * 删除作物及物候详情
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "product.del",useLogin = false)
    public Ret delProductByIds(String ids);

    /**
     * 编辑作物及物候详情
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "product.getList",useLogin = false)
    public Ret getPhenologyListByName(String name);

}
