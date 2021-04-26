package com.example.assignment2.report;

import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.service.BookService;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.assignment2.report.ReportType.PDF_BOX;

@Service
@AllArgsConstructor
public class PdfReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        PDDocument document = new PDDocument();
        PDPage pdPage = new PDPage();
        document.addPage(pdPage);

        List<BookDTO> allBooks = bookService.booksOutOfStock();

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 13);
            contentStream.setLeading(16f);
            contentStream.newLineAtOffset(36, 660);
            contentStream.showText("UNAVAILABLE BOOKS: ");
            contentStream.newLine();
            contentStream.newLine();

            for(BookDTO book: allBooks) {
                contentStream.showText("ID: " + book.getId());
                contentStream.newLine();
                contentStream.showText("Title: " + book.getTitle());
                contentStream.newLine();
                contentStream.showText("Author: " + book.getAuthor());
                contentStream.newLine();
                contentStream.showText("Genre: " + book.getGenre());
                contentStream.newLine();
                contentStream.showText("Price: " + book.getPrice());
                contentStream.newLine();
                contentStream.showText("----------------------------------------------");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            document.save("Books_Out_Of_Stock.pdf");


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Books_Out_Of_Stock.pdf";
    }

    @Override
    public ReportType getType() {
        return PDF_BOX;
    }

}
