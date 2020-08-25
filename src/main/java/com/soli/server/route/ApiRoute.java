package com.soli.server.route;

import com.jfinal.config.Routes;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.soli.server.controller.IndexController;
import com.soli.server.controller.UploadController;

/**
 * @author: xuzhonghao
 * @create: 2019-09-25 10:00
 */
public class ApiRoute extends Routes {
    @Override
    public void config() {
        add("/", IndexController.class,"/WEB-INF/app");
        add("/upload", UploadController.class,"/WEB-INF/app");
        addInterceptor(new JwtTokenInterceptor());
    }
}
