package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.model.Coupon;
import com.poly.model.Product;
import com.poly.repository.CouponDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.CouponService;
import com.poly.service.ProductService;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDAO couponDAO;

    @Override
    public Coupon findByIdActivated(String couponCode) {
        return couponDAO.findByIdActivated(couponCode);
    }

    @Override
    public Coupon findById(String couponCode) {
        return couponDAO.findById(couponCode).get();
    }

    @Override
    public List<Coupon> findAll() {
        return couponDAO.findAll();
    }

    @Override
    public Coupon findByIDActive(String id) {
        return couponDAO.findByIdActivated(id);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponDAO.save(coupon);
    }
}
