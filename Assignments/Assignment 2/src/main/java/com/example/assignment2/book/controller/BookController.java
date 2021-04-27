package com.example.assignment2.book.controller;

import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.service.BookService;
import com.example.assignment2.report.ReportServiceFactory;
import com.example.assignment2.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.assignment2.URLMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @GetMapping(OUT_OF_STOCK)
    public List<BookDTO> outOfStockBooks() {
        return bookService.booksOutOfStock();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book){
        return bookService.edit(id, book);
    }

    @PatchMapping(SELL_BOOK)
    public BookDTO sellBook(@PathVariable Long id, @PathVariable Long quantity) {
        return bookService.sellBook(id, quantity);
    }

    @GetMapping(ENTITY)
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.get(id);
    }

    @DeleteMapping(ENTITY)
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @DeleteMapping
    public void deleteAll() {
        bookService.deleteAll();
    }

    @GetMapping(EXPORT_TYPE)
    public ResponseEntity<?> exportReport(@PathVariable ReportType type){

        ByteArrayOutputStream bodyOutput = reportServiceFactory.getReportService(type).export();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bodyOutput.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Books_Out_Of_Stock_PDFBox.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    @GetMapping(SEARCH_BOOKS)
    public List<BookDTO> searchBook(@PathVariable String search) {
        return bookService.findByGenreOrTitleOrAuthor(search, search, search);
    }

}
