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

import com.beust.jcommander.StringKey;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.soli.server.model.Data;
import com.soli.server.service.DirectoryService;
import com.soli.server.model.Directory;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;

import java.util.Date;
import java.util.List;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-19
 * @since 1.0
 */
public class DirectoryServiceImpl extends LambkitModelServiceImpl<Directory> implements DirectoryService {

    private Directory DAO = null;

    public Directory dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(Directory.class);
        }
        return DAO;
    }

    @Override
    public Ret updateDataDirectory(Integer directory_id, Integer data_id) {
        if (data_id == null) {
            return Ret.fail("errorMsg", "请选择编辑的数据");
        }
        if (directory_id == null) {
            return Ret.fail("errorMsg", "请选择数据所属目录");
        }
        Data data = Data.service().dao().findById(data_id);
        if (data != null) {
			data.setDirectoryid(directory_id);
			data.update();
			return Ret.ok("msg", "修改成功");
        }else{
			return Ret.fail("errorMsg", "该数据不存在");
		}
    }

    @Override
    public Ret add(Integer level, String name, Integer parent_id) {
		if (level == null) {
			return Ret.fail("errorMsg", "请选择目录级别");
		}
		if (parent_id == null) {
			return Ret.fail("errorMsg", "请选择父目录");
		}
		if (StringUtils.isBlank(name)) {
			return Ret.fail("errorMsg", "请输入目录名称");
		}
		Directory directory = new Directory();
		directory.setDel("0");
		directory.setName(name);
		directory.setCreateTime(new Date());
		directory.setLevel(level);
		directory.setParentId(parent_id);
		directory.save();
		return Ret.ok("msg", "添加成功");
    }

    /**
     * 获取指定层级目录列表
     *
     * @param name
     * @param level
     * @return
     */
    @Override
    public Ret getDirectoryList(String name, Integer level) {

        String sql = "SELECT * FROM tr_directory WHERE level = " + level;
        if (StringUtils.isNotBlank(name)) {
            sql += " AND name like '%" + name + "%'";
        }
        List<Record> records = Db.find(sql);
        return Ret.ok("data", records);
    }

    /**
     * 获得指定父目录id下子目录列表
     *
     * @param name
     * @param parentId
     * @return
     */
    @Override
    public Ret getSubDirectoriesList(String name, Integer parentId) {
        String sql = "SELECT * FROM tr_directory WHERE parent_id = " + parentId;
        if (StringUtils.isNotBlank(name)) {
            sql += " AND name like '%" + name + "%'";
        }
        List<Record> records = Db.find(sql);
        return Ret.ok("data", records);
    }


    @Override
    public Ret delDirectories(Integer id) {
        if (id == null) {
            return Ret.fail("errorMsg", "请选择正确的目录");
        }
        Record tr_directory = Db.findById("tr_directory", id);
        if (tr_directory != null) {
            Integer level = tr_directory.getInt("level");
            String sql = "";
            if (level == 1) {
                sql = "DELETE FROM tr_directory WHERE id IN ( " +
                        " SELECT id FROM tr_directory WHERE parent_id IN ( SELECT ID FROM tr_directory WHERE parent_id = " + id + " ) " +
                        " UNION " +
                        " ( SELECT id FROM tr_directory WHERE parent_id = " + id + " ) " +
                        ")";
            } else if (level == 2) {
                sql = "DELETE FROM tr_directory WHERE id IN ( " +
                        " SELECT id FROM tr_directory WHERE parent_id IN ( SELECT ID FROM tr_directory WHERE parent_id = " + id + " ) " +
                        ")";
            } else {
                sql = "DELETE FROM tr_directory WHERE id = " + id;
            }
            int update = Db.update(sql);
        }
        return Ret.ok("data", "success");
    }

    @Override
    public Ret addDirectories(String level1, String level2, String level3) {
        if (level1 == null) {
            return Ret.fail("errorMsg", "请选择一级目录");
        }
        Integer id1 = 0;
        Directory first1 = Directory.service().dao().findFirst(Directory.sql().andNameEqualTo(level1).andLevelEqualTo(1).example());
        if (first1 == null) {
            Directory directory = new Directory();
            directory.setParentId(0);
            directory.setLevel(1);
            directory.setName(level1);
            directory.setCreateTime(new Date());
            directory.setDel("0");
            directory.save();
            id1 = directory.getId();
        } else {
            id1 = first1.getId();
        }
        if (level2 != null) {
            Integer id2 = 0;
            Directory first2 = Directory.service().dao().findFirst(Directory.sql().andNameEqualTo(level2).andParentIdEqualTo(id1).example());
            if (first2 == null) {
                Directory directory = new Directory();
                directory.setParentId(id1);
                directory.setLevel(2);
                directory.setName(level2);
                directory.setCreateTime(new Date());
                directory.setDel("0");
                directory.save();
                id2 = directory.getId();
            } else {
                id2 = first2.getId();
            }
            if (level3 != null) {
                Directory first3 = Directory.service().dao().findFirst(Directory.sql().andNameEqualTo(level3).andParentIdEqualTo(id2).example());
                if (first3 == null) {
                    Directory directory = new Directory();
                    directory.setParentId(id2);
                    directory.setLevel(3);
                    directory.setName(level3);
                    directory.setCreateTime(new Date());
                    directory.setDel("0");
                    directory.save();
                    return Ret.ok("msg", "添加成功");
                } else {
                    return Ret.ok("msg", "该目录已存在");
                }
            } else {
                return Ret.ok("msg", "添加成功");
            }
        } else {
            return Ret.ok("msg", "添加成功");
        }
    }
}
