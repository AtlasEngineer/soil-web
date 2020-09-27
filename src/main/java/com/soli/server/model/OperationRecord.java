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
import com.soli.server.model.base.BaseOperationRecord;
import com.soli.server.model.sql.OperationRecordCriteria;
import com.soli.server.service.OperationRecordService;
import com.soli.server.service.impl.OperationRecordServiceImpl;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public class OperationRecord extends BaseOperationRecord<OperationRecord> {

	private static final long serialVersionUID = 1L;
	
	public static OperationRecordService service() {
		return ServiceKit.inject(OperationRecordService.class, OperationRecordServiceImpl.class);
	}
	
	public static OperationRecordCriteria sql() {
		return new OperationRecordCriteria();
	}
	
	public static OperationRecordCriteria sql(Column column) {
		OperationRecordCriteria that = new OperationRecordCriteria();
		that.add(column);
        return that;
    }

	public OperationRecord() {
		MschConfig config = Lambkit.config(MschConfig.class);
		String dbconfig = config.getDbconfig();
		if(StrKit.notBlank(dbconfig)) {
			this.use(dbconfig);
		}
	}
}
