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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import modules.factory.MainTableFactory;
import modules.file_export.csvExport;
import modules.file_export.pdfExport;
import modules.filter.Person;
import modules.filter.SearchField;
import modules.filter.Suspect;
import modules.filter.Victim;
import modules.note.DragResizeMod;
import modules.note.NoteIcon;
import modules.record_structures.CallRecord;
import modules.record_structures.CaseRecord;
import modules.record_structures.FileRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sql.SQL;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * Created by AndreiM on 2/1/2017.
 * Controller for main window
 */
public class MainController {

    //dynamic data storage:
    private ObservableList<CallRecord> filteredData;
    private ObservableList<CallRecord> databaseCallsData;
    private ObservableList<CaseRecord> databaseCasesData;
    private ObservableList<FileRecord> databaseNotesData;
    private static List<Object[]> filterConstraints = new ArrayList<>();

    //counters:
    private int caseID = 1;
    private char filterLetterID = 'A';
    private static int filterID = 0;

    //extra:
    private ToggleGroup casesToggleGroup = new ToggleGroup();
    private SearchField searchField = new SearchField("");
    private MainTableFactory mainTableFactory = new MainTableFactory();
    private SQL sql = new SQL();

    //fxml elements:
    @FXML
    protected BorderPane root;
    @FXML
    protected VBox notesBox;
    @FXML
    protected VBox casesBox;
    @FXML
    protected HBox filtersBox;
    @FXML
    protected ScrollPane notesSPane;
    @FXML
    protected ScrollPane filtersSPane;
    @FXML
    protected TableView callsTable;
    @FXML
    protected TableColumn fromIDColumn;
    @FXML
    protected TableColumn fromPhoneColumn;
    @FXML
    protected TableColumn toIDColumn;
    @FXML
    protected TableColumn toPhoneColumn;
    @FXML
    protected TableColumn dateColumn;
    @FXML
    protected TableColumn timeColumn;
    @FXML
    protected TableColumn typeColumn;
    @FXML
    protected TableColumn durationColumn;
    @FXML
    protected ToggleButton allToggleBtn;
    @FXML
    protected ToggleButton newToggleBtn;
    @FXML
    protected ToggleButton doneToggleBtn;
    @FXML
    protected DatePicker startDate;
    @FXML
    protected DatePicker endDate;
    @FXML
    protected TextField searchBar;
    @FXML
    protected Label caseTitle;
    @FXML
    protected Label numOfRows;


    /**
     * Opens a window and prompts the user to choose the file whose data is to be imported
     */
    @FXML
    protected void importFile() {
        // New window, where you choose what file to import
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File importedFile = fileChooser.showOpenDialog(new Stage());
        // If there is a file selected then the data from that file is transmitted to the database
        if (importedFile != null) {
            String filePath = importedFile.getPath();
            filePath = filePath.replace("\\", "\\\\");
            importFile(filePath);
        }
    }

    /**
     * Redirects to methods that support importing files with csv, xls or xlsx extension
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
     * Imports data from csv file
     * Takes headers from file and reorders them in the database order. Keeps only the wanted columns
     * Takes the rest of data and puts it under their respective columns
     * Sends it to database
     * Works only with columns that are already in the database.
     *
     * @param filePath full path of file to be imported
     */
    public void importCSV(String filePath) {
        InputStream is;
        try {
            is = new FileInputStream(filePath);
            // Takes the csv file and deconstructs it
            CSVParser shredderOfData = new CSVParser(is);
            shredderOfData.setCommentStart("#;!");
            shredderOfData.setEscapes("nrtf", "\n\r\t\f");

            String currentString;          // currentString is going to hold every single value from file, one at a time
            int[] tableHeader = new int[10];    // tableHeader will contain the order of callsTable columns that are in the csv file
            int tableHeaderIndex = 0;
            // used for the alias method
            CallRecord cr = new CallRecord();
            // databaseColumnIndex holds the index of column in the database
            int databaseColumnIndex;
            // If file contains a predetermined column, under which the time and date are both put,
            // an exception to the normal flow is acknowledged
            boolean dateColumnNameException = false;
            // Takes the headers(from the first line of the file) and puts them into the correct order of the database into tableHeader.
            // If there is an unidentified column, an alert pops out and asks the users about its importance,
            // and have them select an existing column to put the information in. (Mismatched column names)
            while ((currentString = shredderOfData.nextValue()) != null && shredderOfData.lastLineNumber() == 1) {
                // If there is a match with a column in the database, input the new index
                if ((databaseColumnIndex = cr.alias(currentString)) != -1) {
                    tableHeader[tableHeaderIndex] = databaseColumnIndex;
                    if (currentString.equals("Start date/time") || currentString.equals("UTC Start Time")) {
                        dateColumnNameException = true;
                    }
                } else {      //  else Alert pops out.
                    tableHeader[tableHeaderIndex] = alert(currentString);
                }
                tableHeaderIndex++;
            }
            /*
             * At this moment the columns are set in place.
             * How? Check if a file column resembles a database column(using regex).
             * The ones that are unrecognized are sent to the users for future actions
             * During the checking, all file columns are put in the correct order (database order, or the order on which this method is made)
             * The discarded columns, get -1 value.
             * Next step is getting the data from the file
             * length = the number of file columns
             * dataCorrectOrder = data from file, put in the correct order
             * The principle: Iterate thorough the rows of the file, take only the data under the approved columns, and put it in the database
             */
            ObservableList<CallRecord> dataToDatabase = FXCollections.observableArrayList();
            int length = tableHeaderIndex + 1;
            tableHeaderIndex = 0;
            String[] dataCorrectOrder = new String[length];
            while (tableHeader[tableHeaderIndex] == -1) {
                tableHeaderIndex++;
                currentString = shredderOfData.nextValue();
            }
            dataCorrectOrder[tableHeader[tableHeaderIndex]] = currentString;
            tableHeaderIndex++;
            while ((currentString = shredderOfData.nextValue()) != null) {       // As long as there is data left in the file
                // If the current column is approved then the data goes into the correct place
                if (tableHeader[tableHeaderIndex] != -1) {
                    dataCorrectOrder[tableHeader[tableHeaderIndex]] = currentString;
                }
                tableHeaderIndex++;         // Go to the next column
                // If the end of the row was reached, start next row and put the found data into dataToDatabase
                if (tableHeaderIndex == length - 1) {
                    tableHeaderIndex = 0;
                    if (dateColumnNameException) {
                        String[] dateTime = dataCorrectOrder[2].split("\\s");
                        dateTime[0] = changeDateFormat(dateTime[0]);
                        dataToDatabase.add(new CallRecord(String.valueOf(caseID), dataCorrectOrder[0], dataCorrectOrder[1],
                                dateTime[0], dateTime[1], dataCorrectOrder[4], dataCorrectOrder[5]));
                    } else {
                        dataCorrectOrder[2] = changeDateFormat(dataCorrectOrder[2]);
                        dataToDatabase.add(new CallRecord(String.valueOf(caseID), dataCorrectOrder[0], dataCorrectOrder[1],
                                dataCorrectOrder[2], dataCorrectOrder[3], dataCorrectOrder[4], dataCorrectOrder[5]));
                    }

                }
            }
            sql.insertCalls(dataToDatabase);  // After getting all data from the file, send it to database
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Import data from excel file
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
            Workbook excelWorkbook = getWorkbook(fileInputStream, filePath); // Different versions of Excel require different tools
            Sheet datatypeSheet = excelWorkbook.getSheetAt(0);
            Iterator<Row> rowIterator = datatypeSheet.iterator();
            boolean dateColumnNameException = false;
            int databaseColumnIndex;                       // checks if column is recognized by database
            Cell currentCell;                       // cell with current information
            int[] tableHeader = new int[10];           // will hold the preferred order of the recognized headers from file
            int tableHeaderIndex = 0;               // index of tableHeader
            CallRecord cr = new CallRecord();           // used to call the alias method to recognize headers
            if (rowIterator.hasNext()) {               // If there is at least 1 row (header row)
                Row currentRow = rowIterator.next();
                // Makes an iterator for the row (so that you can iterate through it and take the cells)
                Iterator<Cell> cellIterator = currentRow.cellIterator();
                while (cellIterator.hasNext()) {        // As long as there are unverified elements in the row
                    currentCell = cellIterator.next();          // Take cell value
                    String currentString = String.valueOf(getCellValue(currentCell));
                    if ((databaseColumnIndex = cr.alias(currentString)) != -1) {       // if name is recognized by database
                        tableHeader[tableHeaderIndex] = databaseColumnIndex;               // remember order
                        if (currentString.equals("Start date/time") || currentString.equals("UTC Start Time")) {
                            dateColumnNameException = true;
                        }
                    } else {      // else Alert pops out.
                        tableHeader[tableHeaderIndex] = alert(String.valueOf(getCellValue(currentCell)));
                    }
                    tableHeaderIndex++;
                }
            }
            // stores the data that will be sent to database
            ObservableList<CallRecord> dataToDatabase = FXCollections.observableArrayList();
            int length = tableHeaderIndex + 1;                      // number of recognized columns
            tableHeaderIndex = 0;
            String[] dataElement = new String[length];              // row that goes into data variable

            while (rowIterator.hasNext()) {                        // As long as there are rows to check
                Row currentRow = rowIterator.next();                       // Take every single row
                Iterator<Cell> cellIterator = currentRow.cellIterator();        // Make an iterator for it
                while (cellIterator.hasNext()) {        // Check for every value in the row
                    currentCell = cellIterator.next();
                    if (tableHeader[tableHeaderIndex] != -1) {      // If recognized column then store it into dataElement
                        dataElement[tableHeader[tableHeaderIndex]] = String.valueOf(getCellValue(currentCell));
                    }
                    tableHeaderIndex++;
                }
                tableHeaderIndex = 0;           // Start next row
                if (dateColumnNameException) {
                    String[] dateTime = dataElement[2].split("\\s");
                    dateTime[0] = changeDateFormat(dateTime[0]);
                    dataToDatabase.add(new CallRecord(String.valueOf(caseID), dataElement[0], dataElement[1],
                            dateTime[0], dateTime[1], dataElement[4], dataElement[5]));
                } else {
                    dataElement[2] = changeDateFormat(dataElement[2]);
                    dataToDatabase.add(new CallRecord(String.valueOf(caseID), dataElement[0], dataElement[1],
                            dataElement[2], dataElement[3], dataElement[4], dataElement[5]));
                }
            }
            sql.insertCalls(dataToDatabase);              // The essential information is sent to database
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Identifies the version of Microsoft Excel used for the file, or specifies that the file is not supported
     *
     * @return the tailored workbook for the file
     */
    private Workbook getWorkbook(FileInputStream inputStream, String filePath)
            throws IOException {
        Workbook excelWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            excelWorkbook = new XSSFWorkbook(inputStream);
        } else if (filePath.endsWith("xls")) {
            excelWorkbook = new HSSFWorkbook(inputStream);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not recognized");
            alert.setHeaderText("Unsupported file format");
            alert.setContentText("The file you want to import is not supported by the application");
            alert.showAndWait();
        }
        return excelWorkbook;
    }


    /**
     * Loads the callsTable with data from database
     *
     * @param caseID the case id whose data is to be shown
     */
    public void loadTable(int caseID) {
        databaseCallsData = sql.loadCalls(caseID);  // takes the data from the database and puts it into an observable list
        filteredData = databaseCallsData;             // search data gets the default callsTable items from databaseCallsData (usefull for export CSV/PDF)
        mainTableFactory.createOriginNameColumn(fromIDColumn); // builds the columns, without data
        mainTableFactory.createOriginColumn(fromPhoneColumn);
        mainTableFactory.createDestinationNameColumn(toIDColumn);
        mainTableFactory.createDestinationColumn(toPhoneColumn);
        mainTableFactory.createDateColumn(dateColumn);
        mainTableFactory.createTimeColumn(timeColumn);
        mainTableFactory.createCallTypeColumn(typeColumn);
        mainTableFactory.createDurationColumn(durationColumn);
        callsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        callsTable.setItems(databaseCallsData);  // adds the data into the callsTable
        callsTable.setEditable(true);

        //numOfRows label shows the size of the displayed callsTable
        numOfRows.setText("Number of rows: " + callsTable.getItems().size());
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

    /**
     * @return Retrieves the list of active filters
     */
    public static List<Object[]> getFilters() {
        List<Object[]> people = new ArrayList<Object[]>();
        for (int i = 0; i < filterConstraints.size(); i++) {
            if (filterConstraints.get(i)[0] != null) {
                if (filterConstraints.get(i)[0] instanceof Person) {
                    Object[] temp = new Object[2];
                    temp[0] = filterConstraints.get(i)[0];
                    temp[1] = filterConstraints.get(i)[1];
                    people.add(temp);
                }
            }
        }
        return people;
    }

    /**
     * Brings the cases toggles into a group
     */
    private void toggleControl() {
        allToggleBtn.setToggleGroup(casesToggleGroup);
        newToggleBtn.setToggleGroup(casesToggleGroup);
        doneToggleBtn.setToggleGroup(casesToggleGroup);
    }

    /**
     * Filters the data from the table
     * If there are only 2 filters applied, the table will show only data that contains both of those filters
     * Else the table will display any kind of data that contains at least one of the filters, without duplicates
     *
     * @throws ParseException
     */
    @FXML
    private void filter() {
        try {
            for (int i = 0; i < filterID; i++) {
                System.out.println(filterConstraints.get(i)[0]);
            }
            if (filtersBox.getChildren().size() == 2) {
                filteredData = filterSearch(filter(0));
            } else {
                filteredData = filterSearch(filterPhone(filterDates(0)));
            }
            // Writes the number of occurrences for each filter
//            Pane filterPane;
//            for (int j = 0; j < filtersBox.getChildren().size(); j++) {
//                filterPane = (Pane) filtersBox.getChildren().get(j);
//                Pane temp = (Pane) filterPane.getChildren().get(0);
//                Pane temp2 = (Pane) temp.getChildren().get(0);
//                Label filterLabel = (Label) temp2.getChildren().get(2);
//                TextField phoneField = (TextField) temp.getChildren().get(2);
//                Label letter = (Label) temp2.getChildren().get(0);
//
//                // Identifies the filter used
//                for (int i = 0; i < filterID; i++) {
//                    Person person = (Person) filterConstraints.get(i)[0];
//                    if (person.getId().equals(filterPane.getId()) && filterConstraints.get(i)[1].equals("yes")) {
//                        // Puts the number into the designated place
//                        filterLabel.setText(String.valueOf(filterConstraints.get(i)[1]));
//                    }
//                }
//            }

            // Puts info into the callsTable
            callsTable.setItems(filteredData);

            //numOfRows label shows the size of the displayed callsTable
            numOfRows.setText("Number of rows: " + callsTable.getItems().size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filters the data by the date and filters
     *
     * @param i variable used recursively, default should be 0
     * @return the filtered data
     * @throws ParseException
     */
    private ObservableList<CallRecord> filter(int i) throws ParseException {
        if (i < filterID) {
            ObservableList<CallRecord> filteredData = filter(i + 1);
            ObservableList<CallRecord> newData = FXCollections.observableArrayList();
            boolean isDifferent = false;
            if (filterConstraints.get(i)[0] instanceof Date) {
                Date date = (Date) filterConstraints.get(i)[0];
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                isDifferent = true;
                if (filterConstraints.get(i)[1].equals("start")) {
                    for (CallRecord callRecord : filteredData) {
                        Date callDate = sdf.parse(callRecord.getDate());
                        if (callDate.compareTo(date) >= 0) {
                            newData.add(callRecord);
                        }
                    }
                } else {
                    for (CallRecord callRecord : filteredData) {
                        Date callDate = sdf.parse(callRecord.getDate());
                        if (callDate.compareTo(date) <= 0) {
                            newData.add(callRecord);
                        }
                    }
                }
            } else if (filterConstraints.get(i)[0] instanceof Person) {
                ObservableList<TableColumn<CallRecord, ?>> columns = FXCollections.observableArrayList(fromIDColumn,
                        fromPhoneColumn, toIDColumn, toPhoneColumn, dateColumn,
                        timeColumn, typeColumn, durationColumn);
                int sumOfCalls = 0;
                isDifferent = true;
                for (int k = 0; k < filteredData.size(); k++) {
                    for (int j = 1; j < 4; j += 2) {
                        TableColumn currentColumn = columns.get(j);
                        String cellValue = currentColumn.getCellData(filteredData.get(k)).toString();
                        cellValue = cellValue.toLowerCase();
                        Person currentFilter = (Person) filterConstraints.get(i)[0];
                        if (cellValue.contains(currentFilter.getPhone())) {
                            CallRecord call = filteredData.get(k);
                            if (j == 1) {
                                call.setOriginName(currentFilter.getIdentifier());
                            } else if (j == 3) {
                                call.setDestinationName(currentFilter.getIdentifier());
                            }
                            newData.add(call);
                            sumOfCalls++;
                            break;
                        }
                    }
                }
                filterConstraints.get(i)[1] = sumOfCalls;
            }
            if (isDifferent) {
                return newData;
            } else {
                return filteredData;
            }

        } else {
            return databaseCallsData;
        }
    }

    /**
     * Recursive call for filtering the callsTable using the dates
     *
     * @param i index of constraint in the filterConstraint array
     * @return filtered data
     * @throws ParseException
     */
    private ObservableList<CallRecord> filterDates(int i) throws ParseException {
        if (i < filterID) {
            ObservableList<CallRecord> test = filterDates(i + 1);
            ObservableList<CallRecord> test2 = FXCollections.observableArrayList();
            boolean change = false;
            if (filterConstraints.get(i)[0] instanceof Date) {
                Date date = (Date) filterConstraints.get(i)[0];
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
            return databaseCallsData;
        }
    }

    /**
     * Filters the list, using constraints from the search bar
     *
     * @param filteredData data to be filtered
     * @return new filtered data
     */
    public ObservableList<CallRecord> filterSearch(ObservableList<CallRecord> filteredData) {
        ObservableList<CallRecord> currentFilteredData = FXCollections.observableArrayList();
        boolean isDifferent = false;

        for (int i = 0; i < filterID; i++) {
            if (filterConstraints.get(i)[0] instanceof SearchField) {
                ObservableList<TableColumn<CallRecord, ?>> columns = FXCollections.observableArrayList(fromIDColumn,
                        fromPhoneColumn, toIDColumn, toPhoneColumn,
                        dateColumn, timeColumn, typeColumn, durationColumn);
                isDifferent = true;
                for (int j = 0; j < filteredData.size(); j++) {
                    for (int k = 0; k < 8; k++) {
                        TableColumn currentColumn = columns.get(k);
                        String cellValue = currentColumn.getCellData(filteredData.get(j)).toString();
                        cellValue = cellValue.toLowerCase();

                        if (cellValue.contains(searchBar.textProperty().get().toLowerCase())) {
                            currentFilteredData.add(filteredData.get(j));
                            break;
                        }
                    }
                }
            }
        }
        // Duplicates Removal (I don't know why, search gives duplicates)
        //TODO Find a way to make search unique. Only 1 entry in the filterConstraints
        if (isDifferent) {
            ObservableList<CallRecord> newFilteredData = FXCollections.observableArrayList();
            HashSet<CallRecord> setOfCalls = new HashSet<>();
            for (CallRecord currentCall : currentFilteredData) {
                if (!setOfCalls.contains(currentCall)) {
                    newFilteredData.add(currentCall);
                    setOfCalls.add(currentCall);
                }
            }
            return newFilteredData;
        } else return filteredData;
    }

    /**
     * Filters the list using the filter boxes
     *
     * @param filteredData data to filter
     * @return the new filtered data
     */
    public ObservableList<CallRecord> filterPhone(ObservableList<CallRecord> filteredData) {
        ObservableList<CallRecord> currentFilteredData = FXCollections.observableArrayList();
        boolean isDifferent = false;

        for (int i = 0; i < filterID; i++) {
            if (filterConstraints.get(i)[0] instanceof Person) {
                ObservableList<TableColumn<CallRecord, ?>> columns = FXCollections.observableArrayList(fromIDColumn,
                        fromPhoneColumn, toIDColumn, toPhoneColumn, dateColumn,
                        timeColumn, typeColumn, durationColumn);
                isDifferent = true;
                int sum = 0;
                for (int k = 0; k < filteredData.size(); k++) {
                    for (int j = 1; j < 4; j += 2) {
                        TableColumn currentColumn = columns.get(j);
                        String cellValue = currentColumn.getCellData(filteredData.get(k)).toString();
                        cellValue = cellValue.toLowerCase();
                        Person person = (Person) filterConstraints.get(i)[0];
                        if (cellValue.contains(person.getPhone())) {
                            CallRecord call = filteredData.get(k);
                            if (j == 1) {
                                call.setOriginName(person.getIdentifier());
                            } else if (j == 3) {
                                call.setDestinationName(person.getIdentifier());
                            }
                            currentFilteredData.add(call);
                            sum++;
                            break;
                        }
                    }
                }
                filterConstraints.get(i)[1] = sum;
            }
        }
        if (isDifferent) {
            ObservableList<CallRecord> result = FXCollections.observableArrayList();
            HashSet<CallRecord> set = new HashSet<>();
            for (CallRecord item : currentFilteredData) {
                if (!set.contains(item)) {
                    result.add(item);
                    set.add(item);
                }
            }
            return result;
        } else return filteredData;
    }

    /**
     * Retrieves the start date
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
        for (int i = 0; i < filterID; i++) {
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
            filterID++;
        }
        filter();
    }

    /**
     * Retrieves the end date
     *
     * @throws ParseException
     */
    @FXML
    private void getEndDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        if (endDate.getValue() != null) {
            date = sdf.parse(endDate.getValue().toString());
        }
        boolean isOn = false;
        for (int i = 0; i < filterID; i++) {
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
            filterID++;
        }
        filter();
    }

    /**
     * Initialises the cases on the TabPane.
     */
    private void loadCases() {
        databaseCasesData = sql.loadCases();    //  Load Cases List from database:
        toggleControl();
        String caseStatus = "All";
        casesBox.getChildren().clear();
        if (casesToggleGroup.getSelectedToggle() == newToggleBtn) {
            caseStatus = "New";
        } else if (casesToggleGroup.getSelectedToggle() == doneToggleBtn) {
            caseStatus = "Done";
        }
        loadCases(caseStatus);
        casesToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle toggle, Toggle newToggle) {
                String status = "All";
                casesBox.getChildren().clear();
                if (newToggle == newToggleBtn) {
                    status = "New";
                } else if (newToggle == doneToggleBtn) {
                    status = "Done";
                }
                loadCases(status);
            }
        });
    }

    /**
     * Loads the cases based on the open toggle, in the left side of the app
     *
     * @param status Status of the case
     */
    private void loadCases(String status) {
        HBox CaseObject = null;
        int i = 0;
        for (CaseRecord caseEntry : databaseCasesData) {
            // First case will be shown as default
            if (i == 0) {
                caseID = Integer.parseInt(caseEntry.getCaseID());
            }
            i++;
            try {
                CaseObject = (HBox) FXMLLoader.load(getClass().getResource("/fxml/case.fxml")); // Case object loads the fxml, with its nodes
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // Initialise elements from Case object:
            HBox finalCaseObj = CaseObject;
            Pane caseIndicator = (Pane) CaseObject.getChildren().get(0);
            VBox caseDetails = (VBox) CaseObject.getChildren().get(1);
            HBox temp2 = (HBox) caseDetails.getChildren().get(0);
            Label caseStatus = (Label) temp2.getChildren().get(0);
            Label caseName = (Label) temp2.getChildren().get(1);
            caseName.setText(caseEntry.getName());
            Label caseDate = (Label) caseDetails.getChildren().get(1);
            HBox caseActionBox = (HBox) CaseObject.getChildren().get(2);
            Button deleteCaseBtn = (Button) caseActionBox.getChildren().get(1);
            Button editBtn = (Button) caseActionBox.getChildren().get(0);

            // Date maneuvering, it records the last time the case was accessed
            try {
                caseDate.setText(new SimpleDateFormat("yy-MM-dd  [HH:mm]").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(caseEntry.getDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // every case will hold its database id
            finalCaseObj.setId(String.valueOf(caseEntry.getCaseID()));

            // Update the main working area and load case files:
            finalCaseObj.setOnMouseClicked(event -> {
                // loads data into table
                String date = currentTime();
                int id = Integer.valueOf(finalCaseObj.getId());
                caseTitle.setText(caseEntry.getName());
                caseID = id;
                loadTable(caseID);
                loadFiles(caseID);
                sql.updateDate(caseID, date);
                caseEntry.setDate(date);
                try {
                    caseDate.setText(new SimpleDateFormat("yy-MM-dd  [HH:mm]").format(new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss").parse(date)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // resets the filters
                filterLetterID = 'A' - 1;
                filtersBox.getChildren().clear();
                startDate.setValue(null);
                endDate.setValue(null);
                searchBar.clear();
                filterConstraints = new ArrayList<Object[]>();
                filterID = 0;
                // changes visual status of case
                System.out.println(caseStatus.getText());
                caseStatus.setMinWidth(40);
                caseStatus.setText("Current");
                caseStatus.setStyle("-fx-background-color: #22df46;");
                caseIndicator.setStyle("-fx-background-color: #4dff42;");
            });

            // Edit Pane of the Case Object
            Pane CaseEditObject = null;
            try {
                CaseEditObject = (Pane) FXMLLoader.load(getClass().getResource("/fxml/case_edit_pane.fxml")); // Case object loads the fxml, with its nodes
                DragResizeMod.makeResizable(CaseEditObject, null);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Pane finalCaseEditObject = CaseEditObject;
            Pane tempPane = (Pane) finalCaseEditObject.getChildren().get(0);
            TextField editTextField = (TextField) tempPane.getChildren().get(2);
            ToggleButton editCaseStatus = (ToggleButton) tempPane.getChildren().get(0);
            Button saveBtn = (Button) tempPane.getChildren().get(3);

            // Edit case button
            editBtn.setOnAction(event -> {
                finalCaseEditObject.setLayoutX(260);
                finalCaseEditObject.setLayoutY(110);
                editTextField.setText(caseName.getText());
                root.getChildren().add(finalCaseEditObject);
            });

            // Save changes button
            saveBtn.setOnAction(event -> {
                int id = Integer.valueOf(finalCaseObj.getId());
                // case name
                if (caseID == id) {
                    caseTitle.setText(editTextField.getText());
                }
                sql.updateCaseName(id, editTextField.getText());
                // case status
                if (editCaseStatus.isSelected()) {
                    sql.updateCaseStatus(id, "New");
                } else {
                    sql.updateCaseStatus(id, "Done");
                }
                root.getChildren().remove(finalCaseEditObject);
                loadCases();
            });

            // delete Case button
            deleteCaseBtn.setOnAction(event -> {
                sql.removeCase(Integer.valueOf(finalCaseObj.getId()));
                casesBox.getChildren().remove(finalCaseObj);
                loadCases();
            });
            if (caseEntry.getStatus().equals("New")) {
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

            if (status.equals("New") && caseEntry.getStatus().equals("New")) {
                casesBox.getChildren().add(finalCaseObj);
            } else if (status.equals("Done") && caseEntry.getStatus().equals("Done")) {
                casesBox.getChildren().add(finalCaseObj);
            }
            if (status.equals("All")) {
                casesBox.getChildren().add(finalCaseObj);
            }
        }
    }

    /**
     * Shows UI Note
     *
     * @param noteID note to be shown is identified by an id
     * @param parent The icon that is pressed to show the specific note
     */
    private void showNote(int noteID, NoteIcon parent) {
        // note information taken from database
        ObservableList<FileRecord> noteRecord = sql.loadNote(noteID);
        // Preparing the note GUI structure
        Pane notePane = null;
        try {
            notePane = (Pane) FXMLLoader.load(getClass().getResource("/fxml/note_pane.fxml")); // Case file template
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        notePane.setId(String.valueOf(noteID));
        Pane note = notePane;
        DragResizeMod.makeResizable(notePane, null);
        notePane.setLayoutX(1530);
        notePane.setLayoutY(60);
        root.getChildren().add(notePane);

        VBox tempObj = (VBox) notePane.getChildren().get(0);
        HBox noteBar = (HBox) tempObj.getChildren().get(0);
        Label noteName = (Label) noteBar.getChildren().get(0);
        Button closeNoteBtn = (Button) noteBar.getChildren().get(3);
        Button deleteNoteBtn = (Button) noteBar.getChildren().get(2);
        TextArea noteData = (TextArea) tempObj.getChildren().get(1);

        // puts the data into the components
        noteData.setWrapText(true);
        for (FileRecord fileRecord : noteRecord) {
            noteName.setText(fileRecord.getName());
            noteData.appendText(fileRecord.getData());
        }
        // surfaces the GUI note on top of everything
        note.setOnMouseClicked(event -> {
            note.toFront();
        });
        noteData.setOnMouseClicked(event -> {
            note.toFront();
        });
        // Closes note
        closeNoteBtn.setOnAction(event -> {
            root.getChildren().remove(note);
            sql.updateNote(noteData.getText(), note.getId());
            parent.changeOpen(false);
        });
        // Deletes note
        deleteNoteBtn.setOnAction(event -> {
            Pane left = (Pane) root.getChildren().get(3);
            VBox notesVBox = (VBox) left.getChildren().get(0);
            ScrollPane notesScrollPAne = (ScrollPane) notesVBox.getChildren().get(0);
            VBox notesBox = (VBox) notesScrollPAne.getContent();
            int i = 0;
            Pane noteIcon;
            try {
                while ((noteIcon = (Pane) notesBox.getChildren().get(i)) != null) {
                    if (noteIcon.getId().equals(note.getId())) {
                        this.notesBox.getChildren().remove(noteIcon);
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
        databaseNotesData = sql.loadFiles(caseID);  // Getting the data from the database
        NoteIcon CaseFile = null;   //  Clearing the cases already shown
        notesBox.getChildren().clear();
        for (FileRecord element : databaseNotesData) {  // For every case file from the database, checks which one is on the given case, and loads them all into the app
            if (Integer.parseInt(element.getCaseID()) == caseID) {
                // Prepares GUI structure
                try {
                    CaseFile = (NoteIcon) FXMLLoader.load(getClass().getResource("/fxml/note.fxml")); // Case file template
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                // Putting the data into the components
                CaseFile.setId(String.valueOf(element.getNoteID()));
                NoteIcon noteIcon = CaseFile;
                // If note is clicked on, a note pane will open
                CaseFile.setOnMouseClicked(event -> {
                    if (!noteIcon.isOpen()) {
                        int id = Integer.valueOf(element.getNoteID());
                        showNote(id, noteIcon);
                        noteIcon.changeOpen(true);
                    }
                });
                notesBox.getChildren().add(CaseFile);     // the case files get loaded to the app
            }
        }
    }

    /**
     * Controller for "Delete call" button. Same as above, a user should not be able to delete a call because this data is the kind you usually let it be.
     * Adding this functionality is just for availability, in the case such action is wanted.
     * Deletes the call both from the callsTable and the database.
     */
    public void deleteCall() {
        if (callsTable.getSelectionModel().getSelectedItem() != null) {   // A row must be selected for it to work
            CallRecord record = (CallRecord) callsTable.getSelectionModel().getSelectedItem();   //   Getting the data
            if (record != null) {   // Checks if it's something in there
                sql.removeCall(Integer.parseInt(record.getCallID()));   // Deletes from database
                databaseCallsData.remove(callsTable.getSelectionModel().getSelectedItem());  // Removes from callsTable
                System.out.println("DELETE: call " + record.getCallID());
            }
        }
    }

    /**
     * Search bar functionality
     */
    @FXML
    private void search() {
        if (!searchField.getText().equals(searchBar.textProperty().get())) {
            searchField.setText(searchBar.textProperty().get());
            if (searchField.getText().isEmpty()) {
                for (int i = 0; i < filterID; i++) {
                    if (filterConstraints.get(i)[0].equals(searchField)) {
                        filterConstraints.remove(i);
                        filterID--;
                    }
                }
            } else {
                boolean isOn = false;
                for (int i = 0; i < filterID; i++) {
                    if (filterConstraints.get(i)[0].equals(searchField)) {
                        filterConstraints.get(i)[0] = searchField;
                        isOn = true;
                    }
                }
                if (!isOn) {
                    Object[] temp = new Object[1];
                    temp[0] = searchField;
                    filterConstraints.add(temp);
                    filterID++;
                }
            }
            filter();
        }
    }

    /**
     * Filters by phone for victim/suspect
     *
     * @param phoneField Phone area of the filter
     * @param person     The filter itself
     */
    private void checkPhone(TextField phoneField, Person person) {
        if (phoneField.textProperty().get().isEmpty()) {
            for (int i = 0; i < filterID; i++) {
                if (filterConstraints.get(i)[0].equals(person)) {
                    filterConstraints.remove(i);
                    filterID--;
                }
            }
        } else {
            boolean isOn = false;
            for (int i = 0; i < filterID; i++) {
                if (filterConstraints.get(i)[0].equals(person)) {
                    person.setPhone(phoneField.getText());
                    filterConstraints.get(i)[0] = person;
                    filterConstraints.get(i)[1] = 0;
                    isOn = true;
                }
            }
            if (!isOn) {
                person.setPhone(phoneField.getText());
                Object[] temp = new Object[3];
                temp[0] = person;
                temp[2] = 0;
                filterConstraints.add(temp);
                filterID++;
            }
        }
    }

    /**
     * Adds a victim filter
     */
    public void addVictim() {

        if (filtersBox.getChildren().size() == 0) {
            filterLetterID = 'A';
        }

        if (filterLetterID <= 'Z') {
            System.out.println("ADD VICTIM " + filterLetterID);
            // Preparing victim structure
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
            Button deleteBtn = (Button) temp3.getChildren().get(0);
            TextField nameField = (TextField) temp.getChildren().get(1);
            TextField phoneField = (TextField) temp.getChildren().get(2);


            Victim victim = new Victim(String.valueOf(filterLetterID), victimNote.getId());

            Pane finalVictimNote = victimNote;
            // Delete filter button
            deleteBtn.setOnAction(event -> {       // Makes different modifications on the template. This one is to delete the container
                filtersBox.getChildren().remove(finalVictimNote);
                for (int i = 0; i < filterID; i++) {
                    if (filterConstraints.get(i)[0].equals(victim)) {
                        filterConstraints.remove(i);
                        filterID--;
                    }
                }
                ObservableList<CallRecord> test = FXCollections.observableArrayList();
                for (CallRecord cr : filteredData) {
                    if (cr.getOriginName().equals(victim.getIdentifier())) {
                        cr.setOriginName(" ");
                    }
                    if (cr.getDestinationName().equals(victim.getIdentifier())) {
                        cr.setDestinationName(" ");
                    }
                    test.add(cr);
                }
                filteredData = test;
                filter();
                System.out.println("DELETE VICTIM");
            });
            // Listeners for filter fields
            phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                checkPhone(phoneField, victim);
            });
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                for (int i = 0; i < filterID; i++) {
                    if (filterConstraints.get(i)[0].equals(victim)) {
                        Suspect newSuspect = (Suspect) filterConstraints.get(i)[0];
                        newSuspect.setIdentifier(nameField.getText());
                        filterConstraints.get(i)[0] = newSuspect;
                    }
                }
            });

            letter.setText(String.valueOf(filterLetterID));
            filtersBox.getChildren().add(victimNote);
            filterLetterID++;
        }
    }

    /**
     * Adds a suspect filter
     */
    public void addSuspect() {

        if (filtersBox.getChildren().size() == 0) {
            filterLetterID = 'A';
        }

        if (filterLetterID <= 'Z') {
            System.out.println("ADD SUSPECT " + filterLetterID);
            // Prepares the suspect structure
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
            Button deleteBtn = (Button) temp3.getChildren().get(0);
            TextField phoneField = (TextField) temp.getChildren().get(2);
            TextField nameField = (TextField) temp.getChildren().get(1);
            Suspect suspect = new Suspect(String.valueOf(filterLetterID), suspectNote.getId());

            Pane finalVictimNote = suspectNote;
            // Delete Button
            deleteBtn.setOnAction(event -> {       // Makes different modifications on the template. This one is to delete the container
                filtersBox.getChildren().remove(finalVictimNote);
                for (int i = 0; i < filterID; i++) {
                    if (filterConstraints.get(i)[0].equals(suspect)) {
                        filterConstraints.remove(i);
                        filterID--;
                    }
                }
                ObservableList<CallRecord> test = FXCollections.observableArrayList();
                for (CallRecord cr : filteredData) {
                    if (cr.getOriginName().equals(suspect.getIdentifier())) {
                        cr.setOriginName(" ");
                    }
                    if (cr.getDestinationName().equals(suspect.getIdentifier())) {
                        cr.setDestinationName(" ");
                    }
                    test.add(cr);
                }
                filteredData = test;
                filter();
                System.out.println("DELETE SUSPECT");
            });
            // Listeners for filter fields
            phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                checkPhone(phoneField, suspect);
            });
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                for (int i = 0; i < filterID; i++) {
                    if (filterConstraints.get(i)[0].equals(suspect)) {
                        Suspect newSuspect = (Suspect) filterConstraints.get(i)[0];
                        newSuspect.setIdentifier(nameField.getText());
                        filterConstraints.get(i)[0] = newSuspect;
                    }
                }
            });

            letter.setText(String.valueOf(filterLetterID));
            filterLetterID++;
            filtersBox.getChildren().addAll(suspectNote);
        }
    }

    /**
     * Controller for "Add call" button. No one is supposed to add a call, because you already have it in the database, or any excel, csv file, so a user should
     * not be able to add a call. It's made for its availability.
     * Default values, that if left unattended clearly show that they are fake.
     * The method changes the callsTable and the database at the same time.
     */
    public void addCall() {
        // Data manipulation part (the default data is ready to be used)
        CallRecord cr = new CallRecord(String.valueOf(sql.getMaxCallID() + 1), "\"" + caseID + "\"",
                "1", "1", "1900/01/01", "00:00", "Standard", "00:00");
        databaseCallsData.add(cr);  // Add to callsTable (visually) part
        sql.addCall(cr);        // Add to database part
        System.out.println("ADD: call");
        callsTable.scrollTo(cr);
        callsTable.getSelectionModel().select(cr);
    }

    /**
     * Add case button functionality
     *
     * @param actionEvent
     */
    public void addCase(ActionEvent actionEvent) {

        CaseRecord callRecord = new CaseRecord(String.valueOf(0), "case", "Description", "New", currentTime());
        sql.addCase(callRecord);
        loadCases();
    }

    /**
     * Add a note
     */
    public void addNote(ActionEvent actionEvent) {
        FileRecord nr = new FileRecord("\"" + (sql.getMaxIDNote()) + "\"", "" + 1 + "", "" + caseID + "",
                "\"" + "Case file" + "\"", "\"" + LocalDate.now() + "\"", "\" \"");
        sql.insertFile(nr);
        loadFiles(caseID);
    }

    /**
     * Finds the present time
     *
     * @return the present time
     */
    private String currentTime() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return timeStamp;
    }

    /**
     * Checks if any changes has been made to the cases
     *
     * @return true if a change has been made, false otherwise
     */
    public boolean casesUpdate() {
        ObservableList<CaseRecord> temp2 = sql.loadCases();
        boolean requireUpdate = false;
        int i = 0;

        try {
            if (temp2.size() != databaseCasesData.size()) {
                return true;
            }
            for (CaseRecord caseRecord : temp2) {

                if (i <= (databaseCasesData.size() - 1)) {

                    if (!caseRecord.getCaseID().equals(databaseCasesData.get(i).getCaseID())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDate().equals(databaseCasesData.get(i).getDate())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getName().equals(databaseCasesData.get(i).getName())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getStatus().equals(databaseCasesData.get(i).getStatus())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDetails().equals(databaseCasesData.get(i++).getDetails())) {
                        requireUpdate = true;
                    }
                }
            }
            return requireUpdate;
        } catch (Exception e) {
            return requireUpdate;
        }
    }

    /**
     * Checks if any changes has been made to the notes
     *
     * @return true if a change has been made, false otherwise
     */
    public boolean notesUpdate() {
        ObservableList<FileRecord> temp2 = sql.loadFiles(caseID);
        boolean requireUpdate = false;
        int i = 0;

        try {

            if (temp2.size() != databaseNotesData.size()) {
                return true;
            }

            for (FileRecord fileRecord : temp2) {

                if (i <= (databaseNotesData.size() - 1)) {

                    if (!fileRecord.getCaseID().equals(databaseNotesData.get(i).getCaseID())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getDate().equals(databaseNotesData.get(i).getDate())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getNoteID().equals(databaseNotesData.get(i).getNoteID())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getName().equals(databaseNotesData.get(i).getName())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getData().equals(databaseNotesData.get(i).getData())) {
                        requireUpdate = true;
                    }
                    if (!fileRecord.getUserID().equals(databaseNotesData.get(i++).getUserID())) {
                        requireUpdate = true;
                    }
                }
            }
            return requireUpdate;

        } catch (Exception e) {
            return requireUpdate;
        }
    }

    /**
     * Checks if any changes has been made to the calls
     *
     * @return true if a change has been made, false otherwise
     */
    public boolean callsUpdate() {
        ObservableList<CallRecord> temp2 = sql.loadCalls(caseID);
        boolean requireUpdate = false;
        int i = 0;

        try {
            if (temp2.size() != databaseCallsData.size()) {
                return true;
            }

            for (CallRecord caseRecord : temp2) {

                if (i <= (databaseCallsData.size() - 1)) {

                    if (!caseRecord.getCallID().equals(databaseCallsData.get(i).getCallID())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getCaseID().equals(databaseCallsData.get(i).getCaseID())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getOrigin().equals(databaseCallsData.get(i).getOrigin())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDestination().equals(databaseCallsData.get(i).getDestination())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDate().equals(databaseCallsData.get(i).getDate())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getTime().equals(databaseCallsData.get(i).getTime())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getCallType().equals(databaseCallsData.get(i).getCallType())) {
                        requireUpdate = true;
                    }
                    if (!caseRecord.getDuration().equals(databaseCallsData.get(i++).getDuration())) {
                        requireUpdate = true;
                    }
                }
            }
            return requireUpdate;
        } catch (Exception e) {
            return requireUpdate;
        }
    }

    /**
     * Controller initialization method. The controller runs this method first.
     */
    @FXML
    public void initialize() {

        filtersSPane.setPannable(true);
        filtersSPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        filtersSPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        notesSPane.setPannable(true);
        notesSPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Drag and drop
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
        // Initialization of data to their designated place
        loadCases();
        loadTable(caseID);
        loadFiles(caseID);
    }

    /**
     * Refresh button functionality
     */
    public void update() {
        String updateMessage = "UPDATE IN (";
        if (casesUpdate()) {
            updateMessage += " CASES";
            loadCases();
        }
        if (notesUpdate()) {
            updateMessage += " NOTES";
            loadFiles(caseID);
        }
        if (callsUpdate()) {
            updateMessage += " CALLS";
            loadTable(caseID);
        }
        updateMessage += " )";
        System.out.println(updateMessage);
    }

    /**
     * Accessor for column names of the app table
     *
     * @return list of column names
     */
    public List<String> getColumnNames() {
        List columnNames = new ArrayList(6);
        columnNames.add(fromPhoneColumn.getText());
        columnNames.add(toPhoneColumn.getText());
        columnNames.add(dateColumn.getText());
        columnNames.add(timeColumn.getText());
        columnNames.add(typeColumn.getText());
        columnNames.add(durationColumn.getText());

        return columnNames;
    }

    /**
     * Data format translator to appease database
     *
     * @param oldDate Date to be changed (dd/MM/yyyy supported only)
     * @return Data in the new format (yyyy/MM/dd supported)
     */
    private String changeDateFormat(String oldDate) {
        String oldFormat = "dd/MM/yyyy";
        String newFormat = "yyyy/MM/dd";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            Date date = sdf.parse(oldDate);
            sdf.applyPattern(newFormat);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cells are considered objects. To get the data from them use this
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
     * Alert box that asks the users for the importance of some unrecognized columns in the database
     *
     * @param fileString The unrecognized column
     * @return If it returns a number the users decided to use it as an existing column in the database
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
        if (result.get() == buttonTypeYes) {     // If users selects "Yes" button, a new pop-up asks him the correct column that should be used (if any)
            List<String> choices = new ArrayList<>();
            choices.addAll(getColumnNames());

            ChoiceDialog<String> dialog = new ChoiceDialog<>(fromPhoneColumn.getText(), choices);
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

    /**
     * Used for saving in a csv or a pdf file, the filtered/unfiltered information shown in the table
     * it works only if there are elements in the table
     */
    @FXML
    public void exportReport() {

        csvExport csvExp = new csvExport();
        pdfExport pdfExp = new pdfExport();

        //gets the program path and checks if there is a "reports" folder, if not, it will create one
        File exports = new File(System.getProperty("users.dir"), "./reports");

        if (!exports.exists()) {
            exports.mkdirs();
        }

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterCSV = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        FileChooser.ExtensionFilter extFilterPDF = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilterPDF);
        fileChooser.getExtensionFilters().add(extFilterCSV);
        fileChooser.setInitialFileName(caseTitle.getText().toString());
        fileChooser.setInitialDirectory(exports);
        //Show save file dialog
        try {
            File file = fileChooser.showSaveDialog(new Stage());
            String filePath = file.getPath();

            //checks if the extension selected is csv, if it is, calls csvOut method from csvExport class
            if (filePath.endsWith(".csv")) {

                csvExp.csvOut(filePath, filteredData);
                //otherwise it checks if the selected extension is pdf, if it is, calls pdfOut method from pdfExport class
            } else if (filePath.endsWith(".pdf")) {

                pdfExp.pdfOut(filePath, caseTitle.getText().toString(), filteredData);

            }
        } catch (Exception e) {
        }
    }

    /**
     * Coming soon window
     */
    @FXML
    public void openComingSoon() {
        try {
            Pane CaseObject = (Pane) FXMLLoader.load(getClass().getResource("/fxml/comingsoon.fxml"));
            Pane finalCaseObject = CaseObject;
            System.out.println(finalCaseObject);
            Pane pane = (Pane) CaseObject.getChildren().get(0);
            Label label = (Label) pane.getChildren().get(1);
            Button btn = (Button) pane.getChildren().get(0);
            btn.setOnAction(event -> {
                root.getChildren().remove(finalCaseObject);
            });
            label.setText("Coming Soon");
            finalCaseObject.setLayoutX(250);
            finalCaseObject.setLayoutY(100);
            DragResizeMod.makeResizable(finalCaseObject, null);
            root.getChildren().add(finalCaseObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used for opening the manual pdf that is located in the "Manual" folder
     * It uses the default installed pdf reader program on the used computer
     */
    @FXML
    public void openManual() {
        String manualLocation = System.getProperty("user.dir") + "\\Manual\\Manual.pdf";
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(manualLocation);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Extra window
     */
    @FXML
    public void openExtra() {
        try {
            Desktop.getDesktop().browse(new URI("http://www.bradford.ac.uk/business/"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Settings window
     */
    @FXML
    public void openSettings() {
        try {
            Pane CaseObject = (Pane) FXMLLoader.load(getClass().getResource("/fxml/settings.fxml"));
            Pane finalCaseObject = CaseObject;
            System.out.println(finalCaseObject);
            Pane pane = (Pane) CaseObject.getChildren().get(0);
            Button btn = (Button) pane.getChildren().get(3);
            btn.setOnAction(event -> {
                root.getChildren().remove(finalCaseObject);
            });
            finalCaseObject.setLayoutX(250);
            finalCaseObject.setLayoutY(100);
            DragResizeMod.makeResizable(finalCaseObject, null);
            root.getChildren().add(finalCaseObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Messages window
     */
    @FXML
    public void openMessages() {

        try {
            Pane CaseObject = (Pane) FXMLLoader.load(getClass().getResource("/fxml/adminpane.fxml"));
            Pane finalCaseObject = CaseObject;
            System.out.println(finalCaseObject);
            Pane pane = (Pane) CaseObject.getChildren().get(0);
            Button btn = (Button) pane.getChildren().get(1);

            btn.setOnAction(event -> {
                root.getChildren().remove(finalCaseObject);
            });

            finalCaseObject.setLayoutX(250);
            finalCaseObject.setLayoutY(100);
            DragResizeMod.makeResizable(finalCaseObject, null);
            root.getChildren().add(finalCaseObject);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Notes settings window
     */
    @FXML
    public void notes_settings() {
        try {

            Pane CaseObject = (Pane) FXMLLoader.load(getClass().getResource("/fxml/note_settings_pane.fxml"));
            Pane finalCaseObject = CaseObject;
            System.out.println(finalCaseObject);
            Pane pane = (Pane) CaseObject.getChildren().get(0);
            Button btn = (Button) pane.getChildren().get(0);
            Button btn2 = (Button) pane.getChildren().get(1);
            btn.setOnAction(event -> {
                root.getChildren().remove(finalCaseObject);
            });
            btn2.setOnAction(event -> {
                notesBox.getChildren().clear();
                sql.removeNotes(caseID);
                root.getChildren().remove(finalCaseObject);
            });
            finalCaseObject.setLayoutX(1540);

            finalCaseObject.setLayoutY(870);
            DragResizeMod.makeResizable(finalCaseObject, null);
            root.getChildren().add(finalCaseObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Log off functionality
     *
     * @param event
     */
    @FXML
    public void Logoff(ActionEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setResizable(false);
        stage.setWidth(417);
        stage.setHeight(535);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/signin_window.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void changeBG(ActionEvent event) {

        String image = "";

        switch (((Control) event.getSource()).getId()) {
            case "bgA":
                image = MainController.class.getResource("../res/images/background1.jpg").toExternalForm();
                break;
            case "bgB":
                image = MainController.class.getResource("../res/images/background2.jpg").toExternalForm();
                break;
            case "bgC":
                image = MainController.class.getResource("../res/images/background3.jpg").toExternalForm();
                break;
            case "bgD":
                image = MainController.class.getResource("../res/images/background4.jpg").toExternalForm();
                break;
            case "bgE":
                image = MainController.class.getResource("../res/images/background5.jpg").toExternalForm();
                break;
            case "bgF":
                image = MainController.class.getResource("../res/images/background6.jpg").toExternalForm();
                break;
            case "bgG":
                image = MainController.class.getResource("../res/images/background7.jpg").toExternalForm();
                break;
            case "bgH":
                image = MainController.class.getResource("../res/images/background8.jpg").toExternalForm();
                break;
            case "bgJ":
                image = MainController.class.getResource("../res/images/background9.jpg").toExternalForm();
                break;
            case "bgK":
                image = MainController.class.getResource("../res/images/background10.png").toExternalForm();
                break;
        }

        root.setStyle("-fx-background-image: url('" + image + "');");
    }

}

