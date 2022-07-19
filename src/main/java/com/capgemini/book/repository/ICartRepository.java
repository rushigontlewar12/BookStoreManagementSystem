package com.capgemini.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;
import com.capgemini.book.service.CartService;


@Repository
public interface ICartRepository extends JpaRepository<BookCart, Integer>{
	
	@Query("select cart from BookCart cart where cart.customer.customerId=:cid")
	public BookCart getCart(@Param("cid") int cust_id);

	public BookCart findByCartId(int cartId);
	

}
