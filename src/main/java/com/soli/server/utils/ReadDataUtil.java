package com.soli.server.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lambkit.common.util.EncryptUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: xuzhonghao
 * @create: 2019-09-26 09:05
 */
public class ReadDataUtil {

    /**
     * 32位默认长度的uuid 	 * (获取32位uuid)	 * 	 * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * * (获取指定长度uuid)	 * 	 * @return
     */
    public static String getUUID(int len) {
        if (0 >= len) {
            return null;
        }
        String uuid = getUUID();
        System.out.println(uuid);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }
        return str.toString();
    }

    /**
     * @Description: 获取月份列表
     * @Author: xuzhonghao
     * @Date: 2019/9/24
     */
    public static List<String> monthList() {
        Date date = new Date();
        Integer year = Integer.valueOf(String.format("%tY", date));
        Integer month = Integer.valueOf(String.format("%tm", date)) + 1;

        List<String> list = new ArrayList<>();
        Integer lastyear = year - 1;
        String aaa;
        for (int i = 0; i < 12; i++) {
            if (month == 1) {
                lastyear = year;
            }
            list.add(lastyear + "-" + month);
            month += 1;
            if (month == 13) {
                month = 1;
            }
        }
        return list;
    }


    /**
     * 获取当前系统时间最近12月的年月（含当月）
     */

    public static String[] getLast12Months(Date timeDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String time = sdf.format(timeDay);
        //处理月份输入条件
        if (time.length() == 7) {
            time = time + "-01 00:00:00";
        } else if (time.length() == 110) {
            time = time.substring(0, 7) + "-01 00:00:00";
        }
        Date date = new Date();
        SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM");
        try {
            date = sdp.parse(time);
        } catch (Exception e) {
            return null;
        }
        String[] last12Months = new String[12];
        Calendar cal = Calendar.getInstance();
        //设置输入条件时间
        cal.setTime(date);
        //要先+1,才能把本月的算进去
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        for (int i = 0; i < 12; i++) {
            //逐次往前推1个月
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
            last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + addZeroForNum(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
            System.out.println(cal.get(Calendar.MONTH));
            System.out.println(last12Months[i]);

        }
        return last12Months;
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                // 左补0
                sb.append("0").append(str);
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;

    }






}
