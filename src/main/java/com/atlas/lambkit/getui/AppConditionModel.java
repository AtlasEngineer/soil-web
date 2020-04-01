package com.atlas.lambkit.getui;

import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class AppConditionModel {

	private AppConditions conditions;
	
	/**
	 * TransmissionTemplate透传
	 * LinkTemplate 通知打开网页模板
	 * NotificationTemplate 通知打开应用模板
	 * NotyPopLoadTemplate 通知弹框下载模板
	 */
	private AbstractTemplate template;
	
	public AppConditionModel() {
		// 推送给App的目标用户需要满足的条件
		conditions = new AppConditions();
		/*
		 * //手机类型 List<String> phoneTypeList = new ArrayList<String>(); //省份
		 * List<String> provinceList = new ArrayList<String>(); //自定义tag
		 * List<String> tagList = new ArrayList<String>();
		 * 
		 * cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		 * cdt.addCondition(AppConditions.REGION, provinceList);
		 * cdt.addCondition(AppConditions.TAG,tagList);
		 */
	}

	public AppConditions getConditions() {
		return conditions;
	}

	public void setConditions(AppConditions conditions) {
		this.conditions = conditions;
	}

	public AbstractTemplate getTemplate() {
		return template;
	}

	public void setTemplate(AbstractTemplate template) {
		this.template = template;
	}
	
	public void createTransmissionTemplateTest(GetuiConfig config, String msg) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(config.getAppId());
		template.setAppkey(config.getAppKey());
		template.setTransmissionContent(msg);
		template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app
		setTemplate(template);
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
	
	public void createNotificationTemplateDemo(GetuiConfig config) {
		NotificationTemplate template = new NotificationTemplate();
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

		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent("请输入您要透传的内容");
		setTemplate(template);
	}
}
	
