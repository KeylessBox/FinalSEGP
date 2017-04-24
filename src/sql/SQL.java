package sql;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modules.record_structures.CallRecord;
import modules.record_structures.CaseRecord;
import modules.record_structures.FileRecord;
import modules.record_structures.UserRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * MySql queries
 */
public class SQL {

    private int maxIDCall = 1;
    private int maxIDNote = 1;
    private int maxIDCase = 1;
    private int maxUserID = 1;

    /**
     * Connection with MYSQL database
     */
    private DBConnection dbConnection = new DBConnection();

    /**
     * Loads call records from database
     *
     * @return List with calls
     */
    public ObservableList<CallRecord> loadCalls(int caseID) {

        Connection connection = dbConnection.connect();
        ObservableList<CallRecord> data = FXCollections.observableArrayList();
        try {
            //execute query and store result in a result SET:
            ResultSet callsRS = connection.createStatement().executeQuery("SELECT * FROM calls WHERE caseId = " + caseID);

            while (callsRS.next()) {
                ResultSet originRS = connection.createStatement().executeQuery("SELECT personName FROM phoneNumbers WHERE phoneNumber= '"
                        + callsRS.getString(3) + "';");

                ResultSet destinationRS = connection.createStatement().executeQuery
                        ("SELECT personName FROM phoneNumbers WHERE phoneNumber= '" +
                                callsRS.getString(4) + "';");
                if (Integer.parseInt(callsRS.getString(1)) > maxIDCall) {
                    maxIDCall = Integer.parseInt(callsRS.getString(1));
                }
                String originName;
                String destinationName;
                if (originRS.next()) {
                    originName = originRS.getString(1);
                } else {
                    originName = "";
                }
                if (destinationRS.next()) {
                    destinationName = destinationRS.getString(1);
                } else {
                    destinationName = "";
                }
                String switchString = callsRS.getString(5);
                switchString = changeDateFormat(switchString);
                data.add(new CallRecord(callsRS.getString(1), callsRS.getString(2), originName,
                        callsRS.getString(3), destinationName,
                        callsRS.getString(4), switchString, callsRS.getString(6),
                        callsRS.getString(7), callsRS.getString(8)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
        return data;
    }

    /**
     * Loads case records from database
     *
     * @return List with cases
     */
    public ObservableList<CaseRecord> loadCases() {
        maxIDCase = 1;
        Connection connection = dbConnection.connect();
        ObservableList<CaseRecord> data = FXCollections.observableArrayList();

        try {
            //execute query and store result in a result SET:
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT * FROM cases");

            while (caseRS.next()) {

                if (Integer.parseInt(caseRS.getString(1)) > maxIDCall) {
                    maxIDCase = Integer.parseInt(caseRS.getString(1));
                }
                data.add(new CaseRecord(caseRS.getString(1), caseRS.getString(2), caseRS.getString(3), caseRS.getString(4), caseRS.getString(5)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
        return data;
    }

    /**
     * Query to add a case to database
     * @param caseRecord
     */
    public void addCase(CaseRecord caseRecord) {

        Connection connection = dbConnection.connect();
        try {
            String query = "INSERT INTO cases(name, details, status, date)" +
                    " VALUES( '" + caseRecord.getName() + "','" + caseRecord.getDetails() + "','" +
                    caseRecord.getStatus() + "','" + caseRecord.getDate() + "');";
            connection.createStatement().executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates a note from the database
     * @param data New body data to replace the old data
     * @param noteId note that is changed
     */
    public void updateNote(String data, String noteId) {
        Connection connection = dbConnection.connect();

        try {
            String query = ("UPDATE notes SET data ='" + data +
                    "' WHERE id='" + noteId + "';");
            connection.createStatement().executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add note to database
     * @param noteRecord
     */
    public void addNote(FileRecord noteRecord) {
        Connection connection = dbConnection.connect();

        try {
            String query = "INSERT INTO notes(caseID, title, date, data) VALUES(" +
                    noteRecord.getCaseID() + "," + noteRecord.getName() + "," +
                    noteRecord.getDate() + "," + noteRecord.getData() + ");";
            connection.createStatement().executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads the note records from the database
     *
     * @param caseID
     * @return
     */
    public ObservableList<FileRecord> loadFiles(int caseID) {
        Connection connection = dbConnection.connect();
        ObservableList<FileRecord> data = FXCollections.observableArrayList();

        try {
            //execute query and store result in a result SET:
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT * FROM notes WHERE caseID=" + caseID + ";");

            while (caseRS.next()) {
                if (Integer.parseInt(caseRS.getString(1)) > maxIDNote) {
                    maxIDNote = Integer.parseInt(caseRS.getString(1));
                }
                data.add(new FileRecord(caseRS.getString(1), caseRS.getString(2), caseRS.getString(3), caseRS.getString(4), caseRS.getString(5)));
            }
            maxIDNote = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
        return data;
    }

    /**
     * Loads a specifc note from the database
     *
     * @param noteID
     * @return
     */
    public ObservableList<FileRecord> loadNote(int noteID) {
        Connection connection = dbConnection.connect();
        ObservableList<FileRecord> data = FXCollections.observableArrayList();

        try {
            //execute query and store result in a result SET:
            ResultSet noteRS = connection.createStatement().executeQuery("SELECT * FROM notes WHERE id=" + noteID + ";");

            while (noteRS.next()) {
                if (Integer.parseInt(noteRS.getString(1)) > maxIDNote) {
                    maxIDNote = Integer.parseInt(noteRS.getString(1));
                }
                data.add(new FileRecord(noteRS.getString(1), noteRS.getString(2), noteRS.getString(3), noteRS.getString(4), noteRS.getString(5)));
            }
            maxIDNote = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
        return data;
    }

    /**
     * Removes a note from the database with the given ID
     *
     * @param idNote the id of the note to be removed
     */
    public void removeNote(int idNote) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("DELETE FROM `notes` WHERE id = " + idNote + ";");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get max value for call id
     *
     * @return
     */
    public int getMaxCallID() {
        return maxIDCall;
    }

    /**
     * Get max value for note id
     */
    public int getMaxIDNote() {
        maxIDNote++;
        return maxIDNote;
    }

    /**
     * Adds calls to database
     *
     * @param data
     */
    public void insertCalls(ObservableList<CallRecord> data) {
        for (CallRecord cr : data) {
            addCall(cr);
        }
    }

    /**
     * Translates the mysql format to dd/MM/yyyy format
     * @param oldDate date whose format is changed
     * @return date with the new format
     */
    private String changeDateFormat(String oldDate) {
        String oldFormat = "yyyy-MM-dd";
        String newFormat = "dd/MM/yyyy";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            Date d = sdf.parse(oldDate);
            sdf.applyPattern(newFormat);
            return sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts default call to database
     */
    public void addCall(CallRecord callRecord) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("INSERT INTO calls(caseId, origin, destination, date, time, callType, duration)\n" +
                    "VALUES(" + callRecord.getCaseID() + ",'" + callRecord.getOrigin() + "','" + callRecord.getDestination() + "','" +
                    callRecord.getDate() + "','" + callRecord.getTime() + "','" + callRecord.getCallType() + "','" + callRecord.getDuration() + "');");
            connection.createStatement().executeUpdate("INSERT INTO phoneNumbers(personName, phoneNumber) VALUES" +
                    "(' ','" + callRecord.getOrigin() + "');");
            connection.createStatement().executeUpdate("INSERT INTO phoneNumbers(personName, phoneNumber) VALUES" +
                    "(' ','" + callRecord.getDestination() + "');");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes a specific call from the database
     *
     * @param callId
     */
    public void removeCall(int callId) {

        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("DELETE FROM `calls` WHERE id = " + callId);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Eemoves the Case selected
     *
     * @param caseID
     */
    public void removeCase(int caseID) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("DELETE FROM `cases` WHERE id = " + caseID);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Edits the database when the users edit a cell in the callsTable
     *
     * @param id         CallID (unique paramater of the calls) so that there aren't any duplicates
     * @param columnName Where in the specific row the change is made
     * @param change     The new value for the specific attribute
     */
    public void editCell(int id, String columnName, String change) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("UPDATE calls SET " + columnName + "= '" + change + "' WHERE id =" + id);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Edits the database when users
     * @param oldValue
     * @param newValue
     */
    public void editCellNumber(String oldValue, String newValue) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("INSERT INTO phoneNumbers (personName,phoneNumber) VALUES (' ','" + newValue + "');");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editCellName(String phoneNumber, String change) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("UPDATE phoneNumbers SET personName= '" + change +
                    "' WHERE phoneNumber='" + phoneNumber + "';");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates case name
     * @param caseId
     * @param change
     */
    public void updateCaseName(int caseId, String change) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE cases SET name" + "= '" + change + "' WHERE id =" + caseId);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates case status
     * @param caseId
     * @param change
     */
    public void updateCaseStatus(int caseId, String change) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("UPDATE cases SET status" + "= '" + change + "' WHERE id =" + caseId);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates the last accessed date of a case
     * @param id
     * @param change
     */
    public void updateDate(int id, String change) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE cases SET date" + "= '" + change + "' WHERE id =" + id);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes all notes from a case
     * @param caseID
     */
    public void removeNotes(int caseID) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("DELETE FROM `notes` WHERE caseID = " + caseID + ";");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads all users from database
     * @return list of users
     */
    public ObservableList<UserRecord> loadUsers() {
        Connection connection = dbConnection.connect();
        ObservableList<UserRecord> data = FXCollections.observableArrayList();

        try {
            //execute query and store result in a result SET:
            ResultSet usersRS = connection.createStatement().executeQuery("SELECT * FROM accounts;");

            while (usersRS.next()) {
                if (Integer.parseInt(usersRS.getString(1)) > maxUserID) {
                    maxUserID = Integer.parseInt(usersRS.getString(1));
                }
                data.add(new UserRecord(usersRS.getString(1), usersRS.getString(2),
                        usersRS.getString(3),
                        usersRS.getString(4), usersRS.getString(5), usersRS.getString(6)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
        }
        return data;
    }

    /**
     * Edits user related information
     * @param id
     * @param columnName
     * @param change
     */
    public void editUserCell(int id, String columnName, String change) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("UPDATE accounts SET " + columnName + "= '" + change + "' WHERE id =" + id);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a user from database
     * @param userId
     */
    public void removeUser(int userId) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("DELETE FROM `accounts` WHERE id = " + userId);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds a user to database
     * @param cr
     */
    public void addUser(UserRecord cr) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("INSERT INTO accounts(id,name, surname, email, password, privileges)\n" +
                    "VALUES('" + cr.getUserID() + "','" + cr.getUserName() + "','" + cr.getUserSurname() + "','" +
                    cr.getUserEmail() + "','" + cr.getUserPassword() + "','" + cr.getUserPrivelege() + "');");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getMaxUserID() {
        maxUserID++;
        return maxUserID;
    }
}
