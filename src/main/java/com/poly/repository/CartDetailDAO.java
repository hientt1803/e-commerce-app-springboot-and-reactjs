package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.CartDetail;
import com.poly.model.Product;

import jakarta.transaction.Transactional;

public interface CartDetailDAO extends JpaRepository<CartDetail, Integer> {
	List<CartDetail> findAllByCartId(Integer cartId);

	@Modifying
	@Transactional
	@Query("DELETE FROM CartDetail WHERE product.id = :productId")
	void deleteByProductId(@Param("productId") Integer productId);

	@Query("SELECT c FROM CartDetail c WHERE c.cart.id = ?1")
	CartDetail finByCartId(Integer cartId);

	@Query("SELECT c FROM CartDetail c WHERE c.product.id = ?1")
	List<CartDetail> finByProductId(Integer id);

	CartDetail findByProduct(Product product);
}
