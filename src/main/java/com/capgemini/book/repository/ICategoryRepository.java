package com.capgemini.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.book.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Integer>{

	Category findByCategoryName(String categoryName);
	Category findByCategoryId(int categoryId);

}
