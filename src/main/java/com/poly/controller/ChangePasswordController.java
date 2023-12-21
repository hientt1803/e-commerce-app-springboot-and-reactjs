package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.Account;
import com.poly.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@Controller
@RequestMapping("account")
public class ChangePasswordController {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder pe;

    @GetMapping("changepassword")
    public String changePasswordForm(Model model, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String user = ((UserDetails) principal).getUsername();


            model.addAttribute("user", user);
        }
        return "account/changepassword";
    }

    @PostMapping("changepassword")
    public String processChangePassword(Model model, @RequestParam("username") String username, @RequestParam("password") String newPassword, HttpServletResponse response) {
        Optional<Account> account = accountService.findById(username);
        accountService.changePassword(account.get(), pe.encode(newPassword));
        model.addAttribute("message", "Đổi mật khẩu thành công!");
        response.addHeader("refresh", "1,url=/");
        return "account/changepassword";
    }
//	@GetMapping("/change-password")
//	public String changePassword(Model model) {
//		// Account account = accountDAO.findById(session.get("username")).orElse(null);
//		// model.addAttribute("password", account.getPassword());
//		return "/account/change-password";
//	}
//
//	@PostMapping("/change-password")
//	public String changePasswordProcess(Model model) {
//
//		String username = paramService.getString("username", "");
//		String password = paramService.getString("password", "");
//		String newPass = paramService.getString("newpass", "");
//		String comfirm = paramService.getString("confirm", "");
//		boolean changepwSucess = false;
//
//		Account account = accountDAO.findById(username).orElse(null);
//		System.out.println(account.getPassword());
//
//		if (account.getPassword().equals(password)) {
//			session.set("password", password);
//		}
//
//		if (!account.getPassword().equals(password)) {
//			model.addAttribute("message", "Vui lòng kiểm tra mật khẩu");
//		} else if (newPass.equals("")) {
//			model.addAttribute("message", "Vui lòng nhập vào mật khẩu mới");
//		} else if (comfirm.equals("")) {
//			model.addAttribute("message", "Vui lòng nhập vào mật khẩu xác nhận");
//		} else if (!comfirm.equals(newPass)) {
//			model.addAttribute("message", "Mật khẩu xác nhận không trùng khớp");
//		} else {
//			account.setPassword(newPass);
//			accountDAO.save(account);
//			model.addAttribute("message", "Đổi mật khẩu thành công");
//			model.addAttribute("success", changepwSucess = true);
//		}
//
//		return "/account/change-password";
//	}
}