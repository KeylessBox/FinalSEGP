package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modules.table.UserRecord;
import modules.table.UsersTable;
import sql.SQL;

/**
 * Controller for login
 */
public class UsersController {
    @FXML
    protected TableView table2;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn passwordColumn;
    @FXML
    private TableColumn privelegeColumn;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    
    private ObservableList<UserRecord> usersData;

    private UsersTable columnFactoryUsers = new UsersTable();

    SQL sql = new SQL();    //  sql functionality

    public void loadUsersTable() {
        usersData = sql.loadUsers();
        columnFactoryUsers.createNameColumn(nameColumn);
        columnFactoryUsers.createSurnameColumn(surnameColumn);
        columnFactoryUsers.createEmailColumn(emailColumn);
        columnFactoryUsers.createPasswordColumn(passwordColumn);
        columnFactoryUsers.createPrivilegeColumn(privelegeColumn);
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table2.setItems(usersData);
        table2.setEditable(true);
    }

    @FXML
    public void initialize() {
        loadUsersTable();
        add.setOnAction(event -> {
            UserRecord cr = new UserRecord(String.valueOf(sql.getMaxUserID()), "users", "username", "email", "password", "users");
            usersData.add(cr);  // Add to callsTable (visually) part
            sql.addUser(cr);        // Add to database part
            System.out.println("ADD: users " + cr.getUserID() );
            table2.scrollTo(cr);
            table2.getSelectionModel().select(cr);

        });
        delete.setOnAction(event -> {
            if (table2.getSelectionModel().getSelectedItem() != null) {   // A row must be selected for it to work
                UserRecord user = (UserRecord) table2.getSelectionModel().getSelectedItem();   //   Getting the data
                if (user != null) {   // Checking if it's something there
                    sql.removeUser(Integer.parseInt(user.getUserID()));   // Delete from database part
                    usersData.remove(table2.getSelectionModel().getSelectedItem());  // Remove from callsTable part
                    System.out.println("DELETE: users " + user.getUserID());
                }
            }
        });
    }


}
