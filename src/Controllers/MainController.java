package Controllers;


import Modules.File_Import.Case;
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
    protected Label caseTitle;

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
        loadTable(1);
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

    public void loadTable(int i) {
        callsData = sql.loadCalls(i);
        ColumnFactory.createCallerPNColumn(callerPhoneNumber);
        ColumnFactory.createReceiverPNColumn(receiverPhoneNumber);
        ColumnFactory.createDateColumn(date);
        ColumnFactory.createTimeColumn(time);
        ColumnFactory.createTypeOfCallColumn(typeOfCall);
        ColumnFactory.createDurationColumn(duration);
        table.setItems(callsData);
        table.setEditable(true);
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

    /**
     * Controller for "Add call" button. No one is supposed to add a call, because you already have it in the database, or any excel, csv file, so a user should
     * not be able to add a call in my opinion, but let's say he wants to, his choice.
     * Default values, that if left unattended clearly show that they are fake.
     * The method changes the table and the database at the same time.
     */
    public void addCall() {
        CallRecord cr =new CallRecord(String.valueOf(sql.getMaxCallID()+ 1), "\""+sql.loadCase(caseTitle.getText())+"\"", "0", "0", "1900/01/01", "00:00", "Standard", "00:00");
        callsData.add(cr);
        sql.addCall(cr);
        System.out.println("ADD: call");
    }

    /**
     * Controller for "Delete call" button. Same as above, a user should not be able to delete a call because this data is the kind you usually let it be.
     * Adding this functionality is just for it to be there in the case such action is wanted.
     * Deletes the call both from the table and the database.
     */
    public void deleteCall() {

        if (table.getSelectionModel().getSelectedItem() != null) {
            CallRecord record = (CallRecord) table.getSelectionModel().getSelectedItem();
            if (record != null) {
                sql.removeCall(Integer.parseInt(record.getCallID()));
                System.out.println("DELETE: call " + record.getCallID());
                callsData.remove(table.getSelectionModel().getSelectedItem());
            }
        }

    }

    /**
     * Initialises the cases on the TabPane. CaseObj is considered as one of the case entries on the pane (with its image,labels and buttons)
     */
    private void initCases() {
        /**
         * Takes Cases table from database
         */
        casesData = sql.loadCases();
        /**
         * Initialisation of Case Obj
         */
        HBox CaseObj = null;
        /**
         * Every row of the Case Table (thus, every case that exists in the database) has a status attribute (Investigating, Solved or Preliminary)
         * The for loop takes each case and checks what status it has, and then assigns the CaseObj position in the tab
         */
        for (CasesRecords tabPane : casesData) {
            /**
             * If the status of the case is the same as the text of the Investigating Tab (maybe should propose another solution)
             * then the case entry will be on that specific tab
             */
            if (tabPane.getStatus().equals(iTab.getText())) {
                /**
                 * CaseObj loads the fxml, with its nodes
                 */
                try {
                    CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/FXML/CaseObj.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                /**
                 * It adds the case values in the tab
                 */
                HBox hb = (HBox) CaseObj.getChildren().get(3);
                Button b = (Button) hb.getChildren().get(1);

                VBox temp = (VBox) CaseObj.getChildren().get(1);
                Label caseName = (Label) temp.getChildren().get(0);
                caseName.setText(tabPane.getCaseName());
                Label caseDate = (Label) temp.getChildren().get(1);
                caseDate.setText("date");
                /**
                 * Pressing a case changes the table with the proper values from the database
                 * How: It gets the name of the case, and finds its id (not suitable for cases that have the same names //TODO make this as general as possible
                 * Loads the table with only the calls at that specific id
                 * Also changes the big Case name above the table
                 */
                HBox finalCaseObj = CaseObj;
                finalCaseObj.setOnMouseClicked(event -> {
                    String s = caseName.getText();
                    int id = sql.loadCase(s);
                    loadTable(id);
                    caseTitle.setText(s);
                    //TODO Add animation (or some sort of feedback) if that specific case is shown
                });
                finalCaseObj.setOnMousePressed(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #18b5ff;");
                });
                finalCaseObj.setOnMouseReleased(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #ffffff;");
                });
                finalCaseObj.setOnMouseEntered(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #51c5ff;");
                });
                finalCaseObj.setOnMouseExited(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #ffffff;");
                });

                b.setOnAction(event -> {
                    iCase.getChildren().remove(finalCaseObj);
                });
                /**
                 * And loads them to the app
                 */
                iCase.getChildren().add(CaseObj);
            }
            /**
             * The same as above, but that's if the status is "Solved"
             */
            if (tabPane.getStatus().equals(sTab.getText())) {
                try {
                    CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/FXML/CaseObj.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                HBox hb = (HBox) CaseObj.getChildren().get(3);
                Button b = (Button) hb.getChildren().get(1);

                VBox temp = (VBox) CaseObj.getChildren().get(1);
                Label caseName = (Label) temp.getChildren().get(0);
                caseName.setText(tabPane.getCaseName());
                Label caseDate = (Label) temp.getChildren().get(1);
                caseDate.setText("date");
                /**
                 * Same as above
                 */
                HBox finalCaseObj = CaseObj;
                finalCaseObj.setOnMouseClicked(event -> {
                    String s = caseName.getText();
                    int id = sql.loadCase(s);
                    loadTable(id);
                    caseTitle.setText(s);
                });
                finalCaseObj.setOnMousePressed(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #18b5ff;");
                });
                finalCaseObj.setOnMouseReleased(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #ffffff;");
                });
                finalCaseObj.setOnMouseEntered(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #51c5ff;");
                });
                finalCaseObj.setOnMouseExited(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #ffffff;");
                });

                b.setOnAction(event -> {
                    sCase.getChildren().remove(finalCaseObj);
                });
                sCase.getChildren().add(CaseObj);
            }
            /**
             * The same as above, but that's if the status is "Preliminary"
             */
            if (tabPane.getStatus() == pTab.getText()) {
                try {
                    CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/FXML/CaseObj.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                HBox hb = (HBox) CaseObj.getChildren().get(3);
                Button b = (Button) hb.getChildren().get(1);


                VBox temp = (VBox) CaseObj.getChildren().get(1);
                Label caseName = (Label) temp.getChildren().get(0);
                caseName.setText(tabPane.getCaseName());
                Label caseDate = (Label) temp.getChildren().get(1);
                caseDate.setText("date");
                /**
                 * Same as above
                 */
                HBox finalCaseObj = CaseObj;
                finalCaseObj.setOnMouseClicked(event -> {
                    String s = caseName.getText();
                    int id = sql.loadCase(s);
                    loadTable(id);
                    caseTitle.setText(s);
                });
                finalCaseObj.setOnMousePressed(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #18b5ff;");
                });
                finalCaseObj.setOnMouseReleased(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #ffffff;");
                });
                finalCaseObj.setOnMouseEntered(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #51c5ff;");
                });
                finalCaseObj.setOnMouseExited(event -> {
                    finalCaseObj.setStyle("-fx-background-color: #ffffff;");
                });

                b.setOnAction(event -> {
                    pCase.getChildren().remove(finalCaseObj);
                });

                pCase.getChildren().add(CaseObj);
            }

        }
    }

    public void addCase(ActionEvent actionEvent) {
        HBox CaseObj = null;
        try {
            CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/FXML/CaseObj.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HBox finalCaseObj = CaseObj;
        finalCaseObj.setOnMouseClicked(event -> {
            String s = "test";
            int id = sql.loadCase(s);
            loadTable(id);
            caseTitle.setText(s);
        });

        HBox hb = (HBox) CaseObj.getChildren().get(3);
        Button b = (Button) hb.getChildren().get(1);

        finalCaseObj.setOnMousePressed(event -> {
            finalCaseObj.setStyle("-fx-background-color: #18b5ff;");
        });
        finalCaseObj.setOnMouseReleased(event -> {
            finalCaseObj.setStyle("-fx-background-color: #ffffff;");
        });
        finalCaseObj.setOnMouseEntered(event -> {
            finalCaseObj.setStyle("-fx-background-color: #51c5ff;");
        });
        finalCaseObj.setOnMouseExited(event -> {
            finalCaseObj.setStyle("-fx-background-color: #ffffff;");
        });

        System.out.println("HERE");

        b.setOnAction(event -> {
            if (pTab.isSelected()) {
                pCase.getChildren().remove(finalCaseObj);
            } else if (iTab.isSelected()) {
                iCase.getChildren().remove(finalCaseObj);
            } else {
                sCase.getChildren().remove(finalCaseObj);
            }
        });


        if (pTab.isSelected()) {
            pCase.getChildren().add(finalCaseObj);
        } else if (iTab.isSelected()) {
            iCase.getChildren().add(finalCaseObj);
        } else {
            sCase.getChildren().add(finalCaseObj);
        }
    }
}
