package com.capgemini.book.entity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name = "review_master")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "review_id")
	private int reviewId;
	@OneToOne
	@JoinColumn(name="book_id")
	private Book book;
	@OneToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	@NotBlank(message="Head line must be given")
	private String headLine;
	@NotBlank(message="Comment must be given")
	private String comment;
	@Min(value=1,message="Rating should not be less than 1")
	@Max(value=5,message="Rating should  be less or equal to  than 5")
	private double rating;
	public Review(int reviewId, @NotBlank(message = "Head line must be given") String headLine,
			@NotBlank(message = "Comment must be given") String comment,
			@Min(value = 1, message = "Rating should not be less than 1") @Max(value = 5, message = "Rating should  be less or equal to  than 5") double rating,
			LocalDate reviewOn) {
		super();
		this.reviewId = reviewId;
		this.book = book;
		this.customer = customer;
		this.headLine = headLine;
		this.comment = comment;
		this.rating = rating;
		this.reviewOn = reviewOn;
	}
	private LocalDate reviewOn;

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getHeadLine() {
		return headLine;
	}

	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public LocalDate getReviewOn() {
		return reviewOn;
	}

	public void setReviewOn(LocalDate reviewOn) {
		this.reviewOn = reviewOn;
	}

	public Review() {
		super();
	}
	
	public Review(Book book, Customer customer, @NotBlank(message = "Head line must be given") String headLine,
			@NotBlank(message = "Comment must be given") String comment,
			@Min(value = 1, message = "Rating should not be less than 1") @Max(value = 5, message = "Rating should  be less or equal to  than 5") double rating,
			LocalDate reviewOn) {
		this.book = book;
		this.customer = customer;
		this.headLine = headLine;
		this.comment = comment;
		this.rating = rating;
		this.reviewOn = reviewOn;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", book=" + book + ", customer=" + customer + ", headLine=" + headLine
				+ ", comment=" + comment + ", rating=" + rating + ", reviewOn=" + reviewOn + "]";
	}
}