package com.books.books.IntegrationTesting.controllerTesting;

import com.books.books.model.Book;
import com.books.books.respository.TestH2repo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

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
          baseUrl=baseUrl.concat(":").concat(port+"").concat("/api/add");
      }


       @Test
      public void testAddBook_success(){
          Book book=new Book(1,"Mahabharat","yogesh",5);
          Book response = restTemplate.postForObject(baseUrl, book, Book.class);


          assertEquals("Mahabharat",response.getBookName());

          assertEquals(1,testH2repo.findAll().size());



      }







}


