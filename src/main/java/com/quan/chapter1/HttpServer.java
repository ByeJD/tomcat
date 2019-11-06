package com.quan.chapter1;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static final String SHUTDOWN_COMMAND = "/shutdown";
    private boolean shutDown = false;

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.await();
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

//                System.out.println(IOUtils.toString(inputStream, "utf-8"));
                // create Request object and parse
                Request request = new Request(inputStream);
                request.parse();


                System.out.println("************ construct response ********************");
                Response response = new Response(outputStream);
                response.setRequest(request);

                System.out.println("************ begin to send static resource **********");
                response.sendStaticResource();
                System.out.println("************ end to send static resource **********");
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
