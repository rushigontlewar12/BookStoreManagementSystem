package com.capgemini.book.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer_master")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private int customerId;
	@NotBlank(message="Full Name can't be empty")
	@Size(min=5, message="Name should be more than 5 Characters")
	private String fullName;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address address;
	@NotNull(message="phone number cannot be empty")
	private String mobileNumber;
	private LocalDate registerOn;
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "customer")
	private Review review;
	@OneToOne(mappedBy = "customer")
	private BookCart bookCart;
	
	public Customer() {
		super();
	}
	
	

	public Customer(
			 String fullName,
			User user, Address address,String mobileNumber,
			LocalDate registerOn) {
		super();
		this.fullName = fullName;
		this.user = user;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.registerOn = registerOn;
	}



	public Customer(int customerId,
			 String fullName,
			User user, Address address, String mobileNumber,
			LocalDate registerOn) {
		super();
		this.customerId = customerId;
		this.fullName = fullName;
		this.user = user;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.registerOn = registerOn;
	}



	public Customer(int customerId,
			String fullName,
			User user, Address address,  String mobileNumber,
			LocalDate registerOn,BookCart bookCart) {
		super();
		this.customerId = customerId;
		this.fullName = fullName;
		this.user = user;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.registerOn = registerOn;
		this.bookCart = bookCart;
	}

	public Customer(
			 String fullName,
			User user, Address address, String mobileNumber,
			LocalDate registerOn,BookCart bookCart) {
		super();
		this.fullName = fullName;
		this.user = user;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.registerOn = registerOn;
		this.bookCart = bookCart;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public LocalDate getRegisterOn() {
		return registerOn;
	}

	public void setRegisterOn(LocalDate registerOn) {
		this.registerOn = registerOn;
	}



	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", fullName=" + fullName + ", user=" + user + ", address="
				+ address + ", mobileNumber=" + mobileNumber + ", registerOn=" + registerOn + "]";
	}

	

	

}
