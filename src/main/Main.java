package main;/**
 * Created by AndreiM on 1/28/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//I added a comment, YAY!!!
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//sadadasdasda
        //new branch bro
        //something something something truly inspiring
        //TODO start working as a team
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));

        primaryStage.setTitle("Sign In");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
