package com.atlas.lambkit.license;

import com.jfinal.plugin.IPlugin;
import com.lambkit.license.License;
import com.lambkit.license.LicenseFactory;
import com.lambkit.license.LicenseKit;

public class LicensePlugin implements IPlugin {

	@Override
	public boolean start() {
		License license = LicenseKit.getLicense();
		LicenseFactory factory = LicenseKit.getLicenseFactory();
		try {
			if(factory!=null) {
				factory.generateKey();
				factory.sign(license);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
