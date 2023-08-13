package com.books.books.controller;

import com.books.books.model.Book;
import com.books.books.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 *  book controller test
 *
 */
@RunWith(MockitoJUnitRunner.class)
class BookControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @Mock
    private BookService bookService;
    @InjectMocks
    private  BookController bookController;


    Book book=new Book(3,"JungleBook","Srinath",5);
    Book book1=new Book(4,"Avengers","Sri",4);
    Book book2=new Book(5,"Mission Impossible","Jaganath",3);
    Book book3=new Book(6,"Hanuman Chalisa","Prabhayapa",5);

 @BeforeEach
    public  void setUp(){

     MockitoAnnotations.initMocks(this);
     this.mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();


 }

 @Test
 @DisplayName("getingAllRecordsList_success")
    public  void getAllRecords_success() throws  Exception{
     List<Book> bookList=new ArrayList<>(Arrays.asList(book,book1,book2,book3));
     Mockito.when(bookService.getAll()).thenReturn(bookList);
     mockMvc.perform(MockMvcRequestBuilders.get("/book").contentType(MediaType.APPLICATION_JSON)).
             andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(4)));
 }

@Test
    public void getBookByid_success() throws Exception{
     Mockito.when(bookService.findById(book1.getBookId())).thenReturn(book1);
    mockMvc.perform(MockMvcRequestBuilders.get("/book/find/4").contentType(MediaType.APPLICATION_JSON)).
            andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue())).andExpect(MockMvcResultMatchers.jsonPath("$.bookName",is("Avengers")));

}


/**
 * add book_success
 *
 * @throws Exception java.lang. exception
 * Author : Yogesh Dewade
 */
@Test
    public  void  addBook_success() throws Exception{
     Book bookadd=Book.builder().bookId(7).bookName("Savindhan").
             authorName("vilas").ratings(6).build();
     Mockito.when(bookService.addBook(bookadd)).thenReturn(bookadd);
      String content=objectWriter.writeValueAsString(bookadd);

    MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.post("/book/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
    mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$",notNullValue())).andExpect(jsonPath("$.bookName",is("Savindhan")));
}

@Test
    public  void updatedBook_success() throws Exception{
    Book updatedBook = new Book();
    updatedBook.setBookName("Updated book");
    updatedBook.setAuthorName("Updated Author name");
    updatedBook.setRatings(4);

    when(bookService.updateBook(updatedBook, 3)).thenReturn(updatedBook);

    String content = objectWriter.writeValueAsString(updatedBook);

    mockMvc.perform(put("/book/update/3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)) // Use .content() to set the request body
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bookName", is("Updated book")));

    verify(bookService, times(1)).updateBook(updatedBook, 3);
}

@Test
    public  void deleteById() throws  Exception{
    Mockito.when(bookService.deleteById(book1.getBookId())).thenReturn(String.valueOf(book1));

    mockMvc.perform(MockMvcRequestBuilders.delete("/book/5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
}

}