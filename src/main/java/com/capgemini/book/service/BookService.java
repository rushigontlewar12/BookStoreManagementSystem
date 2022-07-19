package com.capgemini.book.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.Category;
import com.capgemini.book.exception.BookAlreadyExistException;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.BooksListIsEmptyException;
import com.capgemini.book.exception.CannotRemoveBookException;
import com.capgemini.book.exception.CategoryNotFoundException;
import com.capgemini.book.repository.IBookRepository;
import com.capgemini.book.repository.ICategoryRepository;

@Service
@Transactional
public class BookService implements IBookService {

	@Autowired
	private IBookRepository bookRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	Logger logger = LoggerFactory.getLogger(BookService.class);

	@Override
	public Book createBook(Book book) throws BookAlreadyExistException, CategoryNotFoundException {
		logger.info("Inside create book");
		Book book1 = null;
		List<Book> books = bookRepository.findAll();
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getBookId() == book.getBookId() || books.get(i).getTitle().equals(book.getTitle())) {
				logger.error("Book already existing");
				throw new BookAlreadyExistException("Book already exists in database");
			}
		}
		if (categoryRepository.existsById(book.getCategory().getCategoryId())==false) {
			logger.error("Category not existing");
			throw new CategoryNotFoundException("Category with this id is not present");
		}
		book.setLastUpdatedOn(LocalDate.now());
		book.setCategory(categoryRepository.findById(book.getCategory().getCategoryId()).orElse(null));
		book1 = bookRepository.save(book);
		return book1;
	}

	@Override
	public Book editBook(Book book) throws BookNotFoundException, CategoryNotFoundException {
		logger.info("Inside Edit Book");
		Book book1=viewBook(book.getBookId());
		if(bookRepository.existsById(book.getBookId())==false) {
			logger.error("Invalid book");
			throw new BookNotFoundException("Book with this id is not present, please enter valid bookId");
		}
		if (categoryRepository.existsById(book.getCategory().getCategoryId())==false) {
			logger.error("Invalid Category");
			throw new CategoryNotFoundException("Invalid category, please enter the valid category");
		}
		book.setCategory(categoryRepository.findById(book.getCategory().getCategoryId()).orElse(null));
		book.setLastUpdatedOn(LocalDate.now());
		book.setBookId(book1.getBookId());;
		Book b = bookRepository.save(book);
		return b;
	}

	@Override
	public String deleteBook(int id) throws BookNotFoundException, CannotRemoveBookException {
		logger.info("Inside Delete Book");
		Book book = viewBook(id);
		if(bookRepository.getOrderDetails(id).size()>0||bookRepository.getReview(id).size()>0||bookRepository.getCart(id).size()>0) {
			throw new CannotRemoveBookException("Book cannot be deleted");
		}
		bookRepository.delete(book);
		return "Deleted Book Successfully";
	}

	@Override
	public Book viewBook(int id) throws BookNotFoundException {
		logger.info("Inside Find Book by Id");
		if (bookRepository.existsById(id)==false) {
			logger.error("Book with this id is not present");
			throw new BookNotFoundException("Book with this id is not present");
		}
		Book book = bookRepository.findByBookId(id);
		return book;
	}

	@Override
	public List<Book> listAllBooks() throws BooksListIsEmptyException {
		logger.info("Inside List All Books");
		List<Book> totalBooks = bookRepository.findAll();
		if (totalBooks.size() == 0) {
			logger.error("Book List is empty");
			throw new BooksListIsEmptyException("Book List is empty");
		}
		return totalBooks;
	}

	@Override
	public List<Book> listBooksByCategory(String categoryName) throws BooksListIsEmptyException, CategoryNotFoundException {
		logger.info("Inside list of books by category");
		Category category = bookRepository.getCategory(categoryName);
		if (category == null) {
			logger.error("Category with this name is not present");
			throw new CategoryNotFoundException("Category with this name is not present");
		} else {
			List<Book> books = bookRepository.findBookByCategory(categoryName.toLowerCase());
			if (books.size() == 0) {
				logger.error("Books with this category is not present");
				throw new BooksListIsEmptyException("Books with this category is not present");
			}
			return books;
		}
	}

	@Override
	public List<Book> listBestSellingBook() throws BooksListIsEmptyException {
		logger.info("Inside list of best books by category");
		double rating = bookRepository.reviewAvg();
		List<Book> books = new ArrayList<>();
			books = bookRepository.listBestSellingBooks(rating);
		if (books.size() == 0) {
			logger.error("Ratings are not available or no book is available");
			throw new BooksListIsEmptyException("Ratings are not available or no book is available");
		}
		return books;
	}

}
