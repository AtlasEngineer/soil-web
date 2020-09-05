package com.soli.server.service;

import com.jfinal.kit.Ret;
import com.lambkit.common.service.LambkitService;
import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;

public interface DirectoryService extends LambkitService {

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.getDirectoryList",useLogin = false)
    public Ret getDirectoryList(String name,Integer lever);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.getSubDirectoriesList",useLogin = false)
    public Ret getSubDirectoriesList(String name,Integer parentId);

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.addDirectories",useLogin = false)
    public Ret addDirectories(String name,Integer parentId,Integer level);


    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "directory.delDirectories",useLogin = false)
    public Ret delDirectories(Integer id);
}
