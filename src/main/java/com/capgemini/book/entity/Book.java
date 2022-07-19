package com.capgemini.book.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
@Table(name = "book_master")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private int bookId;
	@NotEmpty(message = "Title should not be Empty")
	private String title;
	@NotEmpty(message = "Author should not be Empty")
	private String author;
	private String description;
	@Pattern(regexp = "[0-9]{10}",message = "Enter valid isbn Number")
	@Column(name="isbn",unique = true)
	private String isbn;
	@Positive(message = "Price should be greater than zero or should not be empty")
	private double price;
	@NotNull(message ="Publish Date Should be mentioned")
	@PastOrPresent(message = "Publish Date should not be in future")
	private LocalDate publishDate;
	private LocalDate lastUpdatedOn;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToOne(mappedBy = "book")
	private Review review;


	public Book() {
		super();
	}

	public Book(int bookId, String title, String author, String description, String isbn, double price,
			LocalDate publishDate, LocalDate lastUpdatedOn, Category category) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
		this.price = price;
		this.publishDate = publishDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.category=category;
	}

	public Book(String title, String author, String description, String isbn, double price, LocalDate publishDate,
			LocalDate lastUpdatedOn,Category category) {
		super();
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
		this.price = price;
		this.publishDate = publishDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.category=category;

	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public LocalDate getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(LocalDate lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", isbn=" + isbn + ", price=" + price + ", publishDate=" + publishDate + ", lastUpdatedOn="
				+ lastUpdatedOn + "]";
	}
	

}
