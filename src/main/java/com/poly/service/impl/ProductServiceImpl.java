package com.poly.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.model.Product;
import com.poly.repository.ProductDAO;
import com.poly.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDAO;

	@Override
	public Page<Product> getAllProducts(Pageable pageable) {
		return productDAO.findByAvailable(true, pageable);
	}

	@Override
	public Product findById(Integer id) {
		return productDAO.findById(id).get();
	}

	@Override
	public Product save(Product product) {
		return productDAO.save(product);
	}

	@Override
	public Page<Product> findByCategoryName(String name, Pageable pageable) {
		return productDAO.findByCategoryNameLike(name, pageable);
	}

	@Override
	public List<Product> findByProductCategogy(String categoryID, Integer productID) {
		return productDAO.findByProductCategogy(categoryID, productID);
	}

	@Override
	public Page<Product> findByPrice(double minPrice, double maxPrice, Pageable pageable) {
		return productDAO.findByPriceBetween(minPrice, maxPrice, pageable);
	}

	@Override
	public Page<Product> findByName(String name, Pageable pageable) {
		return productDAO.findByNameLike(name, pageable);
	}

	@Override
	public Page<Product> findByPriceSortASC(Pageable pageable) {
		return productDAO.findByPriceSortASC(pageable);
	}

	@Override
	public Page<Product> findByPriceSortDESC(Pageable pageable) {
		return productDAO.findByPriceSortDESC(pageable);
	}

	@Override
	public List<Product> findTop10BestSellingProducts() {
		return productDAO.findTop10BestSellingProducts();
	}

	@Override
	public List<Product> findTop10ByOrderByCreateDateDesc() {
		return productDAO.findTop10ByOrderByCreateDateDesc();
	}

	@Override
	public List<Product> findAll() {
		return productDAO.findAll();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productDAO.findAll(pageable);
	}

	@Override
	public List<Product> findByCreateDateBetween(Date start, Date end) {
		return productDAO.findByCreateDateBetween(start, end);
	}

}
