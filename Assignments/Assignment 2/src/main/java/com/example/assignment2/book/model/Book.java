package com.example.assignment2.book.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String author;

    @Column(nullable = false)
    private String title;

    @Column(length = 150)
    private String genre;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

}
