package com.books.books.serviceImpl;
import com.books.books.model.Book;
import com.books.books.repository.BookRepo;
import com.books.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

@Autowired
    private BookRepo bookRepo;
    @Override
    public Book addBook(Book book) {
        return  bookRepo.save(book);
    }

    @Override
    public Book findById(int bookId) {
for (Book book:bookRepo.findAll()){
    if (book.getBookId()==bookId){
        return  book;
    }
}
return null;
    }

    @Override
    public String deleteById(int bookId) {
        bookRepo.deleteById(bookId);
        return "Deleted";

    }

    @Override
    public Book updateBook(Book book,int bookId) {
        Book existingBook = bookRepo.findById(bookId)
                .orElseThrow(() ->new RuntimeException("book not found with id"+bookId));
          existingBook.setAuthorName(book.getAuthorName());
          existingBook.setBookName(book.getBookName());
          existingBook.setRatings(book.getRatings());

        return bookRepo.save(existingBook);
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList=bookRepo.findAll();
        return bookList;
    }

}
