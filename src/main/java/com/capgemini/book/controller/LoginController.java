package com.capgemini.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.entity.User;
import com.capgemini.book.exception.UserNotFoundException;
import com.capgemini.book.service.ICartService;
import com.capgemini.book.service.ILoginService;

@RestController
@RequestMapping("/bms/user")
@CrossOrigin(origins="http://localhost:3000")
public class LoginController {
	
	@Autowired
	ILoginService service;
	
	@Autowired
	ICartService cservice;
	
	Customer customer;
	
	@GetMapping("/login/{emailId}/{password}/{role}")
	public ResponseEntity<User> validateUser(@PathVariable String emailId,@PathVariable String password,@PathVariable String role) throws UserNotFoundException {
		User user=service.validateUser(emailId, password,role);
		String message="";
		if(user==null)
			throw new UserNotFoundException("User with this credentials is not found");
		else {
			 customer=service.getCustomer(emailId);
			cservice.createCart(customer);
			message="Login succesfull";
		}
		User  data= service.getCustomer(emailId).getUser();
	
		return new ResponseEntity<User>(data,HttpStatus.OK);
	}
	
	@GetMapping("/getCustomer/{emailId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String emailId){
		 customer=service.getCustomer(emailId);
		 return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
	
	@GetMapping("/getCustomerById/{emailId}")
	public ResponseEntity<Integer> getCustomerById(@PathVariable String emailId){
		int customer=service.getCustomerById(emailId);
		 return new ResponseEntity<Integer>(customer,HttpStatus.OK);
	}
	

}
