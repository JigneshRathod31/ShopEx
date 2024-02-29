package com.jignesh.shopex.utils;

import android.os.Environment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Documented;

public class GenerateInvoicePDF {
    public static void GenerateInvoicePDF(){
        try{
//            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//            File file = new File(filePath, "invoice.pdf");
//            OutputStream os = new FileOutputStream(file);
//
//            PdfWriter writer = new PdfWriter(String.valueOf(file));
//            PdfDocument pdfDoc = new PdfDocument(writer);
//            Document doc = new Document(pdfDoc);
//
//            float columnWidth[] = {140, 140, 140, 140};
//            Table tb = new Table(columnWidth);
//
//            tb.addCell(new Cell().add(new Paragraph("1")));
//            tb.addCell(new Cell().add(new Paragraph("2")));
//            tb.addCell(new Cell().add(new Paragraph("3")));
//
//            doc.add(tb);
//
//            doc.close();

        } catch (Exception e) {

        }
    }
}
