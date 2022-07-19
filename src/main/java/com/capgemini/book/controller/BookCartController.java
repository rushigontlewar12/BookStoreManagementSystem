package com.capgemini.book.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;
import com.capgemini.book.exception.BookAlreadyExistException;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CartIsEmptyException;
import com.capgemini.book.exception.CartNotFoundException;
import com.capgemini.book.service.IBookService;
import com.capgemini.book.service.ICartService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/bms/bookCart")
@Validated
public class BookCartController {

	@Autowired
	ICartService cartService;
	@Autowired
	IBookService bookService;
	
	Logger logger =LoggerFactory.getLogger(BookCartController.class);
	
	@PostMapping("/add/{cid}/{bookId}")
	public ResponseEntity<Object> addBookToCart(
			@PathVariable("cid") @Valid @Positive(message = "Enter Positive Number") int cartId,
			@PathVariable int bookId) {
		BookCart bookCartData;
		try {
			logger.info("Inside Add cart");
			bookCartData = cartService.addBookToCart(cartId, bookId);
		} catch (CartNotFoundException | BookNotFoundException | BookAlreadyExistException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(bookCartData, HttpStatus.OK);
	}
	
	@DeleteMapping("/remove/{cid}/{bid}")
	public ResponseEntity<Object> RemoveBookFromCart(@PathVariable("cid") @Valid @Positive(message = "Enter Positive Number") int cartId,@PathVariable("bid") @Valid @Positive(message = "Enter Positive Number") int bookId) 
	{
		logger.info("Inside Remove cart");
		try {
			logger.info("Inside Remove cart");
			BookCart bookCartData=cartService.removeBookFromCart(cartId, bookId);
			return new ResponseEntity<Object>(bookCartData,HttpStatus.OK);
		} catch (CartNotFoundException | BookNotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} 
	}
	
	@GetMapping("/viewAll")
	public ResponseEntity<Object> viewAllCarts()
	{
		List<BookCart> cartList;
		cartList= cartService.listAllCarts();
		return new ResponseEntity<Object>(cartList, HttpStatus.OK);
		
	}
	
	@GetMapping("/clear")
	public ResponseEntity<String> clearCart(@RequestParam("cid") @Valid @Positive(message = "Enter Positive Number") int cartId)
	{
		String message;
		try {
			logger.info("Inside Clear cart");
			message = cartService.clearCart(cartId);
		} catch (CartNotFoundException | CartIsEmptyException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	@GetMapping("/getCart/{custId}")
	public ResponseEntity<Integer> getCart(@PathVariable int custId) {

		int cartId = cartService.getCart(custId);
		return new ResponseEntity<Integer>(cartId, HttpStatus.OK);
	}

	@GetMapping("/viewAllItems/{cartId}")
	public ResponseEntity<Object> viewAllItems(@PathVariable int cartId) {

		List<Book> list = new ArrayList<>();
		try {
			list = cartService.viewAllById(cartId);
		} catch (CartNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}

	@ExceptionHandler
	public ResponseEntity<Object> constraintViolationHandler(ConstraintViolationException ex,WebRequest request) {
		String error= ex.getConstraintViolations().iterator().next().getMessage();
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
}
