package com.atlas.lambkit.start;

import com.atlas.lambkit.start.upms.UpmsEmbeddedRoutes;
import com.atlas.server.route.AdminRoutes;
import com.atlas.server.route.ApiRoute;
import com.jfinal.config.Routes;
import com.lambkit.module.LambkitModule;


public class AtlasModule extends LambkitModule {

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add(new AdminRoutes());
		me.add(new UpmsEmbeddedRoutes());
		me.add(new ApiRoute());
	}
}
