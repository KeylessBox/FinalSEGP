package controllers;

import modules.manageAccounts.CreateAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Florin on 2/5/2017.
 */
public class SignUpController {
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private PasswordField pass;
    @FXML
    private Button signUp;
    @FXML
    private Button back;

    /**
     * Sign up
     * @param event
     * @throws IOException
     */
    @FXML
    protected void signUp(ActionEvent event)throws IOException {
        CreateAccount.createAccount(name.getText(), surname.getText(), email.getText(), pass.getText(), "user");

        Node node = (Node) event.getSource();
        Stage stage3 = (Stage) node.getScene().getWindow();
        Parent root3 = FXMLLoader.load(getClass().getResource("/fxml/signIn.fxml"));/* Exception */
        Scene scene3 = new Scene(root3);

        stage3.setScene(scene3);
        stage3.centerOnScreen();
        stage3.setResizable(true);
        stage3.show();
    }

    @FXML
    protected void back(ActionEvent event) throws IOException{
        Node node = (Node) event.getSource();
        Stage stage3 = (Stage) node.getScene().getWindow();
        Parent root3 = FXMLLoader.load(getClass().getResource("/fxml/signIn.fxml"));/* Exception */
        Scene scene3 = new Scene(root3);

        stage3.setScene(scene3);
        stage3.centerOnScreen();
        stage3.setResizable(true);
        stage3.show();
    }
}
