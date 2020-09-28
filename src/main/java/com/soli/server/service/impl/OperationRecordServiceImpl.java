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
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.soli.server.model.OperationRecordImg;
import com.soli.server.service.OperationRecordService;
import com.soli.server.model.OperationRecord;
import com.soli.server.utils.Co;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @since 1.0
 */
public class OperationRecordServiceImpl extends LambkitModelServiceImpl<OperationRecord> implements OperationRecordService {

    private OperationRecord DAO = null;

    @Override
	public OperationRecord dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(OperationRecord.class);
        }
        return DAO;
    }

    @Override
    public Ret all(Integer pageNum, Integer pageSize, String name, String type, String username, List<String> time) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("from tr_operation_record where del=0 ");

        if (StringUtils.isNotBlank(name)) {
            stringBuffer.append(" and name like '%" + name + "%'");
        }
        if (StringUtils.isNotBlank(username)) {
            stringBuffer.append(" and username like '%" + username + "%'");
        }
        if (StringUtils.isNotBlank(type)) {
            stringBuffer.append(" and type like '%" + type + "%'");
        }

        if (time != null && time.size() >= 0) {
            if (time.get(0).equals(time.get(1))) {
                stringBuffer.append(" and date(time) between '" + time.get(0) + "' and '" + time.get(0) + "'");
            } else {
                stringBuffer.append(" and date(time) between '" + time.get(0) + "' and '" + time.get(1) + "'");
            }
        }
        stringBuffer.append(" order by time desc");
        Page<Record> page = Db.paginate(pageNum, pageSize, "select *", stringBuffer.toString());
        for (Record record:page.getList()){
            List<OperationRecordImg> imgs=OperationRecordImg.service().find(OperationRecordImg.sql().andOperationIdEqualTo(record.getInt("id")).example());
            record.set("imgs",imgs);
        }
        return Ret.ok("data", page);
    }

    @Override
    public Ret all(Integer[] id) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            OperationRecord operationRecord = OperationRecord.service().dao().findById(id[i]);
            operationRecord.setDel(1);
            boolean result = operationRecord.update();
            if (result) {
                list.add(id[i]);
            }

        }
        if (list.size() > 0) {
            return  Ret.ok("errorMsg", "刪除成功");
        } else {
            return Ret.fail("errorMsg", "刪除失败");
        }
    }

}
