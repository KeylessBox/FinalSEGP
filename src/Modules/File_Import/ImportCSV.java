package Modules.File_Import;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by AndreiM on 2/1/2017.
 */
/**
 * Import a csv file. Change the query as you see fit.
 */
public class ImportCSV {
    public static boolean importcsv(String path) {

        Connection con = null;
        PreparedStatement pst = null;

        String url="jdbc:mysql://localhost:3306/investigationsdb";
        String user = "user";
        String pw = "";

        try {
            /**
             * Establishes the connection with the database
             */
            con = DriverManager.getConnection(url, user, pw);
            /**
             * The query used to import data
             */
            String query = "LOAD DATA LOW_PRIORITY LOCAL INFILE '" + path + "' REPLACE INTO TABLE `investigationsdb`.`info` FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' ESCAPED BY '\"' LINES TERMINATED BY '\\r\\n' (`CaseId`, `SuspectName`, `PhoneNumber`, `Date`);"; //TODO Make it flexible (for different kinds of csv data)
            pst = con.prepareStatement(query);
            pst.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                return true;
            }
        }

    }

}