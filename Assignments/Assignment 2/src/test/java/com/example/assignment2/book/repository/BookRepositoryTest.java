package com.example.assignment2.book.repository;

import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.example.assignment2.TestCreationFactory.randomLong;
import static com.example.assignment2.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = bookRepository.save(Book.builder()
                .author(randomString())
                .title(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(Book.builder().build()));
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);
        List<Book> all = bookRepository.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    public void testSearch() {
        final Book book = Book.builder()
                .title("To kill a mockingbird")
                .author("Harper Lee")
                .genre("Southern Gothic")
                .price(35L)
                .quantity(100L)
                .build();

        bookRepository.save(book);

        List<Book> result = bookRepository.findAllByGenreContainingOrTitleContainingOrAuthorContaining("aaaa", "To kill", "ajaja");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(book.getId(), result.get(0).getId());
    }
}
