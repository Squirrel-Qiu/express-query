package org.tencent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HuoBanQueryAPI implements ExpressQuery {
    public String query(String expCompany, String expNo) throws IOException {
        HashMap<String, String> params = new HashMap<>();
        String id = "******************************";
        params.put("id", id);
        params.put("com", expCompany);
        params.put("nu", expNo);
        String reqUrl = "http://q.kdpt.net/api";
        String result = senPost(reqUrl, params);
        // 直接输出
        System.out.println(result);
        // 格式化输出
        JSONObject json1 = JSON.parseObject(result);
        String str1 = json1.getString("data");

        JSONArray json2 = JSON.parseArray(str1);
        for (int i = 0; i < json2.size(); i++) {
            String str2 = json2.getString(i);
            JSONObject json3 = JSONObject.parseObject(str2);
            String str3 = json3.getString("time");
            String str4 = json3.getString("context");
            System.out.println("Time: " + str3 + ", Transport: " + str4);
        }
        return result;
    }

    public String senPost(String Url, Map<String, String> params) throws IOException {
        OutputStreamWriter out;
        BufferedReader in;
        StringBuilder result = new StringBuilder();
        URL url = new URL(Url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        // 获取URLConnection对象对应的输出流
        out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);

        if (params != null) {
            final StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (sb.length() > 0) sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            out.write(sb.toString());
        }
        out.flush();

        // 定义输入流读取URL的响应
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }

        out.close();
        in.close();
        return result.toString();
    }
}
