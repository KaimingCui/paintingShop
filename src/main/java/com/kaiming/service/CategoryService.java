package com.kaiming.service;

import java.util.List;

import com.kaiming.domain.Category;

public interface CategoryService {

	List<Category> findAll();

	void save(Category category);

	Category findOne(Integer cid);

	void update(Category category);

	void delete(Integer cid);

}
