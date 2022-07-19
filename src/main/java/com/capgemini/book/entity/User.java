package com.capgemini.book.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user_master")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private int userId;
	@Email(message="Give the email Id in correct format")
	private String emailId;
	@NotEmpty(message="password can't be empty")
	private String password;
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy ="user")
	private Customer customer;
	
	public User() {
		super();
	}
	public User(int userId, String emailId, String password,String role) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.password = password;
		this.role = role;
		
	}
	public User(String emailId, String password,String role) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.role=role;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", emailId=" + emailId + ", password=" + password +  "]";
	}
	
}
