package com.github.jan222ik.web;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Client {
    private static final String protocol = "http";
    private static final String username = "[-a-zA-Z0-9]+";
    private static final String password = "[-a-zA-Z0-9]*";
    private static final String userPass = "(?<username>" + username + "):" + "(?<password>" + password + ")@";
    private static final String host = "[-a-zA-Z0-9]+(\\.[-a-zA-Z0-9]+)+|localhost";
    private static final String port = ":(?<portnum>[0-9]*)";
    private static final String path = "(/[-a-zA-Z0-9]+)*\\.[-a-zA-Z0-9]+";
    private static final Pattern urlPattern = Pattern.compile("^(?<protocol>"+ protocol + ")://(?<userPass>" + userPass + ")?+(?<host>" + host + ")(" + port +")?+(?<path>" + path + ")?+");
    private static final String[] namedGroups = new String[]{"protocol","userPass","username","password","host","portnum","path"};
    //private static final Pattern regex = Pattern.compile("^(http)://([-a-zA-Z0-9+&@#/%?=~_|!:,.;]*)[-a-zA-Z0-9+&@#/%=~_|]");
    //http://localhost:80/page.html

    public static void main(@Nullable final String... args) {
        Scanner s = new Scanner(System.in, "UTF-8");
        String line = null;
        if (s.hasNext()) {
            line = s.nextLine();
        }
        try {
            new Client().sendGetRequest(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response sendGetRequest(@Nullable final String request) throws IOException {
        if (request != null) {
            Matcher matcher = urlPattern.matcher(request);
            if (matcher.find()) {
                for (String namedGroup : namedGroups) {
                    System.out.println("[" + namedGroup + "]:\t" + matcher.group(namedGroup));
                }
                String host = matcher.group("host");
                int port = (matcher.group("portnum")==null)? 80:Integer.parseInt(matcher.group("portnum"));
                String path = matcher.group("path");
                String statement = prepareGET(1.1, host, path, false);
                Socket socket = new Socket(host, port);
                boolean sendSuccess = send(statement, socket.getOutputStream());
                if (sendSuccess) {
                    return receive(socket.getInputStream());
                }
            }

        }
        return null;
    }

    private Response receive(@NotNull InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder header = new StringBuilder();
            StringBuilder payload = new StringBuilder();
            String line;
            //int i = 0; Comments for Debug
            boolean head = true;
            while((line = br.readLine()) != null) {
                if (head) {
                    if ("".equals(line)) {
                        head = false;
                    } else {
                        header.append(line).append("\n");
                    }
                } else {
                    payload.append(line).append("\n");
                }
                //System.out.println("RESPONSE[" + i + "] < " + t + ">");
                //i++;
            }
            return new Response(header.toString(), payload.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Contract("!null, !null -> _ ; !null, null -> false ; null,!null -> false")
    private boolean send(@NotNull final String statement, @NotNull OutputStream outputStream) {
        System.out.println("statement = " + statement);
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            pw.print(statement);
            pw.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String prepareGET(double httpVersion, @NotNull String host, @Nullable String path, boolean connectionKeepAlive) {
        String ls = "\r\n";
        if (path == null) {
            path = "/";
        }
        return "GET " + path + " HTTP/" + httpVersion + ls +
                "Host: " + host + ls +
                "Connection: " + ((connectionKeepAlive) ? "keep alive" : "close") + ls +
                ls;
    }

    public static Pattern getUrlPattern() {
        return urlPattern;
    }
}
