package com.github.jan222ik.web.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class JavaFXApp  extends Application {

    public void start(Stage stage) throws Exception {
        URL resource = JavaFXApp.class.getResource("/gui.fxml");
        System.out.println("resource = " + resource);
        Parent root = FXMLLoader.load(resource);
        stage.setTitle("SimpleWebBrowser");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(JavaFXApp.class, args);
    }
}
