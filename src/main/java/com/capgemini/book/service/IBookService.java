package com.capgemini.book.service;

import java.util.List;


import com.capgemini.book.entity.Book;
import com.capgemini.book.exception.BookAlreadyExistException;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.BooksListIsEmptyException;
import com.capgemini.book.exception.CannotRemoveBookException;
import com.capgemini.book.exception.CategoryNotFoundException;

public interface IBookService {
	
	public Book createBook(Book book) throws BookAlreadyExistException,CategoryNotFoundException;
	public Book editBook(Book book) throws BookNotFoundException,CategoryNotFoundException;
	public String deleteBook(int id) throws BookNotFoundException, CannotRemoveBookException;
	public Book viewBook(int id) throws BookNotFoundException;
	
	public List<Book> listAllBooks() throws BooksListIsEmptyException;
	
	public List<Book> listBooksByCategory(String category) throws CategoryNotFoundException,BooksListIsEmptyException;
	public List<Book> listBestSellingBook() throws BooksListIsEmptyException;

}
