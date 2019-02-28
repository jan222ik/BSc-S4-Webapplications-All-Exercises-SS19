package com.github.jan222ik.web.ui;

import com.github.jan222ik.web.Client;
import com.github.jan222ik.web.Response;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.URL;    //Import only for JavaFx
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.min;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Controller implements Initializable {
    @FXML private Text statement;
    @FXML private Text headeroutput;
    @FXML private WebView webView;
    @FXML private TextField text_entry;
    @FXML private Label status;
    @FXML private Label reason;
    @FXML private Text text;


    public void executeGET() {
        String entryText = Client.implicitHttp(text_entry.getText());
        status.setText("Executing...");
        text_entry.setStyle("-fx-border-color: yellow");
        try {
            Response response = new Client().sendGetRequest(entryText);
            if (response != null) {
                text_entry.setStyle("-fx-border-color: green");
                status.setText(response.getStatusCode());
                reason.setText(response.getReason());
                if (text != null) {
                    text.setText(response.getPayload());
                    headeroutput.setText(response.getHeader());
                    webView.getEngine().loadContent(response.getPayload());
                    statement.setText(response.getStatement());
                }
            } else {
                status.setText("Syntax Error");
                text_entry.setStyle("-fx-border-color: red");
            }
        } catch (UnknownHostException unknownHost) {
            status.setText("Unknown Host");
            text_entry.setStyle("-fx-border-color: red");
        } catch (ConnectException noCon) {
            status.setText("No connection to host");
            text_entry.setStyle("-fx-border-color: red");
        } catch (SocketException socket) {
            status.setText("Connection reset");
            text_entry.setStyle("-fx-border-color: red");
        } catch (IOException e) {
            e.printStackTrace();
            text_entry.setStyle("-fx-border-color: red");
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        text.setText("Enter URL - http:// is implicit.\n\nExamples: \n\thttp://localhost:80\n\twww.google.com\n");
    }

    public void checkInputSyntax() {
        Pattern p = Client.getUrlPattern();
        String entryText = Client.implicitHttp(text_entry.getText());
        Matcher matcher = p.matcher(entryText);
        String replace = text_entry.getStyle().replace("-fx-border-color: green", "").replace("-fx-border-color: red", "");
        text_entry.setStyle(replace);
        if (matcher.find()) {
            text_entry.setStyle("-fx-border-color: green");
        } else {
            text_entry.setStyle("-fx-border-color: red");
        }
    }
}
