package com.books.books.IntegrationTesting.controllerTesting;

import com.books.books.model.Book;
import com.books.books.respository.TestH2repo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIntegrationTesting {
@LocalServerPort
    private int port;

      private  String baseUrl="http://localhost";


      private static RestTemplate restTemplate;
      @Autowired
      private TestH2repo testH2repo;







      @BeforeAll
      public static  void init(){
          restTemplate=new RestTemplate();
      }
         @BeforeEach
      public void setUp(){

          baseUrl=baseUrl.concat(":").concat(port+"").concat("/api");

      }


       @Test
      public void addBook(){
           Book book=new Book(1,"Mahabharat","yogesh",5);

          Book response = restTemplate.postForObject(baseUrl+"/add", book, Book.class);


          assertEquals("Mahabharat",response.getBookName());

          assertEquals(1,testH2repo.findAll().size());



      }

@Test
@Sql(statements = "insert into book (book_id,author_name,book_name,ratings) values(1,'Yogesh','mahabahrat',5)",executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = "delete from book where author_name='Yogesh' ",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public void getAll(){

          List<Book> response= restTemplate.getForObject(baseUrl,List.class);

          assertEquals(1,testH2repo.findAll().
                  size());



      }

    @Test
    @Sql(statements = "insert into book (book_id,author_name,book_name,ratings) values(3,'satish','ramayana',8)",executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from book where book_id=3 ",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
      public void findById(){

          Book book=restTemplate.getForObject(baseUrl+"/find/{id}", Book.class,3);
          assertAll(
                  ()->assertNotNull(book)
                  ,()->assertEquals(3,book.getBookId())
                  ,()->assertEquals("satish",book.getAuthorName())

          );
      }


    @Test
    @Sql(statements = "insert into book (book_id,author_name,book_name,ratings) values(3,'satish','ramayana',8)",executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from book where book_id=3 ",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
      public void updateBook(){
          Book book=  new Book("yajurvedh","satish",9);
          restTemplate.put(baseUrl+"/update/{bookId}",book,3);
        Book productfromDB = testH2repo.findById(3).get();
        assertAll(
                ()->assertNotNull(productfromDB)
                ,()->assertEquals("yajurvedh",productfromDB.getBookName())
                ,()->assertEquals(9,productfromDB.getRatings())

        );




    }


    @Test
    @Sql(statements = "insert into book (book_id,author_name,book_name,ratings) values(3,'satish','ramayana',8)",executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteById(){

        int size = testH2repo.findAll().size();
        assertEquals(1,size);

        restTemplate.delete(baseUrl+"/{id}",3);
        assertEquals(0,testH2repo.findAll().size());



    }


















}


