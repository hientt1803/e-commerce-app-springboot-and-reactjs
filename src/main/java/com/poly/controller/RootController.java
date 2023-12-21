package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.model.Account;
import com.poly.model.Product;
import com.poly.repository.AccountDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.SessionService;

@Controller
@RequestMapping("/")
public class RootController {
    @Autowired
    ProductDAO productDAO;
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    SessionService session;

    @GetMapping("")
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("user", username);
            session.set("username", username);
        }
        List<Product> items = productDAO.findTop10BestSellingProducts();
        model.addAttribute("items", items);
        model.addAttribute("pageActive", "index");
        return "client/home";
    }

    @RequestMapping("/top-lastest")
    public String topTenLastest(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String user = ((UserDetails) principal).getUsername();
            model.addAttribute("user", user);
        }
        List<Product> items = productDAO.findTop10ByOrderByCreateDateDesc();
        model.addAttribute("items", items);
        model.addAttribute("pageActive", "index");
        return "client/home";
    }
}
