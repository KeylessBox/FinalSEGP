package login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for login
 */
public class LoginController {
    @FXML private Text actionTarget;
    @FXML private TextField username;
    @FXML private TextField pw;
    @FXML private Button editDetails;

    /**
     * Example how it works.
     * @param event
     */
    @FXML protected void handleSubmitButtonAction (ActionEvent event) {
        actionTarget.setText(username.getText() + " " + pw.getText());
    }
}
