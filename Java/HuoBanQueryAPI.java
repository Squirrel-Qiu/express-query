package org.tencent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuoBanQueryAPI {
    private String ReqUrl = "http://q.kdpt.net/api";
    private String id = "******************************";

    public static void main(String[] args) throws IOException {
        final HuoBanQueryAPI api = new HuoBanQueryAPI();
        String result = api.query("Company", "ExpressNo");
        // 无格式化输出
        System.out.println(result);
        if (result == "输入了无效字符") {
            System.out.println(result);
        } else {
            api.formatOut(result);
        }
    }

    public String query(String expCompany, String expNo) throws IOException {
        // 对单号做一个简单的校验
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(expNo);
        if (m.find()) return "输入了无效字符";
        else {
            final HashMap<String, String> params = new HashMap<>();
            params.put("id", id);
            params.put("com", expCompany);
            params.put("nu", expNo);
            String result = senPost(ReqUrl, params);
            return result;
        }
    }

    public String senPost(String Url, Map<String, String> params) throws IOException {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            final URL url = new URL(Url);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return result.toString();
    }

    // 格式化输出
    public void formatOut(String result) throws JSONException {
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
    }
}
