package SQL;

/**
 * Created by AndreiM on 2/3/2017.
 */

import Modules.Table.CallRecord;
import Modules.Table.CasesRecords;
import Modules.Table.DBConnection;
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
    public ObservableList<CallRecord> loadCalls() {

        Connection connection = dbConnection.connect();
        ObservableList<CallRecord> data = FXCollections.observableArrayList();

        try {

            //execute query and store result in a result SET:
            ResultSet callsRS = connection.createStatement().executeQuery("SELECT * FROM Calls");

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
            ResultSet caseRS = connection.createStatement().executeQuery("SELECT * FROM Cases");

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

    /**
     * Get max value for student id
     *
     * @return
     */
    public int getMaxCallID() {
        return maxIDCall;
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
     * Insert empty student record into database
     */
    public void addCall() {

        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("INSERT INTO `Students` (`studentID`, `studentName`, `studentUB`, `courseID`, `yearOfStudy`, `email`, `tutorID`) VALUES (" + (++ID) + ", 'name surname', '00000000', '3', '4', 'example@example.com', '0')");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Remove selected student record from database
     *
     * @param studentID
     */
    public void removeCall(String studentID) {

        Connection connection = dbConnection.connect();

        try {
            connection.createStatement().executeUpdate("DELETE FROM `Students` WHERE studentID = " + studentID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
//
//
//    /**
//     * Delete current table "Students" and fill it with new values
//     *
//     * @param studentList
//     */
//    public void saveStudentsQuery(ObservableList<StudentRecord> studentList) {
//
//        if (studentList != null) {
//
//            Connection connection = dbConnection.connect();
//
//            //delete all values from table:
//            try {
//                connection.createStatement().executeUpdate("DELETE FROM `Students`");
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//
//            maxIDStudent = 0;
//
//            System.out.println("\nSTUDENTS TABLE: \n");
//
//            for (StudentRecord studentRecord : studentList) {
//
//                try {
//
//                    String tutorID = studentRecord.getTutorID();
//
//                    if (!(isInteger(tutorID))) {
//                        ResultSet temp;
//                        temp = connection.createStatement().executeQuery("SELECT * FROM Tutors WHERE tutorName = '" + tutorID + "'");
//                        if (temp.next()) {
//                            tutorID = temp.getString(1);
//                        }
//
//                        if (tutorID.equals("-")){
//                            tutorID ="0";
//                        }
//                    }
//
//                    connection = dbConnection.connect();
//                    String statement = "INSERT INTO `Students` (`studentID`, `studentName`, `studentUB`, `courseID`, `yearOfStudy`, `email`, `tutorID`) VALUES (" + (++maxIDStudent) + ", '" + studentRecord.getStudentName() + "', '" + studentRecord.getStudentUB() + "', '" + studentRecord.getCourseID() + "', '" + studentRecord.getYearOfStudy() + "', '" + studentRecord.getEmail() + "', '" + tutorID + "' )";
//                    connection.createStatement().executeUpdate(statement);
//
//                    System.out.println("[ " + studentRecord.getStudentName() + " , " + studentRecord.getStudentUB() + " , " + studentRecord.getCourseID() + " , " + studentRecord.getYearOfStudy() + " , " + studentRecord.getEmail() + " , " + tutorID + " ]");
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//        }
//    }
//
//    /**
//     * Delete all tables values for new administrator
//     */
//    public void reset(){
//
//        Connection connection = dbConnection.connect();
//
//        //delete all values from table:
//        try {
//            connection.createStatement().executeUpdate("DELETE FROM `calls`");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        connection = dbConnection.connect();
//
//        //delete all values from table:
//        try {
//            connection.createStatement().executeUpdate("DELETE FROM `calls`");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
