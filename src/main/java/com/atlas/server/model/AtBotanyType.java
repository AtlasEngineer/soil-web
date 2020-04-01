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
package com.atlas.server.model;

import com.jfinal.kit.StrKit;
import com.lambkit.Lambkit;
import com.lambkit.common.service.ServiceKit;
import com.lambkit.db.sql.column.Column;
import com.atlas.server.MschConfig;
import com.atlas.server.model.base.BaseAtBotanyType;
import com.atlas.server.model.sql.AtBotanyTypeCriteria;
import com.atlas.server.service.AtBotanyTypeService;
import com.atlas.server.service.impl.AtBotanyTypeServiceImpl;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-01
 * @version 1.0
 * @since 1.0
 */
public class AtBotanyType extends BaseAtBotanyType<AtBotanyType> {

	private static final long serialVersionUID = 1L;
	
	public static AtBotanyTypeService service() {
		return ServiceKit.inject(AtBotanyTypeService.class, AtBotanyTypeServiceImpl.class);
	}
	
	public static AtBotanyTypeCriteria sql() {
		return new AtBotanyTypeCriteria();
	}
	
	public static AtBotanyTypeCriteria sql(Column column) {
		AtBotanyTypeCriteria that = new AtBotanyTypeCriteria();
		that.add(column);
        return that;
    }

	public AtBotanyType() {
		MschConfig config = Lambkit.config(MschConfig.class);
		String dbconfig = config.getDbconfig();
		if(StrKit.notBlank(dbconfig)) {
			this.use(dbconfig);
		}
	}
}
