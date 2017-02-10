package Controllers;


import Modules.File_Import.ImportCSV;
import Modules.ManageAccounts.User;
import Modules.Table.CallRecord;
import Modules.Table.CallsTable;
import Modules.Table.CasesRecords;
import SQL.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private ObservableList<CallRecord> searchData;
    private ObservableList<CallRecord> callsData;
    private ObservableList<CasesRecords> casesData;

    @FXML
    private ScrollPane scrollPane2;
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
    protected TextField textField;
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
    protected ScrollPane scrollPane;
    @FXML
    private VBox caseFilesCT;
    @FXML
    private HBox notesCT;

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
        notesCT.setAlignment(Pos.TOP_LEFT);
        notesCT.setSpacing(50);
        notesCT.setBackground(Background.EMPTY);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane2.setPannable(true);
        scrollPane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        searchData = callsData;
    }

    public void addVictim() {
        System.out.println("VICTIM");
        Pane victimNote = null;
        try {
            victimNote = (Pane) FXMLLoader.load(getClass().getResource("/FXML/Victim.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Pane temp = (Pane) victimNote.getChildren().get(0);
        Button delete = (Button) temp.getChildren().get(1);
        Pane finalVictimNote = victimNote;
        delete.setOnAction(event -> {
            notesCT.getChildren().remove(finalVictimNote);
            searchData = callsData;
            table.setItems(searchData);
        });
        VBox vbox = (VBox) temp.getChildren().get(0);
        TextField txtField = (TextField) vbox.getChildren().get(1);
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (txtField.textProperty().get().isEmpty()) {
                searchData = callsData;
                table.setItems(searchData);
            } else {

                ObservableList<CallRecord> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<CallRecord, ?>> cols = table.getColumns();

                for (int i = 0; i < searchData.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        if (j == 1) {
                            TableColumn col = cols.get(j);
                            String cellValue = col.getCellData(searchData.get(i)).toString();
                            cellValue = cellValue.toLowerCase();
                            if (cellValue.contains(txtField.textProperty().get().toLowerCase())) {
                                tableItems.add(searchData.get(i));
                                break;
                            }
                        }
                    }
                }
                if (searchData != tableItems) {
                    searchData = tableItems;
                    table.setItems(tableItems);
                }
            }
        });

        notesCT.getChildren().addAll(victimNote);

    }

    public void addSuspect() {
        System.out.println("Suspect");
        Pane suspectNote = null;
        try {
            suspectNote = (Pane) FXMLLoader.load(getClass().getResource("/FXML/Suspect.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Pane temp = (Pane) suspectNote.getChildren().get(0);
        Button delete = (Button) temp.getChildren().get(0);
        Pane finalSuspectNote = suspectNote;
        delete.setOnAction(event -> {
            notesCT.getChildren().remove(finalSuspectNote);
            searchData = callsData;
            table.setItems(searchData);
        });

        VBox vbox = (VBox) temp.getChildren().get(1);
        TextField txtField = (TextField) vbox.getChildren().get(1);
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (txtField.textProperty().get().isEmpty()) {
                searchData = callsData;
                table.setItems(searchData);
            } else {

                ObservableList<CallRecord> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<CallRecord, ?>> cols = table.getColumns();

                for (int i = 0; i < searchData.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        if (j == 0) {
                            TableColumn col = cols.get(j);
                            String cellValue = col.getCellData(searchData.get(i)).toString();
                            System.out.println("Column: " + cellValue);
                            cellValue = cellValue.toLowerCase();
                            if (cellValue.contains(txtField.textProperty().get().toLowerCase())) {
                                tableItems.add(searchData.get(i));
                                break;
                            }
                        }
                    }
                }
                if (searchData != tableItems) {
                    searchData = tableItems;
                    table.setItems(searchData);
                }
            }
        });

        notesCT.getChildren().add(suspectNote);

    }

    public void addCaseFile(ActionEvent actionEvent) {
        System.out.println("Suspect");
        Pane CaseFile = null;
        try {
            CaseFile = (Pane) FXMLLoader.load(getClass().getResource("/FXML/CaseFile.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HBox temp = (HBox) CaseFile.getChildren().get(1);
        Button delete = (Button) temp.getChildren().get(2);
        Pane finalCaseFile = CaseFile;
        delete.setOnAction(event -> {
            caseFilesCT.getChildren().remove(finalCaseFile);
        });
        caseFilesCT.getChildren().add(CaseFile);
    }

    public void search() {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField.textProperty().get().isEmpty()) {
                searchData = callsData;
                table.setItems(searchData);
            } else {

                ObservableList<CallRecord> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<CallRecord, ?>> cols = table.getColumns();

                for (int i = 0; i < searchData.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(searchData.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(textField.textProperty().get().toLowerCase())) {
                            tableItems.add(searchData.get(i));
                            break;
                        }
                    }
                }
                if (searchData != tableItems) {
                    searchData = tableItems;
                    table.setItems(searchData);
                }
            }

        });
    }

    public void addCall() {
        if (searchData != null) {
            searchData.add(new CallRecord(String.valueOf(sql.getID() + 1), "0", "0", "0", "0", "0", "0", "0"));
            System.out.println("ADD: call");
            //sql.addCall();
        } else {
            System.out.println("HERE");
        }
    }

    public void deleteCall() {
        if (searchData != null) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                CallRecord record = (CallRecord) table.getSelectionModel().getSelectedItem();
                if (record != null) {
//                    System.out.println();
//                    if (!(record.getCallID().equals(""))) {
//                        //sql.removeCall(record.getCallID());
//                        System.out.println("DELETE: call " + record.getCallID());
//                    }
                    searchData.remove(table.getSelectionModel().getSelectedItem());
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
        for (CasesRecords tabPane : casesData) {
            if (tabPane.getStatus().equals(iTab.getText())) {
                iCase.getChildren().add(i[0], new Label(tabPane.getCaseName()));
                i[0]++;
            }
            if (tabPane.getStatus().equals(sTab.getText())) {
                sCase.getChildren().add(i[1], new Label(tabPane.getCaseName()));
                i[1]++;
            }
            if (tabPane.getStatus() == pTab.getText()) {
                pCase.getChildren().add(i[2], new Label(tabPane.getCaseName()));
                i[2]++;
            }
        }
    }

}
