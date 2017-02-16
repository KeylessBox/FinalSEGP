package modules.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by AndreiM on 2/3/2017.
 */
public class CallRecord {


    /**
     * DataStructure for accessing and modifying Call records from DataBase and TableView
     */
        private final StringProperty callID;
        private final StringProperty caseID;
        private final StringProperty origin;
        private final StringProperty destination;
        private final StringProperty date;
        private final StringProperty time;
        private final StringProperty typeOfCall;
        private final StringProperty duration;

    public CallRecord () {
        this.callID = null;
        this.caseID = null;
        this.origin = null;
        this.destination = null;
        this.date = null;
        this.time = null;
        this.typeOfCall = null;
        this.duration = null;
    }

    public CallRecord(String callID, String caseID, String origin, String destination, String date, String time,
                      String typeOfCall, String duration) {
        this.callID = new SimpleStringProperty(callID);
        this.caseID = new SimpleStringProperty(caseID);
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.typeOfCall = new SimpleStringProperty(typeOfCall);
        this.duration = new SimpleStringProperty(duration);
    }

    public CallRecord(String caseID, String origin, String destination, String date, String time,
                      String typeOfCall, String duration) {
        callID = null;
        this.caseID = new SimpleStringProperty(caseID);
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.typeOfCall = new SimpleStringProperty(typeOfCall);
        this.duration = new SimpleStringProperty(duration);
    }
    private String[][] aliases = new String[][] {
            {"CPN","Caller\\s*Phone\\s*Number"},
            {"ReceiverPhoneNumber","RPN"},
            {"date" },
            {"time"},
            {"typeOfCall"},
            {"Duration", "estimated time", "ETA"}
    };
    public int alias(String header) {

        for (int i=0; i<aliases.length; i++) {
            for (int j=0; j<aliases[i].length; j++ ) {
                if (header.equalsIgnoreCase(aliases[i][j])) {
                    return i;
                }
            }
        }
        return -1;
    }
    public String[][] getAliases() {
        return aliases;
    }

    public static void main(String[] args) {
        CallRecord cr = new CallRecord();
        System.out.println(cr.getAliases()[0][1]);
        System.out.println(cr.alias("cALLer PhoneNumber"));
        System.out.println(cr.alias("destination"));
    }

        //Getters:

        public String getCallID() {
            return callID.get();
        }

        public String getCaseID() {
            return caseID.get();
        }

        public String getOrigin() {
            return origin.get();
        }

        public String getDestination() {
            return destination.get();
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

        public void setOrigin(String origin) {
            this.origin.set(origin);
        }

        public void setDestination(String destination) {
            this.destination.set(destination);
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

        public StringProperty callIDProperty() {
            return callID;
        }

        public StringProperty caseIDProperty() {
            return caseID;
        }

        public StringProperty originProperty() {
            return origin;
        }

        public StringProperty destinationProperty() {
            return destination;
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


