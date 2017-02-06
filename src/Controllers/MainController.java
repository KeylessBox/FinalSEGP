package Controllers;


import Modules.File_Import.ImportCSV;
import Modules.ManageAccounts.User;
import Modules.Table.CallRecord;
import Modules.Table.CallsTable;
import Modules.Table.CasesRecords;
import SQL.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */

public class MainController {
    
    SQL sql = new SQL();
    Modules.Table.CallsTable ColumnFactory = new CallsTable();
    private ObservableList<CallRecord> callsData;
    private ObservableList<CasesRecords> casesData;

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
    protected Label userLabel;
    @FXML
    protected Tab iTab;
    @FXML
    protected Tab sTab;
    @FXML
    protected Tab pTab;
    @FXML
    protected VBox iCase;
    @FXML
    protected VBox sCase;
    @FXML
    protected VBox pCase;
    @FXML
    protected HBox VSBox;
    @FXML
    protected ScrollPane scrollPane;

    @FXML
    protected void importCSV() {
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
            filePath = filePath.replace("\\", "\\\\");
            ImportCSV.importcsv(filePath);
        }
    }

    @FXML
    public void setUserLabel() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("src/RES/tmp.txt")));
            String rd = read.readLine();
            rd = User.userGetName(rd);
            rd = rd.replace(" ", "\n");
            userLabel.setText(rd);
            read.close();

        } catch (IOException e) {

        } finally {
            new File("src/RES/tmp.txt").delete();
        }

    }

    @FXML
    public void initialize() {
        initCases();
        setUserLabel();
        date.setMinWidth(50);
        date.setMaxWidth(50);
        date.setPrefWidth(50);
        callsData = sql.loadCalls();
        ColumnFactory.createCallerPNColumn(callerPhoneNumber);
        ColumnFactory.createReceiverPNColumn(receiverPhoneNumber);
        ColumnFactory.createDateColumn(date);
        ColumnFactory.createTimeColumn(time);
        ColumnFactory.createTypeOfCallColumn(typeOfCall);
        ColumnFactory.createDurationColumn(duration);
        table.setItems(callsData);
        table.setEditable(true);
        search();
        VSBox.setAlignment(Pos.TOP_LEFT);
        VSBox.setSpacing(50);
        VSBox.setBackground(Background.EMPTY);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    public void addVictim() {
        System.out.println("VICTIM");
        Pane victimNote = null;
        try {
            victimNote = (Pane) FXMLLoader.load(getClass().getResource("/FXML/Victim.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Pane temp = (Pane)victimNote.getChildren().get(0);
        Button delete = (Button)temp.getChildren().get(1);
        Pane finalVictimNote = victimNote;
        delete.setOnAction(event -> {
            VSBox.getChildren().remove(finalVictimNote);
        });
        //VSBox.setHgrow(victimNote, Priority.ALWAYS);
        VSBox.getChildren().addAll(victimNote);
        
    }

    public void addSuspect() {
        System.out.println("Suspect");
        Pane suspectNote = null;
        try {
            suspectNote = (Pane) FXMLLoader.load(getClass().getResource("/FXML/Suspect.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Pane temp = (Pane)suspectNote.getChildren().get(0);
        Button delete = (Button)temp.getChildren().get(0);
        Pane finalSuspectNote = suspectNote;
        delete.setOnAction(event -> {
            VSBox.getChildren().remove(finalSuspectNote);
        });

        //VSBox.setHgrow(suspectNote, Priority.ALWAYS);
        VSBox.getChildren().add(suspectNote);
    }

    public void search() {
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (txtField.textProperty().get().isEmpty()) {
                table.setItems(callsData);
                return;
            }

            ObservableList<CallRecord> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<CallRecord, ?>> cols = table.getColumns();

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
            table.setItems(tableItems);
        });

    }

    @FXML
    public void addCall() {
        if (callsData != null) {
            callsData.add(new CallRecord(String.valueOf(sql.getID() + 1), "0", "0", "0", "0", "0", "0", "0"));
            System.out.println("ADD: call");
            //sql.addCall();
        } else {
            System.out.println("HERE");
        }
    }

    @FXML
    public void deleteCall() {
        if (callsData != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                CallRecord record = (CallRecord) table.getSelectionModel().getSelectedItem();
                if (record != null) {
//                    System.out.println();
//                    if (!(record.getCallID().equals(""))) {
//                        //sql.removeCall(record.getCallID());
//                        System.out.println("DELETE: call " + record.getCallID());
//                    }
                    callsData.remove(table.getSelectionModel().getSelectedItem());
                }
            }
        } else {
            System.out.println("HERE");
        }
    }

    /**
     * Initialises the cases on the TabPane
     */
    private void initCases() {
        casesData = sql.loadCases();

        int[] i = new int[2];
        for (CasesRecords tabPane : casesData){
            if (tabPane.getStatus().equals(iTab.getText()))  {
                iCase.getChildren().add(i[0],new Label(tabPane.getCaseName()));
                i[0]++;
            }
            if (tabPane.getStatus().equals(sTab.getText())) {
                sCase.getChildren().add(i[1],new Label(tabPane.getCaseName()));
                i[1]++;
            }
            if (tabPane.getStatus() == pTab.getText()) {
                pCase.getChildren().add(i[2],new Label(tabPane.getCaseName()));
                i[2]++;
            }
        }
    }
}
