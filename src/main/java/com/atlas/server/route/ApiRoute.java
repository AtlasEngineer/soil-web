package com.atlas.server.route;

import com.atlas.server.controller.*;
import com.jfinal.config.Routes;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;

/**
 * @author: xuzhonghao
 * @create: 2019-09-25 10:00
 */
public class ApiRoute extends Routes {
    @Override
    public void config() {
        add("/", IndexController.class,"/WEB-INF/app");
        add("/user", UserController.class,"/WEB-INF/app");
        addInterceptor(new JwtTokenInterceptor());
    }
}
