package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modules.login.LoginDB;
import modules.manageAccounts.CreateAccount;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SignController {

    @FXML
    private Pane SignUp;

    @FXML
    private Pane SignIn;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField email;

    @FXML
    private Button signUp;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private PasswordField pw;

    @FXML
    private TextField username;

    @FXML
    private Pane movingpart;

    public String user = "";

    @FXML
    void remember(ActionEvent event) {

    }

    @FXML
    void changeToSignUp(ActionEvent event) {
        SignIn.setVisible(true);
        SignUp.setVisible(false);
        movingpart.setPrefWidth(49);
        movingpart.setLayoutX(47);
    }

    @FXML
    void changeToSignIn(ActionEvent event) {
        SignIn.setVisible(false);
        SignUp.setVisible(true);
        movingpart.setPrefWidth(125);
        movingpart.setLayoutX(151);
    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        CreateAccount.createAccount(name.getText(), surname.getText(), email.getText(), pass.getText(), "user");
        SignIn.setVisible(true);
        SignUp.setVisible(false);
        movingpart.setPrefWidth(49);
        movingpart.setLayoutX(47);
    }


    @FXML
    void signIn(ActionEvent event) throws IOException {

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

        } else {
            /**
             * If the account details entered are correct,
             * Program moves to the main Window
             * It passes the account details to a file (that will be soon deleted).
             */
            user = username.getText();
            FileWriter writer = new FileWriter(new File("src/res/tmp.txt"));
            writer.write(user);
            writer.close();
            /**
             * The connection to the main app
             */
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));/* Exception */
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

}
