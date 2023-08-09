package com.books.books.serviceImpl;

import com.books.books.model.Book;
import com.books.books.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookServiceImplTest {


    @InjectMocks
    BookServiceImpl bookService;
    @Mock
    private BookRepo bookRepo;


    Book book=new Book(3,"JungleBook","Srinath",5);
    Book book1=new Book(4,"Avengers","Sri",4);
    Book book2=new Book(5,"Mission Impossible","Jaganath",3);
    Book book3=new Book(6,"Hanuman Chalisa","Prabhayapa",5);
     List<Book> bookList=new ArrayList<>(Arrays.asList(book,book1,book2,book3));

    @BeforeEach
    public  void setUp(){
        MockitoAnnotations.openMocks(this);

    }


    @Test
     public void addBook_success() {
       /* Book book=new Book();*/
       when(bookRepo.save(book)).thenReturn(book);
       Book result=bookService.addBook(book);
       assertEquals(book,result);


    }

    @Test
    public void findById_success() {
      /*Book book=new Book();
      book.setBookId(1);*/
      when(bookRepo.findAll()).thenReturn(List.of(book));
      Book result=bookService.findById(3);
      assertEquals(book,result);

    }

    @Test
    public void deleteById_success() {
        int id=3;
       bookService.deleteById(id);
       verify(bookRepo,times(1)).deleteById(id);
    }

    @Test
    public void updateBook_success() {
        when(bookRepo.save(book)).thenReturn(book);
        Book result=bookService.updateBook(book);
        assertEquals(book,result);
    }

    @Test
   public void getAll() {
     when(bookRepo.findAll()).thenReturn(bookList);
     List<Book> result=bookService.getAll();
     assertEquals(bookList,result);
    }



}