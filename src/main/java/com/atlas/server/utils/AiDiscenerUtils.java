package com.atlas.server.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atlas.lambkit.start.BaiDuConfig;
import com.atlas.server.model.Catalogue;
import com.baidu.aip.imagesearch.AipImageSearch;
import com.google.common.collect.Maps;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.util.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class AiDiscenerUtils {

    //百度的连接信息
    private static BaiDuConfig config = Lambkit.config(BaiDuConfig.class);
    //百度接口调用连接对象
    private static AipImageSearch client = new AipImageSearch(config.getAPP_ID(), config.getAPI_KEY(), config.getSECRET_KEY());

    public static Ret searchFlora(String url) throws IOException {
        /**
         * 传入可选参数调用接口, 根据需求来, 参数越详细结果越准确, 这里图片较少, 只写页码和条数
         * 经测试指定页码和条数后, 会根据相似度排序, 最高的排在前面
         */
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        JSONObject res = null;
        String p = "/eatalogue/w/";
        HashMap<String, String> options = new HashMap<String, String>();   //查询时不按分类查询的话默认查所有
        // 页码, 这里从第一页开始, 也就是展示相似度最高的两张图片               //按分类查询时,
        options.put("pn", "0");
        // 条数, 只显示两条
        options.put("rn", "8");

        String images = rootPath + url;

        // 进行相似查询
        res = client.similarSearch(images, options);//直接输入路径进行查询
        // 打印上传结果
        if (StringUtils.isNotBlank(JSON.parseObject(res.toString()).getString("error_msg"))) {
            return Ret.fail("msg", "识别失败").set("data", JSON.parseObject(res.toString()).getString("error_msg"));
        } else {
            JSONArray jsonArray = null;
            String result = "";
            Map<String, Object> map = Maps.newLinkedHashMap();
            if (StringUtils.isNotBlank(res.toString(2))) {
                result = JSON.parseObject(res.toString(2)).getString("result");
                jsonArray = JSONArray.parseArray(result);
                for (int x = 0; x < jsonArray.size(); x++) {
                    String briefid = jsonArray.getJSONObject(x).getJSONObject("brief").getString("id");
                    if (map.containsKey(briefid)) {
                        continue;
                    } else {
                        map.put(briefid, "{\"score\":\"" + jsonArray.getJSONObject(x).get("score") + "\",\"url\":\"" + JSON.parseObject(jsonArray.getJSONObject(x).get("brief").toString()).getString("url") + "\"}");
                    }
                }
            }
            JSONArray list = new JSONArray();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Catalogue catalogue = Catalogue.service().dao().findFirst(Catalogue.sql().andIdEqualTo(entry.getKey()).example());
                if (catalogue != null) {
                    com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(entry.getValue().toString());
                    Double score = json.getDouble("score");
                    com.alibaba.fastjson.JSONObject jb = new com.alibaba.fastjson.JSONObject();
                    jb.fluentPut("score", entry.getValue());
                    jb.fluentPut("sim", score);
                    jb.fluentPut("name", catalogue.getName());
                    jb.fluentPut("ename", catalogue.getEname());
                    jb.fluentPut("id", catalogue.getId());
                    list.add(jb);
                }
            }
            return Ret.ok("list", list);
        }
    }

    public static Ret searchFloraPy(String path) {
        if (StringUtils.isBlank(path)) {
            return Ret.fail("msg", "请选择图片");
        }
        // 初始化参数
        InputStream inputStream = null;
        String response = "";
        BufferedReader br = null;
        PostMethod postMethod = null;
        // 创建实例
        HttpClient httpclient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());
        // 设置接口超时时间
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        try {
            String url = "http://192.168.1.187:9000/Identifyresult";
            postMethod = new PostMethod(url);
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            // 设置http header
            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            postMethod.addParameter("img_path", path);
            // 执行接口请求
            int statusCode1 = httpclient.executeMethod(postMethod);

            // 判断返回值
            if (statusCode1 != HttpStatus.SC_OK) {
                return Ret.fail("msg", "getDataFXJsonBySensorcodes Method is wrong " + postMethod.getStatusLine() + "----" + statusCode1);
            } else {
                //获取返回值
                inputStream = postMethod.getResponseBodyAsStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
            }
            com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(response);
            JSONArray list = json.getJSONArray("list");
            List<String> species = new ArrayList<>();
            Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                com.alibaba.fastjson.JSONObject jb = (com.alibaba.fastjson.JSONObject) iterator.next();
                String species_id = jb.getString("species_id");
                if (species.contains(species_id)) {
                    iterator.remove();
                }
                species.add(species_id);
                Record catalogue1 = Db.findFirst("select * from catalogue where id = ? ", species_id);
                if (catalogue1 != null) {
                    jb.fluentPut("name", catalogue1.get("name"));
                    jb.fluentPut("id", catalogue1.get("id"));
                    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
                    jsonObject.fluentPut("score", jb.get("acquaintance"));
                    jb.fluentPut("sim", jb.get("acquaintance"));
                    jsonObject.fluentPut("url", "/eatalogue/sample/" + species_id + "/" + jb.getString("img_id"));
                    jb.remove("img_id");
                    jb.remove("acquaintance");
                    jb.remove("species_id");
                    jb.fluentPut("score", jsonObject.toString());
                    jb.fluentPut("ename", catalogue1.get("ename"));
                }

            }
            return Ret.ok("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Ret.ok("msg", e.getMessage());
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
                return Ret.ok("msg", e.getMessage());
            }
            postMethod.releaseConnection();
        }
    }

    public static void copyFile3(String srcPath, String destPath) throws IOException {
        // 打开输入流
        FileInputStream fis = new FileInputStream(srcPath);
        // 打开输出流
        FileOutputStream fos = new FileOutputStream(destPath);
        // 读取和写入信息
        int len = 0;
        // 创建一个字节数组，当做缓冲区
        byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
            fos.write(b, 0, len);
        }
        // 关闭流  先开后关  后开先关
        fos.close(); // 后开先关
        fis.close(); // 先开后关
    }

}
