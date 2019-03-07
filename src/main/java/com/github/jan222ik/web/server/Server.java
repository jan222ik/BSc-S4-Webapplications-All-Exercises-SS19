package com.github.jan222ik.web.server;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Server implements Runnable {
    private static String webroot;
    private static Socket socket;
    private static final String defaultWebroot = "server-pages";


    public static void main(String... args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            File file = new File(args[0]);
            if (file.isDirectory() && file.exists()) {
                System.out.println("Found directory at: " + file.getAbsolutePath());
                webroot = file.getAbsolutePath();
            }
        }
        else {
            webroot = defaultWebroot;
        }
        try {
            ServerSocket serverConnect = new ServerSocket(port);
            System.out.println("Server started.\nListening for connections on port : " + port + " ...\n");

            // we listen until user halts server execution
            while (true) {
                Server myServer = new Server(serverConnect.accept());


                // create dedicated thread to manage the client connection
                Thread thread = new Thread(myServer);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    public Server(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        System.out.println("Connection at socket = " + socket);
        try {
            ClientRequest clientRequest = receive(socket.getInputStream());
            if (clientRequest != null) {
                /*
                System.out.println("clientRequest.getMethod() = " + clientRequest.getMethod());
                System.out.println("clientRequest.getPathRequest() = " + clientRequest.getPathRequest());
                System.out.println("clientRequest.getMessageBody() = " + clientRequest.getMessageBody());
                for (Map.Entry<String, String> entry : clientRequest.getRequestHeaders().entrySet()) {
                    //System.out.println(entry.getKey() + " : " + entry.getValue());
                }
                */
                send(socket.getOutputStream(), clientRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized ClientRequest receive(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        return ClientRequest.parseRequest(bufferedReader, 10 * 500);
    }

    private synchronized void send(OutputStream outputStream, ClientRequest parsedRequest) {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        String documentContents = getDocumentContents(parsedRequest.getPathRequest(), false);
        if (documentContents == null) {
            pw.println("HTTP/1.1 404 File Not Found");
            pw.println("Connection: close");
            pw.println("\n");
            String contents = getDocumentContents("/404.html", false);
            if (contents == null) {
                contents = getDocumentContents("/404.html", true);
                if (contents != null) pw.println(contents);

            } else {
                pw.println(contents);
            }
        } else {
            pw.println("HTTP/1.1 200 OK");
            pw.println("Connection: close");
            pw.println("\n");
            pw.println(documentContents);
        }
        pw.println();
        pw.println();
        pw.flush();
        pw.close();
    }

    private synchronized String getDocumentContents(String path, boolean isDefaultRoot) {
        BufferedReader bufferedReader = null;
        try {
            if (path.equalsIgnoreCase("/")) {
                path = "/index.html";
            }
            File file = new File(((isDefaultRoot) ? defaultWebroot:webroot) + path);
            if (file.exists()) {
                StringBuilder sb = new StringBuilder();
                bufferedReader = new BufferedReader(new FileReader(file));
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    sb.append(s).append("\r\n");
                }
                System.out.println("found - searched for: " + file.getAbsolutePath());
                return sb.toString();
            } else {
                System.out.println("not found - searched for: " + file.getAbsolutePath());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
