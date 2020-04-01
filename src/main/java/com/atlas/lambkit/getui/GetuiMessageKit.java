package com.atlas.lambkit.getui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.lambkit.common.util.DateTimeUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xuzhonghao
 * @create: 2019-10-22 17:39
 */
public class GetuiMessageKit {

    /**
     * @Description: 获取个推token
     * @Author: xuzhonghao
     * @Date: 2019/10/22
     */
    public static Kv AuthSign(MessageConfig config) {
        String url = "https://openapi-smsp.getui.com/v1/sps/auth_sign";

        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * appId	    String	必填	由个推短信服务提供，每个应用都对应一个唯一的appId
         * sign	        String	必填	sha256(appKey+timeStamp+masterSecret)
         * timestamp	String	必填	生成sign的时间戳
         */
        Long currentTimeLong = DateTimeUtils.getCurrentTimeLong();
        String timestamp = currentTimeLong.toString();
        nvps.add(new BasicNameValuePair("appId", config.getAppId()));
        nvps.add(new BasicNameValuePair("sign", getSHA256Str(config.getAppKey() + timestamp + config.getMasterSecret())));
        nvps.add(new BasicNameValuePair("timestamp", timestamp));

        Kv result = new Kv();
        try {
            // 执行请求
            HttpResponse response = connect(config, url, nvps);
            System.out.println(response);
            JSONObject responseBody = JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            System.out.println(responseBody);
            result.set("code", responseBody.getInteger("result"));
            if (responseBody.getInteger("code") == 20000) {
                result.set("msg", responseBody.getString("msg"));
                result.set("authToken", responseBody.getJSONObject("data").get("authToken"));
            }
            LogKit.warn("GetuiMessageKit[GetuiAuthSign] sms code is " + responseBody.getInteger("code"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 短信群推
     * @Author: xuzhonghao
     * @Date: 2019/10/22
     */
    public static Kv pushSmsList(MessageConfig config, String authToken, JsonArray md5RecNum) {
        String url = "https://openapi-smsp.getui.com/v1/sps/push_sms_list";
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * appId	        String	    必填	由个推短信服务提供，每个应用都对应一个唯一的appId
         * authToken	    String	    必填	由鉴权接口返回
         * smsTemplateId	String	    必填	模板id
         * smsParam	        Json	    否	    短信模板变量，传参规则{"key":"value"},key的名字需和模板中定义的变量一致；若模板无变量，此参数不需要传
         * recNum	        JsonArray	必填	接收短信的号码，目前单次最大发送量为50个MD5加密后的手机号码。例如：["md5(pn1)","md5(pn2)"]
         * notifyUrl	    String	    否	    客户用于接收短信发送回执的接口地址，请求以Json格式POST方式提交
         */
        nvps.add(new BasicNameValuePair("appId", config.getAppId()));
        nvps.add(new BasicNameValuePair("authToken", authToken));
        nvps.add(new BasicNameValuePair("smsTemplateId", config.getSmsTemplateId()));
//        nvps.add(new BasicNameValuePair("smsParam", ""));
        nvps.add(new BasicNameValuePair("recNum", md5RecNum.toString()));

        Kv result = new Kv();
        try {
            // 执行请求
            HttpResponse response = connect(config, url, nvps);
            JSONObject responseBody = JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            result.set("code", responseBody.getInteger("result"));
            if (responseBody.getInteger("code") == 20000) {
                result.set("msg", responseBody.getString("msg"));
                result.set("data", responseBody.getJSONObject("data"));
            }
            LogKit.warn("GetuiMessageKit[pushSmsList] sms code is " + responseBody.getInteger("code"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static HttpResponse connect(MessageConfig config, String url, List<NameValuePair> nvps) throws IOException {
        if (config == null || StrKit.isBlank(url))
            return null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        String appKey = config.getAppKey();
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        //httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        // 设置请求的参数
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        // 执行请求
        System.out.println(httpPost);
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine());
        return response;
    }

    /***
     * 利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str) {
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

}
