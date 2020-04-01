package com.atlas.server.admin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class AdminRoleInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null) {
			if(subject.hasRole("super") || subject.hasRole("admin")) {
				inv.getController().setAttr("template", "admin");
			} else if(subject.hasRole("member")) {
				inv.getController().setAttr("template", "member");
			} else {
				inv.getController().setAttr("template", "admin");
			}
		} else {
			inv.getController().setAttr("template", "admin");
		}
		inv.invoke();
	}

}
