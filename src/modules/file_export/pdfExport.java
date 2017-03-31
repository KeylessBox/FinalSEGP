package modules.file_export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import modules.table.CallRecord;

import java.io.FileOutputStream;

/**
 * Created by Florin on 3/30/2017.
 */

public class pdfExport {
    public static void pdfOut(String path, String caseName, ObservableList<CallRecord> searchData){
        Document document = new Document();
        Rectangle rectangle = new Rectangle(PageSize.A4.rotate());
        document.setPageSize(rectangle);
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

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
    private static void pdfTest(){
        Document document = new Document();
        Rectangle rectangle = new Rectangle(PageSize.A4.rotate());
        document.setPageSize(rectangle);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("pidief.pdf"));
            document.open();

            float[] columnWidths = {3, 3, 3, 3, 1, 1, 1, 1};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            Font f = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL, GrayColor.GRAYWHITE);
            PdfPCell cell = new PdfPCell(new Phrase("This is a header", f));
            cell.setBackgroundColor(GrayColor.GRAYBLACK);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(8);
            table.addCell(cell);
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));

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
            for (int counter = 1; counter < 101; counter++) {
                table.addCell(String.valueOf(counter));
                table.addCell("key " + counter);
                table.addCell("value " + counter);
            }
            document.add(table);
            document.close();


        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();
    }
    public static void main(String[] args){
     pdfTest();
    }
}

