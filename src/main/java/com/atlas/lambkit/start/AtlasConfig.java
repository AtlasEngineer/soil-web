package com.atlas.lambkit.start;

import com.atlas.lambkit.interceptor.GlobalActionHandler;
import com.atlas.lambkit.interceptor.GlobalActionInterceptor;
import com.atlas.server.MschModule;
import com.jfinal.config.*;
import com.jfinal.template.Engine;
import com.lambkit.Lambkit;
import com.lambkit.db.mgr.MgrdbManager;
import com.lambkit.module.LambkitModule;
import com.lambkit.module.lms.LmsModule;
import com.lambkit.module.upms.server.UpmsModule;

public class AtlasConfig extends JFinalConfig {
    private LambkitModule config = null;

    @Override
    public void configConstant(Constants me) {
        if (config == null) config = Lambkit.getModule();
        if (Lambkit.getLambkitConfig().isLmsActived()) {
            Lambkit.addModule(new UpmsModule());
            Lambkit.addModule(new AtlasModule());
            LambkitModule module = MgrdbManager.me().getLambkitModule();
            if (module != null) {
                Lambkit.addModule(module);
            }
            Lambkit.addModule(new LmsModule());
        }
        config.addModule(new MschModule());
        config.configConstant(me);
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        config.configRoute(me);
    }

    public void configEngine(Engine me) {
        config.configEngine(me);
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
//        me.add(new LicensePlugin());
        //me.add((IPlugin) new JwtTokenPlugin(UpmsJwtUserService.me()));
        config.configPlugin(me);
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
      me.add(new GlobalActionInterceptor());
        config.configInterceptor(me);
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {
//        me.add(new LicenseHandler());
        me.add(new GlobalActionHandler());
        config.configHandler(me);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        config.onStart();
    }
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        config.onStop();
    }
}
