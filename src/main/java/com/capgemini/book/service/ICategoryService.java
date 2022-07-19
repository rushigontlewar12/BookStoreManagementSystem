package com.capgemini.book.service;

import java.util.List;

import com.capgemini.book.entity.Book;
import com.capgemini.book.entity.Category;
import com.capgemini.book.exception.CategoryIdNotFoundException;
import com.capgemini.book.exception.categoryAlredyExistsException;


public interface ICategoryService {
	public Category addCategory(Category category) throws categoryAlredyExistsException;
	public Category editCategory(Category cat) throws CategoryIdNotFoundException, categoryAlredyExistsException; 
	public List<Category> viewAllCategories();
	//public String removeCategory(Category cat)throws CategoryIdNotFoundException;
	public String removecategory (int categoryId) throws CategoryIdNotFoundException; 
	public Category findById(int categoryId) throws CategoryIdNotFoundException;	
	public Category viewCategory(int id) throws CategoryIdNotFoundException;
}