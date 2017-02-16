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

    public static void createOriginColumn(TableColumn origin) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        origin.setMinWidth(minCellWidth + 100);
        origin.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("origin"));
        origin.setCellFactory(editableFactory);
        origin.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setOrigin(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "origin",t.getNewValue());

            }
        });
    }

    public static void createDestinationColumn(TableColumn destination) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EditingCell();
            }
        };

        destination.setMinWidth(minCellWidth + 100);
        destination.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("destination"));
        destination.setCellFactory(editableFactory);
        destination.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDestination(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "destination",t.getNewValue());
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
