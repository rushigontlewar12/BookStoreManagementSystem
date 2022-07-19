package com.capgemini.book.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CustomerListIsEmptyException;
import com.capgemini.book.exception.CustomerNotFoundException;
import com.capgemini.book.exception.CustomerPresentException;
import com.capgemini.book.repository.ICustomerRepository;


@Service
@Transactional
public class CustomerService implements ICustomerService{

	@Autowired
	ICustomerRepository repository;
	
	Logger logger=LoggerFactory.getLogger(CustomerService.class);

	@Override
	public Customer createCustomer(Customer customer) throws CustomerPresentException{
		if(repository.existsById(customer.getCustomerId())) {
			logger.error("Customer with given Id already exits");
			throw new CustomerPresentException("Cannot insert data, customer Id already exits");
		}
		Customer customerData=repository.save(customer);
		return customerData;
	}
    
	@Override
	public List<Customer> listCustomers() throws CustomerListIsEmptyException{
		logger.info("Inside  list all customers");
		List<Customer> list=repository.findAll();
		if(list.size()==0) {
			throw new CustomerListIsEmptyException("Customer list is empty");
		}
		return list;
	}

	@Override
	public Customer updateCustomer(Customer customer)throws CustomerNotFoundException {
		logger.info("Inside update Customer ");
		Customer customerdata=repository.findById(customer.getCustomerId()).orElse(null);
		if(customerdata==null)
		{
			throw new CustomerNotFoundException("Customer with given Id is not present");
		}
		return repository.save(customer);
	}

	@Override
	public List<Customer> listAllCustomerByBook(int bookId) throws BookNotFoundException{
		List<Customer> bookList=repository.findCustomerByBook(bookId);
		if(bookList==null)
		{
			throw new BookNotFoundException("Customers with given Book Id is not found");
		}
		return bookList;
	}

	@Override
	public Customer viewCustomer(int customerId) throws CustomerNotFoundException{
		logger.info("Inside View customer");
		if(repository.existsById(customerId)==false)
			throw new CustomerNotFoundException("Customer with given Id is not present");
		Customer customerdata= repository.findByCustomerId(customerId);
		return customerdata;
	}

	@Override
	public Customer findByCustomerId(int customerId) throws CustomerNotFoundException {
		Customer customerdata = repository.findById(customerId).orElse(null);
		if(customerdata==null)
		{
			throw new CustomerNotFoundException("Id is not available");
		}
		return customerdata;
	}

	
	@Override
	public String deleteCustomer(int customerId) throws CustomerNotFoundException {
		logger.info("Inside delete customer");
		Customer customer= findByCustomerId(customerId);
		repository.delete(customer);
		return "Deleted Customer successfully";
	}

}
