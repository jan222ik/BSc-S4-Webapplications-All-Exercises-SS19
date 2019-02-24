package com.github.jan222ik.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Server {
    ServerSocket socket;

    {
        try {
            socket = new ServerSocket(80);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws IOException {
        new Server().start();
    }

    private void start() throws IOException {
        Socket s = socket.accept();
        System.out.println("s = " + s);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
        String t;
        int i = 3;
        while((t = br.readLine()) != null && i > 0) { System.out.println(t); i--;}

        System.out.println("Reach");
        OutputStream outputStream = s.getOutputStream();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        pw.println("HTTP/1.1 200");
        pw.println("Connection: close");
        pw.println("\n");
        pw.println("This is the payload");
        pw.println();
        pw.flush();
    }
}
