package com.capgemini.book.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.Category;
import com.capgemini.book.exception.CategoryIdNotFoundException;
import com.capgemini.book.exception.categoryAlredyExistsException;
import com.capgemini.book.repository.ICategoryRepository;

@Service
@Transactional
public class CategoryService implements ICategoryService {

	@Autowired
	ICategoryRepository repository;
	
	Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Override
	public Category addCategory(Category category) throws categoryAlredyExistsException{
		
		logger.info("Inside Category method");
		Category cat= repository.findByCategoryName(category.getCategoryName());
		if(cat==null) {
		repository.save(category);
		}
		else
		{
			throw new categoryAlredyExistsException("Category already exists.");
		}
		return category;
	}

	@Override
	public Category editCategory(Category cat) throws CategoryIdNotFoundException,categoryAlredyExistsException{
		logger.info("Inside Category method");
		
		if(repository.existsById(cat.getCategoryId())==false)
		{
			throw new CategoryIdNotFoundException("Id is not available"); 
		}
		Category cat1= repository.findByCategoryName(cat.getCategoryName());
		if(cat1==null) {
		repository.save(cat);
		}
		else
		{
			throw new categoryAlredyExistsException("Category already exists.");
		}
		return cat;
		
		}

	@Override
	public List<Category> viewAllCategories() {

		logger.info("Inside Category method");
		return repository.findAll();

	}


	@Override
	public String removecategory(int categoryId) throws CategoryIdNotFoundException {
		logger.info("Inside Category method");
		Category category= findById(categoryId);
		System.out.println(category.getCategoryName());
		repository.delete(category);
		return "Category is deleted";

	}

	@Override
	public Category findById(int categoryId) throws CategoryIdNotFoundException {
		logger.info("Inside Category method");
		if(repository.existsById(categoryId)==false)
		{
			throw new CategoryIdNotFoundException("Id is not available"); 
		}
		Category cat= repository.findByCategoryId(categoryId);
		return cat;

	}

	@Override
	public Category viewCategory(int id) throws CategoryIdNotFoundException {
		
		Category cat=findById(id);
		return cat;
		//Book book = bookRepository.findByBookId(id);
		//return book;
	}

	
	
	}
