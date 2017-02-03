package Modules.Table;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Created by AndreiM on 2/3/2017.
 */
public class CallsTable {
    private static int minCellWidth = 200;

    public static void createCallerPNColumn(TableColumn callerPhoneNumber) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        callerPhoneNumber.setMinWidth(minCellWidth);
        callerPhoneNumber.setCellValueFactory(new PropertyValueFactory<CallsRecord, String>("callerPhoneNumber"));
        callerPhoneNumber.setCellFactory(editableFactory);
        callerPhoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallsRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallsRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setCallerPhoneNumber(t.getNewValue());
            }
        });
    }

    public static void createReceiverPNColumn(TableColumn receiverPhoneNumber) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EditingCell();
            }
        };

        receiverPhoneNumber.setMinWidth(minCellWidth);
        receiverPhoneNumber.setCellValueFactory(new PropertyValueFactory<CallsRecord, String>("receiverPhoneNumber"));
        receiverPhoneNumber.setCellFactory(editableFactory);
        receiverPhoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallsRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallsRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setReceiverPhoneNumber(t.getNewValue());
            }
        });
    }

    public static void createDateColumn(TableColumn date) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EditingCell();
            }
        };

        date.setMinWidth(minCellWidth);
        date.setCellValueFactory(new PropertyValueFactory<CallsRecord, String>("date"));
        date.setCellFactory(editableFactory);
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallsRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallsRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDate(t.getNewValue());
            }
        });
    }

    public static void createTimeColumn(TableColumn time) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EditingCell();
            }
        };
        time.setMinWidth(minCellWidth);
        time.setCellValueFactory(new PropertyValueFactory<CallsRecord, String>("time"));
        time.setCellFactory(editableFactory);
        time.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallsRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallsRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setTime(t.getNewValue());
            }
        });
    }

    public static void createTypeOfCallColumn(TableColumn typeOfCall) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EditingCell();
            }
        };

        typeOfCall.setMinWidth(minCellWidth);
        typeOfCall.setCellValueFactory(new PropertyValueFactory<CallsRecord, String>("typeOfCall"));
        typeOfCall.setCellFactory(editableFactory);
        typeOfCall.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallsRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallsRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setTypeOfCall(t.getNewValue());
            }
        });
    }

    public static void createDurationColumn(TableColumn duration) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EditingCell();
            }
        };
        duration.setMinWidth(minCellWidth);
        duration.setCellValueFactory(new PropertyValueFactory<CallsRecord, String>("duration"));
        duration.setCellFactory(editableFactory);
        duration.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallsRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallsRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDuration(t.getNewValue());
            }
        });
    }
}
