package com.atlas.lambkit.start;

import com.alibaba.fastjson.JSONObject;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.cache.impl.RedisCacheImpl;
import com.orbitz.okhttp3.OkHttpClient;
import com.orbitz.okhttp3.Request;
import com.orbitz.okhttp3.Response;

import java.io.IOException;

public class TimingTask implements Runnable {

    private boolean started = false;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (started) {
            System.out.println("--------------开始更新ticket---------------");
            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request1 = new Request.Builder()
                        .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf1b673f91742790f&secret=5b80caba77ff9738d5bac0b884339e1a")
                        .method("GET", null)
                        .build();
                Response response1 = client.newCall(request1).execute();
                JSONObject json1 = new JSONObject().parseObject(response1.body().string());
                System.out.println(json1);
                if (StringUtils.isBlank(json1.getString("access_token"))) {
                    System.out.println(json1+"\n--------------更新ticket失败---------------");
                    return;
                }
                Request request2 = new Request.Builder()
                        .url("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + json1.getString("access_token") + "&type=jsapi")
                        .method("GET", null)
                        .build();
                Response response2 = client.newCall(request2).execute();
                JSONObject json2 = new JSONObject().parseObject(response2.body().string());
                System.out.println(json2);
                String ticket = json2.getString("ticket");
                if (StringUtils.isBlank(ticket)) {
                    System.out.println(json2+"\n--------------更新ticket失败---------------");
                    return;
                }
                //redis2小时
                RedisCacheImpl redis = new RedisCacheImpl();
                redis.put("wechat_ticket", "token", json1.getString("access_token"),7200);
                redis.put("wechat_ticket", "ticket", ticket,7200);
                System.out.println("--------------更新ticket完成---------------");
            } catch (IOException e) {
                System.out.println(e.getMessage()+"\n--------------更新ticket失败---------------");
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }


}