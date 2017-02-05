package Controllers;

import Modules.File_Import.ImportCSV;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import Modules.Login.LoginDB;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;

/**
 * Controller for login
 */
public class LoginController {

    @FXML
    private Text actionTarget;
    @FXML
    private TextField username;
    @FXML
    private TextField pw;
    @FXML
    private Button editDetails;

    /**
     * Example how it works.
     *
     * @param event
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
             * Program moves to the Main Window
             */

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root2 = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));/* Exception */
            Scene scene = new Scene(root2);

            scene.setOnDragOver(event1 -> {
                Dragboard db = event1.getDragboard();
                if (db.hasFiles()) {
                    event1.acceptTransferModes(TransferMode.COPY);
                } else {
                    event1.consume();
                }
            });

            // Dropping over surface
            
            scene.setOnDragDropped(event2 -> {
                Dragboard db = event2.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        System.out.println(filePath);
                        if (!filePath.equals("")) {
                            filePath = filePath.replace("\\", "\\\\");
                            ImportCSV.importcsv(filePath);
                        }
                    }
                }
                event2.setDropCompleted(success);
                event2.consume();
            });

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();
        }
    }
}
