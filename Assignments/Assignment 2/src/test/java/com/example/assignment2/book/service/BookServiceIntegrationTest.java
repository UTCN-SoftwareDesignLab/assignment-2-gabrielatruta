package com.example.assignment2.book.service;

import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
       List<Book> books = TestCreationFactory.listOf(Book.class);
       bookRepository.saveAll(books);
       List<BookDTO> all = bookService.findAll();
       assertEquals(books.size(), all.size());
    }


}