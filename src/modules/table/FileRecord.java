package modules.table;

/**
 * Created by AndreiM on 2/11/2017.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileRecord {
    /**
     * DataStructure for accessing and modifying Note(Case files) records from DataBase and TableView
     */
    private final StringProperty noteID;
    private final StringProperty userID;
    private final StringProperty caseID;
    private final StringProperty title;
    private final StringProperty date;
    private final StringProperty data;

    public FileRecord(String fileID, String userID, String caseID, String title, String date, String data) {
        this.noteID = new SimpleStringProperty(fileID);
        this.userID = new SimpleStringProperty(userID);
        this.caseID = new SimpleStringProperty(caseID);
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleStringProperty(date);
        this.data = new SimpleStringProperty(data);
    }

    //Getters:
    public String getFileID() {
        return noteID.get();
    }

    public String getUserID() {
        return userID.get();
    }

    public String getCaseID() {
        return caseID.get();
    }

    public String getName() {
        return title.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getData() {
        return data.get();
    }


    //Setters:
    public void setNoteID(String noteID) {
        caseID.set(noteID);
    }

    public void setUserID(String UserID) {
        caseID.set(UserID);
    }

    public void setCaseID(String CaseID) {
        caseID.set(CaseID);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setData(String data) {
        this.data.set(data);
    }


    //Properties:
    public StringProperty noteIDProperty() {
        return noteID;
    }

    public StringProperty userIDProperty() {
        return userID;
    }

    public StringProperty caseIDProperty() {
        return caseID;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty dataProperty() {
        return data;
    }
}


