import com.sun.istack.internal.Nullable;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Client {
    Socket socket = null;
    public static void main(String... args) {
        Scanner s = new Scanner(System.in);
        String line = null;
        if (s.hasNext()) {
            line = s.nextLine();
        }
        new Client().sendGetRequest(line);
    }

    public void sendGetRequest(@Nullable String request) {
        Pattern regex = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        if (request != null) {
            Matcher matcher = regex.matcher(request);
            if (matcher.find()) {
                String host = "localhost";//TODO
                int port = 80;
                try {
                    socket = new Socket(host, port);
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(outputStream);
                    pw.println("GET / HTTP/1.1");
                    pw.println("Host: " + host);
                    pw.println("Connection: close");
                    pw.println("\n");
                    pw.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String t;
                    StringBuilder sB = new StringBuilder();
                    boolean firstLine = true;
                    while((t = br.readLine()) != null) {
                        System.out.println(t);
                        if (firstLine) {
                            sB.append(t);
                            firstLine = false;
                        }
                    }
                    System.out.println("sB = " + sB.toString());
                    Pattern p = Pattern.compile("^(HTTP/1.[0|1]) (\\d\\d\\d) (.*)");
                    Matcher matcher1 = p.matcher(sB.toString());
                    if (matcher1.find()) {

                        String version = matcher1.group(1);
                        String code = matcher1.group(2);
                        String reason = matcher1.group(3);
                        System.out.println("version = " + version);
                        System.out.println("code = " + code);
                        System.out.println("reason = " + reason);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String prepareGET(double httpVersion, String host, boolean connectionKeepAlive) {

    }
}
