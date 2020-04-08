package com.farming.data;

import java.io.*;
import java.util.UUID;

public class test {

    public static void main(String[] args) throws IOException {
//        String time[]= ReadDataUtil.getLast12Months(new Date());
//        List<String> last12MonthsList= Arrays.asList(time);
//        System.out.println(last12MonthsList);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String nTime = format.format(new Date());
//        boolean tof = Base64Util.compareTime(nTime, "2020-09-11 00:00:00", "yyyy-MM-dd hh:mm:ss");
//        System.out.println(tof);


        for (int i = 0; i < 5; i++) {
            //注意replaceAll前面的是正则表达式
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            System.out.println(uuid);
//            System.out.println(uuid.length());
        }



    }
}
