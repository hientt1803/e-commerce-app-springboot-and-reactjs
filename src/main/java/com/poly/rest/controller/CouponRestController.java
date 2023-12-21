package com.poly.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Coupon;
import com.poly.service.CouponService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/coupon")
public class CouponRestController {
	@Autowired
	CouponService couponService;
	
	@PostMapping()
	public Coupon getOne(@RequestParam String couponId) {
		return couponService.findByIdActivated(couponId);
	}
	
}
