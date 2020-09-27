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
package com.soli.server.model;

import com.jfinal.kit.StrKit;
import com.lambkit.Lambkit;
import com.lambkit.common.service.ServiceKit;
import com.lambkit.db.sql.column.Column;
import com.soli.server.MschConfig;
import com.soli.server.service.DataEachService;
import com.soli.server.service.impl.DataEachServiceImpl;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public class DataEach extends com.atlas.landa.model.base.BaseDataEach<DataEach> {

	private static final long serialVersionUID = 1L;
	
	public static DataEachService service() {
		return ServiceKit.inject(DataEachService.class, DataEachServiceImpl.class);
	}
	
	public static com.atlas.landa.model.sql.DataEachCriteria sql() {
		return new com.atlas.landa.model.sql.DataEachCriteria();
	}
	
	public static com.atlas.landa.model.sql.DataEachCriteria sql(Column column) {
		com.atlas.landa.model.sql.DataEachCriteria that = new com.atlas.landa.model.sql.DataEachCriteria();
		that.add(column);
        return that;
    }

	public DataEach() {
		MschConfig config = Lambkit.config(MschConfig.class);
		String dbconfig = config.getDbconfig();
		if(StrKit.notBlank(dbconfig)) {
			this.use(dbconfig);
		}
	}
}
