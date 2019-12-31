package org.tencent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class KdniaoQueryAPI implements ExpressQuery {
    public String query(String expCompany, String expNo) throws IOException, NoSuchAlgorithmException, JSONException {
        String RequestData = "{'OrderCode':'','ShipperCode':'" + expCompany + "','LogisticCode':'" + expNo + "'}";

        final HashMap<String, String> params = new HashMap<>();
        params.put("RequestData", urlEncoder(RequestData, "UTF-8"));
        String EBusinessID = "*******";
        params.put("EBusinessID", EBusinessID);
        // 请求的接口指令
        params.put("RequestType", "1002");
        String appKey = "************************";
        String dataSign = encrypt(RequestData, appKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        String reqUrl = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
        String result = sendPost(reqUrl, params);
        // 直接输出
        System.out.println(result);
        // 格式化输出
        JSONObject json1 = JSON.parseObject(result);
        String str1 = json1.getString("Traces");

        JSONArray json2 = JSON.parseArray(str1);
        for (int i = 0; i < json2.size(); i++) {
            String str2 = json2.getString(i);
            JSONObject json3 = JSONObject.parseObject(str2);
            String str3 = json3.getString("AcceptTime");
            String str4 = json3.getString("AcceptStation");
            System.out.println("Time: " + str3 + ", Transport: " + str4);
        }
        return result;
    }

    public String urlEncoder(String string, String charset) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, charset);
    }

    public String encrypt(String content, String appKey, String charset) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (appKey != null) {
            return base64(MD5(content + appKey, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    public String base64(String string, String charset) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(string.getBytes(charset));
    }

    private String MD5(String string, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes(charset));
        byte[] result = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (byte b : result) {
            // byte转换为int
            int val = b & 0xff;
            if (val <= 0xf) sb.append("0");
            // 将整数参数的字符串表示形式作为无符号的十六进制整数返回
            sb.append(Integer.toHexString(val));
        }
        // MD5的32位小写
        return sb.toString().toLowerCase();
    }

    public String sendPost(String reqUrl, Map<String, String> params) throws IOException {
        OutputStreamWriter out;
        BufferedReader in;
        StringBuilder result = new StringBuilder();
        URL url = new URL(reqUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("User-agent",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        // 获取URLConnection对象对应的输出流
        out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);

        // 发送请求参数
        if (params != null) {
            final StringBuilder param = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (param.length() > 0) param.append("&");
                param.append(entry.getKey());
                param.append("=");
                param.append(entry.getValue());
            }
            out.write(param.toString());
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
