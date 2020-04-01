package com.atlas.lambkit.interceptor;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalActionHandler extends Handler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));//允许跨域
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(request.getMethod().equals("OPTIONS")){
            response.setHeader("Access-Control-Max-Age","3600");
            response.setHeader("Access-Control-Allow-Methods","POST,GET");
            response.setHeader("Access-Control-Allow-Headers","token,Access-Control-Allow-Origin,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
            response.setHeader("Cache-Control","no-cache");
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            next.handle(target, request, response, isHandled);
        }
    }
}
