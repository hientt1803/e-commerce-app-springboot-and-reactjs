package com.poly.service;

import java.util.List;

import com.poly.model.Coupon;

public interface CouponService {
    List<Coupon> findAll();

    Coupon findByIDActive(String id);

    Coupon save(Coupon coupon);

    Coupon findByIdActivated(String username);

    Coupon findById(String couponCode);
}
