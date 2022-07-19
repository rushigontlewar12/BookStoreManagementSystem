package com.capgemini.book.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.capgemini.book.entity.Book;
import com.capgemini.book.exception.BookAlreadyExistException;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.BooksListIsEmptyException;
import com.capgemini.book.exception.CannotRemoveBookException;
import com.capgemini.book.exception.CategoryNotFoundException;
import com.capgemini.book.service.IBookService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/bms/book")
@Validated
public class BookController {

	@Autowired
	private IBookService bookService;

	Logger logger = LoggerFactory.getLogger(BookController.class);

	@PostMapping("/add")
	public ResponseEntity<Object> addBook(@Valid @RequestBody Book book) {
		Book bookInfo = null;
		try {
			logger.info("Inside Add Book");
			bookInfo = bookService.createBook(book);
		} catch (BookAlreadyExistException | CategoryNotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(bookInfo, HttpStatus.OK);
	}

	@PutMapping("/edit")
	public ResponseEntity<Object> editBook(@Valid @RequestBody Book book) {
		Book editedBook;
		try {
			logger.info("Inside edit Book");
			editedBook = bookService.editBook(book);
		} catch (BookNotFoundException | CategoryNotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(editedBook, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{bid}")
	public ResponseEntity<String> deleteBook(@PathVariable("bid") @Valid @Positive(message = "Enter Positive Number") int id) throws CannotRemoveBookException {
		String message="";
		try {
			logger.info("Inside Delete Book");
			message = bookService.deleteBook(id);
		} catch (BookNotFoundException|CannotRemoveBookException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping("/viewById/{bid}")
	public ResponseEntity<Object> viewBook(@PathVariable("bid") @Valid @Positive(message = "Enter Positive Number") int id) {
		Book book = null;
		try {
			logger.info("Inside view By Id");
			book = bookService.viewBook(id);
		} catch (BookNotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(book, HttpStatus.OK);
	}

	@GetMapping("/viewAll")
	public ResponseEntity<Object> viewAllBooks(){
		List<Book> bookList;
		try {
			logger.info("Inside View all Book");
			bookList = bookService.listAllBooks();
		} catch (BooksListIsEmptyException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(bookList, HttpStatus.OK);
	}

	@GetMapping("/viewByCategory")
	public ResponseEntity<Object> viewBooksByCategory(@RequestParam("category") @Valid @Pattern(regexp = "[a-zA-Z ]+",message = "Enter valid category") String category) {
		List<Book> bookList;
		try {
			logger.info("Inside View By Category");
			bookList = bookService.listBooksByCategory(category);
		} catch (CategoryNotFoundException | BooksListIsEmptyException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(bookList, HttpStatus.OK);
	}

	@GetMapping("/viewBestSellingBook")
	public ResponseEntity<Object> viewBestBooks() {
		List<Book> bookList;
		try {
			logger.info("Inside Best Selling Books");
			bookList = bookService.listBestSellingBook();
		} catch (BooksListIsEmptyException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(bookList, HttpStatus.OK);
	}
	@ExceptionHandler
	public ResponseEntity<Object> constraintViolationHandler(ConstraintViolationException ex,WebRequest request) {
		String error= ex.getConstraintViolations().iterator().next().getMessage();
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	

}