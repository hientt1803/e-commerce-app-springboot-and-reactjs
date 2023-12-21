package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.model.Account;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.Product;
import com.poly.repository.OrderDetailDAO;
import com.poly.service.EmailServiceImpl;
import com.poly.service.OrderService;
import com.poly.service.ParamService;
import com.poly.service.SessionService;
import com.poly.service.impl.OrderDetailImpl;
import com.poly.service.impl.OrderServiceImpl;
import com.poly.service.impl.ProductServiceImpl;
import com.poly.utils.EmailDetail;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	@Autowired
	OrderDetailImpl orderDetailService;
	@Autowired
	ProductServiceImpl productService;

	@Autowired
	EmailServiceImpl emailServiceImpl;
	@Autowired
	SessionService sessionService;
	@Autowired
	ParamService paramService;

	@PostMapping()
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}

	// @GetMapping("")
	// @ResponseBody
	// public List<Order> getAll() {
	// return orderService.findAll();
	// }

	// @GetMapping("/detail/{orderID}")
	// @ResponseBody
	// public List<OrderDetail> getAllOrderDetailByOrderID(@PathVariable("orderID")
	// Long id) {
	// return orderDetailService.findByOrderId(id);
	// }

	// @GetMapping("/users/{orderID}")
	// @ResponseBody
	// public Account getAllAccount(@PathVariable("orderID") Long id) {
	// return orderService.findAllAccount(id);
	// }

	// @PutMapping("/{orderID}")
	// public ResponseEntity<Object> updateOrder(@RequestBody Order order,
	// @PathVariable("orderID") Long id) {
	// if (order.getStatus().equals("H")) {
	// order.setNotes(order.getNotes());
	// // gui mail sau khi huy don hang
	// Account account = (Account) sessionService.get("account");
	// EmailDetail details = new EmailDetail();
	// details.setRecipient(account.getEmail());
	// details.setSubject("4MEMS - Thông báo hủy đơn hàng");
	// details.setMsgBody(
	// "Thông tin đơn hàng của bạn\n"
	// + "Mã đơn hàng: " + id +
	// "\nTổng tiền đơn hàng: " + order.getTotalPrice() + " đ" +
	// "\nNgày đặt hàng: " + order.getCreateDate() +
	// "\nLý do đơn hàng bị hủy: " + order.getNotes() +
	// "\n3MEMS thành thật xin lỗi quý khách vì đơn hàng đã bị hủy.\nMong quý khách
	// luôn tin tưởng và ủng hộ 3MEMS trong thời gian sắp tới ! ");
	// String sts = emailServiceImpl.sendSimpleMail(details);
	// // List<OrderDetail> ls = order.getOrderDetails();
	// // for (OrderDetail orderDetail : ls) {
	// // Product prod = orderDetail.getProduct();
	// // System.out.println("oldProd" + orderDetail.getProduct().getQuantity());
	// // System.out.println("orderDetail " + orderDetail.getQuantity());
	// // prod.setQuantity(prod.getQuantity() + orderDetail.getQuantity());
	// // System.out.println("new Prod " + prod.getQuantity());
	// // productService.save(prod);
	// // }
	// }
	// return ResponseEntity.ok(orderService.save(order));
	// }
}
