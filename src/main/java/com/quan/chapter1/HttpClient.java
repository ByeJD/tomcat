package com.quan.chapter1;


import java.io.*;
import java.net.Socket;

public class HttpClient {


    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", Integer.parseInt("8080"));
        boolean autoFlush = true;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), autoFlush);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // send an HTTP request to the web server
        out.println("get /index.jsp HTTP/1.1");
        out.println("HOST:localhost:8080");
        out.println("Connection: Close");
        out.println();

        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while (loop) {
            if (in.ready()) {
                int i = 0;
                while (i != -1){
                    i = in.read();
                    sb.append((char)i);
                }
                loop = false;
            }
            Thread.sleep(50);
        }
        System.out.println(sb.toString());
        socket.close();
    }
}
