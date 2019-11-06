package com.quan.chapter1;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 格式：
 * 协议---状态码----描述
 * 响应头
 * 响应实体段
 */
public class Response {

    private Request request;
    private OutputStream output;

    public Response(OutputStream outputStream) {
        this.output = outputStream;
    }

    public void sendStaticResource() throws IOException {
        FileInputStream fileInputStream = null;
        try{
            System.out.println("HttpServer.WEB_ROOT : " + HttpServer.WEB_ROOT + " , requestUrl" + request.getUrl());
            if (StringUtils.isNotBlank(request.getUrl())){
                File file = new File(HttpServer.WEB_ROOT,request.getUrl());
                if (file.exists()){
                    fileInputStream = new FileInputStream(file);
                    IOUtils.copy(fileInputStream,output);
                }else {
                    notFound();
                }
            }else {
                notFound();
            }
        }catch (Exception e){
            notFound();
            e.printStackTrace();
        }finally {
            if (fileInputStream != null){
                fileInputStream.close();
                output.flush();
            }
        }
    }

    private void notFound() throws IOException {
        // file not found
        String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n" + "Content-Length:23\r\n"
                +"\r\n"+"<h1>File Not Found</h1>";
        output.write(errorMessage.getBytes(StandardCharsets.UTF_8));
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public OutputStream getOutput() {
        return output;
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }
}
