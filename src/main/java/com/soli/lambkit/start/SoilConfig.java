package com.soli.lambkit.start;


import com.jfinal.config.*;
import com.jfinal.json.JFinalJsonFactory;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.lambkit.LambkitApplicationContext;
import com.lambkit.db.mgr.MgrdbManager;
import com.lambkit.module.LambkitModule;
import com.lambkit.module.upms.jwt.UpmsJwtUserService;
import com.lambkit.module.upms.server.UpmsModule;
import com.lambkit.plugin.jwt.JwtTokenPlugin;
import com.soli.lambkit.interceptor.GlobalActionHandler;
import com.soli.server.MschModule;
import com.soli.server.route.ApiRoute;

public class SoilConfig extends LambkitApplicationContext {


    @Override
    public void configModule(LambkitModule module) {
        super.configModule(module);
        LambkitModule mgrModule = MgrdbManager.me().getLambkitModule();
        if (mgrModule != null) {
            module.addModule(mgrModule);
        }
        module.addModule(new UpmsModule());

        module.addModule(new MschModule());
        module.addModule(new LambkitModule() {
            @Override
            public void configRoute(Routes me) {
                super.configRoute(me);
                me.add(new ApiRoute());
            }

            @Override
            public void configInterceptor(Interceptors me) {
                super.configInterceptor(me);
//				me.add(new GlobalActionInterceptor());
            }

            /**
             * 配置插件
             */
            @Override
            public void configPlugin(Plugins me) {
                super.configPlugin(me);
                me.add(new JwtTokenPlugin(UpmsJwtUserService.me()));
                //        me.add(new LicensePlugin());
            }

            @Override
            public void configHandler(Handlers me) {
                super.configHandler(me);
//				me.add(new ApiRouteHandler("/api"));
                me.add(new GlobalActionHandler());
                me.add(com.lambkit.core.api.route.ApiRoute.me().getHandler("/api"));
            }

//			@Override
//			public void addModule(LambkitModule config) {
//				super.addModule(config);
//				config.addModule(new UpmsModule());
//			}

            @Override
            public void onStart() {
                super.onStart();
                com.lambkit.core.api.route.ApiRoute.me().onStart();
            }
        });
    }
    @Override
    public void configConstant(Constants me) {
        super.configConstant(me);
        me.setJsonFactory(new JFinalJsonFactory());
    }

}
