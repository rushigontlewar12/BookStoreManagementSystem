package com.capgemini.book.repository;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.OrderDetails;

@Repository
public interface IOrderRepository extends JpaRepository<OrderDetails, Integer>{

	@Query(value = "select * from order_details_master where order_id in (SELECT order_id from order_booklist where book_id=:b_id)",nativeQuery = true)
	List<OrderDetails> viewAllOrdersByBook(@Param("b_id") int bookId);
	
	@Query("select o from OrderDetails o where o.customer.customerId=:custId")
	List<OrderDetails> viewAllOrdersByCustomer(@Param("custId") int custId);

	@Query("select o from OrderDetails o where o.customer.customerId=:custId")
	public OrderDetails viewOrderByCustomer(@Param("custId") int custId);

}
