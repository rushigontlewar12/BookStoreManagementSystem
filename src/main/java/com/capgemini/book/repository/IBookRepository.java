package com.capgemini.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;
import com.capgemini.book.entity.Category;
import com.capgemini.book.entity.OrderDetails;
import com.capgemini.book.entity.Review;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer>{

	@Query("select b from Book b where b.category.categoryName=:cname")
	List<Book> findBookByCategory(@Param("cname") String category);

	@Query("select b from Book b where b.review.rating>=:rating")
	List<Book> listBestSellingBooks(@Param("rating") double rating);
	
	@Query("select avg(r.rating) from Review r")
	double reviewAvg();
	
	@Query("select category from Category category where category.categoryName=:cname")
	Category getCategory(@Param("cname") String categoryName);

	@Query(value="select * from order_details_master where order_id in (select order_id from order_booklist  where book_id=:b_id)",nativeQuery = true)
	List<OrderDetails> getOrderDetails(@Param("b_id") int bookId);
	
	@Query("select r from Review r where r.book.bookId=:b_id")
	List<Review> getReview(@Param("b_id") int bookId);
	
	@Query(value="select * from book_cart where cart_id in(select cart_id from cart_booklist where book_id=:b_id)",nativeQuery = true)
	List<Integer> getCart(@Param("b_id") int bookId);
	
	Book findByBookId(int bookId);

}