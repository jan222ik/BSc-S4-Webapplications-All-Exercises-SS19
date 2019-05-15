package com.github.jan222ik;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CountCharsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String input = null;
        while (reader.ready()) {
            String s = reader.readLine();
            Matcher matcher = Pattern.compile("^input:((.)+)").matcher(s);
            if (matcher.find()) {
                input = matcher.group(1);
            }
        }
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        PrintWriter out = resp.getWriter();
        if (input != null) {
            out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><number>").append(Integer.toString(input.length())).append("</number>\n");
        }
    }
}
