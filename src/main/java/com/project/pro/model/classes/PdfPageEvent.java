package com.project.pro.model.classes;

import com.lowagie.text.*;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import static com.itextpdf.kernel.pdf.PdfName.BaseFont;

public class PdfPageEvent extends PdfPageEventHelper {

    private Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
    private Font footerFont = new Font(Font.HELVETICA, 10);



    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER,
                new Phrase("Cabeçalho  ", headerFont ), 297.5f, 812, 0);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        // Adiciona o rodapé
        PdfContentByte canvas = writer.getDirectContent();
        ColumnText.showTextAligned(canvas,
                Element.ALIGN_CENTER,
                new Phrase("Página " + writer.getPageNumber(), footerFont),
                297.5f, 20, 0); // Posição no rodapé
    }
}
