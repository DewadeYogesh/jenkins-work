package com.books.books.controller;
import com.books.books.model.Book;
import com.books.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;


     @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book books=bookService.addBook(book);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook (@RequestBody Book book) {
        Book updateBook=bookService.updateBook(book);
        return  new ResponseEntity<>(updateBook,HttpStatus.ACCEPTED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Book> findById(@PathVariable int id) {
        Book findByIdBook=bookService.findById(id);
    if (findByIdBook==null){
return  new ResponseEntity<Book>(HttpStatus.valueOf(id));
    }
else
        return new ResponseEntity<Book>(findByIdBook,HttpStatus.OK);
    }
@DeleteMapping("/{id}")
    public String deleteById( @PathVariable int id) {
        String deleteBook= bookService.deleteById(id);
      return "Deleted";
    }
@GetMapping()
    public List<Book> getAll() {
        List<Book> bookList=bookService.getAll();
        return bookList;
    }

}


