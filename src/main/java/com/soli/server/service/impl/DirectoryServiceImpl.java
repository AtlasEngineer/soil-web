package com.soli.server.service.impl;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.model.LambkitModel;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.soli.server.service.DirectoryService;

import java.util.Date;
import java.util.List;

public class DirectoryServiceImpl extends LambkitModelServiceImpl implements DirectoryService {

    @Override
    public LambkitModel dao() {
        return null;
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
        Record first1 = Db.findFirst("select * from tr_directory where name = ? ", level1);
        if (first1 == null) {
            Record record = new Record();
            record.set("parent_id", 0);
            record.set("level", 1);
            record.set("name", level1);
            record.set("create_time", new Date());
            record.set("del", 0);
            Db.save("tr_directory", record);
            id1 = record.getInt("id");
        } else {
            id1 = first1.getInt("id");
        }
        if (level2 != null) {
            Integer id2 = 0;
            Record first2 = Db.findFirst("select * from tr_directory where name = ? and  parent_id = ? ", level2, id1);
            if (first1 == null) {
                Record record = new Record();
                record.set("parent_id", id1);
                record.set("level", 2);
                record.set("name", level2);
                record.set("create_time", new Date());
                record.set("del", 0);
                Db.save("tr_directory", record);
                id2 = record.getInt("id");
            } else {
                id2 = first2.getInt("id");
            }
            if (level3 != null) {
                Record first3 = Db.findFirst("select * from tr_directory where name = ? and  parent_id = ? ", level3, id2);
                if (first3 == null) {
                    Record record = new Record();
                    record.set("parent_id", id2);
                    record.set("level", 3);
                    record.set("name", level3);
                    record.set("create_time", new Date());
                    record.set("del", 0);
                    Db.save("tr_directory", record);
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
