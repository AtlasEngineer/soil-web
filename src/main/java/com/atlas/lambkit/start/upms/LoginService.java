package com.atlas.lambkit.start.upms;

import com.jfinal.core.Controller;

public interface LoginService {
	void captcha(Controller c);
	void login(Controller c);
	void logout(Controller c);
	void ajaxLogout(Controller c);
}
