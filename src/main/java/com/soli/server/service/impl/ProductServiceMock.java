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
import com.lambkit.common.model.LambkitModel;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.soli.server.service.ProductService;

/**
 * @author ww
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-11-11
 * @version 1.0
 * @since 1.0
 */
public class ProductServiceMock extends LambkitModelServiceImpl implements ProductService {


	@Override
	public LambkitModel dao() {
		return null;
	}

	@Override
	public Ret getProductList(Integer pagenum, Integer pagesize, String name) {
		return null;
	}

	@Override
	public Ret getProductById(Integer id) {
		return null;
	}

	@Override
	public Ret updateProduct(Integer id, String name, String phenology) {
		return null;
	}

	@Override
	public Ret delProductByIds(String ids) {
		return null;
	}

	@Override
	public Ret getPhenologyListByName(String name) {
		return null;
	}

}
