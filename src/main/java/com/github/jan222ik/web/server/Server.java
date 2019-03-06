package com.github.jan222ik.web.server;

import java.io.*;
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
    private String webroot = "./resources/server-pages";
    Socket socket;


    public static void main(String... args) {
        int port = (args != null && args.length > 0) ? Integer.parseInt(args[0]) : 8080;
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
        System.out.println("socket = " + socket);
        try {
            ClientRequest clientRequest = receive(socket.getInputStream());
            //System.out.println("clientRequest.getMethod() = " + clientRequest.getMethod());
            //System.out.println("clientRequest.getPathRequest() = " + clientRequest.getPathRequest());
            //System.out.println("clientRequest.getMessageBody() = " + clientRequest.getMessageBody());
            for (Map.Entry<String, String> entry : clientRequest.getRequestHeaders().entrySet()) {
                //System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            send(socket.getOutputStream(), clientRequest);
        } catch (HttpFormatException | IOException e) {
            System.err.println("HTTP ERROR");
        }
    }

    private ClientRequest receive(InputStream inputStream) throws IOException, HttpFormatException {
        return ClientRequest.parseRequest(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
    }

    private void send(OutputStream outputStream, ClientRequest parsedRequest) {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        pw.println("HTTP/1.1 200 OK");
        pw.println("Connection: close");
        pw.println("\n");
        String documentContents = getDocumentContents(parsedRequest.getPathRequest());
        if (documentContents == null) {
            pw.println("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>Page Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1>This is a Heading</h1>\n" +
                    "<p>This is a paragraph.</p>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");
        } else {
            pw.println(documentContents);
        }



        pw.println();
        pw.println();
        pw.flush();
        pw.close();
    }

    private String getDocumentContents(String path) {
        try {
            URL resource = Server.class.getResource(path);
            System.out.println(resource.getPath());
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile().replace("%20", " ")));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s).append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
