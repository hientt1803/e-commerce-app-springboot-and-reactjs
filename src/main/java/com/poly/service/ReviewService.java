package com.poly.service;

import java.util.List;
import com.poly.model.Review;

public interface ReviewService {
	List<Review> findAll();
	void delete(int id);
	Review findId(int id);
}
