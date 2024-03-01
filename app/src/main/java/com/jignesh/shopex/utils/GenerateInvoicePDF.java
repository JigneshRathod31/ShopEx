package com.jignesh.shopex.utils;

import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jignesh.shopex.R;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GenerateInvoicePDF {

    static List<InvoiceItem> invItem;
    public static void generateInvoicePDF(Context context){
        invItem = new ArrayList<InvoiceItem>();
        try{
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath().toString()+"/Invoice.pdf";
            Document doc = new Document();

            PdfWriter.getInstance(doc, new FileOutputStream(filePath));

            doc.open();
            Paragraph para = new Paragraph("\n\nInvoice\n\n");
            para.setAlignment(Element.ALIGN_CENTER);

            doc.add(para);

            PdfPTable tb = new PdfPTable(4);
            tb.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Phrase(""));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);


            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPhrase(new Phrase("Item Name:"));
            tb.addCell(cell);

            cell.setPhrase(new Phrase("Item Quantity:"));
            tb.addCell(cell);

            cell.setPhrase(new Phrase("Item Price:"));
            tb.addCell(cell);

            cell.setPhrase(new Phrase("Total:"));
            tb.addCell(cell);

            invItem.add(new InvoiceItem("Item-1", 2, Double.parseDouble("200")));
            invItem.add(new InvoiceItem("Item-2", 6, Double.parseDouble("400")));
            invItem.add(new InvoiceItem("Item-3", 34, Double.parseDouble("600")));

            Double total, grandTotal=Double.parseDouble("0");



            cell.setBackgroundColor(BaseColor.WHITE);
            for(InvoiceItem item : invItem){
                total = (item.itemPrice*item.itemQuantity);

                cell.setPhrase(new Phrase(item.itemName));
                grandTotal+=total;
                tb.addCell(cell);

                cell.setPhrase(new Phrase(String.valueOf(item.itemQuantity)));
                tb.addCell(cell);

                cell.setPhrase(new Phrase(String.valueOf(item.itemPrice)));
                tb.addCell(cell);

                cell.setPhrase(new Phrase(String.valueOf(total)));
                tb.addCell(cell);
                cell.setPhrase(new Phrase(String.valueOf(grandTotal)));
            }

            tb.addCell("");
            tb.addCell("");
            tb.addCell("");
            tb.addCell(cell);

            doc.add(tb);
            doc.close();

            Toast.makeText(context, "PDF Generated Successfully!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}

class InvoiceItem{
    public String itemName;
    public Integer itemQuantity;
    public Double itemPrice;

    public InvoiceItem(String itemName, Integer itemQuantity, Double itemPrice){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

}
