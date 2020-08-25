package com.soli.lambkit.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import java.util.logging.Logger;

/**
 * @author: xuzhonghao
 * @create: 2019-08-09 10:46
 */

public class GlobalActionInterceptor implements Interceptor {
    // 一般为当前的类名
    static Logger logger = Logger.getLogger("GlobalActionInterceptor");

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        controller.getResponse().addHeader("Access-Control-Allow-Origin", controller.getRequest().getHeader("Origin"));
        controller.getResponse().addHeader("Access-Control-Allow-Credentials", "true");

        controller.getResponse().addHeader("Access-Control-Max-Age","3600");
        controller.getResponse().addHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
        controller.getResponse().addHeader("Access-Control-Allow-Headers","token,Access-Control-Allow-Origin,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        inv.invoke();
    }
}