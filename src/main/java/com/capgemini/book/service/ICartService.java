package com.capgemini.book.service;

import java.util.List;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;
import com.capgemini.book.entity.Customer;
import com.capgemini.book.exception.BookAlreadyExistException;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CartIsEmptyException;
import com.capgemini.book.exception.CartNotFoundException;

public interface ICartService {
public BookCart addBookToCart(int cartId, int bookId) throws CartNotFoundException,BookNotFoundException,BookAlreadyExistException;
public BookCart removeBookFromCart(int cartId,int bookId) throws CartNotFoundException,BookNotFoundException;
public String clearCart(int cartId) throws CartNotFoundException, CartIsEmptyException;
public BookCart getCartById(int cartId) throws CartNotFoundException;
public BookCart createCart(Customer customer);
public String removeCart(int cart_id);
public List<BookCart> listAllCarts();
public int getCart(int cust_id);
public List<Book> viewAllById(int cartId) throws CartNotFoundException;
}
