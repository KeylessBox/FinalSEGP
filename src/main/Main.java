package main;
/**
 * Created by AndreiM on 1/28/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Start of the application.
     * @param args
     * 
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * It is a hierarchical structure (like trees from DSA). Someone is the parent of someone else. Some have children. some do not. And the ancestor of them all? (G)root.
     * If you instantiate one, you will probably take its children as well.
     * Loads the fxml file from the brackets into the root parent. This parent needs to be assigned to a scene ( = part of the window without the top bar) with some predefined limits
     * The stage takes the scene and shows it to the user (stage is the window itself).
     * So, it loads all the front and back-end of the app(at this specific point in app's session life), and prepares it in root. After everything is ready to go, it shows itself to the user.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
//        primaryStage.setResizable(false);
//        primaryStage.setTitle("Sign In");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(1000.0);
        primaryStage.setMinWidth(1812.0);
//        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
