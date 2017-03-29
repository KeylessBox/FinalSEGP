package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import modules.login.LoginDB;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller for login
 */
public class LoginController {


    @FXML
    private TextField username;
    @FXML
    private TextField pw;

    public String user = "";
    public MainController main = new MainController();
    /**
     * The sign up process
     *
     * @param event
     */
    @FXML
    protected void signUp(ActionEvent event)throws IOException{

        Node node = (Node) event.getSource();
        Stage stage3 = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));/* Exception */
        Scene scene3 = new Scene(root);

        stage3.setScene(scene3);
        stage3.centerOnScreen();
        stage3.setResizable(true);
        stage3.show();
    }

    /**
     * Log in method. The user must provide existing user details in the database, else there is no access to the main app
     * @param event
     * @throws IOException
     */
    @FXML
    protected void logIn(ActionEvent event) throws IOException {

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
            stage.setResizable(false);
            stage.show();
        }
    }
}
