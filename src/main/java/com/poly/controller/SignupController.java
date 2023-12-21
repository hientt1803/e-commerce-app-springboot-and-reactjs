package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.model.Account;
import com.poly.service.AccountService;
import com.poly.service.EmailServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("account")
public class SignupController {

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Autowired
	AccountService accountService;

	@Autowired
	PasswordEncoder pe;

	@GetMapping("signup")
	public String getFormSignin(Model model) {
		model.addAttribute("account", new Account());
		return "/account/signup";
	}

	@PostMapping("signup")
	public String signUpSuccess(Model model, @Valid @ModelAttribute("account") Account account, Errors error,
			HttpServletResponse response) {
		if (error.hasErrors()) {
			model.addAttribute("message", "Vui lòng nhập đầy đủ thông tin để tạo tài khoản!");
			return "account/signup";
		}
		account.setPhoto(null);
		account.setActivated(true);
		account.setToken(null);
		account.setRoles(null);
		account.setPassword(pe.encode(account.getPassword()));
		accountService.create(account);
		model.addAttribute("message", "Đăng ký tài khoản thành công!");
		response.addHeader("refresh", "1,url=/account/login");
		return "account/signup";
	}

//    @PostMapping("signup")
//    public String sendCode(@Valid Account account, BindingResult rs, @RequestParam("avt") MultipartFile file,
//            Model model) {
//        String confirmPass = paramService.getString("confirmPass", "");
//        System.out.println(confirmPass);
//        if (rs.hasErrors() || file.isEmpty() || !confirmPass.equals(account.getPassword())) {
//            model.addAttribute("msg", "Vui lòng nhập đầy đủ thông tin để tạo tài khoản !");
//            if (file.isEmpty()) {
//                model.addAttribute("img_msg", "Vui lòng chọn ảnh đại diện !");
//            }
//            if (!confirmPass.equals(account.getPassword())) {
//                model.addAttribute("confirmPassMsg", "Xác nhận mật khẩu không đúng, vui lòng thử lại !");
//            }
//            model.addAttribute("confirmPass", confirmPass);
//            // model.addAttribute("alertType", "danger");
//            return "account/signup";
//        } else {
//            // Account existAccount = accountDAO.findById(account.getUsername()).get();
//            // if (existAccount != null) {
//            // model.addAttribute("msg", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác
//            // !");
//            // return "account/signup";
//            // }
//            File newFile = paramService.save(file, "/img/user-management");
//            account.setPhoto(newFile.getName());
//            sessionService.set("signupMail", account.getEmail());
//            sessionService.set("signupUser", account);
//            String code = Generator.getCode();
//            sessionService.set("code", code);
//            EmailDetail details = new EmailDetail();
//            details.setRecipient(account.getEmail());
//            details.setSubject("Xác nhận tài khoản thành viên 3MEMS ");
//            details.setMsgBody(
//                    "Vui lòng nhập mã xác nhận bên dưới để hoàn thành tạo tài khoản\nMã xác nhận của bạn là " + code);
//            String status = emailServiceImpl.sendSimpleMail(details);
//            System.out.println(status);
//            return "/account/verify";
//        }
//    }
}
