package modules.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Aleksandr on 4/3/2017.
 */
public class UserRecord {

    private final StringProperty userID;
    private final StringProperty userName;
    private final StringProperty userSurname;
    private final StringProperty userEmail;
    private final StringProperty userPassword;
    private final StringProperty userPrivelege;

    public UserRecord(String userID, String userName, String userSurname, String userEmail, String userPassword, String userPrivelege) {
        this.userID = new SimpleStringProperty(userID);
        this.userName = new SimpleStringProperty(userName);
        this.userSurname = new SimpleStringProperty(userSurname);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.userPrivelege = new SimpleStringProperty(userPrivelege);
    }

    public String getUserID() {
        return userID.get();
    }

    public StringProperty userIDProperty() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserSurname() {
        return userSurname.get();
    }

    public StringProperty userSurnameProperty() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname.set(userSurname);
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public StringProperty userEmailProperty() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail.set(userEmail);
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public StringProperty userPasswordProperty() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword.set(userPassword);
    }

    public String getUserPrivelege() {
        return userPrivelege.get();
    }

    public StringProperty userPrivelegeProperty() {
        return userPrivelege;
    }

    public void setUserPrivelege(String userPrivelege) {
        this.userPrivelege.set(userPrivelege);
    }
}
