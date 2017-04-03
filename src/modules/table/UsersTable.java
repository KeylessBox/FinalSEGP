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
                return new EdititingUserCell();
            }
        };
        name.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("name"));
        name.setCellFactory(editableFactory);
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setUserName(t.getNewValue());
                sql.editUserCell(Integer.parseInt(t.getRowValue().getUserID()), "name", t.getNewValue());
            }
        });
    }

    public static void createSurnameColumn(TableColumn surname) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EdititingUserCell();
            }
        };

        surname.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("surname"));
        surname.setCellFactory(editableFactory);
        surname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setUserSurname(t.getNewValue());
                sql.editUserCell(Integer.parseInt(t.getRowValue().getUserID()), "surname", t.getNewValue());
            }
        });
    }

    public static void createEmailColumn(TableColumn email) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {

                return new EdititingUserCell();
            }
        };
        email.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("email"));
        email.setCellFactory(editableFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setUserEmail(t.getNewValue());
                sql.editUserCell(Integer.parseInt(t.getRowValue().getUserID()), "email", t.getNewValue());
            }
        });
    }

    public static void createPasswordColumn(TableColumn password) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EdititingUserCell();
            }
        };
        password.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("password"));
        password.setCellFactory(editableFactory);
        password.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setUserPassword(t.getNewValue());
                sql.editUserCell(Integer.parseInt(t.getRowValue().getUserID()), "password", t.getNewValue());
            }
        });
    }

    public static void createPrivilegeColumn(TableColumn privilege) {

        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EdititingUserCell();
            }
        };

        privilege.setCellValueFactory(new PropertyValueFactory<UserRecord, String>("privelege"));
        privilege.setCellFactory(editableFactory);
        privilege.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserRecord, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserRecord, String> t) {
                System.out.println("CHANGE  Previous: " + t.getOldValue() + "   New: " + t.getNewValue());
                t.getRowValue().setUserPrivelege(t.getNewValue());
                sql.editUserCell(Integer.parseInt(t.getRowValue().getUserID()), "privileges", t.getNewValue());
            }
        });
    }

}