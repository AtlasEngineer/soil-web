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
import com.soli.server.model.base.BaseOperationRecordImg;
import com.soli.server.model.sql.OperationRecordImgCriteria;
import com.soli.server.service.OperationRecordImgService;
import com.soli.server.service.impl.OperationRecordImgServiceImpl;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public class OperationRecordImg extends BaseOperationRecordImg<OperationRecordImg> {

	private static final long serialVersionUID = 1L;
	
	public static OperationRecordImgService service() {
		return ServiceKit.inject(OperationRecordImgService.class, OperationRecordImgServiceImpl.class);
	}
	
	public static OperationRecordImgCriteria sql() {
		return new OperationRecordImgCriteria();
	}
	
	public static OperationRecordImgCriteria sql(Column column) {
		OperationRecordImgCriteria that = new OperationRecordImgCriteria();
		that.add(column);
        return that;
    }

	public OperationRecordImg() {
		MschConfig config = Lambkit.config(MschConfig.class);
		String dbconfig = config.getDbconfig();
		if(StrKit.notBlank(dbconfig)) {
			this.use(dbconfig);
		}
	}
}