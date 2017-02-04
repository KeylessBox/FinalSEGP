package Controllers;
import Modules.Login.LoginDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for login
 */
public class LoginController {

    @FXML private Text actionTarget;
    @FXML private TextField username;
    @FXML private PasswordField pw;
    @FXML private Button editDetails;

    /**
     * Checks if the details entered correspond to an account
     * that is located in database and says below the
     * "Log in" button either true or false
     */
    @FXML protected void handleSubmitButtonAction (ActionEvent event) {
        actionTarget.setText(LoginDB.checkUserDetails(username.getText(), pw.getText()).toString());
    }
    @FXML Button loginButton;

    @FXML protected void logIn (ActionEvent event) {
        System.out.println(LoginDB.checkUserDetails(username.getText(), pw.getText()));
    }
}
