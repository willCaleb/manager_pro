package com.project.pro.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.sql.*;

@RestController
public class PDFGeneratorController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/download/pdf")
    public ResponseEntity<byte[]> generatePDF(@RequestParam String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            // Obter os dados da tabela
            String query = "SELECT * FROM " + tableName;
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                // Extrair metadados
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Criar documento PDF em memória
                Document document = new Document(PageSize.A4);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, outputStream);

                // Adicionar cabeçalho e rodapé
                writer.setPageEvent(new HeaderFooterPageEvent("Relatório: " + tableName));

                document.open();

                // Adicionar título
                Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
                Paragraph title = new Paragraph("Tabela: " + tableName, titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE);

                // Criar tabela PDF
                PdfPTable pdfTable = new PdfPTable(columnCount);
                pdfTable.setWidthPercentage(100);

                // Adicionar cabeçalhos
                for (int i = 1; i <= columnCount; i++) {
                    PdfPCell header = new PdfPCell(new Phrase(metaData.getColumnName(i)));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(header);
                }

                // Adicionar dados da tabela
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        pdfTable.addCell(rs.getString(i));
                    }
                }

                document.add(pdfTable);
                document.close();

                // Retornar o PDF como resposta
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("inline", tableName + ".pdf");

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(outputStream.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    static class HeaderFooterPageEvent extends PdfPageEventHelper {
        private final String headerText;

        public HeaderFooterPageEvent(String headerText) {
            this.headerText = headerText;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable header = new PdfPTable(1);
            PdfPTable footer = new PdfPTable(1);
            try {
                header.setWidths(new int[]{100});
                footer.setWidths(new int[]{100});

                // Adicionar cabeçalho
                PdfPCell headerCell = new PdfPCell(new Phrase(headerText));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBorder(Rectangle.NO_BORDER);
                header.addCell(headerCell);
                header.setTotalWidth(527);
                header.writeSelectedRows(0, -1, 36, 806, writer.getDirectContent());

                // Adicionar rodapé
                PdfPCell footerCell = new PdfPCell(new Phrase("Página " + writer.getPageNumber()));
                footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                footerCell.setBorder(Rectangle.NO_BORDER);
                footer.addCell(footerCell);
                footer.setTotalWidth(527);
                footer.writeSelectedRows(0, -1, 36, 30, writer.getDirectContent());
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
