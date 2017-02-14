package controllers;

import com.Ostermiller.util.CSVParser;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import modules.file_import.ImportCSV;
import modules.manageAccounts.User;
import modules.table.*;
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

import java.io.*;
import java.sql.Connection;
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
    private int caseID = 1;
    private int id = 1;

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
    protected BorderPane root;

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
            importTest(filePath);
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

        //TODO Is this the drag/drop functionality? Let's find another place for it
        root.setOnDragOver(event1 -> {
            Dragboard db = event1.getDragboard();
            if (db.hasFiles()) {
                event1.acceptTransferModes(TransferMode.COPY);
            } else {
                event1.consume();
            }
        });

        // Dropping over surface

        root.setOnDragDropped(event2 -> {
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
                        importTest(filePath);
                    }
                }
            }
            event2.setDropCompleted(success);
            event2.consume();
            loadTable(caseID);
            loadFiles(caseID);
        });
    }

    /**
     * Loads the table with data from database
     *
     * @param caseID the case id whose data is to be shown
     */
    public void loadTable(int caseID) {
        /**
         * 
         * takes the data from the database and puts it into an observable list
         */

        callsData = sql.loadCalls(caseID);
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
         * Load Cases List from database:
         */
        casesData = sql.loadCases();

        /**
         * Clear current tabs:
         */
        iCase.getChildren().clear();
        pCase.getChildren().clear();
        sCase.getChildren().clear();
        HBox CaseObject = null;

        /**
         * Every row of the Case table (thus, every case that exists in the database) has a status attribute (Investigating, Solved or Preliminary)
         * The for loop takes each case and checks what status it has, and then assigns the CaseObj position in the tab
         */
        for (CasesRecords tabPane : casesData) {

            /**
             * Case object loads the fxml, with its nodes
             */
            try {
                CaseObject = (HBox) FXMLLoader.load(getClass().getResource("/fxml/caseObj.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            /**
             * Initialise elements from Case object:
             */
            HBox hb = (HBox) CaseObject.getChildren().get(3);
            Button deleteBtn = (Button) hb.getChildren().get(1);
            HBox finalCaseObj = CaseObject;
            VBox temp = (VBox) CaseObject.getChildren().get(1);
            TextField caseName = (TextField) temp.getChildren().get(0);
            caseName.setText(tabPane.getCaseName());
            Label caseDate = (Label) temp.getChildren().get(1);
            caseDate.setText("date");

            String t = caseName.getText();
            int it = sql.getCaseId(t);
            finalCaseObj.setId(String.valueOf(it));

            /**
             * Update the main working area and load case files:
             */
            finalCaseObj.setOnMouseClicked(event -> {
                String s = caseName.getText();
                int id = sql.getCaseId(s);
                loadTable(id);
                loadFiles(id);
                caseID = id;
                caseTitle.setText(s);
            });

            /**
             * Update case name:
             */
            caseName.setOnMouseClicked(event -> {
                caseName.setEditable(true);
            });

            caseName.setOnAction(event -> {
                String change = caseName.getText();
                int id = Integer.valueOf(finalCaseObj.getId());
                System.out.println("Change case " + id + " name to : " + change);
                sql.updateCaseName(id, change);
                loadCases();
                caseName.setEditable(false);
            });

            /**
             * Animations:
             */
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

            /**
             * Add Case object to specific tab:
             */
            if (tabPane.getStatus().equals(iTab.getText())) {
                iCase.getChildren().add(finalCaseObj);
                deleteBtn.setOnAction(event -> {
                    sql.removeCase(Integer.valueOf(finalCaseObj.getId()));
                    iCase.getChildren().remove(finalCaseObj);
                });

            } else if (tabPane.getStatus().equals(sTab.getText())) {
                sCase.getChildren().add(finalCaseObj);
                deleteBtn.setOnAction(event -> {
                    sql.removeCase(Integer.valueOf(finalCaseObj.getId()));
                    sCase.getChildren().remove(finalCaseObj);
                });
            } else if (tabPane.getStatus().equals(pTab.getText())) {
                pCase.getChildren().add(finalCaseObj);
                deleteBtn.setOnAction(event -> {
                    sql.removeCase(Integer.valueOf(finalCaseObj.getId()));
                    pCase.getChildren().remove(finalCaseObj);
                });
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
                    System.out.println("Change case file " + id + " name to : " + change);
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

        String status = "";
        if (pTab.isSelected()) {
            status = "Preliminary";
        } else if (iTab.isSelected()) {
            status = "Investigating";
        } else {
            status = "Solved";
        }
        CasesRecords callRecord = new CasesRecords(String.valueOf(sql.getMaxCallID() + 1), "case" + id++, "Description", status);
        sql.addCase(callRecord);
        loadCases();
    }

    /**
     * Add a case file
     *
     * @param actionEvent
     */
    public void addCaseFile(ActionEvent actionEvent) {

        NoteRecord nr = new NoteRecord("\"" + (sql.getMaxIDNote()) + "\"", "" + 1 + "", "" + sql.getCaseId(caseTitle.getText()) + "",
                "\"" + "Case file" + "\"", "\"" + LocalDate.now() + "\"", "\" \"");
        sql.insertFile(nr);
        loadFiles(sql.getCaseId(caseTitle.getText()));
    }

    public void importTest(String path) {

        InputStream is;

        try {
            /**
             * Takes the csv file and parsers it
             */
            is = new FileInputStream(path);
            CSVParser shredder = new CSVParser(is);
            shredder.setCommentStart("#;!");
            shredder.setEscapes("nrtf", "\n\r\t\f");
            /**
             * t is going to hold every single value from file, one at a time
             */
            String t;
            /**
             * tableHeader contains table columns that are in the file
             */
            int[] tableHeader = new int[10];
            int j = 0;
            CallRecord cr = new CallRecord();
            /**
             * Takes only the headers of the file and puts them into tableHeader
             */
            //TODO add comments on how it's done. Works only for a specific number of columns of data. Add more aliases. Test with different types of faulty csvs
            while ((t = shredder.nextValue()) != null && shredder.lastLineNumber() == 1) {
                System.out.println(t);
                tableHeader[j] = cr.alias(t);
                j++;
            }
            ObservableList<CallRecord> data = FXCollections.observableArrayList();
            int length = j + 1;
            j = 0;
            System.out.println("" + shredder.lastLineNumber() + " " + t);
            String[] s = new String[length];
            s[tableHeader[j]] = t;
            j++;
            while ((t = shredder.nextValue()) != null) {
                System.out.println("" + shredder.lastLineNumber() + " " + t);
                s[tableHeader[j]] = t;
                j++;
                if (j == length - 1) {
                    j = 0;
                    data.add(new CallRecord(String.valueOf(caseID), s[0], s[1], s[2], s[3], s[4], s[5]));
                }
            }
            sql.insertCalls(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
