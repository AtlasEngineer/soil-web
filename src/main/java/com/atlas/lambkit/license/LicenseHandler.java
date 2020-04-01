package com.atlas.lambkit.license;

import com.jfinal.handler.Handler;
import com.lambkit.license.LicenseKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LicenseHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		// TODO Auto-generated method stub
		if(!LicenseKit.verify()) {
			target = "/license";
		}
		next.handle(target, request, response, isHandled);
	}

}
