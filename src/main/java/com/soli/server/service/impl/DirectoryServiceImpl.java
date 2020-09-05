package com.soli.server.service.impl;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.model.LambkitModel;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.soli.server.service.DirectoryService;

import java.util.List;

public class DirectoryServiceImpl extends LambkitModelServiceImpl implements DirectoryService {

    @Override
    public LambkitModel dao() { return null; }

    @Override
    public Ret getDirectoryList(String name, Integer lever) {

        List<Record> records = Db.find("");
        return Ret.ok("data",records);
    }
}
