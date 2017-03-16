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
    public static void csvOut(String path, ObservableList<CallRecord> searchData){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("Caller Name,Caller Phone Number,Receiver Name,Receiver Phone Number,Date,Time,Type Of Call,Duration");
            writer.newLine();
            int i;

            for (i = 0; i < searchData.size(); i++)    {
                //writer.write(searchData.get(i).getOriginName().toString() + ",");
                writer.write(searchData.get(i).getOrigin().toString() + ",");
                //writer.write(searchData.get(i).getDestinationName().toString() + ",");
                writer.write(searchData.get(i).getDestination().toString() + ",");
                writer.write(searchData.get(i).getDate().toString() + ",");
                writer.write(searchData.get(i).getTime().toString() + ",");
                writer.write(searchData.get(i).getTypeOfCall().toString() + ",");
                writer.write(searchData.get(i).getDuration().toString());

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
