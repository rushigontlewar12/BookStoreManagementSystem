package com.capgemini.book.service;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.entity.User;

public interface ILoginService {

	public Customer getCustomer(String emailId);

	public User validateUser(String emailId, String password,String role);
	
	public int getCustomerById(String emailId);

}
