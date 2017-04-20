package modules.file_export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import modules.record_structures.CallRecord;

import java.io.FileOutputStream;

/**
 * Created by Florin on 3/30/2017.
 */

public class pdfExport {
    //pdfOut method gets the path, the name of the case and the filtered items from the table
    public static void pdfOut(String path, String caseName, ObservableList<CallRecord> searchData){

        //sets the page settings for the pdf
        Document document = new Document();
        Rectangle rectangle = new Rectangle(PageSize.A4.rotate());
        document.setPageSize(rectangle);

        try {

            //creates the pdf where the path was chosen
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            //sets each column's width
            float[] columnWidths = { 2.5f, 2.5f, 2.5f, 2.5f, 1.5f, 1.5f, 1.5f, 1.5f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font f = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, GrayColor.GRAYWHITE);
            PdfPCell cell = new PdfPCell(new Phrase(caseName, f));
            cell.setBackgroundColor(GrayColor.GRAYBLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(8);
            table.addCell(cell);
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));

            //adds the cells with the column names
            table.addCell("Caller Name");
            table.addCell("Caller Phone Number");
            table.addCell("Receiver Name");
            table.addCell("Receiver Phone Number");
            table.addCell("Date");
            table.addCell("Time");
            table.addCell("Type Of Call");
            table.addCell("Duration");

            table.setHeaderRows(2);
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            //goes through each element in the list and prints it in the pdf table
            for (int i = 0; i < searchData.size(); i++)    {

                table.addCell(searchData.get(i).getOriginName().toString());
                table.addCell(searchData.get(i).getOrigin().toString());
                table.addCell(searchData.get(i).getDestinationName().toString());
                table.addCell(searchData.get(i).getDestination().toString());
                table.addCell(searchData.get(i).getDate().toString());
                table.addCell(searchData.get(i).getTime().toString());
                table.addCell(searchData.get(i).getCallType().toString());
                table.addCell(searchData.get(i).getDuration().toString());


            }
            document.add(table);

        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();
    }

}

