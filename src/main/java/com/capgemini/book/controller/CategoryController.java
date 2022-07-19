package com.capgemini.book.controller;

import java.util.List;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.book.entity.Category;
import com.capgemini.book.exception.CategoryIdNotFoundException;
import com.capgemini.book.exception.categoryAlredyExistsException;
import com.capgemini.book.service.ICategoryService;
@CrossOrigin(origins="http://localhost:3000")

@RestController
@RequestMapping("/bms/category")

public class CategoryController {
	@Autowired
	ICategoryService service;
	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@PostMapping("/addCategory")
	public ResponseEntity<Object> addCategory(@Valid @RequestBody Category category) {

		Category categoryname = null;
		try {
			logger.info("Inside category method implementing add category operation");
			categoryname = service.addCategory(category);
		} catch (categoryAlredyExistsException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<Object>(categoryname, HttpStatus.OK);
	}

	@PutMapping("/editCategory")
	public ResponseEntity<Object> editCategory(@Valid @RequestBody Category category) {

		Category categoryData = null;
		try {
			logger.info("Inside category method implementing edit category operation");
			categoryData = service.editCategory(category);
		} catch (CategoryIdNotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (categoryAlredyExistsException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(categoryData, HttpStatus.OK);

	}

	@GetMapping("/viewAllCategories")
	public ResponseEntity<List<Category>> viewAllCategories() {
		logger.info("Inside category method implementing view all category operation");
		List<Category> viewall = service.viewAllCategories();
		return new ResponseEntity<List<Category>>(viewall, HttpStatus.OK);

	}

		@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") int categoryId)
	{
		logger.info("Inside category method implementing Delete category operation");
		String message="";
		try {
			message= service.removecategory(categoryId);
		}catch(CategoryIdNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(message, HttpStatus.OK);
		
		
		
	}
		
		@GetMapping("/view/{id}")
	  public ResponseEntity<Object> viewCategory(@PathVariable("id") int categoryId) throws CategoryIdNotFoundException
	  {
		  Category cat=service.viewCategory(categoryId);
		  return new ResponseEntity<Object>(cat, HttpStatus.OK);
	  }
	
}
