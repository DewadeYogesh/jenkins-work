package com.books.books.respository;

import com.books.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2repo extends JpaRepository<Book,Integer> {
}
