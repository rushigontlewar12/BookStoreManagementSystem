package com.capgemini.book.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CustomerListIsEmptyException;
import com.capgemini.book.exception.CustomerNotFoundException;
import com.capgemini.book.exception.CustomerPresentException;
import com.capgemini.book.service.IBookService;
import com.capgemini.book.service.ICustomerService;

@CrossOrigin(origins="http://localhost:3000")
@Validated
@RestController
@RequestMapping("/bms/customer")
public class CustomerController {
	
	@Autowired
	ICustomerService service;
	
	@Autowired
	IBookService bookservice;
	
	Logger logger=LoggerFactory.getLogger(CustomerController.class);
	
	@PostMapping("/create")
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer) {
		logger.info("Inside create customer");
		Customer customerdata=null;;
		try {
			customerdata = service.createCustomer(customer);
		} catch (CustomerPresentException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(customerdata,HttpStatus.OK);
	}
		
	@GetMapping("/viewAll")
	public ResponseEntity<Object> viewAllCustomers(){
		logger.info("Inside the view all customers");
		List<Customer> customerList;
		try {
			customerList = service.listCustomers();
		} catch (CustomerListIsEmptyException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(customerList,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/deleteById/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
		logger.info("Inside the Delete Customer");
		String message="";
		try {
		      message=service.deleteCustomer(customerId);
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(message,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/viewById/{customerId}")
	public ResponseEntity<Object> viewCustomer(@PathVariable int customerId) {
		logger.info("Indise view customer");
		Customer customerdata= null;
		try {
			 customerdata = service.viewCustomer(customerId);
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(customerdata,HttpStatus.OK);
	}
	
	@PutMapping("/update/{customerId}")
	public ResponseEntity<Object> updateCustomer(@Valid @RequestBody Customer customer) {
		Customer customerdata=null;
		try {
			logger.info("Inside update customer");
			 customerdata = service.updateCustomer(customer);
		} catch ( CustomerNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity<Object>(customerdata,HttpStatus.OK);
	}
	
	@GetMapping("/customerByBook/{bookId}")
	public ResponseEntity<Object> listAllCustomerByBook(@PathVariable int bookId){
		List<Customer> customerList;
		try {
			customerList = service.listAllCustomerByBook(bookId);
		} catch (BookNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>(customerList,HttpStatus.OK);
	}

}
