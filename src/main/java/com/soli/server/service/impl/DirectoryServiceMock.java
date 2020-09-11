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
    public Ret getDirectoryList(String name, Integer lever) {
        return null;
    }

    @Override
    public Ret getSubDirectoriesList(String name, Integer parentId) {
        return null;
    }

    @Override
    public Ret addDirectories(String name, Integer parentId, Integer level) {
        return null;
    }

    @Override
    public Ret delDirectories(Integer id) {
        return null;
    }

}
