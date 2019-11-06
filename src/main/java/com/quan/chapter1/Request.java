package com.quan.chapter1;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import sun.swing.StringUIClientPropertyKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;

/**
 * HTTP request format:
 * 请求方法----统一资源定位符(Uniform Resources Identifier,URI)------协议/版本
 * 请求头
 * 实体
 */

public class Request {
    private String url;
    private InputStream inputStream;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parse() throws IOException {

        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] bytes = new byte[2048];
        try{
            i = inputStream.read(bytes);
        }catch (Exception e){
            e.printStackTrace();
            i=-1;
        }

        for (int j = 0; j < i; j++) {
            request.append((char)bytes[j]);
        }

        System.out.println(request.toString());
        if (request.toString().length() > 0){
            this.url =  request.toString().split("\\s+")[1];
        }
//
        // 这里有个坑,使用readLine()方法会阻塞(即使流的末尾有\r\n),导致流程走不下去,本质上是TCP的包的不连续性，TCP的粘包,导read或者readLine()方法
        // 不会返回-1,TCP是面向流或者字节的传输，自定义TLV(type,length,value)格式解析消息
        // IOUtils本质上也是对inputStream方法的封装。
//        String data =  IOUtils.toString(inputStream, "utf-8");
//        if (StringUtils.isNotBlank(data)){
//            this.url = data.split("\\s+")[1];
//        }
//
//
//        System.out.println("data  = " + data);
//
//
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String httpInfo  = bufferedReader.readLine();
//        if (StringUtils.isNotBlank(httpInfo)){
//            System.out.println(httpInfo);
//            this.url = httpInfo.split("\\s+")[1];
//        }
//
//        while ((httpInfo = bufferedReader.readLine())!= null){
//            System.out.println(httpInfo);
//        }

//        System.out.println("request parse end");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
