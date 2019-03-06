package com.github.jan222ik.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Janik Mayr on 23.02.2019
 */
public class Response {
    private String header;
    private String payload;
    private String statement;
    private Pattern headerPattern = Pattern.compile("(HTTP/\\d.\\d) (\\d{3}) (.*\n)");

    public Response(String header, String payload, String statement) {
        this.header = header;
        this.payload = payload;
        this.statement = statement;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
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
}
