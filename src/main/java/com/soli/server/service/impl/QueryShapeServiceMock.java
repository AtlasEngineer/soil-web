package com.soli.server.service.impl;

import com.jfinal.kit.Ret;
import com.lambkit.common.model.LambkitModel;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.soli.server.service.QueryShapeService;

public class QueryShapeServiceMock extends LambkitModelServiceImpl implements QueryShapeService {
    @Override
    public LambkitModel dao() {
        return null;
    }


    @Override
    public Ret getDirectoryList(String name, Integer lever) {
        return null;
    }
}
