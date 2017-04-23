package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modules.record_structures.UserRecord;
import modules.factory.UserTableFactory;
import sql.SQL;

/**
 * Created by Aleksandr Stserbatski on 6/2/2017.
 * Controller for Users Admin Pane
 */
public class UsersController {

    @FXML
    protected TableView usersTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn passwordColumn;
    @FXML
    private TableColumn privilegeColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    // dynamic data storage:
    private ObservableList<UserRecord> usersData;
    private UserTableFactory columnFactoryUsers = new UserTableFactory();
    SQL sql = new SQL();

    /**
     * Load users table from database
     */
    public void loadUsersTable() {
        usersData = sql.loadUsers();
        columnFactoryUsers.createNameColumn(nameColumn);
        columnFactoryUsers.createSurnameColumn(surnameColumn);
        columnFactoryUsers.createEmailColumn(emailColumn);
        columnFactoryUsers.createPasswordColumn(passwordColumn);
        columnFactoryUsers.createPrivilegeColumn(privilegeColumn);
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        usersTable.setItems(usersData);
        usersTable.setEditable(true);
    }

    /**
     * Initialise a content inside Admin Pane
      */
    @FXML
    public void initialize() {

        loadUsersTable();

        // event listener for Add button
        addButton.setOnAction(event -> {
            UserRecord user = new UserRecord(String.valueOf(sql.getMaxUserID()), "users", "username", "email", "password", "users");
            usersData.add(user);
            sql.addUser(user);
            usersTable.scrollTo(user);
            usersTable.getSelectionModel().select(user);
            System.out.println("ADD: users " + user.getUserID() );
        });

        // event listener for Delete button
        deleteButton.setOnAction(event -> {
            if (usersTable.getSelectionModel().getSelectedItem() != null) {
                UserRecord user = (UserRecord) usersTable.getSelectionModel().getSelectedItem();
                if (user != null) {
                    sql.removeUser(Integer.parseInt(user.getUserID()));
                    usersData.remove(usersTable.getSelectionModel().getSelectedItem());
                    System.out.println("DELETE: users " + user.getUserID());
                }
            }
        });
    }
}
