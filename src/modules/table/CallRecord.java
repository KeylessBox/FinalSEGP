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

    /**
     * Regex to identify column names. Further inquires to the police are needed to make it as complete as possible
     */
    private String[][] aliases = new String[][] {
            {"[Cc][Pp][Nn]","[Oo][Rr][Ii][Gg][Ii][Nn]", "[Cc][Aa][Ll][Ll][Ee][Rr]\\s*[Pp][Hh][Oo][Nn][Ee]\\s*[Nn][Uu][Mm][Bb][Ee][Rr]"},
            {"[Rr][Ee][Cc][Ee][Ii][Vv][Ee][Rr]\\s*[Pp][Hh][Oo][Nn][Ee]\\s*[Nn][Uu][Mm][Bb][Ee][Rr]","[Rr][Pp][Nn]", "[Dd][Ee][Ss][Tt][Ii][Nn][Aa][Tt][Ii][Oo][Nn]"},
            {"[Dd][Aa][Tt][Ee]"},
            {"[Tt][Ii][Mm][Ee]"},
            {"[Tt][Yy][Pp][Ee]\\s*[Oo][Ff]\\s*[Cc][Aa][Ll][Ll]"},
            {"[Dd][Uu][Rr][Aa][Tt][Ii][Oo][Nn]", "[Ee][Ss][Tt][Ii][Mm][Aa][Tt][Ee][Dd]\\s*[Tt][Ii][Mm][Ee]", "[Ee][Tt][Aa]"}
    };

    /**
     * Takes a column from the csv file. Checks if it resembles anything in the database.
     * @param header
     * @return  the index of the equivalent database column
     * @return  -1 if there aren't any matches
     */
    public int alias(String header) {

        for (int i=0; i<aliases.length; i++) {
            for (int j=0; j<aliases[i].length; j++ ) {
                if (header.matches(aliases[i][j])) {
                    return i;
                }
            }
        }
        return -1;
    }



    //Getter:
    /**
     * Get existing regex for comparing
     * @return
     */
    public String[][] getAliases() {
        return aliases;
    }

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

    public void setDestination(String destination) {this.destination.set(destination);}

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


