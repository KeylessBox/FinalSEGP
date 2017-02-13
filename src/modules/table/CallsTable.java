package modules.table;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sql.SQL;

/**
 * Created by AndreiM on 2/3/2017.
 */
public class CallsTable {
    private static SQL sql = new SQL();

    private static int minCellWidth = 100;

    public static void createCallerPNColumn(TableColumn callerPhoneNumber) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        callerPhoneNumber.setMinWidth(minCellWidth + 100);
        callerPhoneNumber.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("callerPhoneNumber"));
        callerPhoneNumber.setCellFactory(editableFactory);
        callerPhoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setCallerPhoneNumber(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "CallerPhoneNumber",t.getNewValue());

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

        receiverPhoneNumber.setMinWidth(minCellWidth + 100);
        receiverPhoneNumber.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("receiverPhoneNumber"));
        receiverPhoneNumber.setCellFactory(editableFactory);
        receiverPhoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setReceiverPhoneNumber(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "ReceiverPhoneNumber",t.getNewValue());
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
        date.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("date"));
        date.setCellFactory(editableFactory);
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDate(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "date",t.getNewValue());
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
        time.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("time"));
        time.setCellFactory(editableFactory);
        time.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setTime(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "time",t.getNewValue());
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

        typeOfCall.setMinWidth(minCellWidth + 50);
        typeOfCall.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("typeOfCall"));
        typeOfCall.setCellFactory(editableFactory);
        typeOfCall.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setTypeOfCall(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "typeOfCall",t.getNewValue());
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
        duration.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("duration"));
        duration.setCellFactory(editableFactory);
        duration.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDuration(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "duration",t.getNewValue());
            }
        });
    }
}
