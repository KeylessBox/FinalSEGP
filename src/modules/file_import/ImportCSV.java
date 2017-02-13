package modules.file_import;

import modules.table.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by AndreiM on 2/1/2017.
 * Import a csv file. Change the query as you see fit. So, for now, it is only a fixed query who import only 1 type of csv files.
 */
public class ImportCSV {
    public static void importcsv(String path) {
        /**
         * Establishing the connection
         */
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.connect();
        PreparedStatement pst = null;
        try {
            /**
             * The query used to import data to database, its preparation and execution
             */
            //TODO Make it flexible (for different kinds of csv data)
            String query = "LOAD DATA LOW_PRIORITY INFILE '" + path + "' REPLACE INTO TABLE `investigationsdb`.`calls` FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES(`CaseId`, `CallerPhoneNumber`, `ReceiverPhoneNumber`, `Date`, `Time`, `TypeOfCall`, `Duration`);";
            pst = connection.prepareStatement(query);
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
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
