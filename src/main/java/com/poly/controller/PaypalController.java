package com.poly.controller;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.poly.model.Account;
import com.poly.model.Cart;
import com.poly.model.CartDetail;
import com.poly.model.Coupon;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.repository.CartDAO;
import com.poly.repository.CartDetailDAO;
import com.poly.repository.OrderDAO;
import com.poly.repository.OrderDetailDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.AccountService;
import com.poly.service.ParamService;
import com.poly.service.PaypalService;
import com.poly.service.SessionService;

@Controller
public class PaypalController {

	@Autowired
	PaypalService service;
	@Autowired
	SessionService sessionService;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	ParamService paramService;
	@Autowired
	CartDAO cartDAO;
	@Autowired
	CartDetailDAO cartDetailDAO;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	@Autowired
	AccountService accountService;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@PostMapping("/pay")
	public String payment() {
		double total = paramService.getDouble("total", 0);
		String phone = paramService.getString("sdt", "");
		String address = paramService.getString("dc", "");
		
		// Kiểm tra định dạng số điện thoại bằng biểu thức chính quy
		String phoneRegex = "^\\d{10}$"; // Kiểm tra đúng 10 chữ số
		Pattern pattern = Pattern.compile(phoneRegex);

		if (phone.trim().isEmpty() || address.trim().isEmpty() || !pattern.matcher(phone).matches()) {
			return "redirect:/shop/checkout";
		} else {
			try {
				Payment payment = service.createPayment(total, "USD", "paypal", "sale", address,
						"http://localhost:8080/" + CANCEL_URL, "http://localhost:8080/" + SUCCESS_URL);
				for (Links link : payment.getLinks()) {
					if (link.getRel().equals("approval_url")) {
						return "redirect:" + link.getHref();
					}
				}
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				JsonNode orderData = sessionService.get("orderData");
				ObjectMapper mapper = new ObjectMapper();
				Order order = mapper.convertValue(orderData, Order.class);
				if (order.getPayment().getIdPaymemt() == 1) {
					Coupon coupon = order.getCoupon();
		            if (coupon == null) {
		            	 order.setCoupon(null);
		            }
		            String account =  sessionService.get("username");
		            Account account2 = accountService.findById(account).get();
		            order.setAccount(account2);
		            System.out.println(order);
		            orderDAO.save(order);
		            TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
		            };
		            List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
		                    .peek(d -> d.setOrder(order)).collect(Collectors.toList());
		            orderDetailDAO.saveAll(details);
					return "redirect:/shop/order-history";
				}
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/shop/order-history";
	}

}