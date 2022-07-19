package com.capgemini.book.service;

import java.util.List;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;
import com.capgemini.book.entity.Customer;
import com.capgemini.book.entity.OrderDetails;
import com.capgemini.book.exception.NoDataFoundByIdException;
import com.capgemini.book.exception.NoSuchCustomerAvailableException;
import com.capgemini.book.exception.NoSuchDataFoundException;
import com.capgemini.book.exception.NoSuchOrdersByBookException;


public interface IOrderService {

	public String cancelOrder(OrderDetails od) throws NoSuchDataFoundException, NoDataFoundByIdException;

	public OrderDetails createOrder(BookCart cart)  ;

	public OrderDetails updateOrder(OrderDetails od) throws NoSuchDataFoundException, NoDataFoundByIdException;

	public List<OrderDetails> viewAllOrdersByBook(int id) throws  NoSuchOrdersByBookException ;

	public OrderDetails findById(int quantity) throws  NoDataFoundByIdException;

	public List<OrderDetails> listAllOrdersByCustomer(int customerId) throws NoSuchCustomerAvailableException;
    public List<Book> findBookList(int id);
    public int listOrderByCustomer(int customerId);
}
