package com.project.pro.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.pro.exception.CustomException;
import com.project.pro.model.classes.PdfPageEvent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

public class PDFUtils {

    public static ResponseEntity<byte[]> generatePdf() {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);

            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);

            PdfPageEvent event = new PdfPageEvent();

            pdfWriter.setPageEvent(event);

            document.open();

            PdfPTable table = new PdfPTable(3);

            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell(new PdfPCell(new Phrase("Coluna 1")));
            table.addCell(new PdfPCell(new Phrase("Coluna 2")));
            table.addCell(new PdfPCell(new Phrase("Coluna 3")));

            document.add(table);

            document.add(new Paragraph("Teste inclusão PDF"));

            document.close();

            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "documento.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        }catch (Exception e) {
            throw new CustomException("Não foi possível gerar PDF");
        }

    }

}
