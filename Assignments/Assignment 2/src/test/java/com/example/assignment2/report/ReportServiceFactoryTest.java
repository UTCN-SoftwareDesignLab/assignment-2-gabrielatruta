package com.example.assignment2.report;

import com.example.assignment2.book.mapper.BookMapper;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.assignment2.report.ReportType.CSV;
import static com.example.assignment2.report.ReportType.PDF_BOX;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private PdfReportServiceJasper pdfReportServiceJasper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Test
    void getReportService() {
        bookService.create(bookMapper.toDTO(Book.builder()
                .id(-1L)
                .title("To kill a mockingbird")
                .author("Harper Lee")
                .genre("Southern Gothic")
                .price(35L)
                .quantity(0L)
                .build()));

        bookService.create(bookMapper.toDTO(Book.builder()
                .id(-1L)
                .title("The meaning of Happiness")
                .author("Alan Watts")
                .genre("Philosophy")
                .price(43L)
                .quantity(0L)
                .build()));

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF_BOX);
        Assertions.assertEquals("Books_Out_Of_Stock.pdf", pdfReportService.export());

        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("Books_Out_Of_Stock.csv", csvReportService.export());

        Assertions.assertEquals("Books_Out_Of_Stock_Jasper.pdf", pdfReportServiceJasper.export());

    }
}