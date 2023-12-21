package com.poly.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Coupon;

public interface CouponDAO extends JpaRepository<Coupon, String> {
	@Query("SELECT c FROM Coupon c WHERE c.expirationDate = ?1")
	Page<Coupon> findByExpirationDate(Date ngayHetHan, Pageable pageable);

	@Query("SELECT c FROM Coupon c WHERE c.createdDate = ?1")
	Page<Coupon> findByCreatedDate(Date ngayTao, Pageable pageable);

	@Query("SELECT c FROM Coupon c WHERE c.couponName LIKE ?1")
	Page<Coupon> findByCouponName(String ten, Pageable pageable);

	@Query("SELECT c FROM Coupon c WHERE c.activated = ?1")
	Page<Coupon> findByActivated(Boolean trangthai, Pageable pageable);

	@Query("SELECT c FROM Coupon c WHERE c.couponCode = ?1 AND c.activated = true AND c.expirationDate > CURRENT_DATE AND c.startDate < CURRENT_DATE")
	Coupon findByIdActivated(String code);

	List<Coupon> findAll();
}
