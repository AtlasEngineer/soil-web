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
import com.atlas.server.model.base.BaseAnswer;
import com.atlas.server.model.sql.AnswerCriteria;
import com.atlas.server.service.AnswerService;
import com.atlas.server.service.impl.AnswerServiceImpl;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
public class Answer extends BaseAnswer<Answer> {

	private static final long serialVersionUID = 1L;
	
	public static AnswerService service() {
		return ServiceKit.inject(AnswerService.class, AnswerServiceImpl.class);
	}
	
	public static AnswerCriteria sql() {
		return new AnswerCriteria();
	}
	
	public static AnswerCriteria sql(Column column) {
		AnswerCriteria that = new AnswerCriteria();
		that.add(column);
        return that;
    }

	public Answer() {
		MschConfig config = Lambkit.config(MschConfig.class);
		String dbconfig = config.getDbconfig();
		if(StrKit.notBlank(dbconfig)) {
			this.use(dbconfig);
		}
	}
}
