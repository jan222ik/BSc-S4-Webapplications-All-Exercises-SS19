package com.github.jan222ik.web.ui;

import com.github.jan222ik.web.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class JavaFXApp extends Application {

    public void start(Stage stage) throws Exception {
        URL resource = JavaFXApp.class.getResource("/gui.fxml");
        Parent root = FXMLLoader.load(resource);
        FXMLLoader l = new FXMLLoader();
        l.getController();
        stage.setTitle("SimpleWebBrowser");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Type \"!ui\" to open UI or type \"!console\" to continue in console");
            Scanner scanner = new Scanner(System.in, "UTF-8");
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("!ui")) {
                    Application.launch(JavaFXApp.class, (String) null);     //Cast for vararg def
                    break;
                } else if (line.equalsIgnoreCase("console") || line.equalsIgnoreCase("!console")) {
                    Client.main((String) null);   //Cast vor varArgs
                    break;
                } else {
                    System.out.println("Unknown Command");
                    System.out.println("Type \"!ui\" to open UI or type \"!console\" to continue in console");
                }
            }
        } else if (args.length == 1) {
            if ((args[0].charAt(0) == '!')) {
                if (args[0].equalsIgnoreCase("!UI")) {
                    Application.launch(JavaFXApp.class, (String) null);     //Cast for vararg def
                } else if (args[0].equalsIgnoreCase("!console")) {
                    Client.main((String) null);
                } else {
                    System.out.println("Unknown !command. Known Args: \"!console\" and \"!ui\"");
                }
            } else {
                System.out.println("Found: " + args[0] + " - try to open url!");
                Client.main(args[0]);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("!console") && args[1].charAt(0)!='!') {
                Client.main(args[1]);
            } else if (args[1].equalsIgnoreCase("!console") && args[0].charAt(0) != '!') {
                Client.main(args[0]);
            } else {
                System.out.println("Args could not be parsed correctly: Possible Combinations for args of length 2: \"URL\", \"!console\" and \"!console\", \"URL\"");
            }
        } else {
            System.out.println("Too many varargs");
        }
    }
}
