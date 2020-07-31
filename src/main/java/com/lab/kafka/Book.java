package com.lab.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class Book {
    private String isbn;
    private String title;
    private Author author;

    public static Book create(String title, String firstname, String lastname) {
        Book book = new Book();
        book.setIsbn(UUID.randomUUID().toString());
        book.setTitle(title);
        Author author = new Author();
        author.setFirstname(firstname);
        author.setLastname(lastname);
        book.setAuthor(author);
        return book;
    }
}
