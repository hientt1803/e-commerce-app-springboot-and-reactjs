package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.model.Account;
import com.poly.service.AccountService;

import java.util.Optional;

@Controller
@RequestMapping("account")
public class UpdateProfileController {
	@Autowired
	AccountService accountService;
	
	@GetMapping("update-account")
	public String index() {
		Optional<Account> account = accountService.findById("hoainam");
		return "/account/update_account";
	}
}
