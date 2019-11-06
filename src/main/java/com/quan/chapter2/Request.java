package com.quan.chapter2;

import javax.servlet.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * HTTP request format:
 * 请求方法----统一资源定位符(Uniform Resources Identifier,URI)------协议/版本
 * 请求头
 * 实体
 */

public class Request implements ServletRequest {
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
        // 这里有个坑,使用readLine()方法会阻塞(即使流的末尾有\r\n),导致流程走不下去,本质上是TCP的包的不连续性(TCP的粘包),read或者readLine()方法
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
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String httpInfo  = bufferedReader.readLine();
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

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
