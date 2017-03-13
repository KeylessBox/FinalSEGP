package sql;

/**
 * Created by AndreiM on 2/3/2017.
 */

import modules.table.CallRecord;
import modules.table.CaseRecord;
import modules.table.DBConnection;
import modules.table.FileRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Aleksandr on 24-Oct-16.
 */
public class SQL {

    int maxIDCall = 1;
    int maxIDNote = 1;
    int maxIDCase = 1;

    /**
     * Connection with MYSQL database
     */
    private DBConnection dbConnection = new DBConnection();

    /**
     * Load students records from database
     *
     * @return ObservableList<StudentRecord>
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
                data.add(new CallRecord(callsRS.getString(1), callsRS.getString(2), originName,
                        callsRS.getString(3), destinationName,
                        callsRS.getString(4), callsRS.getString(5), callsRS.getString(6),
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
                data.add(new CaseRecord(caseRS.getString(1), caseRS.getString(2), caseRS.getString(3), caseRS.getString(4),caseRS.getString(5)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        }catch (SQLException e ){

        }
        return data;
    }

    public void addCase(CaseRecord cr) {

        Connection connection = dbConnection.connect();

        try {

            String s = "INSERT INTO cases(name, details, status, date)" +
                    " VALUES( '" + cr.getName() + "','" + cr.getDetails() + "','" +
                    cr.getStatus() + "','" + cr.getDate() + "');";
            System.out.println(s);
            connection.createStatement().executeUpdate(s);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getMaxCaseId(String s) {
        Connection connection = dbConnection.connect();
        int caseId = -1;

        try {
            //execute query and store result in a result SET:
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT id FROM cases WHERE date =\"" + s + "\"");
            if (caseRS.next()) {
                caseId = (caseRS.getInt(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
        return caseId;
    }

    public int getUserID(String s) {
        Connection connection = dbConnection.connect();
        int userId = -1;

        try {
            //execute query and store result in a result SET:
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT id FROM accounts WHERE name ='" + s + "'");
            System.out.println("SELECT id FROM Users Where Name ='" + s + "'");
            if (caseRS.next()) {
                userId = (caseRS.getInt(0));
                System.out.println("UserID inside getUserID sql" + userId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {

        }
        return userId;
    }

    public void updateNote(String data, String noteId) {
        Connection connection = dbConnection.connect();

        try {
            String query = ("UPDATE notes SET data ='" + data +
                    "' WHERE id='" + noteId + "';");
            System.out.println(query);
            connection.createStatement().executeUpdate(query);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertFile(FileRecord nr) {
        Connection connection = dbConnection.connect();

        try {
            String query = "INSERT INTO notes(accountID, caseID, title, date, data) VALUES(" +
                    nr.getUserID() + "," + nr.getCaseID() + "," + nr.getName() + "," +
                    nr.getDate() + "," + nr.getData() + ");";
            System.out.println(query);
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
                data.add(new FileRecord(caseRS.getString(1), caseRS.getString(2), caseRS.getString(3), caseRS.getString(4), caseRS.getString(5), caseRS.getString(6)));
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
     * Loads the note record from the database
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
                data.add(new FileRecord(noteRS.getString(1), noteRS.getString(2), noteRS.getString(3), noteRS.getString(4), noteRS.getString(5), noteRS.getString(6)));
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
     * @param idNote
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
     * Check if String is an integer
     *
     * @param data
     * @return
     */
    public static boolean isInteger(String data) {
        try {
            Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Insert lots of calls from file probably
     *
     * @param data
     */
    public void insertCalls(ObservableList<CallRecord> data) {
        for (CallRecord cr : data) {
            addCall(cr);
        }
    }

    /**
     * Inserts default call to database
     */
    public void addCall(CallRecord cr) {

        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("INSERT INTO calls(caseId, origin, destination, date, time, typeOfCall, duration)\n" +
                    "VALUES(" + cr.getCaseID() + ",'" + cr.getOrigin() + "','" + cr.getDestination() + "','" +
                    cr.getDate() + "','" + cr.getTime() + "','" + cr.getTypeOfCall() + "','" + cr.getDuration() + "');");
            connection.createStatement().executeUpdate("INSERT INTO phoneNumbers(personName, phoneNumber) VALUES" +
                    "(' ','" +cr.getOrigin() + "');");
            connection.createStatement().executeUpdate("INSERT INTO phoneNumbers(personName, phoneNumber) VALUES" +
                    "(' ','" +cr.getDestination() + "');");
            connection.createStatement().executeUpdate("DELETE n1 FROM phoneNumbers n1, phoneNumbers n2 " +
                    "WHERE n1.id > n2.id AND n1.phoneNumber = n2.phoneNumber");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes a specific call from the database
     *
     * @param id
     */
    public void removeCall(int id) {

        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("DELETE FROM `calls` WHERE id = " + id);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * remove the Case selected
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
     * Edits the database when the user edits a cell in the table
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


    public void updateCaseName(int caseId, String change) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE cases SET name" + "= '" + change + "' WHERE id =" + caseId);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateCaseStatus(int caseId, String change) {
        Connection connection = dbConnection.connect();
        try {
            connection.createStatement().executeUpdate("UPDATE cases SET status" + "= '" + change + "' WHERE id =" + caseId);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateCaseFile(int id, String change) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE notes SET title= '" + change + "' WHERE id =" + id);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateDate(int id, String change){
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE cases SET date" + "= '" + change + "' WHERE id =" + id);
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
