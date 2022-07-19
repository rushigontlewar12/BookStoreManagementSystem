package com.capgemini.book.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "OrderDetails_master")
public class OrderDetails {
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private int orderId;
	
	private LocalDate orderDate;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "ORDER_BOOKLIST",joinColumns = { @JoinColumn(name = "ORDER_ID") }, inverseJoinColumns = { @JoinColumn(name = "Book_ID") })
	private List<Book> bookList = new ArrayList<Book>();
	
	@OneToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	private String status;
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderDetails(LocalDate orderDate, List<Book> bookList, Customer customer) {
		super();
		this.orderDate = orderDate;
		this.bookList = bookList;
		this.customer = customer;
	}

	public OrderDetails(int orderId, LocalDate orderDate, List<Book> bookList, Customer customer) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.bookList = bookList;
		this.customer = customer;
	}

	public OrderDetails() {
		super();
	}

	@Override
	public String toString() {
		return "OrderDetails [orderId=" + orderId + ", orderDate=" + orderDate + ", bookList=" + bookList
				+ ", customer=" + customer + "]";
	}
	
	

	

}
