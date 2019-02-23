import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Janik Mayr on 21.02.2019
 */
public class Controller implements Initializable {
    public TextField text_entry;
    public Label status;
    public Label response;
    public TextFlow payload;
    public Button btn;

    private Pattern p = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    public void executeGET() {
        String text = text_entry.getText();
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            new Client().sendGetRequest(text);
        } else {
            //TODO ERROR IN SYNTAX
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
