package com.github.jan222ik.web.ui;

import com.github.jan222ik.web.Client;
import com.github.jan222ik.web.Response;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.min;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Controller implements Initializable {
    @FXML private WebView webView;
    @FXML private TextField text_entry;
    @FXML private Label status;
    @FXML private Label reason;
    @FXML private Text text;
    private static final String http = "http://";
    public void executeGET() {
        String entryText = implicitHttp();
        Pattern p = Client.getUrlPattern();
        Matcher matcher = p.matcher(entryText); //TODO SIMPLIFY
        if (matcher.find()) {
            status.setText("Executing...");
            text_entry.setStyle("-fx-border-color: blue");
            try {
                Response response = new Client().sendGetRequest(entryText);
                if (response != null) {
                    text_entry.setStyle("-fx-border-color: green");
                    status.setText(response.getStatusCode());
                    reason.setText(response.getReason());
                    if (text != null) {
                        text.setText(response.getPayload());
                        webView.getEngine().loadContent(response.getPayload());
                    }
                }
            } catch (UnknownHostException unknownHost) {
                status.setText("Unknown Host");
                text_entry.setStyle("-fx-border-color: red");
            } catch (ConnectException noCon) {
                status.setText("No connection to host");
                text_entry.setStyle("-fx-border-color: red");
            } catch (IOException e) {
                e.printStackTrace();
                text_entry.setStyle("-fx-border-color: red");
            }
        } else {
            text_entry.setStyle("-fx-border-color: red");
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        text.setText("Enter URL http:// is implicit.\n\nExamples: \n\thttp://localhost:80\n");
    }

    public void checkInputSyntax() {
        Pattern p = Client.getUrlPattern();
        String entryText = implicitHttp();
        Matcher matcher = p.matcher(entryText);
        String replace = text_entry.getStyle().replace("-fx-border-color: green", "").replace("-fx-border-color: red", "");
        text_entry.setStyle(replace);
        if (matcher.find()) {
            text_entry.setStyle("-fx-border-color: green");
        } else {
            text_entry.setStyle("-fx-border-color: red");
        }
    }

    private String implicitHttp() {
        String entryText = text_entry.getText();
        for (int i = min(http.length(),entryText.length())-1; (i > -1); i--) {
            if (entryText.charAt(i) != http.charAt(i) && i == 0) {
                entryText = http + entryText;
            }
        }
        return entryText;
    }
}
