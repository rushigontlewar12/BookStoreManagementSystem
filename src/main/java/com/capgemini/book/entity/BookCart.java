package com.capgemini.book.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book_cart")
public class BookCart {
	@Id
	@GeneratedValue
	@Column(name = "cart_id")
	private int cartId;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(fetch  = FetchType.EAGER)
	@JoinTable(name = "CART_BOOKLIST",joinColumns = { @JoinColumn(name = "CART_ID") }, inverseJoinColumns = { @JoinColumn(name = "Book_ID") })
	private List<Book> bookList;
	
	

	public BookCart(int cartId, Customer customer, List<Book> bookList) {
		super();
		this.cartId = cartId;
		this.customer = customer;
		this.bookList = bookList;
	}

	public BookCart(Customer customer, List<Book> bookList) {
		super();
		this.customer = customer;

	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	

	@Override
	public String toString() {
		return "BookCart [cartId=" + cartId + ", customer=" + customer + ", bookList=" + bookList + "]";
	}

	public BookCart() {
		super();
	}

}
