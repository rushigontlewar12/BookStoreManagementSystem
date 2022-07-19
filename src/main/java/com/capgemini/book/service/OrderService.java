package com.capgemini.book.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;
import com.capgemini.book.entity.OrderDetails;
import com.capgemini.book.exception.NoDataFoundByIdException;
import com.capgemini.book.exception.NoSuchCustomerAvailableException;
import com.capgemini.book.exception.NoSuchDataFoundException;
import com.capgemini.book.exception.NoSuchOrdersByBookException;
import com.capgemini.book.repository.ICartRepository;
import com.capgemini.book.repository.IOrderRepository;

@Service
@Transactional
public class OrderService implements IOrderService {
	Logger logger=LoggerFactory.getLogger(OrderService.class);
	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	ICartRepository cartRepository;

	@Override
	public OrderDetails createOrder(BookCart bookCart) {
		logger.info("Inside Create Order Method");
	

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCustomer(bookCart.getCustomer());
		orderDetails.setBookList(bookCart.getBookList());
		orderDetails.setOrderDate(LocalDate.now());
		orderDetails.setStatus("Order Placed");
		cartRepository.deleteById(bookCart.getCartId());
		return orderRepository.save(orderDetails);

	}

	@Override
	public OrderDetails updateOrder(OrderDetails orderDetails) throws NoSuchDataFoundException, NoDataFoundByIdException{
		logger.info("Inside update Order Method");
		OrderDetails orderDetails2 = findById(orderDetails.getOrderId());
		if(orderDetails2==null) {
			throw new NoSuchDataFoundException("Order with this Id is not present");
		}
		else {
		orderDetails2.setStatus("Order Delivered");
		orderRepository.save(orderDetails2);
		return orderDetails2;
		}

	}

	@Override
	public String cancelOrder(OrderDetails orderDetails) throws NoSuchDataFoundException, NoDataFoundByIdException  {
		logger.info("Inside Cancel Order Method");
		OrderDetails orderDetailsData= findById(orderDetails.getOrderId());
	if(orderDetailsData==null)
	{
		throw new NoDataFoundByIdException("Database Is Empty");
	}
		List<Book> bookList=new ArrayList<Book>();
		orderDetailsData.setStatus("Order Cancelled");
		return "Order Cancelled";
		
	}

	@Override
	public OrderDetails findById(int id) throws NoDataFoundByIdException {
		
		logger.info("Inside FindBy Id Method");
		OrderDetails orderDetails=  orderRepository.findById(id).orElse(null);
		if(orderDetails==null)
		{
			throw new NoDataFoundByIdException("Order with this Id is not present");
		}
		else {
		return orderDetails;
		}

	}

	
	@Override
	public List<OrderDetails> viewAllOrdersByBook(int id) throws NoSuchOrdersByBookException {
 logger.info("Inside View All Orders By Book Method");
		List<OrderDetails> orderIds= orderRepository.viewAllOrdersByBook(id);
		if(orderIds.size()<=0)
		{
			throw new NoSuchOrdersByBookException("Sorry, Order With such Book Id is not Confirmed Yet");
		}
		else {
		List<OrderDetails> finalList=new ArrayList<>();
		for(OrderDetails oid:orderIds) {
			OrderDetails od=orderRepository.findById(oid.getOrderId()).orElse(null);
			finalList.add(od);
		}
			return finalList;
		}
		}
		
		
	
	 
	@Override
	public List<OrderDetails> listAllOrdersByCustomer(int customerId) throws NoSuchCustomerAvailableException {
		logger.info("Inside View all orders By Customer Method");
		List<OrderDetails> orderDetails = orderRepository.viewAllOrdersByCustomer(customerId);
		if(orderDetails.size()<=0)
		{
			throw new NoSuchCustomerAvailableException("NO Customer With Given ID..Thank You");
		}
		return orderDetails;
	}

	@Override
	public List<Book> findBookList(int id) {
		logger.info("Inside FindBy Id Method");
		OrderDetails orderDetails=  orderRepository.findById(id).orElse(null);
		
		
		return orderDetails.getBookList();
		
	}

	@Override
	public int listOrderByCustomer(int customerId) {
		OrderDetails orderDetails = orderRepository.viewOrderByCustomer(customerId);
		return orderDetails.getOrderId();
	}
	

}
