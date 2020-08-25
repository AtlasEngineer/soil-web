package com.soli.server.controller;

import com.jfinal.aop.Clear;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;

@Clear(JwtTokenInterceptor.class)
public class IndexController extends LambkitController {


    @Clear
    public void index() {
        renderText("Hello,土壤");
    }

}



