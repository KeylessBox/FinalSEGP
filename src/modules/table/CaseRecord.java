package modules.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by AndreiM on 2/3/2017.
 */
public class CaseRecord {
    /**
     * DataStructure for accessing and modifying Case records from DataBase and TableView
     */
    private final StringProperty caseID;
    private final StringProperty name;
    private final StringProperty details;
    private final StringProperty status;
    private final StringProperty date;

    public CaseRecord(String caseID, String name, String details, String status, String date) {
        this.caseID = new SimpleStringProperty(caseID);
        this.name = new SimpleStringProperty(name);
        this.details = new SimpleStringProperty(details);
        this.status = new SimpleStringProperty(status);
        this.date = new SimpleStringProperty(date);
    }

    //Getters:

    public String getCaseID() {
        return caseID.get();
    }

    public String getName() { return name.get();}

    public String getDate() { return date.get();}

    public String getDetails() {
        return details.get();
    }

    public String getStatus() {
        return status.get();
    }


    //Setters:

    public void setDate(String date) { this.date.set(date);}

    public void setCaseID(String CaseID) {
        this.caseID.set(CaseID);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    //Properties:

    public StringProperty dateProperty() { return date;}

    public StringProperty caseIDProperty() {
        return caseID;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty detailsProperty() {
        return details;
    }

    public StringProperty statusProperty() {
        return status;
    }
}


