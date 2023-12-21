package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.poly.service.EmailService;
import com.poly.service.SessionService;
import com.poly.utils.EmailDetail;
import com.poly.utils.Generator;

@Controller

public class EmailController {

	@Autowired
	private EmailService emailService;
	@Autowired
	SessionService sessionService;

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String sendMail(Model m) {
		String code = Generator.getCode();
		sessionService.set("code", code);
		EmailDetail details = new EmailDetail();
		details.setRecipient("namnhpc03517@fpt.edu.vn");
		details.setSubject("Xác nhận tài khoản thành viên 3MEMS");
		details.setMsgBody("Vui lòng nhập mã xác nhận bên dưới để hoàn thành tạo tài khoản\nMã xác nhận của bạn là "
				+ "<strong>" + code + "</strong>");
		String status = emailService.sendSimpleMail(details);
		System.out.println(status);
		return "/client/login";
	}

	// Sending email with attachment
	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetail details) {
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}
}