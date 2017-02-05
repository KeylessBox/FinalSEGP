package Main;/**
 * Created by AndreiM on 1/28/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        primaryStage.setScene(new Scene(root,500, 400));
        primaryStage.show();
    }
}
