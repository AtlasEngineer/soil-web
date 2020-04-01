package com.atlas.lambkit.start.upms;

import com.jfinal.config.Routes;

public class UpmsEmbeddedRoutes extends Routes {
	
	public void config() {
		// TODO Auto-generated method stub
		setBaseViewPath("/WEB-INF/jsp");
//		add("/user", PortalIndexController.class);



		//add("/sso", SSOController.class);

		//add("/action", ActionController.class);
		//add("/manage", ManageController.class);
		
		/*add("/manage/log", UpmsLogController.class);
		add("/manage/organization", UpmsOrganizationController.class);
		add("/manage/permission", UpmsPermissionController.class);
		add("/manage/role", UpmsRoleController.class);
		add("/manage/session", UpmsSessionController.class);
		add("/manage/system", UpmsSystemController.class);
		add("/manage/user", UpmsUserController.class);*/
	}

}
