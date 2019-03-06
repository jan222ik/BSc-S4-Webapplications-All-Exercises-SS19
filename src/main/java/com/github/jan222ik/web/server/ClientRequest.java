package com.github.jan222ik.web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * @author Janik Mayr on 28.02.2019
 */
public class ClientRequest {
    private String method;
    private String pathRequest;
    private Hashtable<String, String> requestHeaders;
    private StringBuffer messageBody;

    private ClientRequest() {
        requestHeaders = new Hashtable<>();
        messageBody = new StringBuffer();
    }

    public static ClientRequest parseRequest(BufferedReader request) throws IOException, HttpFormatException {
        ClientRequest clientRequest = new ClientRequest();
        String input = request.readLine();
        StringTokenizer parse = new StringTokenizer(input);
        clientRequest.method = parse.nextToken().toUpperCase();
        clientRequest.pathRequest = parse.nextToken().toLowerCase();


            String line = request.readLine();
            while (line.length() > 0) {
                clientRequest.appendHeaderParameter(line);
                line = request.readLine();
            }
            String bodyLine;
        if (!clientRequest.method.equalsIgnoreCase("get")) {
            while ((bodyLine = request.readLine()) != null) {
                clientRequest.appendMessageBody(bodyLine);
            }
        }

        return clientRequest;
    }

    private void appendMessageBody(String bodyLine) {
        System.out.println(bodyLine);
        messageBody.append(bodyLine).append("\r\n");
    }

    private void appendHeaderParameter(String header) throws HttpFormatException {
        int index = header.indexOf(':');
        if (index > 0) {
            requestHeaders.put(header.substring(0,index + 1),header.substring(index + 1));
        } else {
            throw new HttpFormatException("Invalid line in header");
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPathRequest() {
        return pathRequest;
    }

    public Hashtable<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public StringBuffer getMessageBody() {
        return messageBody;
    }

    public String valueOfHeaderParm(String paramName) {
        return requestHeaders.get(paramName);
    }
}
