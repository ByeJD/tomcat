package com.quan.chapter2;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer2 {

    public static final String SHUTDOWN_COMMAND = "/shutdown";
    private boolean shutDown = false;

    public static void main(String[] args) {
        HttpServer2 httpServer2 = new HttpServer2();
        httpServer2.await();
    }



    private void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!shutDown) {
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                // create Request object and parse
                Request request = new Request(inputStream);
                request.parse();
                Response response = new Response(outputStream);
                response.setRequest(request);

                if (request.getUrl() != null && request.getUrl().startsWith("/servlet/")){
                    ServletProcessor2 servletProcessor2 = new ServletProcessor2();
                    servletProcessor2.process(request,response);
                }else {
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request,response);
                }

                socket.close();


                // check if the previous url is a shutdown command
                shutDown = SHUTDOWN_COMMAND.equalsIgnoreCase(request.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

    }
}
