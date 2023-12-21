package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.poly.model.Account;
import com.poly.model.Role;
import com.poly.repository.AccountDAO;
import com.poly.repository.CartDAO;
import com.poly.service.CookieService;
import com.poly.service.ParamService;
import com.poly.service.SessionService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("account")
public class LoginController {
    @Autowired
    AccountDAO dao;
    @Autowired
    SessionService session;
    @Autowired
    CookieService cookieService;
    @Autowired
    ParamService paramService;
    @Autowired
    CartDAO cartDAO;

    @RequestMapping("login")
    public String logInForm(Model model, Account account) {
        System.out.println(account);
        String username = cookieService.getValue("username");
        if (username != null) {
            session.set("username", username);
            model.addAttribute("username", username);
        }
        return "account/login";
    }

    @RequestMapping("login/success")
    public String logInSuccess(Model model, Principal principal) {
        Account account = dao.findByUsername(principal.getName());

        System.out.println(account.getRoles().toString());
        // Trả về List: role
        List<Role> roles = account.getRoles();
        if (!roles.isEmpty()) {
            for (Role role : roles) {
                if (!role.getName().equalsIgnoreCase("user")) {
                    return "redirect:http://localhost:3000/#/";
                }
            }
        }
        model.addAttribute("message", "Đăng nhập thành công");
        return "redirect:/";
    }

    @RequestMapping("login/error")
    public String logInError(Model model, @Validated Account account, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("message", "Tài khoản hoặc mật khẩu không đúng!");
            return "account/login";
        }
        return "account/login";
    }

    @RequestMapping("unauthoried")
    public String unauthoried(Model model, @ModelAttribute("account") Account account) {
        model.addAttribute("message", "Bạn không có quyền truy cập!");
        return "account/login";
    }
}
