package com.capgemini.book.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.Review;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CustomerNotFoundException;
import com.capgemini.book.exception.ReviewIdNotFoundException;
import com.capgemini.book.exception.ReviewListIsEmptyException;
import com.capgemini.book.repository.IBookRepository;
import com.capgemini.book.repository.IReviewRepository;

@Service
@Transactional
public class ReviewService implements IReviewService {
	@Autowired
	IReviewRepository repository;
	@Autowired
	ICustomerService cservice;
	@Autowired
	IBookRepository brepository;
	@Autowired
	IBookService bservice;
	Logger logger=LoggerFactory.getLogger(ReviewService.class);
	@Override
	public List<Review> listAllReviews() {
		return repository.findAll();
	}
	@Override
	public Review addReview(Review review)
			throws CustomerNotFoundException, BookNotFoundException 
	{
		logger.info("Inside add review method"); 
		review.setReviewOn(LocalDate.now());
		review.setCustomer(cservice.viewCustomer(review.getCustomer().getCustomerId()));
	review.setBook(bservice.viewBook(review.getBook().getBookId()));
		return repository.save(review);
	}
	
	
	@Override
	public Review findById(int reviewId) {
		logger.info("Inside  Method");
		return repository.findById(reviewId).orElse(null);

	}
	@Override
	public List<Review> listAllReviewsByBook(int reviewid) throws BookNotFoundException,ReviewListIsEmptyException {
		logger.info("Inside ListAllReviewsByBook method");
		Book book=repository.getBook(reviewid);
		if(book==null) {
			throw new BookNotFoundException("Book not present with given id");
		}
		List<Review> reList = repository.getAllReviewsByBook(reviewid);
		return reList;
	}

	@Override
	public List<Review> listAllReviewsByCustomer(int reviewid) throws ReviewListIsEmptyException {
		logger.info("Inside ListAllReviewsByCustomer");
		List<Review> reList=repository.getAllReviewsByCustomer(reviewid);
		if(reList.size()==0) {
			throw new ReviewListIsEmptyException("No review is available:");
		}
		return reList;
	}
	@Override
	public List<Book> listMostFavoredBooks() throws ReviewListIsEmptyException {
		logger.info("Inside ListMostFavoredBooks");
		double rating=repository.reviewAvg();
		List<Book> list=repository.listMostFavoredBooks(rating);
		if(list.size()==0) {
			throw new ReviewListIsEmptyException("No books available:");
		}
		return list;
	}
	/*@Override
	public String deleteReview(int reviewId) throws ReviewIdNotFoundException {
		logger.info("Inside delete review method");
		Review review;
		repository.deleteById(reviewId);
		return "Deleted review successfully";
	}*/
	@Override
	public Review deleteReview(int reviewId) throws ReviewIdNotFoundException {
		logger.info("Inside Customer Method");
		Optional<Review> optional= repository.findById(reviewId);
			if(!optional.isPresent()) {
				throw new ReviewIdNotFoundException("Review is present with id"+reviewId);
			}
		repository.deleteById(reviewId);
		return optional.get();
	}
	
	@Override
	public Review updateReview(Review review)throws ReviewIdNotFoundException {
		logger.info("Inside update review method");
		if (review == null) {
			throw new ReviewIdNotFoundException("Id is not available:" + review.getReviewId());
		}
		Review reviewData = repository.save(review);
		return reviewData;
	}
	
}
