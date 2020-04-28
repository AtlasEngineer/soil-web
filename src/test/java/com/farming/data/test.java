package com.farming.data;

import com.atlas.server.utils.Base64Util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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


        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
                 String sftBirth = "1998-09-11";
                 Date date = null;
               try{
                        date = sft.parse(sftBirth);
                     }catch(Exception e){
                         e.printStackTrace();
                     }
                int age = Base64Util.getAgeByBirth(date);
                System.out.print("年龄=" + age + "岁");
            }
}
