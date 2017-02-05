package Controllers;


import Modules.File_Import.ImportCSV;
import Modules.Table.CallsRecord;
import Modules.Table.CallsTable;
import Modules.Table.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Modules.*;

import java.io.File;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */

public class MainController {
    Modules.Table.SQL sql = new SQL();
    Modules.Table.CallsTable doColumn = new CallsTable();
    private ObservableList<CallsRecord> callsData;

    @FXML
    protected TableColumn callerPhoneNumber;
    @FXML
    protected TableColumn receiverPhoneNumber;
    @FXML
    protected TableColumn date;
    @FXML
    protected TableColumn time;
    @FXML
    protected TableColumn typeOfCall;
    @FXML
    protected TableColumn duration;
    @FXML
    protected TableView mainTable;
    @FXML
    protected TextField txtField;

    @FXML
    protected void importCSV() {
        /**
         * New window, where you choose what file to import BITCH
         */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        /**
         * If there is a file selected then the information is transmitted to the database
         */
        if (file != null) {
            String filePath = file.getPath();
            filePath = filePath.replace("\\", "\\\\");
            ImportCSV.importcsv(filePath);
        }
    }

    @FXML
    public void initialize() {

        date.setMinWidth(50);
        date.setMaxWidth(50);
        date.setPrefWidth(50);
        callsData = sql.loadCalls();
        doColumn.createCallerPNColumn(callerPhoneNumber);
        doColumn.createReceiverPNColumn(receiverPhoneNumber);
        doColumn.createDateColumn(date);
        doColumn.createTimeColumn(time);
        doColumn.createTypeOfCallColumn(typeOfCall);
        doColumn.createDurationColumn(duration);
        mainTable.setItems(callsData);
        mainTable.setEditable(true);
        search();

    }


    public void search(){
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (txtField.textProperty().get().isEmpty()) {
                mainTable.setItems(callsData);
                return;
            }

            ObservableList<CallsRecord> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<CallsRecord, ?>> cols = mainTable.getColumns();

            for (int i = 0; i < callsData.size(); i++) {
                for (int j = 0; j < cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(callsData.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if (cellValue.contains(txtField.textProperty().get().toLowerCase())) {
                        tableItems.add(callsData.get(i));
                        break;
                    }
                }
            }
            mainTable.setItems(tableItems);
        });

    }


}
