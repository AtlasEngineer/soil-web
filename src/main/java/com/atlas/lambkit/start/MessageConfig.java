package com.atlas.lambkit.start;


import com.lambkit.core.config.annotation.PropertieConfig;

@PropertieConfig(prefix = "lambkit.message")
public class MessageConfig {

    private String appId = "vS5dVko0KR9rSvP4PF3n63";
    private String appKey = "2BnYzLuUtk6gGK102CxMP9";
    private String masterSecret = "tgbDVxj7zg6xwvMDqDr7P";
    //短信模板id
    private String smsTemplateId = "0001";



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
