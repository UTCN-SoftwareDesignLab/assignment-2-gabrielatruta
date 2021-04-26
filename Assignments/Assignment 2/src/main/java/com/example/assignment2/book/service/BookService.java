package com.example.assignment2.book.service;

import com.example.assignment2.book.mapper.BookMapper;
import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Book findByID (Long id) {
        return bookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book with id = " + id + " not found!"));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDTO(bookRepository.save(
                bookMapper.fromDTO(book)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findByID(id);

        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setQuantity(book.getQuantity());
        actBook.setGenre(book.getGenre());
        actBook.setPrice(book.getPrice());

        return  bookMapper.toDTO(
                bookRepository.save(actBook)
        );
    }

    public BookDTO changePrice(Long id, Long price) {
        Book book = findByID(id);
        book.setPrice(price);
        return bookMapper.toDTO(
                bookRepository.save(book)
        );
    }

    public BookDTO get(Long id) {
        return bookMapper.toDTO(
                findByID(id)
        );
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public List<BookDTO> findByGenreOrTitleOrAuthor(String genre, String title, String author){
        return bookRepository.findAllByGenreContainingOrTitleContainingOrAuthorContaining(genre, title, author).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean sellBook(Long id, long quantity) {

        Book book = findByID(id);

        if (book.getQuantity() < quantity)
            return false;

        book.setQuantity(book.getQuantity() - quantity);
        bookRepository.save(book);
        return true;
    }

    public List<BookDTO> booksOutOfStock() {
        List<BookDTO> outOfStock = new ArrayList<>();

        List<BookDTO> noQuantity = bookRepository.findAll().stream().map(bookMapper::toDTO).collect(Collectors.toList());

        for (BookDTO bookDTO: noQuantity)
            if (bookDTO.getQuantity() == 0)
                outOfStock.add(bookDTO);

        return outOfStock;
    }
}