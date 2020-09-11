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
    public LambkitModel dao() { return null; }

    /**
     * 获取指定层级目录列表
     * @param name
     * @param lever
     * @return
     */
    @Override
    public Ret getDirectoryList(String name, Integer lever) {

        String sql = "SELECT * FROM tr_directory WHERE lever = " + lever ;
        if (StringUtils.isNotBlank(name)){
            sql += " AND name like '%"+name+"%'";
        }
        List<Record> records = Db.find( sql );
        return Ret.ok("data",records);
    }

    /**
     * 获得指定父目录id下子目录列表
     * @param name
     * @param parentId
     * @return
     */
    @Override
    public Ret getSubDirectoriesList(String name, Integer parentId) {
        String sql = "SELECT * FROM tr_directory WHERE parent_id = " + parentId ;
        if (StringUtils.isNotBlank(name)){
            sql += " AND name like '%"+name+"%'";
        }
        List<Record> records = Db.find( sql );
        return Ret.ok("data",records);
    }


    @Override
    public Ret delDirectories(Integer id) {
        Record tr_directory = Db.findById("tr_directory", id);
        if (tr_directory != null ){
            Integer level = tr_directory.getInt("level");
            String sql = "";
            if (level == 1){
                sql =   "DELETE FROM tr_directory WHERE id IN ( " +
                        " SELECT id FROM tr_directory WHERE parent_id IN ( SELECT ID FROM tr_directory WHERE parent_id = "+ id +" ) " +
                        " UNION " +
                        " ( SELECT id FROM tr_directory WHERE parent_id = "+ id +" ) "+
                        ")";
            }else if (level == 2){
                sql =   "DELETE FROM tr_directory WHERE id IN ( " +
                        " SELECT id FROM tr_directory WHERE parent_id IN ( SELECT ID FROM tr_directory WHERE parent_id = "+ id +" ) " +
                        ")";
            }else {
                sql =    "DELETE FROM tr_directory WHERE id = " +id ;
            }
            int update = Db.update(sql);
        }
        return Ret.ok("data","success");
    }

    @Override
    public Ret addDirectories(String name, Integer parentId, Integer level) {

        Record record = new Record();
        record.set("parent_id",parentId);
        record.set("level",level);
        record.set("name",name);
        record.set("create_time",new Date());
        record.set("del",0);
        boolean tr_directory = Db.save("tr_directory", record);
        if (tr_directory){
            return Ret.ok("data","success");
        }
        else{
            return Ret.fail("data","error");
        }
    }


}
