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
import com.soli.server.model.base.BaseTiankuai;
import com.soli.server.model.sql.TiankuaiCriteria;
import com.soli.server.service.TiankuaiService;
import com.soli.server.service.impl.TiankuaiServiceImpl;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public class Tiankuai extends BaseTiankuai<Tiankuai> {

	private static final long serialVersionUID = 1L;
	
	public static TiankuaiService service() {
		return ServiceKit.inject(TiankuaiService.class, TiankuaiServiceImpl.class);
	}
	
	public static TiankuaiCriteria sql() {
		return new TiankuaiCriteria();
	}
	
	public static TiankuaiCriteria sql(Column column) {
		TiankuaiCriteria that = new TiankuaiCriteria();
		that.add(column);
        return that;
    }

	public Tiankuai() {
		MschConfig config = Lambkit.config(MschConfig.class);
		String dbconfig = config.getDbconfig();
		if(StrKit.notBlank(dbconfig)) {
			this.use(dbconfig);
		}
	}
}
