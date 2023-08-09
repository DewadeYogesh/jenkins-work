package com.books.books.services;

import com.books.books.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book findById(int id);

    List<Book> getAll();

    String deleteById(int id);

    Book updateBook(Book book);
}
