package com.soli.lambkit.start;

import com.lambkit.LambkitApplication;

public class SoilStarter {
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 端口号在资源文件 undertow.txt文件配置
		 */
		LambkitApplication.run(SoilConfig.class, args);
	}
}
