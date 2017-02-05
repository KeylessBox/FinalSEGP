package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import Modules.Login.LoginDB;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * Controller for login
 */
public class LoginController{

    @FXML private Text actionTarget;
    @FXML private TextField username;
    @FXML private TextField pw;
    @FXML private Button editDetails;

    /**
     * Example how it works.
     * @param event
     */
    @FXML protected void logIn (ActionEvent event) throws IOException{

        if (!LoginDB.checkUserDetails(username.getText(), pw.getText())) {
            /**
             * If the account details introduced are wrong
             * Alert Box is shown
             */
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong Details");
            alert.setHeaderText("Wrong Username or Password");
            alert.setContentText("Please, re-enter your Log In Details.");

            alert.showAndWait();

        }
        else
        {
            /**
             * If the account details entered are correct,
             * Program moves to the Main Window
             */

            Node node=(Node) event.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root2 = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));/* Exception */
            Scene scene = new Scene(root2);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();
        }
    }
}
