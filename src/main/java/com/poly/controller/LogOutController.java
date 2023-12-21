package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.model.Account;
import com.poly.service.SessionService;

@Controller
@RequestMapping("account")
public class LogOutController {
	@Autowired
	SessionService session;

	@RequestMapping("logout/success")
	public String logOutSuccess(Model model, Account account) {
		model.addAttribute("message", "You are logged out!");

		session.remove("username");
		System.out.println("" + session.get("username"));
		return "account/login";
	}
}
