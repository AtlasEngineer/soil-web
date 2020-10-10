package com.soli.server.route;

import com.jfinal.config.Routes;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.soli.server.controller.*;

/**
 * @author: xuzhonghao
 * @create: 2019-09-25 10:00
 */
public class ApiRoute extends Routes {
    @Override
    public void config() {
        add("/", IndexController.class,"/WEB-INF/app");
        add("/upload", UploadController.class,"/WEB-INF/app");
        add("/download", DownloadController.class,"/WEB-INF/app");
        add("/weather", WeatherController.class,"/WEB-INF/app");
        add("/region", RegionController.class,"/WEB-INF/app");
        add("/user", UserController.class);
        addInterceptor(new JwtTokenInterceptor());
    }
}
