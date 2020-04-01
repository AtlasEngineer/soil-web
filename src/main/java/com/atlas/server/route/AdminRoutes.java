package com.atlas.server.route;


import com.atlas.server.admin.controller.AdminIndexController;
import com.atlas.server.admin.controller.CommonController;
import com.atlas.server.admin.controller.UserController;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.lambkit.common.util.StringUtils;

/**
 * 后台管理系统的路由控制
 * 
 * @author yangyong
 *
 */
public class AdminRoutes extends Routes {

	String baseControllerKey = "/admin";
	String baseViewPath = "/WEB-INF/admin";

	public AdminRoutes(String baseControllerKey, String baseViewPath) {
		this.baseControllerKey = baseControllerKey;
		this.baseViewPath = baseViewPath;
	}

	public AdminRoutes() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void config() {
		// TODO Auto-generated method stub
		// addPage(baseControllerKey, AdminController.class, baseViewPath,
		// "adminPage");
		if(baseControllerKey.endsWith("/")) baseControllerKey = baseControllerKey.substring(0, baseControllerKey.length() - 1);
		String indexkey = StringUtils.hasText(baseControllerKey) ? baseControllerKey : "/";
		
		addPage(indexkey, AdminIndexController.class, baseViewPath, "admin_page");
		addPage(baseControllerKey + "/user", UserController.class, baseViewPath + "/user", "admin_user_page");
		addPage(baseControllerKey + "/data", CommonController.class, baseViewPath + "/common", "admin_user_page");
		/*addPage(baseControllerKey + "/auth", AuthController.class, baseViewPath + "/auth", "admin_auth_page");
		addPage(baseControllerKey + "/role", RoleController.class, baseViewPath + "/role", "admin_role_page");
		//addPage(baseControllerKey + "/login", LoginController.class, baseViewPath + "/user", "admin_login_page");

		addPage(baseControllerKey + "/posts", PostsController.class, baseViewPath + "/article", "admin_posts_page");
		addPage(baseControllerKey + "/terms", TermsController.class, baseViewPath + "/terms", "admin_terms_page");
		// 公共Controller
		

		addPage(baseControllerKey + "/log", ActionLogController.class, baseViewPath + "/sys", "admin_log_page");
		addPage(baseControllerKey + "/mgrdb", MgrdbController.class, baseViewPath + "/sys", "admin_mgrdb_page");
		addPage(baseControllerKey + "/dbfield", MgrdbfieldController.class, baseViewPath + "/sys", "admin_dbfield_page");
		
		 * me.add("/", IndexController.class, "/admin"); me.add("/user",
		 * UserController.class, "/admin/user"); me.add("/auth",
		 * AuthController.class, "/admin/auth"); me.add("/role",
		 * RoleController.class, "/admin/role"); me.add("/login",
		 * LoginController.class, "/admin/user");
		 * 
		 * me.add("/posts",PostsController.class,"/admin/article");
		 * me.add("/terms",TermsController.class,"/admin/terms"); //公共Controller
		 * me.add("/data", CommonController.class, "/admin/common");
		 * 
		 * me.add("/log", ActionLogController.class, "/admin/sys");
		 * me.add("/mgrdb", MgrdbController.class, "/admin/sys");
		 * me.add("/dbfield", MgrdbfieldController.class, "/admin/sys");
		 */
	}

	public void addPage(String controllerkey, Class<? extends Controller> controllerClass, String pagekey) {
		add(controllerkey, controllerClass);
		//CmsEngine.me.put(controllerkey, pagekey);
	}

	public void addPage(String controllerkey, Class<? extends Controller> controllerClass, String viewPath,
			String pagekey) {
		add(controllerkey, controllerClass, viewPath);
		//CmsEngine.me.put(controllerkey, pagekey);
	}
}
