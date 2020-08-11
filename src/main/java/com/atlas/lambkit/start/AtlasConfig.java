package com.atlas.lambkit.start;

import com.atlas.lambkit.interceptor.GlobalActionHandler;
import com.atlas.server.MschModule;
import com.atlas.server.route.ApiRoute;
import com.jfinal.config.*;
import com.jfinal.json.JFinalJsonFactory;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.lambkit.LambkitApplicationContext;
import com.lambkit.db.mgr.MgrdbManager;
import com.lambkit.module.LambkitModule;
import com.lambkit.module.admin.AdminModule;
import com.lambkit.module.cms.CmsModule;
import com.lambkit.module.oss.OssModule;
import com.lambkit.module.pay.PayModule;
import com.lambkit.module.ucenter.UcenterModule;
import com.lambkit.module.upms.jwt.UpmsJwtUserService;
import com.lambkit.module.upms.server.UpmsModule;
import com.lambkit.plugin.jwt.JwtTokenPlugin;

public class AtlasConfig extends LambkitApplicationContext {

    TimingTask myTask;

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
            public void configPlugin(Plugins me) {
                super.configPlugin(me);
                Cron4jPlugin cp = new Cron4jPlugin();
                myTask = new TimingTask();
                cp.addTask("0 */2 * * *", myTask);
                me.add(cp);
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
                if(myTask!=null) myTask.setStarted(true);
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
