package controllers;

import com.Ostermiller.util.CSVParser;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import modules.file_export.csvExport;
import modules.manageAccounts.User;
import modules.table.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sql.SQL;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */

public class MainController {

    SQL sql = new SQL();    //  sql functionality
    CallsTable columnFactory = new CallsTable();    // building the columns of the table
    private ObservableList<CallRecord> searchData;
    private ObservableList<CallRecord> callsData;   //the calls from the database
    private ObservableList<CaseRecord> casesData;   // the cases from the database
    private ObservableList<FileRecord> filesData;   // the case files from the database
    private int caseID = 1;
    private int id = 1;
    private boolean editing = false;
    private char alphabet = 'A';
    private static List<Object[]> filterConstraints = new ArrayList<Object[]>();
    private static int filterIndex = 0;


    @FXML
    private ScrollPane notes_scroll_pane;
    @FXML
    protected TableColumn originIdentifierColumn;
    @FXML
    protected TableColumn originPhoneColumn;
    @FXML
    protected TableColumn destinationIdentifierColumn;
    @FXML
    protected TableColumn destinationPhoneColumn;
    @FXML
    protected TableColumn dateColumn;
    @FXML
    protected TableColumn timeColumn;
    @FXML
    protected TableColumn typeColumn;
    @FXML
    protected TableColumn durationColumn;
    @FXML
    protected TableColumn deleteColumn;
    @FXML
    protected TableView table;
    @FXML
    protected TextField searchBar;
    @FXML
    protected Label userLabel;
    @FXML
    protected ToggleButton allToggleBtn;
    @FXML
    protected ToggleButton newToggleBtn;
    @FXML
    protected ToggleButton doneToggleBtn;
    final ToggleGroup casesToggleGroup = new ToggleGroup();
    @FXML
    protected DatePicker startDate;
    @FXML
    protected DatePicker endDate;
    @FXML
    protected VBox casesContainer;
    @FXML
    protected ScrollPane filters_scroll;
    @FXML
    private VBox notes_box;
    @FXML
    protected Label caseTitle;
    @FXML
    protected HBox filtersBox;
    @FXML
    protected BorderPane root;
    SearchField searchTxt = new SearchField("");
    /**
     * Import functionality.
     */
    @FXML
    protected void importFile() {
        FileChooser fileChooser = new FileChooser();    // New window, where you choose what file to import
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) { // If there is a file selected then the data from that file is transmitted to the database
            String filePath = file.getPath();
            filePath = filePath.replace("\\", "\\\\");
            importFile(filePath);
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
     * Loads the table with data from database
     *
     * @param caseID the case id whose data is to be shown
     */
    public void loadTable(int caseID) {
        callsData = sql.loadCalls(caseID);  // takes the data from the database and puts it into an observable list
        columnFactory.createOriginNameColumn(originIdentifierColumn); // builds the columns, without data
        columnFactory.createOriginColumn(originPhoneColumn);
        columnFactory.createDestinationNameColumn(destinationIdentifierColumn);
        columnFactory.createDestinationColumn(destinationPhoneColumn);
        columnFactory.createDateColumn(dateColumn);
        columnFactory.createTimeColumn(timeColumn);
        columnFactory.createTypeOfCallColumn(typeColumn);
        columnFactory.createDurationColumn(durationColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(callsData);  // adds the data into the table
        table.setEditable(true);
    }

    public void createDeleteColumn(TableColumn deleteColumn) {

        deleteColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<CallRecord, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<CallRecord, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        deleteColumn.setCellFactory(
                new Callback<TableColumn<CallRecord, Boolean>, TableCell<CallRecord, Boolean>>() {

                    @Override
                    public TableCell<CallRecord, Boolean> call(TableColumn<CallRecord, Boolean> p) {
                        return new ButtonCell();
                    }

                });
        deleteColumn.setSortable(false);

    }

    private class ButtonCell extends TableCell<CallRecord, Boolean> {
        final Button cellButton = new Button("");

        ButtonCell() {
            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    deleteCall();
                }
            });
            cellButton.setId("delete-button");
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

    public static List<Object[]> getPeople() {
        List<Object[]> people = new ArrayList<Object[]>();
        for (int i=0; i<filterConstraints.size(); i++) {
            if (filterConstraints.get(i)[0] != null) {
                if (filterConstraints.get(i)[0] instanceof Person) {
                    Object[] temp = new Object[2];
                    temp[0] = filterConstraints.get(i)[0];
                    temp[1] =  filterConstraints.get(i)[1];
                    people.add(temp);
                }
            }
        }
        return people;
    }

    private void toggleControl() {
        allToggleBtn.setToggleGroup(casesToggleGroup);
        newToggleBtn.setToggleGroup(casesToggleGroup);
        doneToggleBtn.setToggleGroup(casesToggleGroup);
    }

    /**
     * Filters table by date (at the moment)
     *
     * @throws ParseException
     */
    @FXML
    private void filter() {
        try {
            for (int i=0; i<filterIndex; i++)  {
                if (filterConstraints.get(i)[0] != null) {
                    System.out.println(filterConstraints.get(i)[0].toString());
                }
            }
            System.out.println();
            searchData = filterSearch(filterPhone(filterDates(0)));
            table.setItems(searchData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursive call for filtering the table
     *
     * @param i index of constraint in the filterConstraint array
     * @return filtered data
     * @throws ParseException
     */
    private ObservableList<CallRecord> filterDates(int i) throws ParseException {
        if (i < filterIndex) {
            ObservableList<CallRecord> test = filterDates(i + 1);
            ObservableList<CallRecord> test2 = FXCollections.observableArrayList();
            boolean change = false;
            if (filterConstraints.get(i)[1].equals("no")) {
                return filterDates(i + 1);
            }
            if (filterConstraints.get(i)[0] instanceof Date) {
                Date date = (Date) filterConstraints.get(i)[0];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                change = true;
                if (filterConstraints.get(i)[1].equals("start")) {
                    for (CallRecord callRecord : test) {
                        Date callDate = sdf.parse(callRecord.getDate());
                        if (callDate.compareTo(date) >= 0) {
                            test2.add(callRecord);
                        }
                    }
                } else {
                    for (CallRecord callRecord : test) {
                        Date callDate = sdf.parse(callRecord.getDate());
                        if (callDate.compareTo(date) <= 0) {
                            test2.add(callRecord);
                        }
                    }
                }
            }
            if (change) {
                return test2;
            } else {
                return test;
            }
        } else {
            return callsData;
        }
    }

  /*  private ObservableList<CallRecord> search (ObservableList<CallRecord> test) {

        return tableItems;

        return tableItems;
    }*/

    public ObservableList<CallRecord> filterSearch(ObservableList<CallRecord> test) {
        ObservableList<CallRecord> tableItems = FXCollections.observableArrayList();
        boolean change = false;

        for (int i = 0; i < filterIndex; i++) {
            if (filterConstraints.get(i)[0] instanceof SearchField && filterConstraints.get(i)[1].equals("yes")) {
                ObservableList<TableColumn<CallRecord, ?>> cols = FXCollections.observableArrayList(originIdentifierColumn,
                        originPhoneColumn, destinationIdentifierColumn, destinationPhoneColumn,
                        dateColumn, timeColumn, typeColumn, durationColumn);
                change = true;
                for (int j = 0; j < test.size(); j++) {
                    for (int k = 0; k < 8; k++) {
                        TableColumn col = cols.get(k);
                        String cellValue = col.getCellData(test.get(j)).toString();
                        cellValue = cellValue.toLowerCase();

                        if (cellValue.contains(searchBar.textProperty().get ().toLowerCase())) {
                            tableItems.add(test.get(j));
                            break;
                        }
                    }
                }
            }
        }
        // Duplicates Removal (I don't know why, search gives duplicates)
        //TODO Find a way to make search unique. Only 1 entry in the filterConstraints
        if (change) {
            ObservableList<CallRecord> result = FXCollections.observableArrayList();
            HashSet<CallRecord> set = new HashSet<>();
            for (CallRecord item : tableItems) {
                if (!set.contains(item)) {
                    result.add(item);
                    set.add(item);
                }
            }
            return result;
        } else return test;
    }

    public ObservableList<CallRecord> filterPhone(ObservableList<CallRecord> test) {
        ObservableList<CallRecord> tableItems = FXCollections.observableArrayList();
        boolean change = false;
        for (int i = 0; i < filterIndex; i++) {
            if (filterConstraints.get(i)[0] instanceof Person && filterConstraints.get(i)[1].equals("yes")) {
                ObservableList<TableColumn<CallRecord, ?>> cols = FXCollections.observableArrayList(originIdentifierColumn,
                        originPhoneColumn, destinationIdentifierColumn, destinationPhoneColumn, dateColumn,
                        timeColumn, typeColumn, durationColumn);
                change = true;
                for (int k = 0; k < test.size(); k++) {
                    for (int j = 1; j < 4; j += 2) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(test.get(k)).toString();
                        cellValue = cellValue.toLowerCase();
                        Person person = (Person) filterConstraints.get(i)[0];
                        if (cellValue.contains(person.getPhone())) {
                            tableItems.add(test.get(k));
                            break;
                        }
                    }
                }
            }
        }
        if (change) {
            ObservableList<CallRecord> result = FXCollections.observableArrayList();
            HashSet<CallRecord> set = new HashSet<>();
            for (CallRecord item : tableItems) {
                if (!set.contains(item)) {
                    result.add(item);
                    set.add(item);
                }
            }
            return result;
        } else return test;
    }

   /* private void setSearchBarEmpty() {
        searchBar.textProperty().set("");
    }
*/

    /**
     * Retrieves the start date and filters the table
     *
     * @throws ParseException
     */
    @FXML
    private void getStartDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        if (startDate.getValue() != null) {
            date = sdf.parse(startDate.getValue().toString());
        }
        boolean isOn = false;
        for (int i = 0; i < filterIndex; i++) {
            if (filterConstraints.get(i)[1].equals("start")) {
                filterConstraints.get(i)[0] = date;
                isOn = true;
            }
        }
        if (!isOn) {
            Object[] temp = new Object[2];
            temp[0] = date;
            temp[1] = "start";
            filterConstraints.add(temp);
            filterIndex++;
        }
        filter();
    }

    /**
     * Retrieves the end date and filters the table
     *
     * @throws ParseException
     */
    @FXML
    private void getEndDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        if (startDate.getValue() != null) {
            date = sdf.parse(startDate.getValue().toString());
        }
        boolean isOn = false;
        for (int i = 0; i < filterIndex; i++) {
            if (filterConstraints.get(i)[1].equals("end")) {
                filterConstraints.get(i)[0] = date;
                isOn = true;
            }
        }
        if (!isOn) {
            Object[] temp = new Object[2];
            temp[0] = date;
            temp[1] = "end";
            filterConstraints.add(temp);
            filterIndex++;
        }
        filter();
    }

    /**
     * Initialises the cases on the TabPane.
     */
    private void loadCases() {

        //  CaseObj is considered as one of the case entries on the pane (with its image,labels and buttons)
        casesData = sql.loadCases();    //  Load Cases List from database:
        toggleControl();
        String status = "All";
        casesContainer.getChildren().clear();
        if (casesToggleGroup.getSelectedToggle() == newToggleBtn) {
            status = "New";
        } else if (casesToggleGroup.getSelectedToggle() == doneToggleBtn) {
            status = "Done";
        }
        loadTest(status);
        casesToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle toggle, Toggle newToggle) {
                String status = "All";
                casesContainer.getChildren().clear();
                if (newToggle == newToggleBtn) {
                    status = "New";
                } else if (newToggle == doneToggleBtn) {
                    status = "Done";
                }
                loadTest(status);
            }
        });
        //Every row of the Case table (thus, every case that exists in the database) has a status attribute (Investigating, Solved or Preliminary)
        //The for loop takes each case and checks what status it has, and then assigns the CaseObj position in the tab
    }

    private void loadTest(String status) {
        HBox CaseObject = null;
        for (CaseRecord caseRecord : casesData) {
            try {
                CaseObject = (HBox) FXMLLoader.load(getClass().getResource("/fxml/case.fxml")); // Case object loads the fxml, with its nodes
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // Initialise elements from Case object:
            HBox hb = (HBox) CaseObject.getChildren().get(2);
            Button deleteBtn = (Button) hb.getChildren().get(1);
            Button editBtn = (Button) hb.getChildren().get(0);
            HBox finalCaseObj = CaseObject;
            VBox temp = (VBox) CaseObject.getChildren().get(1);
            Pane caseIndicator = (Pane) CaseObject.getChildren().get(0);
            HBox temp2 = (HBox) temp.getChildren().get(0);
            Label caseStatus = (Label) temp2.getChildren().get(0);
            Label caseName = (Label) temp2.getChildren().get(1);
            caseName.setText(caseRecord.getName());
            Label caseDate = (Label) temp.getChildren().get(1);


            try {
                caseDate.setText(new SimpleDateFormat("yy-MM-dd  [HH:mm]").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(caseRecord.getDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finalCaseObj.setId(String.valueOf(caseRecord.getCaseID()));

            // Update the main working area and load case files:
            finalCaseObj.setOnMouseClicked(event -> {
                String date = currentTime();
                int id = Integer.valueOf(finalCaseObj.getId());
                caseTitle.setText(caseRecord.getName());
                caseID = id;
                loadTable(caseID);
                loadFiles(caseID);
                sql.updateDate(caseID, date);
                caseRecord.setDate(date);
                try {
                    caseDate.setText(new SimpleDateFormat("yy-MM-dd  [HH:mm]").format(new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss").parse(date)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                alphabet = 'A' - 1;
                filtersBox.getChildren().clear();
                searchBar.clear();
                System.out.println(caseStatus.getText());
                caseStatus.setMinWidth(40);
                caseStatus.setText("Current");
                caseStatus.setStyle("-fx-background-color: #22df46;");
                caseIndicator.setStyle("-fx-background-color: #4dff42;");
            });

            Pane CaseEditObject = null;
            try {
                CaseEditObject = (Pane) FXMLLoader.load(getClass().getResource("/fxml/case_edit_pane.fxml")); // Case object loads the fxml, with its nodes
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Pane finalCaseEditObject = CaseEditObject;
            HBox t1 = (HBox) finalCaseEditObject.getChildren().get(0);
            VBox t2 = (VBox) t1.getChildren().get(0);
            TextField t3 = (TextField) t2.getChildren().get(1);
            HBox t34 = (HBox) t2.getChildren().get(0);
            ToggleButton t35 = (ToggleButton) t34.getChildren().get(0);
            ToggleButton t36 = (ToggleButton) t34.getChildren().get(1);

            Button t4 = (Button) t1.getChildren().get(1);


            editBtn.setOnAction(event -> {
                finalCaseEditObject.setLayoutX(21);
                finalCaseEditObject.setLayoutY(92);
                t3.setText(caseName.getText());
                root.getChildren().add(finalCaseEditObject);
            });

            t4.setOnAction(event -> {

                int id = Integer.valueOf(finalCaseObj.getId());
                if (caseID == id) {
                    caseTitle.setText(t3.getText());
                }
                sql.updateCaseName(id, t3.getText());
                if (t35.isSelected()) {
                    sql.updateCaseStatus(id, "New");
                } else {
                    sql.updateCaseStatus(id, "Done");
                }
                root.getChildren().remove(finalCaseEditObject);
                loadCases();
            });

            deleteBtn.setOnAction(event -> {
                sql.removeCase(Integer.valueOf(finalCaseObj.getId()));
                casesContainer.getChildren().remove(finalCaseObj);
                loadCases();
            });
            if (caseRecord.getStatus().equals("New")) {
                caseStatus.setText("New");
            } else {
                caseStatus.setText("Done");
                caseStatus.setStyle("-fx-background-color: #df1e00;");
                caseIndicator.setStyle("-fx-background-color: #df2100;");
            }
            // Search Listener
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                search();
            });

            if (status.equals("New") && caseRecord.getStatus().equals("New")) {
                casesContainer.getChildren().add(finalCaseObj);
            } else if (status.equals("Done") && caseRecord.getStatus().equals("Done")) {
                casesContainer.getChildren().add(finalCaseObj);
            }
            if (status.equals("All")) {
                casesContainer.getChildren().add(finalCaseObj);
            }

        }
    }

    private void showNote(int noteID, NoteIcon parent) {

        ObservableList<FileRecord> noteRecord = sql.loadNote(noteID);
        Pane notePane = null;
        try {
            notePane = (Pane) FXMLLoader.load(getClass().getResource("/fxml/note_pane.fxml")); // Case file template
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        notePane.setId(String.valueOf(noteID));
        Pane note = notePane;
//      for (int i=0; i<indexNote; i++){
//            if (listOfNotePanes[indexNote].getId().equals(notePane.getId()) && !notePane.isOpen()) {
//
//            }
//        }
//        if (!isOpen) {
//            indexNote++;
//            listOfNotePanes[indexNote] = notePane;
        DragResizeMod.makeResizable(notePane, null);
        notePane.setLayoutX(1530);
        notePane.setLayoutY(60);
//        if (!notePane.isOpen()) {
//
//            notePane.changeOpen(true);
//        }
        root.getChildren().add(notePane);
//      }
        VBox temp = (VBox) notePane.getChildren().get(0);
        HBox noteBar = (HBox) temp.getChildren().get(0);
        Label noteName = (Label) noteBar.getChildren().get(0);
        Button closeNote = (Button) noteBar.getChildren().get(3);
        Button deleteNote = (Button) noteBar.getChildren().get(2);
        TextArea data = (TextArea) temp.getChildren().get(1);

//        noteName.setOnAction(event -> {
//                    String change = noteName.getText();
//                    int id = Integer.valueOf(parent.getId());
//                    System.out.println("Change case file " + id + " name to : " + change);
//                    sql.updateCaseFile(id, change);
//                    noteName.setEditable(false);
//                });
//                noteName.setOnMouseClicked(event -> {
//                    noteName.setEditable(true);
//                });
        data.setWrapText(true);
        for (FileRecord fr : noteRecord) {
            noteName.setText(fr.getName());
            data.appendText(fr.getData());
        }

        note.setOnMouseClicked(event -> {
            note.toFront();
        });

        data.setOnMouseClicked(event -> {
            note.toFront();
        });

        closeNote.setOnAction(event -> {
            root.getChildren().remove(note);
            sql.updateNote(data.getText(), note.getId());
            parent.changeOpen(false);
        });

        deleteNote.setOnAction(event -> {
            Pane left = (Pane) root.getChildren().get(3);
            VBox notesVBox = (VBox) left.getChildren().get(0);
            ScrollPane notesScrollPAne = (ScrollPane) notesVBox.getChildren().get(0);
            VBox notesBox = (VBox) notesScrollPAne.getContent();
            int i = 0;
            Pane noteIcon;
            try {
                while ((noteIcon = (Pane) notesBox.getChildren().get(i)) != null) {
                    if (noteIcon.getId().equals(note.getId())) {
                        notes_box.getChildren().remove(noteIcon);
                        root.getChildren().remove(note);
                        sql.removeNote(Integer.valueOf(note.getId()));
                    }
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {
                // DO Nothing. It works as intended
            }
        });
    }

    /**
     * Loads the Case Files (notes) of a specific case into the app
     *
     * @param caseID
     */
    private void loadFiles(int caseID) {
        filesData = sql.loadFiles(caseID);  // Getting the data from the database part
        NoteIcon CaseFile = null;   //  Clearing the cases already shown
        notes_box.getChildren().clear();
        for (FileRecord element : filesData) {  // For every case file from the database, checks which one is on the given case, and loads them all into the app
            if (Integer.parseInt(element.getCaseID()) == caseID) {
                try {
                    CaseFile = (NoteIcon) FXMLLoader.load(getClass().getResource("/fxml/note.fxml")); // Case file template
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                // Putting the data into the templates
                //TODO Still needs working on these. Make them a bit unique.
                CaseFile.setId(String.valueOf(element.getNoteID()));
                NoteIcon noteIcon = CaseFile;
                CaseFile.setOnMouseClicked(event -> {
                    if (!noteIcon.isOpen()) {
                        int id = Integer.valueOf(element.getNoteID());
                        showNote(id, noteIcon);
                        noteIcon.changeOpen(true);
                    }
                });
//                HBox temp = (HBox) CaseFile.getChildren().get(1);
//                HBox temp2 = (HBox) CaseFile.getChildren().get(0);
//                TextField fileName = (TextField) temp2.getChildren().get(0);
//                fileName.setText(element.getName());
//                Button delete = (Button) temp.getChildren().get(2);
//                Pane finalCaseFile = CaseFile;
//
//
//
                notes_box.getChildren().add(CaseFile);     // the case files get loaded to the app
            }
        }
    }

    //TODO Add comments
    @FXML
    public void search() {
        if (!searchTxt.getText().equals(searchBar.textProperty().get())) {
            searchTxt.setText(searchBar.textProperty().get());
            if (searchTxt.getText().isEmpty()) {
                for (int i = 0; i < filterIndex; i++) {
                    if (filterConstraints.get(i)[0].equals(searchTxt)) {
                        System.out.println("Case 1");
                        filterConstraints.get(i)[1] = "no";
                    }
                }
            } else {
                boolean isOn = false;
                for (int i = 0; i < filterIndex; i++) {
                    if (filterConstraints.get(i)[0].equals(searchTxt)) {
                        filterConstraints.get(i)[0] = searchTxt;
                        filterConstraints.get(i)[1] = "yes";
                        isOn = true;
                    }
                }
                if (!isOn) {
                    System.out.println("Case 3");
                    Object[] temp = new Object[2];
                    temp[0] = searchTxt;
                    temp[1] = "yes";
                    filterConstraints.add(temp);
                    filterIndex++;
                }
            }
            filter();
        }
    }

    /**
     * Controller for "Delete call" button. Same as above, a user should not be able to delete a call because this data is the kind you usually let it be.
     * Adding this functionality is just for it to be there in the case such action is wanted.
     * Deletes the call both from the table and the database.
     */
    public void deleteCall() {
        if (table.getSelectionModel().getSelectedItem() != null) {   // A row must be selected for it to work
            CallRecord record = (CallRecord) table.getSelectionModel().getSelectedItem();   //   Getting the data
            if (record != null) {   // Checking if it's something there
                sql.removeCall(Integer.parseInt(record.getCallID()));   // Delete from database part
                callsData.remove(table.getSelectionModel().getSelectedItem());  // Remove from table part
                System.out.println("DELETE: call " + record.getCallID());
            }
        }
    }

    public void editCase() {
        System.out.println("HERE");
    }

    /**
     * Filter by phone for victim/suspect
     *
     * @param phoneField
     * @param person
     */
    public void checkPhone(TextField phoneField, Person person) {
        if (phoneField.textProperty().get().isEmpty()) {
            for (int i = 0; i < filterIndex; i++) {
                if (filterConstraints.get(i)[0].equals(person)) {
                    filterConstraints.get(i)[1] = "no";
                }
            }
        } else {
            boolean isOn = false;
            for (int i = 0; i < filterIndex; i++) {
                if (filterConstraints.get(i)[0].equals(person)) {
                    person.setPhone(phoneField.getText());
                    filterConstraints.get(i)[0] = person;
                    filterConstraints.get(i)[1] = "yes";
                    isOn = true;
                }
            }
            if (!isOn) {
                person.setPhone(phoneField.getText());
                Object[] temp = new Object[2];
                temp[0] = person;
                temp[1] = "yes";
                filterConstraints.add(temp);
                filterIndex++;

            }
        }
    }

    /**
     * Add a victim functionality
     */
    public void addVictim() {

        //TODO add counting A B C D for filters and table view when searching

        if (filtersBox.getChildren().size() == 0) {
            alphabet = 'A';
        }

        if (alphabet <= 'Z') {
            System.out.println("ADD VICTIM " + alphabet);
            Pane victimNote = null;
            try {
                victimNote = (Pane) FXMLLoader.load(getClass().getResource("/fxml/victim.fxml"));   // Prepares the template
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Pane temp = (Pane) victimNote.getChildren().get(0);
            Pane temp2 = (Pane) temp.getChildren().get(0);
            Label letter = (Label) temp2.getChildren().get(0);
            Pane temp3 = (Pane) temp2.getChildren().get(4);
            Button delete = (Button) temp3.getChildren().get(0);
            TextField nameField = (TextField) temp.getChildren().get(1);
            TextField phoneField = (TextField) temp.getChildren().get(2);


            Victim victim = new Victim(String.valueOf(alphabet));

            Pane finalVictimNote = victimNote;
            delete.setOnAction(event -> {       // Makes different modifications on the template. This one is to delete the container
                filtersBox.getChildren().remove(finalVictimNote);
                for (int i = 0; i < filterIndex; i++) {
                    if (filterConstraints.get(i)[0].equals(victim)) {
                        filterConstraints.get(i)[1] = "no";
                    }
                }
                filter();
                System.out.println("DELETE VICTIM");
            });
            //TODO Aleks can you please write some comments here? It would take a while for me to understand what's happening here :)

            phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                checkPhone(phoneField, victim);
            });
            letter.setText(String.valueOf(alphabet));
            filtersBox.getChildren().add(victimNote);
            alphabet++;
        }
    }

    //TODO Comments to add
    public void addSuspect() {

        if (filtersBox.getChildren().size() == 0) {
            alphabet = 'A';
        }

        if (alphabet <= 'Z') {
            System.out.println("ADD SUSPECT " + alphabet);
            Pane suspectNote = null;
            try {
                suspectNote = (Pane) FXMLLoader.load(getClass().getResource("/fxml/suspect.fxml"));   // Prepares the template
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Pane temp = (Pane) suspectNote.getChildren().get(0);
            Pane temp2 = (Pane) temp.getChildren().get(0);
            Label letter = (Label) temp2.getChildren().get(0);
            Pane temp3 = (Pane) temp2.getChildren().get(4);
            Button delete = (Button) temp3.getChildren().get(0);
            TextField phoneField = (TextField) temp.getChildren().get(2);

            Suspect suspect = new Suspect(String.valueOf(alphabet));

            Pane finalVictimNote = suspectNote;
            delete.setOnAction(event -> {       // Makes different modifications on the template. This one is to delete the container
                filtersBox.getChildren().remove(finalVictimNote);
                for (int i = 0; i < filterIndex; i++) {
                    if (filterConstraints.get(i)[0].equals(suspect)) {
                        filterConstraints.get(i)[1] = "no";
                    }
                }
                filter();
                System.out.println("DELETE SUSPECT");
            });
            //TODO Aleks can you please write some comments here? It would take a while for me to understand what's happening here :)

            phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                checkPhone(phoneField, suspect);
            });
            letter.setText(String.valueOf(alphabet));
            alphabet++;
            filtersBox.getChildren().addAll(suspectNote);
        }
    }

    /**
     * Controller for "Add call" button. No one is supposed to add a call, because you already have it in the database, or any excel, csv file, so a user should
     * not be able to add a call in my opinion, but let's say he wants to, his choice.
     * Default values, that if left unattended clearly show that they are fake.
     * The method changes the table and the database at the same time.
     */
    public void addCall() {
        // Data manipulation part (the default data is ready to be used)
        CallRecord cr = new CallRecord(String.valueOf(sql.getMaxCallID() + 1), "\"" + caseID + "\"",
                "1", "1", "1900/01/01", "00:00", "Standard", "00:00");
        callsData.add(cr);  // Add to table (visually) part
        sql.addCall(cr);        // Add to database part
        System.out.println("ADD: call");
        table.scrollTo(cr);
        table.getSelectionModel().select(cr);
    }

    /**
     * Add case button functionality
     *
     * @param actionEvent
     */
    public void addCase(ActionEvent actionEvent) {

        CaseRecord callRecord = new CaseRecord(String.valueOf(0), "case" + id++, "Description", "New", currentTime());
        sql.addCase(callRecord);
        loadCases();
    }

    /**
     * Add a case file
     */
    public void addNote(ActionEvent actionEvent) {
        FileRecord nr = new FileRecord("\"" + (sql.getMaxIDNote()) + "\"", "" + 1 + "", "" + caseID + "",
                "\"" + "Case file" + "\"", "\"" + LocalDate.now() + "\"", "\" \"");
        sql.insertFile(nr);
        loadFiles(caseID);
    }

    //TODO Add Comments
    public String currentTime() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return timeStamp;
    }

    //TODO Add Comments
    public boolean casesUpdate() {
        ObservableList<CaseRecord> temp2 = sql.loadCases();
        boolean requireUpdate = false;
        int i = 0;

        try {
            if (temp2.size() != casesData.size()) {
                return true;
            }
            for (CaseRecord caseRecord : temp2) {

                if (i <= (casesData.size() - 1)) {

                    if (!caseRecord.getCaseID().equals(casesData.get(i).getCaseID())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDate().equals(casesData.get(i).getDate())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getName().equals(casesData.get(i).getName())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getStatus().equals(casesData.get(i).getStatus())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDetails().equals(casesData.get(i++).getDetails())) {
                        requireUpdate = true;
                    }
                }
            }
            return requireUpdate;
        } catch (Exception e) {
            return requireUpdate;
        }
    }

    //TODO Add Comments
    public boolean filesUpdate() {
        ObservableList<FileRecord> temp2 = sql.loadFiles(caseID);
        boolean requireUpdate = false;
        int i = 0;

        try {

            if (temp2.size() != filesData.size()) {
                return true;
            }

            for (FileRecord fileRecord : temp2) {

                if (i <= (filesData.size() - 1)) {

                    if (!fileRecord.getCaseID().equals(filesData.get(i).getCaseID())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getDate().equals(filesData.get(i).getDate())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getNoteID().equals(filesData.get(i).getNoteID())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getName().equals(filesData.get(i).getName())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getData().equals(filesData.get(i).getData())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getUserID().equals(filesData.get(i++).getUserID())) {
                        requireUpdate = true;
                    }
                }
            }
            return requireUpdate;

        } catch (Exception e) {
            return requireUpdate;
        }
    }

    //TODO Add Comments
    public boolean callsUpdate() {
        ObservableList<CallRecord> temp2 = sql.loadCalls(caseID);
        boolean requireUpdate = false;
        int i = 0;

        try {
            if (temp2.size() != callsData.size()) {
                return true;
            }

            for (CallRecord caseRecord : temp2) {

                if (i <= (callsData.size() - 1)) {

                    if (!caseRecord.getCallID().equals(callsData.get(i).getCallID())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getCaseID().equals(callsData.get(i).getCaseID())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getOrigin().equals(callsData.get(i).getOrigin())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDestination().equals(callsData.get(i).getDestination())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDate().equals(callsData.get(i).getDate())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getTime().equals(callsData.get(i).getTime())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getTypeOfCall().equals(callsData.get(i).getTypeOfCall())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDuration().equals(callsData.get(i++).getDuration())) {
                        requireUpdate = true;
                    }
                }
            }
            return requireUpdate;
        } catch (Exception e) {
            return requireUpdate;
        }
    }

    @FXML
    public void initialize() {

        filters_scroll.setPannable(true);
        filters_scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        filters_scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        notes_scroll_pane.setPannable(true);
        notes_scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
                        importFile(filePath);
                    }
                }
            }
            event2.setDropCompleted(success);
            event2.consume();
            loadTable(caseID);
            loadFiles(caseID);
        });
        setUserLabel();
        loadCases();
    }

    public void update() {
        loadTable(caseID);
        loadFiles(caseID);
//        if (!editing) {
//            System.out.println("UPDATE");
//            if (casesUpdate()) {
//                System.out.println("EXTERNAL CHANGE IN CASES");
//                loadCases();
//                for (CaseRecord temp : casesData) {
//                    if (Integer.valueOf(temp.getCaseID()) == caseID) {
//                        caseTitle.setText(temp.getName());
//                    }
//                }
//            }
//            if (filesUpdate()) {
//                System.out.println("EXTERNAL CHANGE IN FILES");
//                loadFiles(caseID);
//            }
//            if (callsUpdate()) {
//                System.out.println("EXTERNAL CHANGE IN TABLE");
//                loadTable(caseID);
//            }
//        }
    }

    /**
     * Getter for column names of the main table
     *
     * @return
     */
    public List<String> getColumnNames() {
        List nameOfColumns = new ArrayList(6);
        nameOfColumns.add(originPhoneColumn.getText());
        nameOfColumns.add(destinationPhoneColumn.getText());
        nameOfColumns.add(dateColumn.getText());
        nameOfColumns.add(timeColumn.getText());
        nameOfColumns.add(typeColumn.getText());
        nameOfColumns.add(durationColumn.getText());

        return nameOfColumns;
    }

    /**
     * Makes the difference between excel files from 2003 and excel files after 2007
     */
    private Workbook getWorkbook(FileInputStream inputStream, String filePath)
            throws IOException {
        Workbook workbook = null;
        if (filePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (filePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not recognized");
            alert.setHeaderText("Unsupported file format");
            alert.setContentText("The file you want to import is not supported by the application");
            alert.showAndWait();
        }
        return workbook;
    }

    /**
     * Imports the data from a file. It must be csv, xls, xlsx
     *
     * @param filePath full path of file
     */
    public void importFile(String filePath) {
        if (filePath.endsWith("csv")) {
            importCSV(filePath);
            System.out.println("IMPORT FROM CSV");
        } else {
            importExcel(filePath);
            System.out.println("IMPORT FROM EXCEL");
        }
    }

    /**
     * Import from csv file
     * Takes headers from file and reorders them in the database order. Keeps only the wanted columns of information
     * Takes the rest of data and puts it under their respective columns
     * Sends it to database
     * Works only with columns that are already in the database.
     *
     * @param filePath full path of file to be imported
     */
    public void importCSV(String filePath) {
        InputStream is;
        try {
            is = new FileInputStream(filePath); // Takes the csv file and deconstructs it
            CSVParser shredder = new CSVParser(is);
            shredder.setCommentStart("#;!");
            shredder.setEscapes("nrtf", "\n\r\t\f");

            String fileString;          // fileString is going to hold every single value from file, one at a time
            int[] tableHeader = new int[10];    // tableHeader will contain the order of table columns that are in the csv file
            int tableHeaderIndex = 0;
            CallRecord cr = new CallRecord();
            int isOnDatabase;
            //FIXME Add more aliases. Test with different types of faulty csvs
//            Takes only the headers of the file and puts them into tableHeader. If there is an unidentified column, an alert pops out and asks the user about
//            its importance, and have him, maybe, select an existing column to put the information in. (Mismatched column names)
            while ((fileString = shredder.nextValue()) != null && shredder.lastLineNumber() == 1) {
                if ((isOnDatabase = cr.alias(fileString)) != -1) { // If there is a match with a column in the database, input the new index
                    tableHeader[tableHeaderIndex] = isOnDatabase;
                } else {      //  else Alert pops out.
                    tableHeader[tableHeaderIndex] = alert(fileString);
                }
                tableHeaderIndex++;
            }
            /*
             * At this moment the columns are set in place.
             * How? Check if a file column resembles a database column(using regex). The ones that are unrecognized are sent to the user for future actions
             * To implement something that spares the user these questions is a bit more difficult.
             * During this checking, all file columns are put in the correct order (database order, or the order on which this method is made)
             * The discarded columns, get -1 value.
             * Next step is getting the data from the file
             * length = the number of file columns
             * dataElement = data from file, put in the correct spot
             * The principle: Iterate thorough the rows of the file, take only the data under the approved columns, and put it in the database
             */
            ObservableList<CallRecord> data = FXCollections.observableArrayList();
            int length = tableHeaderIndex + 1;
            tableHeaderIndex = 0;
            String[] dataElement = new String[length];
            while (tableHeader[tableHeaderIndex] == -1) {       // The 1st dataElement has to be taken individually.
                tableHeaderIndex++;
                fileString = shredder.nextValue();
            }
            dataElement[tableHeader[tableHeaderIndex]] = fileString;
            tableHeaderIndex++;
            while ((fileString = shredder.nextValue()) != null) {       // As long as there is data left in the file
                if (tableHeader[tableHeaderIndex] != -1) {              // If the current column is approved then the data goes into the correct place
                    dataElement[tableHeader[tableHeaderIndex]] = fileString;
                }
                tableHeaderIndex++;         // Go to the next column
                if (tableHeaderIndex == length - 1) {       // If the end of the row was reached, start next row and put the previously filtered row into the data
                    tableHeaderIndex = 0;
                    data.add(new CallRecord(String.valueOf(caseID), dataElement[0], dataElement[1], dataElement[2], dataElement[3], dataElement[4], dataElement[5]));
                }
            }
            sql.insertCalls(data);  // After getting all data from the file, send it to database
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Import from excel file
     * Takes headers from file and reorders them in the database order. Keeps only the wanted columns of information
     * Takes the rest of data and puts it under their respective columns
     * Sends it to database
     * Works only with columns that are already in the database.
     *
     * @param filePath full path of file to be imported
     */
    public void importExcel(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath)); // Prepares tools to take data from excel
            Workbook workbook = getWorkbook(fileInputStream, filePath);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            int isOnDatabase;                       // checks if column is recognized by database
            Cell currentCell;                       // cell with current information
            int[] tableHeader = new int[10];           // will hold the preferred order of the recognized headers from file
            int tableHeaderIndex = 0;               // index of tableHeader
            CallRecord cr = new CallRecord();           // used to call the alias method to recognize headers
            if (iterator.hasNext()) {               // If there is at least 1 row (header row)
                Row currentRow = iterator.next();       // Take it
                Iterator<Cell> cellIterator = currentRow.cellIterator();    // Make an iterator for the row (so that you can iterate through it and take the cells)
                while (cellIterator.hasNext()) {        // As long as there are unverified elements in the row
                    currentCell = cellIterator.next();          // Take cell value
                    if ((isOnDatabase = cr.alias(String.valueOf(getCellValue(currentCell)))) != -1) {       // if name is recognized by database
                        tableHeader[tableHeaderIndex] = isOnDatabase;               // remember order
                    } else {      // else Alert pops out.
                        tableHeader[tableHeaderIndex] = alert(String.valueOf(getCellValue(currentCell)));   // keep user decision (to recognize or not)
                    }
                    tableHeaderIndex++;
                }
            }
            ObservableList<CallRecord> data = FXCollections.observableArrayList();        // stores the data that will be sent to database
            int length = tableHeaderIndex + 1;                      // number of recognized columns
            tableHeaderIndex = 0;
            String[] dataElement = new String[length];              // row that goes into data variable

            while (iterator.hasNext()) {                        // As long as there are rows to check
                Row currentRow = iterator.next();                       // Take every single row
                Iterator<Cell> cellIterator = currentRow.cellIterator();        // Make an iterator for it
                while (cellIterator.hasNext()) {        // Check for every value in the row
                    currentCell = cellIterator.next();
                    if (tableHeader[tableHeaderIndex] != -1) {      // If recognized column then store it into dataElement
                        dataElement[tableHeader[tableHeaderIndex]] = String.valueOf(getCellValue(currentCell));
                    }
                    tableHeaderIndex++;
                }
                tableHeaderIndex = 0;           // After the row is checked go from the beginning
                data.add(new CallRecord(String.valueOf(caseID), dataElement[0], dataElement[1],
                        dataElement[2], dataElement[3], dataElement[4], dataElement[5])); // The row is added to data
            }
            sql.insertCalls(data);              // The essential information is sent to database
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cells are considered objects. To get the data on them the following way has to be done.
     *
     * @param cell Cell to take the value from
     * @return The information contained in the cell
     */
    private Object getCellValue(Cell cell) {
        if (cell.getCellTypeEnum() == CellType.STRING) {        // getCellTypeEnum is not deprecated in this version of Apache poi. It's just a bug
            return cell.getStringCellValue();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        return null;
    }

    /**
     * Alert box that asks the user for the importance of some unrecognized columns in the database
     *
     * @param fileString The unrecognized column
     * @return If it returns a number the user decided to use it as an existing column in the database
     */
    public int alert(String fileString) {
        CallRecord cr = new CallRecord();           // Alias method
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unidentified Column");
        alert.setHeaderText("Column \"" + fileString + "\" is an unidentified column!");
        alert.setContentText("Do you need this column?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {     // If user selects "Yes" button, a new pop-up asks him the correct column that should be used (if any)
            List<String> choices = new ArrayList<>();
            choices.addAll(getColumnNames());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(originPhoneColumn.getText(), choices);
            dialog.setTitle("Choose Column");
            dialog.setHeaderText("Choose fr0m the following columns.");
            dialog.setHeaderText("If the column is not there, contact University of Bradford");
            dialog.setContentText("Choose the appropriate column:");

            Optional<String> result2 = dialog.showAndWait();
            result2.ifPresent(column -> {               // Choose and select a column
                dialog.setSelectedItem(column);
            });
            if (cr.alias(dialog.getSelectedItem()) != -1) {
                return cr.alias(dialog.getSelectedItem());
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public void exportCSV() {

        csvExport bla = new csvExport();
        File exports = new File(System.getProperty("user.dir"), "./reports");
        if (!exports.exists()) {
            exports.mkdirs();
        }

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterCSV = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilterCSV);
        fileChooser.setInitialFileName(caseTitle.getText().toString());
        fileChooser.setInitialDirectory(exports);

        //Show save file dialog
        try {
            File file = fileChooser.showSaveDialog(new Stage());
            String filePath = file.getPath();

            bla.csvOut(filePath, searchData);
        } catch (Exception e) {
        }
    }
}

