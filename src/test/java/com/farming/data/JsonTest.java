package com.farming.data;


import net.sf.json.JSONArray;
import org.junit.Test;

/**
 * @author: xuzhonghao
 * @create: 2019-08-07 13:00
 */
public class JsonTest {

    @Test
    public void jsonTest(){
        String json = "[{id: 1,q: '第一题',a: [{ id: 'A', text: '答案一' }, { id: 'B', text: '答案二' }, { id: 'C', text: '答案三' }, { id: 'D', text: '答案四' }],r: 'C'},{id: 2,q: '第二题',a: [{ id: 'A', text: '答案一' }, { id: 'B', text: '答案二' }, { id: 'C', text: '答案三' }, { id: 'D', text: '答案四' }],r: 'B'}]";

        JSONArray myJsonArray = JSONArray.fromObject(json);
        for (int i = 0; i < myJsonArray.size(); i++) {
            System.out.println(myJsonArray.get(i));
            //保存题目
            System.out.println("题目："+myJsonArray.getJSONObject(i).get("q").toString());
            JSONArray a = JSONArray.fromObject(myJsonArray.getJSONObject(i).get("a"));
            for (int j = 0; j < a.size(); j++) {
                System.out.println("选项"+a.getJSONObject(j).getString("id")+":"+a.getJSONObject(j).getString("text"));
            }
            System.out.println("答案："+myJsonArray.getJSONObject(i).get("r").toString());
        }

    }
}
