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
package com.atlas.server.web.validator;

import com.jfinal.core.Controller;
import com.lambkit.web.validator.LambkitValidator;

import com.atlas.server.model.Integral;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-03
 * @version 1.0
 * @since 1.0
 */
public class IntegralValidator extends LambkitValidator {

	@Override
	protected String getTableName(Controller c) {
		return Integral.service().getTableName();
	}
	
	@Override
	protected String getPrefix() {
		return "model";//StrKit.firstCharToLowerCase(Integral.class.getSimpleName());
	}
}
