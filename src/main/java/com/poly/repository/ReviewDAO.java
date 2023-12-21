package com.poly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.ProductRatingDTO;
import com.poly.model.Review;
import java.util.List;


public interface ReviewDAO  extends JpaRepository<Review, Integer>{
	@Query("SELECT c FROM Review c WHERE c.product.name LIKE ?1")
	Page<Review> findByNameProduct(String ten, Pageable pageable);
	
	@Query("SELECT c FROM Review c WHERE c.rating = ?1")
	Page<Review> findByRating(Integer sao, Pageable pageable);
	
	@Query("SELECT c FROM Review c WHERE c.account.fullname LIKE ?1")
	Page<Review> findByNameAcount(String ten, Pageable pageable);
	
	@Query("SELECT c FROM Review c WHERE c.product.id = ?1")
	List<Review> findByProductId(int id);
	
	@Query("SELECT p.name, AVG(r.rating) AS rating "
	        + "FROM Product p "
	        + "JOIN Review r ON p.id = r.product.id "
	        + "GROUP BY p.name")
	public List<Object[]> get();
}
