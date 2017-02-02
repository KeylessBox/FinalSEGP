package main;


import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */

public class MainController {

    @FXML protected void importCSV() {
        /**
         * New window, where you choose what file to import
         */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        /**
         * If there is a file selected then the information is transmitted to the database
         */
        if (file != null) {
            String filePath = file.getPath();
            filePath = filePath.replace("\\","\\\\");
            ImportCSV.importcsv(filePath);
        }
    }
}
