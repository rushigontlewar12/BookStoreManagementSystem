package com.capgemini.book.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.Customer;
import com.capgemini.book.entity.Review;
@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer>{
	@Query("select r from Review r where r.book.bookId=:bid")
	List<Review> getAllReviewsByBook(@Param("bid") int bookId);
	@Query("select  r from Review r where r.customer.customerId=:custId")
	List<Review> getAllReviewsByCustomer(@Param("custId") int custId);
	@Query("select r.book from Review r where r.rating>:rating")
	List<Book> listMostFavoredBooks(@Param("rating") double rating);
	@Query("select avg(r.rating) from Review r")
	double reviewAvg();
	@Query("select book from Book book where book.bookId=:bid")
	Book getBook(@Param("bid")int BookId);
	@Query("select customer from Customer customer where customer.customerId=:custId")
	Customer getCustomer(@Param("custId")int CustId);
	Review findByReviewId(int reviewId);
	
}

