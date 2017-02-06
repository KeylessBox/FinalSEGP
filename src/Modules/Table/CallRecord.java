package Modules.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by AndreiM on 2/3/2017.
 */
public class CallRecord {


    /**
     * DataStructure for accessing and modifying Student records from DataBase and TableView
     */
        private final StringProperty callID;
        private final StringProperty caseID;
        private final StringProperty callerPhoneNumber;
        private final StringProperty receiverPhoneNumber;
        private final StringProperty date;
        private final StringProperty time;
        private final StringProperty typeOfCall;
        private final StringProperty duration;

    public CallRecord(String callID, String caseID, String callerPhoneNumber, String receiverPhoneNumber, String date, String time,
                      String typeOfCall, String duration) {
        this.callID = new SimpleStringProperty(callID);
        this.caseID = new SimpleStringProperty(caseID);
        this.callerPhoneNumber = new SimpleStringProperty(callerPhoneNumber);
        this.receiverPhoneNumber = new SimpleStringProperty(receiverPhoneNumber);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.typeOfCall = new SimpleStringProperty(typeOfCall);
        this.duration = new SimpleStringProperty(duration);
    }
        //Getters:

        public String getCallID() {
            return callID.get();
        }

        public String getCaseID() {
            return caseID.get();
        }

        public String getCallerPhoneNumber() {
            return callerPhoneNumber.get();
        }

        public String getReceiverPhoneNumber() {
            return receiverPhoneNumber.get();
        }

        public String getDate() {
            return date.get();
        }

        public String getTime() {
            return time.get();
        }

        public String getTypeOfCall() {
            return typeOfCall.get();
        }

        public String getDuration() {
        return duration.get();
    }

        //Setters:

        public void setCallID(String CallID) {
            callID.set(CallID);
        }

        public void setCaseID(String CaseID) {
            caseID.set(CaseID);
        }

        public void setCallerPhoneNumber(String CallerPhoneNumber) {
            this.callerPhoneNumber.set(CallerPhoneNumber);
        }

        public void setReceiverPhoneNumber(String ReceiverPhoneNumber) {
            this.receiverPhoneNumber.set(ReceiverPhoneNumber);
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public void setTime(String time) {
            this.time.set(time);
        }

        public void setTypeOfCall(String typeOfCall) {
            this.typeOfCall.set(typeOfCall);
        }

        public void setDuration(String duration) {
        this.duration.set(duration);
    }

        //Properties:

        public StringProperty studentIDProperty() {
            return callID;
        }

        public StringProperty caseIDProperty() {
            return caseID;
        }

        public StringProperty callerPhoneNumberProperty() {
            return callerPhoneNumber;
        }

        public StringProperty receiverPhoneNumberProperty() {
            return receiverPhoneNumber;
        }

        public StringProperty dateProperty() {
            return date;
        }

        public StringProperty timeProperty() {
            return time;
        }

        public StringProperty typeOfCallProperty() {
            return typeOfCall;
        }

        public StringProperty durationProperty() {
        return duration;
    }

    }


