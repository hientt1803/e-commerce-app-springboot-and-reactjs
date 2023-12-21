package com.poly.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poly.model.Account;
import com.poly.model.Category;
import com.poly.model.Payment;
import com.poly.repository.AccountDAO;
import com.poly.repository.CategoryDAO;
import com.poly.repository.PaymentDAO;
import com.poly.service.AccountService;
import com.poly.service.PaymentService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.poly.model.Account;
import com.poly.repository.AccountDAO;
import com.poly.service.AccountService;
import org.springframework.ui.Model;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	PaymentDAO paymentDAO;

	@Override
	public List<Payment> findAll() {
		return paymentDAO.findAll();
	}


}
