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
public class UsersTable {
    private static SQL sql = new SQL();

    public static void createNameColumn(TableColumn name) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        name.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("originName"));
        name.setCellFactory(editableFactory);
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setOriginName(t.getNewValue());
                sql.editCellName(t.getRowValue().getOrigin(), t.getNewValue());
            }
        });
    }

    public static void createSurnameColumn(TableColumn surname) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCellPhone();
            }
        };

        surname.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("origin"));
        surname.setCellFactory(editableFactory);
        surname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setOrigin(t.getNewValue());
                sql.editCellNumber(t.getOldValue(), t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "origin", t.getNewValue());
            }
        });
    }

    public static void createEmailColumn(TableColumn email) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EditingCell();
            }
        };
        email.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("destinationName"));
        email.setCellFactory(editableFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
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

    public static void createPasswordColumn(TableColumn password) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCellPhone();
            }
        };
        password.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("destination"));
        password.setCellFactory(editableFactory);
        password.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDestination(t.getNewValue());
                sql.editCellNumber(t.getOldValue(), t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "destination", t.getNewValue());
            }
        });
    }

    public static void createPrivilegeColumn(TableColumn privilege) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        privilege.setCellValueFactory(new PropertyValueFactory<CallRecord, String>("date"));
        privilege.setCellFactory(editableFactory);
        privilege.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CallRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CallRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setDate(t.getNewValue());
                sql.editCell(Integer.parseInt(t.getRowValue().getCallID()), "date", t.getNewValue());
            }
        });
    }

}