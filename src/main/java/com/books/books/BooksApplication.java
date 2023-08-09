package com.books.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksApplication {
	public static Logger logger= LoggerFactory.getLogger(BooksApplication.class)

	public static void main(String[] args) {


		SpringApplication.run(BooksApplication.class, args);
	}

}
