package com.atlas.lambkit.getui;


import com.lambkit.core.config.annotation.PropertieConfig;

@PropertieConfig(prefix = "lambkit.message")
public class MessageConfig {

    private String appId = "gNrHxrJIGi5rGBVweQF1G1";
    private String appKey = "fQNFd65Pxn6EiyqDkAzW49";
    private String masterSecret = "xFP8KebpLa8e2w4eC9les5";
    //短信模板id
    private String smsTemplateId = "00001";

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
}
