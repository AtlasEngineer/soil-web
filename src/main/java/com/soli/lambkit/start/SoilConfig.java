package com.soli.lambkit.start;


import com.jfinal.config.*;
import com.jfinal.json.JFinalJsonFactory;
import com.jfinal.plugin.activerecord.OrderedFieldContainerFactory;
import com.lambkit.LambkitApplicationContext;
import com.lambkit.core.api.route.ApiInterceptorManager;
import com.lambkit.core.api.route.ApiJwtAuthInterceptor;
import com.lambkit.db.datasource.ActiveRecordPluginWrapper;
import com.lambkit.db.mgr.MgrdbManager;
import com.lambkit.module.LambkitModule;
import com.lambkit.module.upms.jwt.UpmsJwtUserService;
import com.lambkit.module.upms.server.UpmsModule;
import com.lambkit.plugin.jwt.JwtTokenPlugin;
import com.lambkit.web.websocket.WebSocketHandler;
import com.soli.lambkit.interceptor.GlobalActionHandler;
import com.soli.lambkit.interceptor.UnknownSessionInterceptor;
import com.soli.lambkit.license.LicenseHandler;
import com.soli.lambkit.license.LicensePlugin;
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
//				ApiInterceptorManager.me().addGlobalServiceInterceptor(new TokenInterceptor());
                ApiInterceptorManager.me().addGlobalServiceInterceptor(new UnknownSessionInterceptor());
//				me.add(new GlobalActionInterceptor());
            }

            @Override
            public void configMapping(ActiveRecordPluginWrapper arp) {
                super.configMapping(arp);
                arp.getPlugin().setContainerFactory(new OrderedFieldContainerFactory());
            }

            /**
             * 配置插件
             */
            @Override
            public void configPlugin(Plugins me) {
                super.configPlugin(me);
                me.add(new JwtTokenPlugin(UpmsJwtUserService.me()));
//                me.add(new LicensePlugin());
            }

            @Override
            public void configHandler(Handlers me) {
                super.configHandler(me);
//				me.add(new ApiRouteHandler("/api"));
                me.add(new GlobalActionHandler());
                me.add(new WebSocketHandler());
//                me.add(new LicenseHandler());
                me.add(com.lambkit.core.api.route.ApiRoute.me().getHandler("/api"));
            }

            @Override
            public void addModule(LambkitModule config) {
                super.addModule(config);
            }

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
