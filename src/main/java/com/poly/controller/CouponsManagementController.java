package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Coupon;
import com.poly.service.ParamService;
import com.poly.service.impl.CouponServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/coupons")
public class CouponsManagementController {
	@Autowired
	CouponServiceImpl couponService;
	@Autowired
	ParamService paramService;

	@GetMapping("")
	@ResponseBody
	public List<Coupon> getAll() {
		return couponService.findAll();
	}

	@PutMapping("")
	@ResponseBody
	public ResponseEntity<Coupon> update(@RequestBody Coupon coupon) {

		System.out.println("CREATE DATE" + coupon.getCreatedDate());
		return ResponseEntity.ok(couponService.save(coupon));
	}

	@PostMapping("")
	@ResponseBody
	public ResponseEntity<Coupon> add(@RequestBody Coupon coupon) {

		System.out.println("CREATE DATE" + coupon.getCreatedDate());
		return ResponseEntity.ok(couponService.save(coupon));
	}

	// @GetMapping("")
	// public String index(Model model, @RequestParam("p") Optional<Integer> p,
	// @RequestParam("eop") Optional<Integer> eop,
	// @RequestParam("field") Optional<String> field, @RequestParam("d")
	// Optional<Boolean> direc) {
	// int defaultPage = 0;
	// int defaultElementOfPage = 3;
	// String defaultField = "expirationDate";
	// String keyword = paramService.getString("keyword", " ");
	// String search = paramService.getString("search", " ");
	// model.addAttribute("isPageActive", "coupon");

	// // asending is default
	// Pageable pageable = PageRequest.of(p.orElse(defaultPage),
	// eop.orElse(defaultElementOfPage),
	// Sort.by(field.orElse(defaultField)).ascending());

	// if (direc.isPresent() && !direc.get().booleanValue()) {
	// pageable = PageRequest.of(p.orElse(defaultPage),
	// eop.orElse(defaultElementOfPage),
	// Sort.by(field.orElse(defaultField)).descending());
	// }

	// Page<Coupon> coupons = dao.findAll(pageable);
	// if (search.equals("ed")) {
	// Date date = null;
	// try {
	// date = paramService.getDate2(keyword, "yyyy-MM-dd");
	// } catch (Exception e) {
	// model.addAttribute("success", "Bạn chưa chọn ngày hết hạn !!!");
	// model.addAttribute("isEd", true);
	// model.addAttribute("isEd", true);
	// model.addAttribute("isSuscess", true);
	// return "/admin/coupons";
	// }
	// coupons = dao.findByExpirationDate(date, pageable);
	// if (coupons.getTotalPages() > 0) {
	// model.addAttribute("coupons", coupons);
	// model.addAttribute("success", "Đã tìm thấy ngày hết hạn: " + keyword);
	// model.addAttribute("isEd", true);
	// model.addAttribute("isSuscess", true);
	// } else {
	// model.addAttribute("success", "Không tìm thấy ngày hết hạn là: " + keyword);
	// model.addAttribute("isEd", true);
	// model.addAttribute("isSuscess", true);
	// }
	// } else if (search.equals("select")) {
	// model.addAttribute("success", "Chúng tôi chưa biết bạn tìm gì !!!");
	// return "/admin/coupons";
	// } else if (search.equals("name")) {
	// if (keyword.equals("")) {
	// model.addAttribute("success", "Vui lòng nhập dữ liệu trước khi tìm !!! ");
	// model.addAttribute("isName", true);
	// return "/admin/coupons";
	// }
	// try {
	// Integer.parseInt(keyword);
	// model.addAttribute("success", "Tên giảm giá phải là ký tự !!! ");
	// model.addAttribute("isName", true);
	// return "/admin/coupons";
	// } catch (Exception e) {
	// }
	// coupons = dao.findByCouponName("%" + keyword + "%", pageable);
	// if (coupons.getTotalPages() > 0) {
	// model.addAttribute("coupons", coupons);
	// model.addAttribute("success", "Đã tìm thấy tên giảm giá có chứa : " +
	// keyword);
	// model.addAttribute("isName", true);
	// } else {
	// model.addAttribute("success", "Không tìm thấy giảm giá có tên " + keyword);
	// }
	// } else if (search.equals("select")) {
	// model.addAttribute("success", "Chúng tôi chưa biết bạn tìm gì !!!");
	// return "/admin/coupons";
	// } else if (search.equals("cd")) {
	// Date date = null;
	// try {
	// date = paramService.getDate2(keyword, "yyyy-MM-dd");
	// } catch (Exception e) {
	// model.addAttribute("success", "Bạn chưa chọn ngày tạo !!!");
	// model.addAttribute("isCd", true);
	// model.addAttribute("isSuscess", true);
	// return "/admin/coupons";
	// }
	// coupons = dao.findByCreatedDate(date, pageable);
	// if (coupons.getTotalPages() > 0) {
	// model.addAttribute("coupons", coupons);
	// model.addAttribute("success", "Đã tìm thấy ngày tạo: " + keyword);
	// model.addAttribute("isCd", true);
	// model.addAttribute("isSuscess", true);
	// } else {
	// model.addAttribute("success", "Không tìm thấy ngày tạo là: " + keyword);
	// model.addAttribute("isSuscess", true);
	// }
	// }

	// model.addAttribute("field", field.orElse(defaultField));
	// model.addAttribute("eop", eop.orElse(defaultElementOfPage));
	// model.addAttribute("p", p.orElse(defaultPage));
	// model.addAttribute("d", direc.orElse(true));
	// model.addAttribute("coupons", coupons);
	// return "/admin/coupons";
	// }

	// @GetMapping("add")
	// public String add(Coupon coupon) {
	// return "/admin/coupons-add";
	// }

	// @GetMapping("edit/{id}")
	// public String edit(Model model, @PathVariable("id") String id) {
	// Coupon coupon = dao.findById(id).get();
	// model.addAttribute("coupon", coupon);
	// return "/admin/coupons-update";
	// }

	// @PostMapping("update")
	// public String update(Model model, @Validated @ModelAttribute("coupon") Coupon
	// coupon, BindingResult result) {
	// LocalDate startDate =
	// coupon.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	// LocalDate endDate =
	// coupon.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	// LocalDate now = LocalDate.now();

	// if (result.hasErrors()) {
	// return "/admin/coupons-update";
	// }
	// if (startDate.isAfter(endDate)) {
	// //
	// model.addAttribute("sussces2", "Ngày bắt đầu trước Ngày hết hạn !!!");
	// model.addAttribute("expirationDate", endDate);
	// model.addAttribute("startDate", startDate);
	// return "/admin/coupons-update";
	// }
	// if (endDate.isBefore(startDate)) {
	// //
	// model.addAttribute("sussces", "Ngày hết hạn sau ngày bắt đầu !!!");
	// model.addAttribute("startDate", startDate);
	// model.addAttribute("expirationDate", endDate);
	// return "/admin/coupons-update";
	// }
	// if (endDate.isBefore(now)) {
	// // Ngày kết thúc đã qua ngày hiện tại
	// model.addAttribute("sussces", "Ngày hết hạn nhỏ hơn Ngày hiện tại !!!");
	// model.addAttribute("startDate", startDate);
	// model.addAttribute("expirationDate", endDate);
	// return "/admin/coupons-update";
	// }
	// Date dateBD =
	// Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	// Date dateKT =
	// Date.from(endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	// coupon.setStartDate(dateBD);
	// coupon.setExpirationDate(dateKT);
	// dao.save(coupon);
	// model.addAttribute("success", "Cập nhật giảm giá thành công");
	// return "/admin/coupons";
	// }

	// @PostMapping("create")
	// public String create(Model model, @Validated @ModelAttribute("coupon") Coupon
	// coupon, BindingResult result) {
	// LocalDate startDate =
	// coupon.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	// LocalDate endDate =
	// coupon.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	// LocalDate now = LocalDate.now();

	// if (result.hasErrors()) {
	// return "/admin/coupons-add";
	// }
	// if (startDate.isAfter(endDate)) {
	// //
	// model.addAttribute("sussces2", "Ngày bắt đầu trước Ngày hết hạn !!!");
	// model.addAttribute("expirationDate", endDate);
	// model.addAttribute("startDate", startDate);
	// return "/admin/coupons-add";
	// }
	// if (endDate.isBefore(startDate)) {
	// //
	// model.addAttribute("sussces", "Ngày hết hạn sau ngày bắt đầu !!!");
	// model.addAttribute("startDate", startDate);
	// model.addAttribute("expirationDate", endDate);
	// return "/admin/coupons-add";
	// }
	// if (endDate.isBefore(now)) {
	// // Ngày kết thúc đã qua ngày hiện tại
	// model.addAttribute("sussces", "Ngày hết hạn nhỏ hơn Ngày hiện tại !!!");
	// model.addAttribute("startDate", startDate);
	// model.addAttribute("expirationDate", endDate);
	// return "/admin/coupons-add";
	// }
	// dao.save(coupon);
	// model.addAttribute("success", "Thêm giảm giá thành công");
	// return "/admin/coupons";
	// }

	// @RequestMapping("search")
	// public String searchCoupon(@RequestParam("keyword") String keyword,
	// @RequestParam("search") String search) {
	// return "forward:/admin/coupon";
	// }

	// @PostMapping("filter")
	// public String search(Model model, @RequestParam("status") String status,
	// @RequestParam("p") Optional<Integer> p,
	// @RequestParam("eop") Optional<Integer> eop, @RequestParam("field")
	// Optional<String> field,
	// @RequestParam("d") Optional<Boolean> direc) {
	// int defaultPage = 0;
	// int defaultElementOfPage = 3;
	// String defaultField = "expirationDate";

	// // asending is default
	// Pageable pageable = PageRequest.of(p.orElse(defaultPage),
	// eop.orElse(defaultElementOfPage),
	// Sort.by(field.orElse(defaultField)).ascending());

	// if (direc.isPresent() && !direc.get().booleanValue()) {
	// pageable = PageRequest.of(p.orElse(defaultPage),
	// eop.orElse(defaultElementOfPage),
	// Sort.by(field.orElse(defaultField)).descending());
	// }

	// if (status.equals("active")) {
	// Page<Coupon> coupons = dao.findByActivated(true, pageable);
	// model.addAttribute("coupons", coupons);
	// model.addAttribute("isActive", true);
	// model.addAttribute("success", "Đã tìm thấy giảm giá có trạng thái là Hoạt
	// động");
	// } else if (status.equals("inactive")) {
	// Page<Coupon> coupons = dao.findByActivated(false, pageable);
	// model.addAttribute("coupons", coupons);
	// model.addAttribute("isInactive", true);
	// model.addAttribute("success", "Đã tìm thấy giảm giá có trạng thái là Không
	// hoạt động");
	// } else if (status.equals("select")) {
	// model.addAttribute("success", "Chúng tôi chưa biết bạn lọc gì !!!");
	// return "/admin/coupons";
	// }
	// model.addAttribute("isHide", true);
	// return "/admin/coupons";
	// }
}
