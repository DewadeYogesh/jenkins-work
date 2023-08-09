package com.books.books.serviceImpl;
import com.books.books.model.Book;
import com.books.books.repository.BookRepo;
import com.books.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Book findById(int id) {
for (Book book:bookRepo.findAll()){
    if (book.getBookId()==id){
        return  book;
    }
}
return null;
    }

    @Override
    public String deleteById(int id) {
        bookRepo.deleteById(id);
        return "Deleted";

    }

    @Override
    public Book updateBook(Book book) {

        bookRepo.save(book);

        return book;
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList=bookRepo.findAll();
        return bookList;
    }

}
