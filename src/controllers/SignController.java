package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
    void signUp(ActionEvent event) {

    }

    @FXML
    void signIn(ActionEvent event) {

    }

}
