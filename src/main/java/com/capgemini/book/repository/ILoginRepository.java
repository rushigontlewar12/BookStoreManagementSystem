package com.capgemini.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.entity.User;
import com.google.common.base.Optional;

public interface ILoginRepository extends JpaRepository<User, Integer>{

	@Query("select u from User u where u.emailId=:emailId  and u.password=:password and u.role=:role")
	public User validateUser(@Param("emailId")  String emailId,@Param("password") String password,@Param("role") String role) ;
	
	@Query("select customer from Customer customer where customer.user.emailId=:email")
	public Customer getCustomer(@Param("email") String emailId);
	
	@Query("select customer from Customer customer where customer.user.emailId=:email")
	public Customer getCustomerById(@Param("email") String emailId);


}
