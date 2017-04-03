package modules.table;

import controllers.MainController;
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

    public static void createOriginNameColumn(TableColumn originName) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        originName.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("originName"));
        originName.setCellFactory(editableFactory);
        originName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setOriginName(t.getNewValue());
                sql.editCellName(t.getRowValue().getOrigin(), t.getNewValue());
            }
        });
    }

    public static void createOriginColumn(TableColumn origin) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCellPhone();
            }
        };

        origin.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("origin"));
        origin.setCellFactory(editableFactory);
        origin.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setOrigin(t.getNewValue());

                sql.editCellNumber(t.getOldValue(), t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "origin", t.getNewValue());
            }
        });
    }

    public static void createDestinationNameColumn(TableColumn destinationName) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };
        destinationName.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("destinationName"));
        destinationName.setCellFactory(editableFactory);
        destinationName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDestinationName(t.getNewValue());
                if (t.getNewValue().length() < 5) {
                    sql.editCellName(t.getRowValue().getDestination(), t.getNewValue());
                } else {
                    MainController mc = new MainController();
//                    mc.showError("Identification/Name is too big");
                    t.getRowValue().setDestinationName(t.getOldValue());
                }
            }
        });
    }

    public static void createDestinationColumn(TableColumn destination) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCellPhone();
            }
        };
        destination.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("destination"));
        destination.setCellFactory(editableFactory);
        destination.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDestination(t.getNewValue());
                sql.editCellNumber(t.getOldValue(), t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "destination", t.getNewValue());
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

        date.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("date"));
        date.setCellFactory(editableFactory);
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDate(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "date", t.getNewValue());
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
        time.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("time"));
        time.setCellFactory(editableFactory);
        time.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setTime(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "time", t.getNewValue());
            }
        });
    }

    public static void createCallTypeColumn(TableColumn callType) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        callType.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("callType"));
        callType.setCellFactory(editableFactory);
        callType.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setCallType(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "callType", t.getNewValue());
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
        duration.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("duration"));
        duration.setCellFactory(editableFactory);
        duration.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDuration(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "duration", t.getNewValue());
            }
        });

    }
}