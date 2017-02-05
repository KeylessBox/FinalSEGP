package Main;/**
 * Created by AndreiM on 1/28/2017.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    // comment
    public static void main(String[] args) {
        launch(args);
    }

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/signIn.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sign In");
        Scene scene = new Scene(root,500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
