package com.poly.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.Account;
import com.poly.model.Order;
import com.poly.repository.OrderDAO;
import com.poly.repository.OrderDetailDAO;
import com.poly.service.AccountService;
import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;
import com.poly.service.ParamService;
import com.poly.service.SessionService;
import com.poly.service.impl.AccountServiceImpl;

@Controller
@RequestMapping("/shop/order-history")
public class OrderHistoryController {
	@Autowired
	OrderDAO orderDao;
	@Autowired
	OrderDetailDAO orderDetailDao;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;

	@Autowired
	AccountServiceImpl accountService;

	@GetMapping("")
	public String index(Model model) {

		Account acc = new Account();
		List<Order> orders = new ArrayList<>();
		try {
			String uname = sessionService.get("username");
			acc = accountService.findById(uname).get();
			if (acc == null) {
				sessionService.set("messageShop", "Đăng nhập trước khi xem đơn hàng");
				return "redirect:/account/login";
			}
			orders = orderService.findByAccountName(uname);
		} catch (Exception e) {
			// TODO: handle exception
		}
		model.addAttribute("orders", orders);
		// model.addAttribute("pageActive", "order-history");

		return "client/order-history";
	}

	@PostMapping("search")
	public String search(Model model, @RequestParam("search") String search, @RequestParam("keyword") String keyword) {
		if (search.equals("select")) {
			model.addAttribute("success", "Chúng tôi chưa biết bạn tìm gì !!!");
		} else if (search.equals("date")) {
			Date date = null;
			try {
				date = paramService.getDate2(keyword, "yyyy-MM-dd");
				List<Order> orders = orderService.findByCreatedDate(date);
				if (orders.size() > 0) {
					model.addAttribute("success", "Đã tìm thấy ngày đặt hàng là : " + keyword);
					model.addAttribute("orders", orders);
					model.addAttribute("isCreateDate", true);
					model.addAttribute("isSuscess", true);
				} else {
					model.addAttribute("success", "Không tìm thấy ngày đặt hàng là : " + keyword);
					model.addAttribute("isSuscess", true);
				}
			} catch (Exception e) {
				model.addAttribute("success", "Bạn chưa chọn ngày đặt !!!");
				model.addAttribute("isSuscess", true);
			}
		} else if (search.equals("id")) {
			try {
				List<Order> orders = (List<Order>) orderDao.findByID(Long.parseLong(keyword));
				if (orders.size() > 0) {
					model.addAttribute("orders", orders);
					model.addAttribute("isId", true);
					model.addAttribute("success", "Đã tìm thấy mã đơn hàng: " + keyword);
				} else {
					model.addAttribute("success", "Không tìm thấy mã đơn hàng: " + keyword);
				}
			} catch (Exception e) {
				model.addAttribute("success", "Mã đơn hàng phải là số nguyên !!! ");
			}
		}
		model.addAttribute("pageActive", "order-history");

		return "client/order-history";
	}

	@PostMapping("filter")
	public String search(Model model, @RequestParam("status") String status) {
		if (status.equals("C")) {
			List<Order> orders = orderService.findByStatusAndUser(status, sessionService.get("username"));
			if (orders.size() > 0) {
				model.addAttribute("orders", orders);
				model.addAttribute("isC", true);
				model.addAttribute("success", "Đã tìm thấy đơn hàng có trạng thái là Đang chờ");
			} else {
				model.addAttribute("success", "Không tìm thấy đơn hàng có trạng thái là Đang chờ");
			}
		} else if (status.equals("XL")) {
			List<Order> orders = orderService.findByStatusAndUser(status, sessionService.get("username"));
			if (orders.size() > 0) {
				model.addAttribute("orders", orders);
				model.addAttribute("isXl", true);
				model.addAttribute("success", "Đã tìm thấy đơn hàng có trạng thái là Đang xử lý");
			} else {
				model.addAttribute("success", "Không tìm thấy đơn hàng có trạng thái là Đang xử lý");
			}
		} else if (status.equals("G")) {
			List<Order> orders = orderService.findByStatusAndUser(status, sessionService.get("username"));
			if (orders.size() > 0) {
				model.addAttribute("orders", orders);
				model.addAttribute("isG", true);
				model.addAttribute("success", "Đã tìm thấy đơn hàng có trạng thái là Đang giao");
			} else {
				model.addAttribute("success", "Không tìm thấy đơn hàng có trạng thái là Đang giao");
			}
		} else if (status.equals("DG")) {
			List<Order> orders = orderService.findByStatusAndUser(status, sessionService.get("username"));
			if (orders.size() > 0) {
				model.addAttribute("orders", orders);
				model.addAttribute("isDg", true);
				model.addAttribute("success", "Đã tìm thấy đơn hàng có trạng thái là Đã giao");
			} else {
				model.addAttribute("success", "Không tìm thấy đơn hàng có trạng thái là Đã giao");
			}

		} else if (status.equals("H")) {
			List<Order> orders = orderService.findByStatusAndUser(status, sessionService.get("username"));
			if (orders.size() > 0) {
				model.addAttribute("orders", orders);
				model.addAttribute("isH", true);
				model.addAttribute("success", "Đã tìm thấy đơn hàng có trạng thái là Đã hủy");
			} else {
				model.addAttribute("success", "Không tìm thấy đơn hàng có trạng thái là Đã hủy");
			}

		} else if (status.equals("select")) {
			model.addAttribute("success", "Bạn chưa chọn trạng thái !!! ");
		}
		model.addAttribute("pageActive", "order-history");

		return "client/order-history";
	}
}
