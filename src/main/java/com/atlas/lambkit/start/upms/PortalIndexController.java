package com.atlas.lambkit.start.upms;

import com.jfinal.aop.Clear;
import com.lambkit.Lambkit;

import com.lambkit.component.shiro.ShiroConfig;
import com.lambkit.module.upms.UpmsInterceptor;
import com.lambkit.module.upms.client.ShiroClientInterceptor;
import com.lambkit.module.upms.shiro.ShiroSsoInterceptor;
import com.lambkit.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresUser;


@RequiresUser
public class PortalIndexController extends BaseController {

	LoginService loginService = new UpmsEmbeddedLoginService();
	
	@Clear({ShiroClientInterceptor.class, ShiroSsoInterceptor.class})
	public void index() {
		renderTemplate("index.html");
	}
	
	@Clear({ShiroClientInterceptor.class, ShiroSsoInterceptor.class, UpmsInterceptor.class})
	public void login() {
		loginService.login(this);
	}
	
	@Clear
	public void captcha() {
		loginService.captcha(this);
	}
	
	@Clear
	public void regist() {
		setAttr("title", "注册");
		createToken("registToken");
		renderTemplate("regist.html");
	}
	
	@Clear//@Before(UCodeInterceptor.class)
	public void logout() {
		loginService.logout(this);
	}
	
	@Clear
	public void needPermission() {
		ShiroConfig config = Lambkit.config(ShiroConfig.class);
		render(config.getUnauthorizedUrl());
	}
	
	public void introduce() {
		renderTemplate("introduce.html");
	}
	
	public void army() {
		renderTemplate("army.html");
	}
	
	public void item() {
		renderTemplate("item.html");
	}
}
