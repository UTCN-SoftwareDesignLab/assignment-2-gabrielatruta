package com.example.assignment2.book.controller;

import com.example.assignment2.book.service.BookService;
import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.report.ReportServiceFactory;
import com.example.assignment2.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping(ENTITY)
    public BookDTO changePrice(@PathVariable Long id, @RequestBody Long newPrice){
        return bookService.changePrice(id, newPrice);
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

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type){
        return reportServiceFactory.getReportService(type).export();
    }

    @GetMapping(SEARCH_BOOKS)
    public List<BookDTO> searchBook(@RequestBody String genre, @RequestBody String title, @RequestBody String author) {
        return bookService.findByGenreOrTitleOrAuthor(genre, title, author);
    }

    @PatchMapping(ENTITY + QUANTITY)
    public ResponseEntity<?> sellBook(@PathVariable Long id, @PathVariable long quantity) {
        if (bookService.sellBook(id, quantity))
            return ResponseEntity.ok(quantity + " of books were sold!");
        else
            return ResponseEntity.badRequest().body("There aren't " + quantity + " such books!");
    }

}
