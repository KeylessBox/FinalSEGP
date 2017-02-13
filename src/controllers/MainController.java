package controllers;

import modules.file_import.ImportCSV;
import modules.manageAccounts.User;
import modules.table.CallRecord;
import modules.table.CallsTable;
import modules.table.CasesRecords;
import modules.table.NoteRecord;
import sql.SQL;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */

public class MainController {
    /**
     * sql - sql functionality
     * columnFactory - building the columns of the table
     * searchData -
     * callsData - the calls from the database
     * casesData - the cases from the database
     * notesData - the case files from the database
     */
    SQL sql = new SQL();
    CallsTable columnFactory = new CallsTable();
    private ObservableList<CallRecord> searchData;
    private ObservableList<CallRecord> callsData;
    private ObservableList<CasesRecords> casesData;
    private ObservableList<NoteRecord> notesData;

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

    /**
     * Import functionality. It supports only certain csv files (that have the same number and order of columns as the database)
     */
    @FXML
    protected void importCSV() {
        /**
         * New window, where you choose what file to import
         */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        /**
         * If there is a file selected then the data from that file is transmitted to the database
         */
        if (file != null) {
            String filePath = file.getPath();
            filePath = filePath.replace("\\", "\\\\");
            ImportCSV.importcsv(filePath);
        }
    }

    /**
     * Reads user details from the file created after log in
     */
    @FXML
    public void setUserLabel() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(new File("src/res/tmp.txt")));
            String rd = read.readLine();
            rd = User.userGetName(rd);
            rd = rd.replace(" ", "\n");
            userLabel.setText(rd);
            read.close();
        } catch (IOException e) {
        } finally {
            new File("src/res/tmp.txt").delete();
        }
    }

    /**
     * Initialises the main app
     */
    @FXML
    public void initialize() {
        /**
         * Initialisation of the cases first
         * //TODO Default case? App should remember (debatable) the last case for a specific user
         */
        loadCases();
        /**
         * Loading the notes
         */
        loadFiles(1);
        /**
         * Loading user label
         */
        setUserLabel();
        /**
         * not sure what these do, some layout properties
         */
        date.setMinWidth(50);
        date.setMaxWidth(50);
        date.setPrefWidth(50);
        /**
         * Loading the table
         */
        loadTable(1);
        /**
         * Implementing search?
         */
        search();
        /**
         * Take these somewhere else :D
         */
        notesCT.setAlignment(Pos.TOP_LEFT);
        notesCT.setSpacing(50);
        notesCT.setBackground(Background.EMPTY);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane2.setPannable(true);
        scrollPane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        /**
         *
         */
        searchData = callsData;
    }

    /**
     * Loads the table with data from database
     *
     * @param i the case id whose data is to be shown
     */
    public void loadTable(int i) {
        /**
         * takes the data from the database and puts it into an observable list
         */
        callsData = sql.loadCalls(i);
        /**
         * builds the columns, without data
         */
        columnFactory.createCallerPNColumn(callerPhoneNumber);
        columnFactory.createReceiverPNColumn(receiverPhoneNumber);
        columnFactory.createDateColumn(date);
        columnFactory.createTimeColumn(time);
        columnFactory.createTypeOfCallColumn(typeOfCall);
        columnFactory.createDurationColumn(duration);
        /**
         * adds the data into the table
         */
        table.setItems(callsData);
        table.setEditable(true);
    }

    /**
     * Add a victim functionality
     */
    public void addVictim() {
        System.out.println("VICTIM");
        /**
         * Prepares the template
         */
        Pane victimNote = null;
        try {
            victimNote = (Pane) FXMLLoader.load(getClass().getResource("/fxml/victim.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /**
         * Makes different modifications on the template. This one is to delete the container
         */
        Pane temp = (Pane) victimNote.getChildren().get(0);
        Button delete = (Button) temp.getChildren().get(1);
        Pane finalVictimNote = victimNote;
        delete.setOnAction(event -> {
            notesCT.getChildren().remove(finalVictimNote);
            searchData = callsData;
            table.setItems(searchData);
        });
        //TODO Aleks can you please write some comments here? It would take a while for me to understand what's happening here :)
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

    //TODO Here as well
    public void addSuspect() {
        System.out.println("Suspect");
        Pane suspectNote = null;
        try {
            suspectNote = (Pane) FXMLLoader.load(getClass().getResource("/fxml/suspect.fxml"));
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

    /**
     * Add a case file
     *
     * @param actionEvent
     */
    public void addCaseFile(ActionEvent actionEvent) {
        System.out.println("Suspect");
        /**
         * Preparing the template
         */
        Pane CaseFile = null;
        try {
            CaseFile = (Pane) FXMLLoader.load(getClass().getResource("/fxml/caseFile.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /**
         * Adding the new case file to the database under a specific case. The noteId is not that important, as it is not used for anything, but if I keep it there, it works
         */
        HBox temp2 = (HBox) CaseFile.getChildren().get(0);
        TextField noteName = (TextField) temp2.getChildren().get(0);




        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        /**
         * Almost a sql query. The data that is forwarded to the database
         */
        NoteRecord nr = new NoteRecord("\"" + (sql.getMaxIDNote()) + "\"", "" + 1 + "", "" + sql.getCaseId(caseTitle.getText()) + "",
                "\"" + noteName.getText() + "\"", "\"" + LocalDate.now() + "\"", "\" \"");
        /**
         * Sql insertion
         */
        sql.insertNote(nr);
        /**
         * Delete case file part
         */
        HBox temp = (HBox) CaseFile.getChildren().get(1);
        Button delete = (Button) temp.getChildren().get(2);
        Pane finalCaseFile = CaseFile;
        TextField fileName = (TextField) temp2.getChildren().get(0);



        delete.setOnAction(event -> {
            caseFilesCT.getChildren().remove(finalCaseFile);
        });


        noteName.setOnAction(event -> {
            String change = fileName.getText();
            int id = Integer.valueOf(sql.getMaxIDNote());
            System.out.println("Change case file " + id+ " name to : " + change);
            sql.updateCaseFile(id, change);
            fileName.setEditable(false);
        });
        noteName.setOnMouseClicked(event -> {
            fileName.setEditable(true);
        });




        /**
         * After everything is set up, the user can see the new case file on the left tab
         */
        caseFilesCT.getChildren().add(CaseFile);
    }

    //TODO Get back at it later
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
        /**
         * Data manipulation part (the default data is ready to be used)
         */
        CallRecord cr = new CallRecord(String.valueOf(sql.getMaxCallID() + 1), "\"" + sql.getCaseId(caseTitle.getText()) + "\"",
                "0", "0", "1900/01/01", "00:00", "Standard", "00:00");
        /**
         * Add to table (visually) part
         */
        callsData.add(cr);
        /**
         * Add to database part
         */
        sql.addCall(cr);
        System.out.println("ADD: call");
    }

    /**
     * Controller for "Delete call" button. Same as above, a user should not be able to delete a call because this data is the kind you usually let it be.
     * Adding this functionality is just for it to be there in the case such action is wanted.
     * Deletes the call both from the table and the database.
     */
    public void deleteCall() {
        /**
         * A row must be selected for it to work
         */
        if (table.getSelectionModel().getSelectedItem() != null) {
            /**
             * Getting the data
             */
            CallRecord record = (CallRecord) table.getSelectionModel().getSelectedItem();
            /**
             * Checking if it's something there
             */
            if (record != null) {
                /**
                 * Delete from database part
                 */
                sql.removeCall(Integer.parseInt(record.getCallID()));
                /**
                 * Remove from table part
                 */
                callsData.remove(table.getSelectionModel().getSelectedItem());
                System.out.println("DELETE: call " + record.getCallID());
            }
        }

    }

    /**
     * Initialises the cases on the TabPane. CaseObj is considered as one of the case entries on the pane (with its image,labels and buttons)
     */
    private void loadCases() {
        /**
         * Takes Cases table from database
         */
        casesData = sql.loadCases();
        /**
         * Initialisation of Case Obj
         */
        HBox CaseObj = null;
        /**
         * Every row of the Case table (thus, every case that exists in the database) has a status attribute (Investigating, Solved or Preliminary)
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
                    CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/fxml/caseObj.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                /**
                 * It adds the case values in the tab
                 */
                HBox hb = (HBox) CaseObj.getChildren().get(3);
                Button b = (Button) hb.getChildren().get(1);

                VBox temp = (VBox) CaseObj.getChildren().get(1);
                TextField caseName = (TextField) temp.getChildren().get(0);
                caseName.setText(tabPane.getCaseName());
                Label caseDate = (Label) temp.getChildren().get(1);
                caseDate.setText("date");
                /**
                 * Pressing a case, changes the table with the proper values from the database
                 * How: It gets the name of the case, and finds its id (not suitable for cases that have the same names //TODO make this as general as possible
                 * Loads the table with only the calls at that specific id
                 * Also changes the big Case Title above the table
                 * + Some cool feedback animations
                 */
                HBox finalCaseObj = CaseObj;

                String t = caseName.getText();
                int it = sql.getCaseId(t);
                finalCaseObj.setId(String.valueOf(it));

                finalCaseObj.setOnMouseClicked(event -> {
                    String s = caseName.getText();
                    int id = sql.getCaseId(s);
                    loadTable(id);
                    loadFiles(id);
                    caseTitle.setText(s);
                });

                caseName.setOnMouseClicked(event -> {
                    caseName.setEditable(true);
                });
                caseName.setOnAction(event -> {
                    String change = caseName.getText();
                    int id = Integer.valueOf(finalCaseObj.getId());
                    System.out.println("Change case " + id+ " name to : " + change);
                    sql.updateCaseName(id, change);
                    caseName.setEditable(false);
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
                    CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/fxml/caseObj.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                HBox hb = (HBox) CaseObj.getChildren().get(3);
                Button b = (Button) hb.getChildren().get(1);

                VBox temp = (VBox) CaseObj.getChildren().get(1);
                TextField caseName = (TextField) temp.getChildren().get(0);
                caseName.setText(tabPane.getCaseName());
                Label caseDate = (Label) temp.getChildren().get(1);
                caseDate.setText("date");
                /**
                 * Same as above
                 */


                HBox finalCaseObj = CaseObj;
                finalCaseObj.setOnMouseClicked(event -> {
                    String s = caseName.getText();
                    int id = sql.getCaseId(s);
                    loadTable(id);
                    loadFiles(id);
                    caseTitle.setText(s);
                    caseName.setEditable(true);
                });


                caseName.setOnAction(event -> {
                    String change = caseName.getText();
                    int id = Integer.valueOf(finalCaseObj.getId());
                    System.out.println("Change case " + id+ " name to : " + change);
                    sql.updateCaseName(id, change);
                    caseName.setEditable(false);

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

                String t = caseName.getText();
                int it = sql.getCaseId(t);
                finalCaseObj.setId(String.valueOf(it));


                sCase.getChildren().add(CaseObj);


            }
            /**
             * The same as above, but that's if the status is "Preliminary"
             */
            if (tabPane.getStatus() == pTab.getText()) {
                try {
                    CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/fxml/caseObj.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                HBox hb = (HBox) CaseObj.getChildren().get(3);
                Button b = (Button) hb.getChildren().get(1);


                VBox temp = (VBox) CaseObj.getChildren().get(1);
                TextField caseName = (TextField) temp.getChildren().get(0);
                caseName.setText(tabPane.getCaseName());
                Label caseDate = (Label) temp.getChildren().get(1);
                caseDate.setText("date");
                /**
                 * Same as above
                 */

                HBox finalCaseObj = CaseObj;
                finalCaseObj.setOnMouseClicked(event -> {
                    String s = caseName.getText();
                    int id = sql.getCaseId(s);
                    loadTable(id);
                    loadFiles(id);
                    caseTitle.setText(s);
                    caseName.setEditable(true);
                });

                caseName.setOnAction(event -> {
                    String change = caseName.getText();
                    int id = Integer.valueOf(finalCaseObj.getId());
                    System.out.println("Change case " + id+ " name to : " + change);
                    sql.updateCaseName(id, change);
                    caseName.setEditable(false);
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

                String t = caseName.getText();
                int it = sql.getCaseId(t);
                finalCaseObj.setId(String.valueOf(it));
                pCase.getChildren().add(CaseObj);
            }

        }
    }

    /**
     * Loads the Case Files (notes) of a specific case into the app
     *
     * @param caseID
     */
    private void loadFiles(int caseID) {
        /**
         * Getting the data from the database part
         */
        notesData = sql.loadSQLNotes(caseID);
        /**
         * Clearing the cases already shown
         */
        Pane CaseFile = null;
        caseFilesCT.getChildren().clear();
        /**
         * For every case file from the database, checks which one is on the given case, and loads them all into the app
         */
        for (NoteRecord element : notesData) {
            if (Integer.parseInt(element.getCaseID()) == caseID) {
                /**
                 * Case file template
                 */
                try {
                    CaseFile = (Pane) FXMLLoader.load(getClass().getResource("/fxml/caseFile.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                /**
                 * Putting the data into the templates
                 */
                //TODO Still needs working on these. Make them a bit unique.
                HBox temp = (HBox) CaseFile.getChildren().get(1);
                HBox temp2 = (HBox) CaseFile.getChildren().get(0);
                TextField fileName = (TextField) temp2.getChildren().get(0);
                fileName.setText(element.getNoteName());
                Button delete = (Button) temp.getChildren().get(2);
                Pane finalCaseFile = CaseFile;

                fileName.setOnAction(event -> {
                    String change = fileName.getText();
                    int id = Integer.valueOf(element.getFileID());
                    System.out.println("Change case file " + id+ " name to : " + change);
                    sql.updateCaseFile(id, change);
                    fileName.setEditable(false);
                });
                fileName.setOnMouseClicked(event -> {
                    fileName.setEditable(true);
                });

                delete.setOnAction(event -> {
                    caseFilesCT.getChildren().remove(finalCaseFile);
                    sql.removeNote(Integer.parseInt(element.getFileID()));
                });
                /**
                 * the case files get loaded to the app
                 */
                caseFilesCT.getChildren().add(CaseFile);
            }
        }
    }

    /**
     * Add case button functionality
     *
     * @param actionEvent
     */
    public void addCase(ActionEvent actionEvent) {

        /**
         * The template for the case
         */
        HBox CaseObj = null;
        try {
            CaseObj = (HBox) FXMLLoader.load(getClass().getResource("/fxml/caseObj.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /**
         * If a case is chosen (clicking on it), it loads the notes, table, everything that has a relation with that case. (The same as above)
         * //TODO Maybe make it more compact. These lines of code are in 4 different places, a bit of object-oriented approach might work.
         */
        HBox finalCaseObj = CaseObj;
        finalCaseObj.setOnMouseClicked(event -> {
            String s = "empty";
            int id = sql.getCaseId(s);
            loadTable(id);
            loadFiles(id);
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
        String status = "";
        if (pTab.isSelected()) {
            status = "Preliminary";
            pCase.getChildren().add(finalCaseObj);
        } else if (iTab.isSelected()) {
            status = "Investigating";
            iCase.getChildren().add(finalCaseObj);
        } else {
            status = "Solved";
            sCase.getChildren().add(finalCaseObj);
        }
        /**
         *
         */
        VBox temp = (VBox) CaseObj.getChildren().get(1);
        TextField caseName = (TextField) temp.getChildren().get(0);
        CasesRecords cr = new CasesRecords(String.valueOf(sql.getMaxCallID() + 1), caseName.getText(), "Description",  status );
        sql.addCase(cr);

    }

}
