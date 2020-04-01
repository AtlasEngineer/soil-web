package com.farming.data;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CyclicBarrier;

/**
 * 模拟运动员
 **/
public class MyThread extends Thread {
    private CyclicBarrier cyclicBarrier;
    private String name;

    public MyThread(CyclicBarrier cyclicBarrier, String name) {
        super();
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + "开始准备");
        try {
            Thread.currentThread().sleep(5000);
            System.out.println(name + "准备完毕！等待发令枪");
            try {
                cyclicBarrier.await();
                //new LocalGoodsController().getGoods(name,"47");
                String post = post(name, "173");
                System.out.println("aaa: "+post);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    /**
     * @Description: 发请求
     * @Author: xuzhonghao
     * @Date: 2019/9/12
     */
    public String post(String userid,String id){
        // 初始化参数
        InputStream inputStream = null;
        String response = "";
        BufferedReader br = null;
        GetMethod getMethod = null;

        // 创建实例
        HttpClient httpclient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());

        // 设置接口超时时间
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);

        try {
            String url = "http://192.168.1.144:9090/order/add?userid="+userid+"&id="+id;
            getMethod = new GetMethod(url);
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

            // 设置http header
            getMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // 执行接口请求
            int statusCode1 = httpclient.executeMethod(getMethod);

            // 判断返回值
            if (statusCode1 != HttpStatus.SC_OK) {
                System.out.println("zuixinhuodianMethod is wrong " + getMethod.getStatusLine() + "----" + statusCode1);
                return null;
            } else {
                // 获取返回值
                inputStream = getMethod.getResponseBodyAsStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            getMethod.releaseConnection();
        }
    }
}

