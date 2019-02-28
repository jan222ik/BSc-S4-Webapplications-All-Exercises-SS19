package com.github.jan222ik.web;


import com.github.jan222ik.web.ui.JavaFXApp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.min;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Client {
    private static final String protocol = "http";
    private static final String username = "[-a-zA-Z0-9]+";
    private static final String password = "[-a-zA-Z0-9]*";
    private static final String userPass = "(?<userPass>(?<username>" + username + "):" + "(?<password>" + password + "))@";
    private static final String host = "[-a-zA-Z0-9]+(\\.[-a-zA-Z0-9]+)+|localhost";
    private static final String port = ":(?<portnum>[0-9]*)";
    private static final String path = "(/[-a-zA-Z0-9]+)*\\.[-a-zA-Z0-9]+";
    private static final Pattern urlPattern = Pattern.compile("^(?<protocol>"+ protocol + ")://(" + userPass + ")?+(?<host>" + host + ")(" + port +")?+(?<path>" + path + ")?+");
    private static final String[] namedGroups = new String[]{"protocol","userPass","username","password","host","portnum","path"};
    private static final String http = "http://";


    public static void main(@Nullable String... args) {
        boolean cmpExit = false;
        boolean handover = false;
        if (args != null && args[0] != null) {
            handover = true;
        } else {
            System.out.println("Enter a url of format: [http://][username:[password]@]host[:port][/[path]] or type \"!UI\" to open UI or type \"!exit\" to exit system");
        }
        Scanner s = new Scanner(System.in, "UTF-8");
        String line;
        Client client = new Client();
        while (handover || s.hasNext()) {
            if (handover) {
                line = args[0];
                handover = false;
            } else {
                line = s.nextLine();
            }
            if (line.equalsIgnoreCase("!ui")) {
               openUI();
            } else {
                if (line.equalsIgnoreCase("!exit")) {
                    System.exit(0);
                } else  {
                    try {
                        Response response = client.sendGetRequest(implicitHttp(line));
                        if (response != null) {
                            System.out.println("Connection successful - Type \"header\" or \"payload\" or \"statement\" for respective information or \"all\" for both, type \"exit\" to leave this query.");
                            while (s.hasNext()) {
                                line = s.nextLine();
                                if (line.equalsIgnoreCase("!ui")) {
                                    openUI();
                                } else if (line.equalsIgnoreCase("exit")) {
                                    break;
                                } else if (line.equalsIgnoreCase("!exit")) {
                                    cmpExit = true;
                                    break;
                                } else if (line.equalsIgnoreCase("header")) {
                                    System.out.println("Response Header = \n" + response.getHeader());
                                } else if (line.equalsIgnoreCase("payload")) {
                                    System.out.println("Response Payload = \n" + response.getPayload());
                                } else if (line.equalsIgnoreCase("statement")) {
                                    System.out.println("Response Statement =\n " + response.getStatement());
                                } else if (line.equalsIgnoreCase("all")) {
                                    System.out.println("Response Header = \n" + response.getHeader());
                                    System.out.println("Response Payload = \n" + response.getPayload());
                                    System.out.println("Response Statement = \n" + response.getStatement());
                                }
                                System.out.println("Type \"header\" or \"payload\" or \"statement\" for respective information or \"all\" for both, type \"exit\" to leave this query.");
                            }
                        } else {
                            System.out.println("Syntax Error");
                        }
                    } catch (UnknownHostException unknown) {
                        System.out.println("Unknown Host");
                    } catch (ConnectException connect) {
                        System.out.println("Connection refused by host");
                    } catch (SocketException socketException) {
                        System.out.println("Connection reset");
                    } catch (IOException e) {
                        System.out.println("Exception <" + e.getClass().getCanonicalName() + "> was thrown: " + e.getMessage());
                    }
                    if (cmpExit) System.exit(0);
                    System.out.println("Enter a url of format: [http://][username:[password]@]host[:port][/[port]] or type \"!UI\" to open UI or type \"!exit\" to exit system");
                }
            }
        }
    }

    public Response sendGetRequest(@Nullable final String request) throws IOException {
        if (request != null) {
            Matcher matcher = urlPattern.matcher(request);
            if (matcher.find()) {
                /*
                for (String namedGroup : namedGroups) {
                    System.out.println("[" + namedGroup + "]:\t" + matcher.group(namedGroup));
                }
                */
                String host = matcher.group("host");
                int port = (matcher.group("portnum")==null)? 80:Integer.parseInt(matcher.group("portnum"));
                String path = matcher.group("path");
                String authentication = matcher.group("userPass");
                String statement = prepareGET(null, host, path,authentication, false);
                Socket socket = new Socket(host, port);
                boolean sendSuccess = send(statement, socket.getOutputStream());
                if (sendSuccess) {
                    return receive(socket.getInputStream(), statement);
                }
            }

        }
        return null;
    }

    private Response receive(InputStream inputStream, @NotNull final String statement) throws SocketException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder header = new StringBuilder();
            StringBuilder payload = new StringBuilder();
            String line;
            //int i = 0; //Comments for Debug
            boolean head = true;
            while ((line = br.readLine()) != null) {
                if (head) {
                    if ("".equals(line)) {
                        head = false;
                    } else {
                        header.append(line).append("\n");
                    }
                } else {
                    payload.append(line).append("\n");
                }
                //System.out.println("RESPONSE[" + i + "] < " + line + ">");
                //i++;
            }
            return new Response(header.toString(), payload.toString(), statement);
        } catch (SocketException s) {
            throw new SocketException(s.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Contract("!null, !null -> _ ; !null, null -> false ; null,!null -> false")
    private boolean send(@NotNull final String statement, OutputStream outputStream) {
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            pw.print(statement);
            pw.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Prepares the Get-Statement.
     * @param httpVersion {@Code Double}version, default = 1.1
     * @param host {@Code String} host
     * @param path {@Code String} path, default = /
     * @param authentication {@Code String} authentication of type basic in clear or base64
     * @param connectionKeepAlive {@Code boolean} close connection ?
     * @return {@Code String} statement
     */
    private String prepareGET(Double httpVersion, @NotNull String host, @Nullable String path, @Nullable String authentication, boolean connectionKeepAlive) {
        String ls = "\r\n";
        if (path == null) {
            path = "/";
        }
        return "GET " + path + " HTTP/" + ((httpVersion != null)? httpVersion:"1.1") + ls +
                "Host: " + host + ls +
                ((authentication != null)? ("Authentication: Basic " + authentication + ls):"") +
                "Connection: " + ((connectionKeepAlive) ? "keep alive" : "close") + ls + ls;
    }

    public static Pattern getUrlPattern() {
        return urlPattern;
    }

    public static String implicitHttp(String entryText) {
        for (int i = min(http.length(), entryText.length()) - 1; (i > -1); i--) {
            if (entryText.charAt(i) != http.charAt(i) && i == 0) {
                entryText = http + entryText;
            }
        }
        return entryText;
    }

    private static void openUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JavaFXApp.main("!ui");
            }
        }).start();
        System.out.println("UI Opening - Console Application still running");
    }
}
