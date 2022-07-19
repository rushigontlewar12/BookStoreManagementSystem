package com.capgemini.book.service;
import java.util.List;
import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.Review;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CustomerNotFoundException;
import com.capgemini.book.exception.ReviewAlreadyExistException;
import com.capgemini.book.exception.ReviewIdNotFoundException;
import com.capgemini.book.exception.ReviewListIsEmptyException;
public interface IReviewService {
	public List<Review> listAllReviews() throws ReviewListIsEmptyException;
	public Review addReview(Review updateReview) throws BookNotFoundException,CustomerNotFoundException,ReviewAlreadyExistException;
	public Review updateReview(Review review) throws BookNotFoundException,ReviewIdNotFoundException;
	public List<Review> listAllReviewsByBook(int  reviewid) throws BookNotFoundException,ReviewListIsEmptyException;
	public List<Review> listAllReviewsByCustomer(int  reviewid) throws CustomerNotFoundException,ReviewListIsEmptyException;
	public List<Book> listMostFavoredBooks() throws ReviewListIsEmptyException;
	public Review findById(int reviewId) throws ReviewIdNotFoundException;
	public Review deleteReview(int reviewId) throws ReviewIdNotFoundException  ;
}
