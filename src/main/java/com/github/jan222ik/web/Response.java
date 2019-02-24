package com.github.jan222ik.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Response {
    private String header;
    private String payload;
    private Pattern headerPattern = Pattern.compile("(HTTP/\\d.\\d) (\\d{3}) (.*\n)");

    public Response(String header, String payload) {
        this.header = header;
        this.payload = payload;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getStatusCode() {
        Matcher matcher = headerPattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }

    public String getReason() {
        Matcher matcher = headerPattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(3);
        }
        return "";
    }
    /*
                    Pattern p = Pattern.compile("^(HTTP/1.[0|1]) (\\d\\d\\d) (.*)");
                    Matcher matcher1 = p.matcher();
                    if (matcher1.find()) {

                        String version = matcher1.group(1);
                        String code = matcher1.group(2);
                        String reason = matcher1.group(3);
                        System.out.println("version = " + version);
                        System.out.println("code = " + code);
                        System.out.println("reason = " + reason);
                    }
     */
}
