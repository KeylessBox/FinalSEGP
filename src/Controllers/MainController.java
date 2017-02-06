package Controllers;

import Modules.File_Import.ImportCSV;
import Modules.Table.CallRecord;
import Modules.Table.CallsTable;
import SQL.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */

public class MainController {
    SQL sql = new SQL();
    Modules.Table.CallsTable ColumnFactory = new CallsTable();
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
    protected Button addVictim;
    @FXML
    protected HBox VSBox;
    @FXML
    protected Pane Victim1;
    @FXML
    protected Pane Suspect1;

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
    public void addVictim() {
        System.out.println("VICTIM");
        Pane suspectNote = null;
        try {
            suspectNote = (Pane) FXMLLoader.load(getClass().getResource("/FXML/Victim.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        VSBox.setHgrow(suspectNote, Priority.ALWAYS);
        VSBox.getChildren().addAll(suspectNote);
    }

    @FXML
    public void addSuspect() {
        System.out.println("Suspect");
        Pane victimNote = null;
        try {
            victimNote = (Pane) FXMLLoader.load(getClass().getResource("/FXML/Suspect.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        VSBox.setHgrow(victimNote, Priority.ALWAYS);
        VSBox.getChildren().addAll(victimNote);

    }


    @FXML
    public void initialize() {
        date.setMinWidth(50);
        date.setMaxWidth(50);
        date.setPrefWidth(50);
        data = sql.loadCalls();
        ColumnFactory.createCallerPNColumn(callerPhoneNumber);
        ColumnFactory.createReceiverPNColumn(receiverPhoneNumber);
        ColumnFactory.createDateColumn(date);
        ColumnFactory.createTimeColumn(time);
        ColumnFactory.createTypeOfCallColumn(typeOfCall);
        ColumnFactory.createDurationColumn(duration);
        table.setItems(data);
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
            data.add(new CallRecord(String.valueOf(sql.getID() + 1), "0", "0", "0", "0", "0", "0", "0"));
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
                if (record != null) {
//                    System.out.println();
//                    if (!(record.getCallID().equals(""))) {
//                        //sql.removeCall(record.getCallID());
//                        System.out.println("DELETE: call " + record.getCallID());
//                    }
                    data.remove(table.getSelectionModel().getSelectedItem());
                }
            }
        } else {
            System.out.println("HERE");
        }
    }

}
