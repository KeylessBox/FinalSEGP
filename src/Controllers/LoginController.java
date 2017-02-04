package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import Modules.Login.LoginDB;
import Modules.Login.Login;
import javafx.stage.Stage;


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
    @FXML protected void logIn (ActionEvent event) {
        if (!LoginDB.checkUserDetails(username.getText(), pw.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong Details");
            alert.setHeaderText("Wrong Username or Password");
            alert.setContentText("Please, re-enter your Log In Details.");

            alert.showAndWait();

        }
        else
        {
            
        }
    }
}
