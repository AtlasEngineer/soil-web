package com.soli.server.service;

import com.jfinal.kit.Ret;
import com.lambkit.common.service.LambkitService;
import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;

public interface QueryShapeService extends LambkitService {

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "queryShp.getDirectoryList",useLogin = false)
    public Ret getDirectoryList(String name, Integer lever);

}
