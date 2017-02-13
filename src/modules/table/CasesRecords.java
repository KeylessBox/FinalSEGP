package modules.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by AndreiM on 2/3/2017.
 */
public class CasesRecords {
    /**
     * DataStructure for accessing and modifying Student records from DataBase and TableView
     */
        private final StringProperty caseID;
        private final StringProperty name;
        private final StringProperty details;
        private final StringProperty status;

    public CasesRecords(String caseID, String name, String details, String status) {
        this.caseID = new SimpleStringProperty(caseID);
        this.name = new SimpleStringProperty(name);
        this.details = new SimpleStringProperty(details);
        this.status = new SimpleStringProperty(status);
    }
        //Getters:

        public String getCaseID() {
            return caseID.get();
        }

        public String getCaseName() {
            return name.get();
        }

        public String getDetails() {
            return details.get();
        }

        public String getStatus() {
            return status.get();
        }


        //Setters:
        public void setCaseID(String CaseID) {
            caseID.set(CaseID);
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


