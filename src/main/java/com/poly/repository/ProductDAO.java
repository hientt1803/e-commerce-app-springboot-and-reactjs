package com.poly.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.Category;
import com.poly.model.Order;
import com.poly.model.Product;

import jakarta.transaction.Transactional;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	// ******************** select AREA ********************
	// SELECT ALL WHERE AVAIABLE == true
	Page<Product> findByAvailable(Boolean available, Pageable pageable);

	// Select Top 10 product
	@Query("SELECT p " + "FROM Product p " + "JOIN OrderDetail od ON p.id = od.product.id " + "GROUP BY p "
			+ "ORDER BY COUNT(od.id) DESC LIMIT 10")
	List<Product> findTop10BestSellingProducts();

	// SELECT TOP 10 lastest
	List<Product> findTop10ByOrderByCreateDateDesc();

	// SELECT count TOP PRODUCT LASTER
	List<Product> findByCreateDateBetween(Date start, Date end);

	// Select by price between
	Page<Product> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);

	// Select By Category name like
	@Query("SELECT p FROM Product p WHERE p.category.name like ?1")
	Page<Product> findByCategoryNameLike(String name, Pageable pageable);

	// DSL Select By Name
	Page<Product> findByNameLike(String name, Pageable pageable);

	List<Product> findByNameLike(String name);

	// ******************** SORT AREA ********************
	// SELECT All by price sort like
	@Query("SELECT p FROM Product p ORDER BY p.price ASC")
	Page<Product> findByPriceSortASC(Pageable pageable);

	// SELECT All by price sort like
	@Query("SELECT p FROM Product p ORDER BY p.price DESC")
	Page<Product> findByPriceSortDESC(Pageable pageable);

	// ******************** Filter AREA ********************
	// Select by avaiable
	@Query("SELECT p FROM Product p WHERE p.available = :isAvailable")
	Page<Product> findProductsByAvailability(@Param("isAvailable") boolean isAvailable, Pageable pageable);

	// select by category
	@Query("SELECT p FROM Product p WHERE p.category.id like ?1")
	Page<Product> findProductsByCategory(String categoryId, Pageable pageable);

	@Query("SELECT c FROM Product c WHERE c.category.id = ?1 AND c.id != ?2")
	List<Product> findByProductCategogy(String id, Integer idProduct);

	// cập nhật lại số lượng sản phẩm khi nhấn thanh toán
	@Modifying
	@Transactional
	@Query("UPDATE Product SET quantity = quantity - ?1 WHERE id = ?2")
	void updateQuantityProduct(Integer sl, Integer idProduct);
}