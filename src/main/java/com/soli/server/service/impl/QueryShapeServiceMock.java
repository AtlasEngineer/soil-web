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
    public Ret getPoint(Integer id, Double lon, Double lat) {
        return null;
    }
}
