package com.capgemini.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CustomerNotFoundException;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>{
	
	@Query(value = "select customer_id from order_details_master where order_id in (select order_id from order_booklist  where book_id=:b_id)",nativeQuery = true)
	List<Customer> findCustomerByBook(@Param("b_id") int id) throws BookNotFoundException;

	Customer findByCustomerId(int customerId) throws CustomerNotFoundException;

}
