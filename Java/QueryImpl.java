package org.tencent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryImpl {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        QueryImpl api = new QueryImpl();
        Scanner sc = new Scanner(System.in);
        System.out.println("请选择要调用的接口：");
        String impl = sc.nextLine();
        System.out.println("请输入快递公司：");
        String com = sc.nextLine();
        System.out.println("请输入快递单号：");
        String num = sc.nextLine();
        // 对单号做一个简单的校验
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(num);
        if (m.find()) System.out.println("输入了无效字符");
        else {
            if (impl.equals("api1")) api.api1(com, num);
            if (impl.equals("api2")) api.api2(com, num);
        }
    }

    public void api1(String com, String num) throws IOException, NoSuchAlgorithmException {
        KdniaoQueryAPI kdniaoQueryAPI = new KdniaoQueryAPI();
        kdniaoQueryAPI.query(com, num);
    }

    public void api2(String com, String num) throws IOException {
        HuoBanQueryAPI huoBanQueryAPI = new HuoBanQueryAPI();
        huoBanQueryAPI.query(com, num);
    }
}
