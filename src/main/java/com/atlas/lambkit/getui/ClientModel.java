package com.atlas.lambkit.getui;

import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class ClientModel {

	private String clientId;
	private String alias;
	
	/**
	 * TransmissionTemplate透传
	 * LinkTemplate 通知打开网页模板
	 * NotificationTemplate 通知打开应用模板
	 * NotyPopLoadTemplate 通知弹框下载模板
	 */
	private AbstractTemplate template;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public AbstractTemplate getTemplate() {
		return template;
	}

	public void setTemplate(AbstractTemplate template) {
		this.template = template;
	}
	
	public void createTransmissionTemplateTest(com.atlas.lambkit.getui.GetuiConfig config, String msg) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(config.getAppId());
		template.setAppkey(config.getAppKey());
		template.setTransmissionContent(msg);
		template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app
		setTemplate(template);
	}
	
	public void createNotificationTemplateTest(com.atlas.lambkit.getui.GetuiConfig config) {
		
	}
	
	public void createLineTemplateTest(GetuiConfig config) {
		LinkTemplate template = new LinkTemplate();
		template.setAppId(config.getAppId());
		template.setAppkey(config.getAppKey());
		template.setTitle("title");
		template.setText("msg");
		template.setLogo("push.png");
		template.setLogoUrl("logoUrl");
		template.setUrl("url");
		setTemplate(template);
	}
	
	public void createLineTemplateDemo(GetuiConfig config) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(config.getAppId());
		template.setAppkey(config.getAppKey());

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		// 设置打开的网址地址
		template.setUrl("http://www.baidu.com");
		setTemplate(template);
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
