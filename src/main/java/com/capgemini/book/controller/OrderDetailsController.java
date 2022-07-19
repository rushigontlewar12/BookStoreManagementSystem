package com.capgemini.book.controller;


import java.util.List;

import javax.validation.Valid;

import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.BookCart;

import com.capgemini.book.entity.OrderDetails;
import com.capgemini.book.exception.CartNotFoundException;
import com.capgemini.book.exception.NoDataFoundByIdException;
import com.capgemini.book.exception.NoSuchCustomerAvailableException;
import com.capgemini.book.exception.NoSuchDataFoundException;
import com.capgemini.book.exception.NoSuchOrdersByBookException;
import com.capgemini.book.exception.OrderAlreadyExistException;
import com.capgemini.book.service.ICartService;
import com.capgemini.book.service.IOrderService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/bms/order")
public class OrderDetailsController {
	Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

	@Autowired
	IOrderService service;

	@Autowired
	ICartService cservice;

	@PostMapping("/add/{cid}")
	public ResponseEntity<Object> createOrder(@Valid @PathVariable("cid") int cartId) {
		logger.info("Inside Create Order");
		BookCart cart = null;
		try {
			cart = cservice.getCartById(cartId);
			OrderDetails orderDetailsData;

			orderDetailsData = service.createOrder(cart);
			return new ResponseEntity<Object>(orderDetailsData, HttpStatus.OK);
		} catch (CartNotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		// return new ResponseEntity<OrderDetails>(orderDetailsData, HttpStatus.OK);

	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateOrder(@Valid @RequestBody OrderDetails orderDetails) {

		try {
			logger.info("Inside Update Order Method");
			OrderDetails orderDetailsData = service.updateOrder(orderDetails);
			return new ResponseEntity<Object>(orderDetailsData, HttpStatus.OK);
		} catch (NoSuchDataFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);

			// TODO: handle exception
		} catch (NoDataFoundByIdException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/viewOrder/{oid}")
	public ResponseEntity<Object> viewOrderById(@RequestParam("oid") int id) {
		OrderDetails orderDetailsData;
		try {
			logger.info("inside View Order By Id method");
			orderDetailsData = service.findById(id);
		} catch (NoDataFoundByIdException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(orderDetailsData, HttpStatus.OK);

	}

	@GetMapping("/viewOrdersByBook")
	public ResponseEntity<Object> viewAllOrdersByBook(@RequestParam("b_id") int id) {

		List<OrderDetails> orderDetailsdata;
		try {
			logger.info("inside view order by book method");
			orderDetailsdata = service.viewAllOrdersByBook(id);
		} catch (NoSuchOrdersByBookException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(orderDetailsdata, HttpStatus.OK);
	}

	@GetMapping("/viewOrdersByCustomer/{custId}")
	public ResponseEntity<Object> viewAllOrdersByCustomer(@PathVariable("custId") int customerId) {

		List<OrderDetails> orderDetailsdata;
		try {
			logger.info("inside View Order By Customer Method");
			orderDetailsdata = service.listAllOrdersByCustomer(customerId);
		} catch (NoSuchCustomerAvailableException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.OK);

		}
		return new ResponseEntity<Object>(orderDetailsdata, HttpStatus.OK);
	}

	@DeleteMapping("/cancel/{orderId}")
	public ResponseEntity<Object> cancelOrder(@PathVariable("orderId") int orderId) {
		OrderDetails orderDetails;
		try {
			logger.info("Inside Cancel Order Method");
			orderDetails = service.findById(orderId);
			String message = service.cancelOrder(orderDetails);
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} catch (NoDataFoundByIdException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.OK);
		} catch (NoSuchDataFoundException e) { // TODO Auto-generated catch block
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.OK);
		}

	}
	
	@GetMapping("/getList/{orderId}")
	public ResponseEntity<Object> viewBookList(@PathVariable("orderId") int orderId)
	{
		List<Book> booklist = service.findBookList(orderId);
		return new ResponseEntity<Object>(booklist, HttpStatus.OK);
	}
	
	@GetMapping("/getOrderId/{custId}")
	public ResponseEntity<Object> viewOrderIdByCustomer(@PathVariable("custId") int customerId)
	{
		int orderid = service.listOrderByCustomer(customerId);
		return new ResponseEntity<Object>(orderid, HttpStatus.OK);
		
	}

}
