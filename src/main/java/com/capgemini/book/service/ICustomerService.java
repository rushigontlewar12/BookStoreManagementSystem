package com.capgemini.book.service;

import java.util.List;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CustomerListIsEmptyException;
import com.capgemini.book.exception.CustomerNotFoundException;
import com.capgemini.book.exception.CustomerPresentException;

public interface ICustomerService {
	public Customer createCustomer(Customer customer) throws CustomerPresentException;
	public List<Customer> listCustomers()throws CustomerListIsEmptyException;
	public Customer updateCustomer(Customer customer)throws CustomerNotFoundException;
	public Customer viewCustomer(int customerId) throws CustomerNotFoundException;
	public List<Customer> listAllCustomerByBook(int bookId) throws BookNotFoundException;
	public Customer findByCustomerId(int customerId) throws CustomerNotFoundException;
	public String deleteCustomer(int customerId) throws CustomerNotFoundException;
	

}
