package com.atlas.lambkit.getui;

import com.lambkit.core.config.annotation.PropertieConfig;

@PropertieConfig(prefix = "lambkit.getui")
public class GetuiConfig {
	//定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private String appId = "gNrHxrJIGi5rGBVweQF1G1";
    private String appKey = "fQNFd65Pxn6EiyqDkAzW49";
    private String masterSecret = "xFP8KebpLa8e2w4eC9les5";
    //短信模板id
	private String smsTemplateId = "";
    private String host = "http://sdk.open.api.igexin.com/apiex.htm";
    
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getMasterSecret() {
		return masterSecret;
	}
	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}
	public String getSmsTemplateId() {
		return smsTemplateId;
	}
	public void setSmsTemplateId(String smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
