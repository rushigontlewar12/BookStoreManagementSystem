package com.capgemini.book.controller;
import java.util.List;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.Review;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CustomerNotFoundException;
import com.capgemini.book.exception.ReviewAlreadyExistException;
import com.capgemini.book.exception.ReviewIdNotFoundException;
import com.capgemini.book.exception.ReviewListIsEmptyException;
import com.capgemini.book.service.IBookService;
import com.capgemini.book.service.ICustomerService;
import com.capgemini.book.service.IReviewService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
@Validated
public class ReviewController {
	@Autowired
	IReviewService service;
	@Autowired
	ICustomerService cservice;
	@Autowired
	IBookService bservice;
	Logger logger=LoggerFactory.getLogger(Review.class);
	@GetMapping("/getReview/{reviewId}")
	public ResponseEntity<Object> getReview(@PathVariable int reviewId) throws ReviewIdNotFoundException  {
		logger.info("Inside getReview method");
		Review review=null;
		try {
			review = service.findById(reviewId);
		} catch (ReviewIdNotFoundException e) {
			
			System.err.println(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
			return new ResponseEntity<Object>(review, HttpStatus.OK);
	}
	@PostMapping("/addReview")
	public ResponseEntity<Object> addReview(@RequestBody @Valid Review review) throws BookNotFoundException,CustomerNotFoundException,ReviewAlreadyExistException {
		try {
			logger.info("Inside addReview method");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Review reviewData=null;
		reviewData = service.addReview(review);
		try {
			reviewData = service.addReview(review);
		} catch (BookNotFoundException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			
		} catch (CustomerNotFoundException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			
			
		} catch (ReviewAlreadyExistException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(reviewData, HttpStatus.OK);
	}

	@PutMapping("/updateReview")
	public ResponseEntity<Object> updateReview(@RequestBody Review review) {
		logger.info("Inside updateReview method");
		Review reviewData=null;
		try {
			reviewData = service.updateReview(review);
		} catch (BookNotFoundException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
			
		} catch (ReviewIdNotFoundException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(reviewData, HttpStatus.OK);
	}

	@GetMapping("/selectAll")
	public ResponseEntity<List<Review>> selectAllReviews() throws ReviewListIsEmptyException {
		logger.info("Inside selectAllReview method");
		List<Review> ReList = service.listAllReviews();
		return new ResponseEntity<List<Review>>(ReList, HttpStatus.OK);
	}
	@GetMapping("/FavoredBooks")
	public ResponseEntity<List<Book>>viewAllBooksByRating() throws ReviewListIsEmptyException  {
		logger.info("Inside FavoredBooks method");
		List<Book> ReList = service.listMostFavoredBooks();
		return new ResponseEntity<List<Book>>(ReList, HttpStatus.OK);
	}
	@GetMapping("/ByBook")
	public ResponseEntity<List<Review>>viewAllReviewsByBook(@PathVariable("bookId")  int bid) throws BookNotFoundException, ReviewListIsEmptyException  {
		logger.info("Inside viewAllReviewsByBook method");
		List<Review> ReList = service.listAllReviewsByBook(bid);
		return new ResponseEntity<List<Review>>(ReList, HttpStatus.OK);
	}
	
	@GetMapping("/Bycustomer/{customerId}")
	public ResponseEntity<List<Review>>viewAllReviewsByCustomer(@PathVariable("customerId") int cid) throws CustomerNotFoundException,ReviewListIsEmptyException {
		logger.info("Inside viewAllReviewsByCustomer method");
		List<Review> ReList = service.listAllReviewsByCustomer(cid);
		System.out.println(ReList.size());
		return new ResponseEntity<List<Review>>(ReList, HttpStatus.OK);
	}

	
	@DeleteMapping("/deleteReview/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable("reviewId") int reviewId)throws ReviewIdNotFoundException {
		logger.info("Inside Delete Review Method");
		Review reviewData = service.deleteReview(reviewId);
		
		return new ResponseEntity<String>("review deleted with given id"+reviewData.getReviewId(), HttpStatus.OK);
	}
	
}