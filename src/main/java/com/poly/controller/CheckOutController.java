package com.poly.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.poly.model.Account;
import com.poly.model.Cart;
import com.poly.model.CartDetail;
import com.poly.model.Coupon;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.Payment;
import com.poly.model.Product;
import com.poly.repository.AccountDAO;
import com.poly.repository.CartDAO;
import com.poly.repository.CartDetailDAO;
import com.poly.repository.CouponDAO;
import com.poly.repository.OrderDAO;
import com.poly.repository.OrderDetailDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.AccountService;
import com.poly.service.ParamService;
import com.poly.service.PaymentService;
import com.poly.service.PhoneNumberValidator;
import com.poly.service.SessionService;

@Controller
@RequestMapping("shop/checkout")
public class CheckOutController {
	@Autowired
	AccountService accountService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	SessionService sessionService;

	@GetMapping("")
	public String index(Model model) {
		// lấy account
		// Account account = sessionService.get("account");
		// Cart cart = cartDAO.findByUserName(account.getUsername());
		// lấy product
		// List<CartDetail> listCartDetail = cart.getCartDetails();
		// tính tổng tiền
		// double toTal_Price = 0;
		// for (CartDetail od : listCartDetail) {
		// double toTal = od.getProduct().getPrice() * od.getQuantity();
		// toTal_Price += toTal;
		// }
		// model.addAttribute("cartDetails", listCartDetail);
		// model.addAttribute("provisional", toTal_Price);
		// model.addAttribute("account", account);
		String username = sessionService.get("username");
		if (username == null) {
			sessionService.set("state", "cart");
		}
		Account acc = accountService.findById(username).get();
		// Optional<Account> account = accountService.findById("hoainam");
		model.addAttribute("account", acc);
		List<Payment> payment = paymentService.findAll();
		model.addAttribute("payment", payment);
		return "/client/checkoutt";
	}

	@PostMapping("/create")
	public String checkout(Model model) {
		// Order
		// Order order = new Order();
		// double toTal_Price = 0;
		// Account account = sessionService.get("account");
		// Cart cart = cartDAO.findByUserName(account.getUsername());
		// lấy product
		// List<CartDetail> listCartDetail = cart.getCartDetails();
		// for (CartDetail od : listCartDetail) {
		// double toTal = od.getProduct().getPrice() * od.getQuantity();
		// toTal_Price += toTal;
		// }
		// String phone = paramService.getString("phone", "");
		// String address = paramService.getString("address", "");
		// Coupon coupon = sessionService.get("coupon");
		// double discountAmount = 0.0;
		// if (coupon != null) {
		// discountAmount = coupon.getDiscountAmount();
		// }
		// if (PhoneNumberValidator.validate(phone)) {
		// order.setCoupon(coupon);
		// order.setAccount(account);
		// order.setPhone(phone);
		// order.setAddress(address);
		// order.setTotalPrice(toTal_Price - (toTal_Price * discountAmount));
		// order.setStatus("C");
		// orderDAO.save(order);
		// sessionService.remove("coupon");
		// OrderDetail
		// for (CartDetail od : listCartDetail) {
		// OrderDetail orderDetail = new OrderDetail();
		// orderDetail.setOrder(order);
		// orderDetail.setProduct(od.getProduct());
		// orderDetail.setPrice(od.getProduct().getPrice());
		// orderDetail.setQuantity(od.getQuantity());
		// orderDetailDAO.save(orderDetail);
		// xóa sản phẩm của cartdetail
		// int productId = od.getProduct().getId();
		// cartDetailDAO.deleteByProductId(productId);
		// cập nhật lại số lượng của sản phẩm
		// productDAO.updateQuantityProduct(od.getQuantity(), productId);
		// }
		// } else if (phone.equals("")) {
		// model.addAttribute("success", "Bạn chưa nhập số điện thoại !!!");
		// return "/client/checkout";
		// } else if (address.equals("")) {
		// model.addAttribute("success", "Bạn chưa nhập địa chỉ !!!");
		// return "/client/checkout";
		// } else {
		// model.addAttribute("success", "Số điện thoại không hợp lệ !!!");
		// return "/client/checkout";
		// }
		return "redirect:/shop/order-history";
	}
}
