package Controllers;

import Modules.File_Import.ImportCSV;
import Modules.Table.CallRecord;
import Modules.Table.CallsTable;
import SQL.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */

public class MainController {
    SQL sql = new SQL();
    Modules.Table.CallsTable doColumn = new CallsTable();
    private ObservableList<CallRecord> data;

    @FXML
    protected Button addCall;
    @FXML
    protected Button deleteCall;
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
    protected TableView table;
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
        data = sql.loadCalls();
        doColumn.createCallerPNColumn(callerPhoneNumber);
        doColumn.createReceiverPNColumn(receiverPhoneNumber);
        doColumn.createDateColumn(date);
        doColumn.createTimeColumn(time);
        doColumn.createTypeOfCallColumn(typeOfCall);
        doColumn.createDurationColumn(duration);
        table.setItems(data);
        table.setEditable(true);
        search();
    }

    public void search() {

        txtField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (txtField.textProperty().get().isEmpty()) {
                table.setItems(data);

                return;
            }

            ObservableList<CallRecord> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<CallRecord, ?>> cols = table.getColumns();

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(data.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if (cellValue.contains(txtField.textProperty().get().toLowerCase())) {
                        tableItems.add(data.get(i));
                        break;
                    }
                }
            }
            table.setItems(tableItems);
        });
    }

    @FXML
    public void addCall() {
        if (data != null) {
            data.add(new CallRecord(String.valueOf(sql.getID() + 1), "test", "test", "test", "test", "test", "test", "test"));
            System.out.println("ADD: call");
            //sql.addCall();
        } else {
            System.out.println("HERE");
        }
    }

    @FXML
    public void deleteCall() {
        if (data != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                CallRecord record = (CallRecord) table.getSelectionModel().getSelectedItem();
                if (!(record.getCallID().isEmpty())) {
                    //sql.removeCall(record.getCallID());
                    System.out.println("DELETE: call " + record.getCallID());
                }
            }
            data.remove(table.getSelectionModel().getSelectedItem());
        } else {
            System.out.println("HERE");
        }
    }

}
