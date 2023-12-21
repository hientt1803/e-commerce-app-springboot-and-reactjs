package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.model.Review;
import com.poly.repository.ReviewDAO;
import com.poly.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDAO reviewDAO;

	@Override
	public List<Review> findAll() {
		return reviewDAO.findAll();
	}

	@Override
	public void delete(int id) {
		reviewDAO.deleteById(id);
	}

	@Override
	public Review findId(int id) {
		return reviewDAO.findById(id).get();
	}
}
