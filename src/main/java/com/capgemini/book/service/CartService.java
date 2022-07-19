package com.capgemini.book.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;
import com.capgemini.book.entity.Customer;
import com.capgemini.book.exception.BookAlreadyExistException;
import com.capgemini.book.exception.BookNotFoundException;
import com.capgemini.book.exception.CartIsEmptyException;
import com.capgemini.book.exception.CartNotFoundException;
import com.capgemini.book.repository.IBookRepository;
import com.capgemini.book.repository.ICartRepository;

@Service
@Transactional
public class CartService implements ICartService {
	@Autowired
	private ICartRepository repository;

	@Autowired
	private IBookRepository brepository;

	Logger logger = LoggerFactory.getLogger(CartService.class);

	@Override
	public BookCart addBookToCart(int cartId, int bookId)
			throws CartNotFoundException, BookNotFoundException, BookAlreadyExistException {
		logger.info("Inside Add cart");
		if (repository.existsById(cartId) == false) {
			throw new CartNotFoundException("Cart with this id is no there");
		}
		BookCart cart = getCartById(cartId);
		Book book=brepository.findByBookId(bookId);
		List<Book> cartBookList = cart.getBookList();
		if(!cartBookList.contains(book)) {
			cartBookList.add(book);
		}
		cart.setBookList(cartBookList);
		repository.save(cart);
		return cart;
	}

	@Override
	public BookCart removeBookFromCart(int cartId, int bookId) throws CartNotFoundException, BookNotFoundException {
		logger.info("Inside Remove cart");
		BookCart cart = getCartById(cartId);
		Book book = brepository.findByBookId(bookId);
		if (book == null) {
			throw new BookNotFoundException("Book is not available in database");
		}
		int count = 0;
		List<Book> bookList = cart.getBookList();
		for (int i = 0; i < bookList.size(); i++) {
			if (bookId == bookList.get(i).getBookId()) {
				bookList.remove(i);
				count++;
			}
		}
		if (count == 0) {
			throw new BookNotFoundException("The book you are trying to remove is not available in your cart");
		}
		cart.setBookList(bookList);
		repository.save(cart);
		return cart;
	}

	@Override
	public String clearCart(int cartId) throws CartNotFoundException, CartIsEmptyException {
		logger.info("Inside Clear cart");
		BookCart cart = getCartById(cartId);
		if (cart == null) {
			throw new CartNotFoundException("Cart with this id is no there");
		}
		List<Book> bookList = cart.getBookList();
		if (bookList.size() == 0) {
			throw new CartIsEmptyException("No Book is available in your cart to delete");
		}
		bookList.clear();
		repository.save(cart);
		return "Your cart is empty";
	}

	@Override
	public BookCart getCartById(int cartId) throws CartNotFoundException {
		if (repository.existsById(cartId) == false) {
			throw new CartNotFoundException("Cart is not present with this id " + cartId);
		}
		BookCart cart = repository.findByCartId(cartId);
		return cart;
	}

	@Override
	public BookCart createCart(Customer customer) {
		BookCart cart = repository.getCart(customer.getCustomerId());
		if (cart == null) {
			cart = new BookCart();
			cart.setCustomer(customer);
			repository.save(cart);
		}
		return cart;
	}

	@Override
	public String removeCart(int cart_id) {
		repository.deleteById(cart_id);
		return "Your Cart is empty";
	}

	@Override
	public List<BookCart> listAllCarts() {
		List<BookCart> carts=  repository.findAll();
		return carts;
	}

	@Override
	public int getCart(int cust_id) {
		// TODO Auto-generated method stub
		BookCart cart = repository.getCart(cust_id);
		System.out.println(cart.getCartId());
		return cart.getCartId();
	}

	

	@Override
	public List<Book> viewAllById(int cartId) throws CartNotFoundException {
		if (repository.existsById(cartId) == false) {
			throw new CartNotFoundException("Cart is not present with this id " + cartId);
		}
		BookCart cart = repository.findByCartId(cartId);
		return cart.getBookList();
	}

}
