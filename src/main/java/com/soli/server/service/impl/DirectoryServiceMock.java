package com.soli.server.service.impl;

import com.jfinal.kit.Ret;
import com.lambkit.common.model.LambkitModel;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.soli.server.service.DirectoryService;

public class DirectoryServiceMock extends LambkitModelServiceImpl implements DirectoryService {
    @Override
    public LambkitModel dao() {
        return null;
    }


    @Override
    public Ret getDirectoryList(String name, Integer level) {
        return null;
    }

    @Override
    public Ret getSubDirectoriesList(String name, Integer parentId) {
        return null;
    }

    @Override
    public Ret addDirectories(String level1, String level2, String level3) {
        return null;
    }


    @Override
    public Ret delDirectories(Integer id) {
        return null;
    }

}
