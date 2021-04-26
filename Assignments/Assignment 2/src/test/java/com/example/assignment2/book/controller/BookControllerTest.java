package com.example.assignment2.book.controller;

import com.example.assignment2.BaseControllerTest;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.service.BookService;
import com.example.assignment2.report.CsvReportService;
import com.example.assignment2.report.PdfReportService;
import com.example.assignment2.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.assignment2.TestCreationFactory.*;
import static com.example.assignment2.URLMapping.*;
import static com.example.assignment2.report.ReportType.CSV;
import static com.example.assignment2.report.ReportType.PDF_BOX;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CsvReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    void findAll() throws Exception {
        List<BookDTO> books = listOf(Book.class);

        when(bookService.findAll()).thenReturn(books);
        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }



    @Test
    void create() throws Exception {
        BookDTO reqBook =  BookDTO.builder()
                .author(randomString())
                .title(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        BookDTO reqBook =  BookDTO.builder()
                .id(id)
                .author(randomString())
                .title(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariable (BOOKS + ENTITY, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void changePrice() throws Exception {
        long id = randomLong();
        long newPrice = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .author(randomString())
                .title(randomString())
                .genre(randomString())
                .price(newPrice)
                .quantity(randomLong())
                .build();

        when(bookService.changePrice(id, newPrice)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS + ENTITY, newPrice, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void getBook() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .author(randomString())
                .title(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();
        when(bookService.get(id)).thenReturn(reqBook);

        ResultActions result = performGetWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void deleteBook() throws Exception {
        long id = randomLong();

        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk());
    }

    @Test
    void deleteAll() throws Exception {

        doNothing().when(bookService).deleteAll();

        ResultActions result = performDeleteAll(BOOKS);

        result.andExpect(status().isOk());
        verify(bookService, times(1)).deleteAll();

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF_BOX)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);

        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF_BOX.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));

        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

//        @Test
//    void searchBooks() throws Exception {
//
//        BookDTO book1 = BookDTO.builder()
//                .id(randomLong())
//                .author("Harper Lee")
//                .title(randomString())
//                .genre(randomString())
//                .price(randomLong())
//                .quantity(randomLong())
//                .build();
//        BookDTO book2 = BookDTO.builder()
//                .id(randomLong())
//                .author(randomString())
//                .title("To kill a mockingbird")
//                .genre(randomString())
//                .price(randomLong())
//                .quantity(randomLong())
//                .build();
//
////        bookService.create(book1);
////        bookService.create(book2);
//
//        List<BookDTO> books = listOf(Book.class);
//
//        when(bookService.findByGenreOrTitleOrAuthor("", "To kill", "Harper")).thenReturn(books);
//
//        ResultActions result = mockMvc.perform(get(BOOKS));
//
//        result.andExpect(status().isOk())
//                .andExpect(jsonContentToBe(books));
//
//    }
//
//
//        @Test
//    void sellBook() throws Exception {
//        long id = randomLong();
//        long requiredQuantity = 13;
//        long currentQuantity = 50;
//        BookDTO reqBook = BookDTO.builder()
//                .id(id)
//                .author(randomString())
//                .title(randomString())
//                .genre(randomString())
//                .price(randomLong())
//                .quantity(currentQuantity)
//                .build();
//
//        long finalQuantity = currentQuantity - requiredQuantity;
//
//        when(bookService.sellBook(id, requiredQuantity)).thenReturn(true);
//
//        ResultActions result = performSellBook(BOOKS + QUANTITY, finalQuantity, id);
//
//        result.andExpect(status().isOk());
//
//    }

}