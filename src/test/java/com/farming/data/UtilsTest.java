package com.farming.data;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xuzhonghao
 * @create: 2019-08-12 14:14
 */
public class UtilsTest {

    @Test
    public void test(){
        int i = (int) ((Math.random() * 9 + 1) * 10000);
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String trade_no = sdf.format(new Date()) + i;
        System.out.println(trade_no);
    }
}
