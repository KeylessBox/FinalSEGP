package modules.file_export;

import javafx.collections.ObservableList;
import modules.table.CallRecord;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by Florin on 3/10/2017.
 */
public class csvExport {
    //csvOut method gets the path and the filtered items from the table
    public static void csvOut(String path, ObservableList<CallRecord> searchData){
        try {
            //creates the new file in the given path using the given name
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            //writes in the csv on the first line the names of the columns
            writer.write("Caller Name,Caller Phone Number,Receiver Name,Receiver Phone Number,Date,Time,Type Of Call,Duration");
            writer.newLine();
            int i;

            //goes through each row in the table an prints into csv the elements, separated by a comma
            for (i = 0; i < searchData.size(); i++)    {
                writer.write(searchData.get(i).getOriginName().toString() + ",");
                writer.write(searchData.get(i).getOrigin().toString() + ",");
                writer.write(searchData.get(i).getDestinationName().toString() + ",");
                writer.write(searchData.get(i).getDestination().toString() + ",");
                writer.write(searchData.get(i).getDate().toString() + ",");
                writer.write(searchData.get(i).getTime().toString() + ",");
                writer.write(searchData.get(i).getCallType().toString() + ",");
                writer.write(searchData.get(i).getDuration().toString());
                //checks if the last row was reached, if not, it prints "new line"
                if(i < searchData.size() - 1) {
                    writer.newLine();
                }
                writer.flush();

            }

            writer.close();
        }catch (IOException e){

        }
    }
}
