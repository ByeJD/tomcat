package com.quan.chapter2;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor2 {
    public void process(Request request, Response response) {
        String url = request.getUrl();
        String servletName = url.substring(url.lastIndexOf("/") + 1);
        URLClassLoader urlClassLoader = null;
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);
            // 后面带有File.separator表示文件夹
            String respository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, respository, streamHandler);
            urlClassLoader = new URLClassLoader(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class cls = null;
        try {
            // 这里的servletName必须使用全限定名
            cls =  urlClassLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet = null;
        try {
            servlet = (Servlet) cls.newInstance();
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            // Request是继承ServletRequest，子类强转成父类会导致部分信息丢失，
            servlet.service((ServletRequest) requestFacade, (ServletResponse) responseFacade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 这里遇到了问题：当把PrimitiveServlet放到webroot目录下面会出现ClassNotFoundException错误，需要将PrimitiveServlet放到java目录下
     * 为什么？这样载入PrimitiveServlet就不需要使用全限定名，或者说全限定名就是类名本身，
     * 其实这个时候该类已经被载入jvm中了，URLClassLoader.loadClass()会先判断类是否已经被载入JVM
     * 如果已经载入JVM中，直接返回。这样的话，其实就不用指定webroot路径
     * @param args
     */
    public static void main(String[] args) {
        String servletName = "PrimitiveServlet";
        URLClassLoader urlClassLoader = null;
        try {
            URL[] urls = new URL[1];
//            URLStreamHandler streamHandler = null;
//            File classPath = new File(Constants.WEB_ROOT);
//            String respository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
//            System.out.println(respository);
//            urls[0] = new URL(null, respository, streamHandler);
            urlClassLoader = new URLClassLoader(urls);
            Class cls =  urlClassLoader.loadClass(servletName);
            Object object =  cls.newInstance();
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
