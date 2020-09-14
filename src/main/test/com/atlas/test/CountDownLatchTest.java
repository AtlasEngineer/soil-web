package com.atlas.test;

import com.orbitz.okhttp3.*;
import jxl.demo.Demo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 单例模式，发令枪测试单例创建
 * 1、修改发令枪数量
 * 2、修改doWork 方法内容即可使用
 *
 * @author sloar,  jzyadmin@163.com
 * @create 2019/9/16 18:11
 **/
public class CountDownLatchTest {

    // TODO 发令枪数量设置
    private static final int N = 200;
    private static int num1 = 0;
    private static int num2 = 0;

    public static void main(String rgs[]) throws InterruptedException {
        //发令枪测试
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor service = Executors.newCachedThreadPool();
        for (int i = 0; i < N; ++i) // create and start threads
            service.execute(new WorkerRunnable(doneSignal, i, num1, num2));

        doneSignal.await();           // wait for all to finish
        System.out.println("结束总数：" + N);
    }
}


class WorkerRunnable implements Runnable {


    private final CountDownLatch doneSignal;
    private final int i;
    private static int num1 = 0;
    private static int num2 = 0;

    WorkerRunnable(CountDownLatch doneSignal, int i, int num1, int num2) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    public void run() {
        doWork(i);
        doneSignal.countDown();
    }

    // TODO 其他业务测试在此方法内修改代码即可
    void doWork(int i) {
        //请求
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://47.94.134.236/sct/searchyear?tb=ydyl_import_myfgb")
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                num1++;
            } else {
                num2++;
            }
            System.out.println(num1+"----"+num2);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(i + "--->"+ Demo.getInstance());
    }
}