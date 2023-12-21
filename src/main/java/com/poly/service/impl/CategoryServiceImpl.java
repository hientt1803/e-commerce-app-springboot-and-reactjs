package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.model.Category;
import com.poly.model.Coupon;
import com.poly.model.Product;
import com.poly.repository.CategoryDAO;
import com.poly.repository.CouponDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.CategoryService;
import com.poly.service.CouponService;
import com.poly.service.ProductService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDAO categoryDAO;

	@Override
	public List<Category> findAll() {
		return categoryDAO.findAll();
	}

	@Override
	public Category save(Category category) {
		return categoryDAO.save(category);
	}

	@Override
	public Category findById(String categoryId) {
		return categoryDAO.findById(categoryId).get();
	}

	@Override
	public List<Category> getCategory(String id, String name) {
		return categoryDAO.getCategory(id, name);
	}

}
