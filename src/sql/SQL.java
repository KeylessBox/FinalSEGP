package sql;

/**
 * Created by AndreiM on 2/3/2017.
 */

import modules.table.CallRecord;
import modules.table.CasesRecords;
import modules.table.DBConnection;
import modules.table.NoteRecord;
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

    int ID = 0;
    /**
     * Connection with MYSQL database
     */
    private DBConnection dbConnection = new DBConnection();

    /**
     * Load students records from database
     *
     * @return ObservableList<StudentRecord>
     */
    public ObservableList<CallRecord> loadCalls(int i) {

        Connection connection = dbConnection.connect();
        ObservableList<CallRecord> data = FXCollections.observableArrayList();

        try {

            //execute query and store result in a result SET:
            ResultSet callsRS = connection.createStatement().executeQuery("SELECT * FROM calls WHERE caseId = " + i);

            while (callsRS.next()) {

                if (Integer.parseInt(callsRS.getString(1)) > maxIDCall) {
                    maxIDCall = Integer.parseInt(callsRS.getString(1));
                }

                data.add(new CallRecord(callsRS.getString(1),callsRS.getString(2),callsRS.getString(3),
                        callsRS.getString(4), callsRS.getString(5), callsRS.getString(6), callsRS.getString(7),
                        callsRS.getString(8)));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }


    public ObservableList<CasesRecords> loadCases() {
        Connection connection = dbConnection.connect();
        ObservableList<CasesRecords> data = FXCollections.observableArrayList();

        try {
            //execute query and store result in a result SET:
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT * FROM cases");

            while (caseRS.next()) {

                if (Integer.parseInt(caseRS.getString(1)) > maxIDCall) {
                    maxIDCall = Integer.parseInt(caseRS.getString(1));
                }
                data.add(new CasesRecords(caseRS.getString(1), caseRS.getString(2), caseRS.getString(3), caseRS.getString(4)));
            }
            maxIDCall = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public void addCase(CasesRecords cr) {

        Connection connection = dbConnection.connect();

        try {

            String s = "INSERT INTO cases(name, details, status)" +
                    " VALUES( '"+ cr.getCaseName() + "','"+ cr.getDetails() + "','" +
                    cr.getStatus() + "');";
            System.out.println(s);
            connection.createStatement().executeUpdate(s);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getCaseId(String s) {
        Connection connection = dbConnection.connect();
        int caseId = -1;

        try {
            //execute query and store result in a result SET:
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT id FROM cases Where name =\"" + s + "\"");
            if (caseRS.next()) {
                caseId = (caseRS.getInt(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return userId;
    }

    public void insertFile(NoteRecord nr) {
        Connection connection = dbConnection.connect();

        try {
            String query = "INSERT INTO notes(accountID, caseID, title, date, data) VALUES(" +
                    nr.getUserID() + "," + nr.getCaseID() + ","+ nr.getNoteName() + "," +
                    nr.getDate() + "," + nr.getData() + ");";
            System.out.println(query);
            connection.createStatement().executeUpdate(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads the note records from the database
     * @param i
     * @return
     */
    public ObservableList<NoteRecord> loadSQLNotes(int i) {
        Connection connection = dbConnection.connect();
        ObservableList<NoteRecord> data = FXCollections.observableArrayList();

        try {
            //execute query and store result in a result SET:
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT * FROM notes WHERE caseID=" + i +";");

            while (caseRS.next()) {
                if (Integer.parseInt(caseRS.getString(1)) > maxIDNote) {
                    maxIDNote = Integer.parseInt(caseRS.getString(1));
                }
                data.add(new NoteRecord(caseRS.getString(1), caseRS.getString(2), caseRS.getString(3), caseRS.getString(4), caseRS.getString(5), caseRS.getString(6)));
            }
            maxIDNote = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    /**
     * Removes a note from the database with the given ID
     * @param idNote
     */
    public void removeNote(int idNote) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("DELETE FROM `notes` WHERE id = " + idNote + ";");
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
        return maxIDNote;}

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
            connection.createStatement().executeUpdate("INSERT INTO calls(caseId, callerPhoneNumber, receiverPhoneNumber, date, time, typeOfCall, duration)\n" +
                    "VALUES("+cr.getCaseID()+",\""+ cr.getCallerPhoneNumber() + "\",\""+ cr.getReceiverPhoneNumber() + "\",\"" +
                    cr.getDate() + "\",\"" + cr.getTime() + "\",\"" + cr.getTypeOfCall() + "\",\"" + cr.getDuration() + "\");");
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * remove the Case selected
     * @param id
     */
    public void removeCase(int id){
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("DELETE FROM `cases` WHERE id = " + id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Edits the database when the user edits a cell in the table
     * @param id CallID (unique paramater of the calls) so that there aren't any duplicates
     * @param columnName Where in the specific row the change is made
     * @param change The new value for the specific attribute
     */
    public void editCell(int id, String columnName, String change) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE calls SET " + columnName + "= '" + change + "' WHERE id =" + id);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateCaseName(int id,String change){

        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE cases SET name" + "= '" + change + "' WHERE id =" + id);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateCaseFile(int id, String change) {
        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("UPDATE notes SET title" + "= '" + change + "' WHERE id =" + id);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }




}
