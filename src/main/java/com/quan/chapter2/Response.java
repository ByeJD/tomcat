package com.quan.chapter2;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * 格式：
 * 协议---状态码----描述
 * 响应头
 * 响应实体段
 */
public class Response implements ServletResponse {

    private Request request;
    private OutputStream output;
    private PrintWriter printWriter;

    public Response(OutputStream outputStream) {
        this.output = outputStream;
    }

    public void sendStaticResource() throws IOException {
        FileInputStream fileInputStream = null;
        try{
            System.out.println("HttpServer.WEB_ROOT : " + Constants.WEB_ROOT + " , requestUrl" + request.getUrl());
            if (StringUtils.isNotBlank(request.getUrl())){
                File file = new File(Constants.WEB_ROOT,request.getUrl());
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

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        // autoFlush is true,println() will flush
        // print() will not.
        printWriter = new PrintWriter(output,true);
        return printWriter;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
