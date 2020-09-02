package com.soli.lambkit.interceptor;

import com.jfinal.kit.Ret;
import com.lambkit.core.api.route.ApiInterceptor;
import com.lambkit.core.api.route.ApiInvocation;

public class UnknownSessionInterceptor implements ApiInterceptor {

    @Override
    public void intercept(ApiInvocation apiInvocation) {
        try {
            apiInvocation.invoke();
        }catch (org.apache.shiro.session.UnknownSessionException e){
            System.out.println("获取 session 报错， 提示信息为:");
                System.out.println("****" + e.toString());
            apiInvocation.setReturnValue(Ret.fail("errorMsg", "session异常,请重新登录"));
        }
    }
}
