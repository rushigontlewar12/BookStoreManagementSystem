package com.capgemini.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.book.entity.Customer;
import com.capgemini.book.entity.User;
import com.capgemini.book.repository.ILoginRepository;



@Service
@Transactional
public class LoginService implements ILoginService{

	@Autowired
	ILoginRepository repository;

	@Override
	public User validateUser(String emailId,String password,String role) {
		return repository.validateUser(emailId,password,role);
	}

	@Override
	public Customer getCustomer(String emailId) {
		return repository.getCustomer(emailId);
	}

	@Override
	public int getCustomerById(String emailId) {
		Customer customer=repository.getCustomerById(emailId);
		return customer.getCustomerId();
	}

}
