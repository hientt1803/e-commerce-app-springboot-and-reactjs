package com.poly.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Account;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.Product;
import com.poly.repository.AccountDAO;
import com.poly.repository.OrderDAO;
import com.poly.repository.OrderDetailDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.SessionService;

@CrossOrigin("*")
@RestController
public class AdminIndexRestController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    AccountDAO accountDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    SessionService sessionService;

    @GetMapping("/admin/index/findByStatusC")
    public List<Order> getFindByStatusC() {
        return orderDAO.findByStatus("C");
    }

    @GetMapping("/admin/username")
    public Account getUsernameInDashboard() {
        String uname = sessionService.get("username");
        System.out.println(uname);
        Account acc = accountDAO.findByUsername(uname);
        return acc;
    }

    @GetMapping("/admin/index/selectProductNewest")
    public List<Product> getNewestProducts() {
        Date start = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(LocalDate.now().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        return productDAO.findByCreateDateBetween(start, end);
    }

    @GetMapping("/admin/index/totalRevenue")
    public Double getTotalRevenue() {
        return orderDAO.getTotalRevenue();
    }

    @GetMapping("/admin/index/totalProductsSoldToday")
    public Long getTotalProductsSoldToday() {
        LocalDate today = LocalDate.now();
        Date startDateSold = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateSold = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return orderDAO.countProductsSoldByDate(startDateSold, endDateSold);
    }

    @GetMapping("/admin/index/getPageTopTenOrderDetails")
    public List<OrderDetail> getPageTopTenOrderDetails() {
        Account ac = new Account();
        ac.setUsername("hoainam");
        ac.setPassword("123456");
        ac.setFullname("Hoài Nam");
        ac.setEmail("namnhpc03517@fpt.edu.vn");

        sessionService.set("account", ac);
        return orderDetailDAO.getTop10OrderDetailNonePageAble();
    }

    @GetMapping("/admin/index/bestSellingProduct")
    public List<Product> bestSellingProduct() {
        return productDAO.findTop10BestSellingProducts();
    }

}
