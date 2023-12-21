package com.poly.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.repository.CategoryDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.ParamService;
import com.poly.service.SessionService;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@RestController
@RequestMapping("")
@CrossOrigin("*")
public class ProductManagementController {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	ServletContext app;
	@Autowired
	SessionService session;
	@Autowired
	ParamService param;

	@GetMapping("/rest/products/{id}")
	public Product findById(@PathVariable("id") int id) {
		return productDAO.findById(id).get();
	}

	@GetMapping("/rest/products")
	public List<Product> findAll() {
		return productDAO.findAll();
	}

	// @GetMapping("")
	// private String getProductManager(Model model, @RequestParam("p")
	// Optional<Integer> p,
	// @RequestParam("field") Optional<String> field, @RequestParam("eop")
	// Optional<Integer> eop,
	// @RequestParam("d") Optional<Boolean> direc) {
	// Product item = new Product();
	// model.addAttribute("item", item);
	// int defaultPage = 0;
	// int defaultElementOfPage = 5;
	// String defaultField = "id";
	// // asending is default
	// Pageable pageable = PageRequest.of(p.orElse(defaultPage),
	// eop.orElse(defaultElementOfPage),
	// Sort.by(field.orElse(defaultField)).descending());
	// if (direc.isPresent() && !direc.get().booleanValue()) {
	// pageable = PageRequest.of(p.orElse(defaultPage),
	// eop.orElse(defaultElementOfPage),
	// Sort.by(field.orElse(defaultField)).ascending());
	// }
	// Page<Product> page = productDAO.findAll(pageable);
	// model.addAttribute("page", page);
	// model.addAttribute("field", field.orElse(defaultField));
	// model.addAttribute("eop", eop.orElse(defaultElementOfPage));
	// model.addAttribute("p", p.orElse(defaultPage));
	// model.addAttribute("d", direc.orElse(true));
	// session.set("stateAdmin", "admin");
	// // Title
	// model.addAttribute("title", "QUẢN LÝ SẢN PHẨM");
	// // Remove a kwords search session
	// session.remove("isAvaiable");
	// // Sidebar Active
	// model.addAttribute("isPageActive", "product");
	// List<Category> category = categoryDAO.findAll();
	// model.addAttribute("lst_category", category);
	// for (Category c : category) {
	// System.out.println(c.getName());
	// }
	// return "admin/productManager";
	// }

	// @GetMapping("/edit/{id}")
	// public String editProduct(@ModelAttribute("product") Product product,
	// @PathVariable("id") Integer id, Model model) {
	// product = productDAO.findById(id).get();
	// model.addAttribute("product", product);
	// System.out.println(product.getName());
	// return "/admin/product-update";
	// }

	// @PostMapping("/update")
	// public String updateProduct(@ModelAttribute("product") Product product,
	// BindingResult binding,
	// @RequestParam("photo_file") MultipartFile img, Model model) {
	// if (binding.hasErrors()) {
	// return "/admin/product-update";
	// }
	// String oldImg = param.getString("oldImage", "");
	// System.out.println(oldImg + "Ảnh củ");
	// if (!img.isEmpty()) {
	// File file2 = param.save(img, "/img/product");
	// product.setImage(file2.getName());
	// } else {
	// product.setImage(oldImg);
	// }
	// Product pd = productDAO.save(product);
	// if (pd != null) {
	// session.set("messageProductManager", "Cập nhật sản phẩm thành công");
	// } else {
	// session.set("messageProductManager", "Cập nhật sản phẩm thất bại");
	// }
	// return "redirect:/admin/product-manager";
	// }

	// @GetMapping("/add")
	// private String add(@ModelAttribute("product") Product product, Model model) {
	// List<Category> category = categoryDAO.findAll();
	// model.addAttribute("lst_category", category);
	// return "/admin/product-add";
	// }

	// @PostMapping("/create")
	// private String createProduct(@Valid @ModelAttribute("product") Product
	// product, BindingResult binding,
	// @RequestParam("photo_file") MultipartFile img, Model model) {
	// if (binding.hasErrors() || img.isEmpty()) {
	// if (img.isEmpty()) {
	// model.addAttribute("message", "Vui lòng chọn ảnh sản phẩm");
	// }
	// return "/admin/product-add";
	// }
	// System.out.println(product.getName());
	// File file = null;
	// if (!img.isEmpty()) {
	// file = param.save(img, "/img/product");
	// product.setImage(file.getName());
	// product.setAvailable(true);
	// }
	// session.set("messageProductManager", "Thêm sản phẩm thành công");
	// productDAO.save(product);
	// return "redirect:/admin/product-manager";
	// }

	// @ModelAttribute("list_category")
	// public Map<String, String> getCategory() {
	// Map<String, String> map = new HashMap<>();
	// List<Category> categoryitems = categoryDAO.findAll();
	// for (Category item : categoryitems) {
	// map.put(item.getId(), item.getName());
	// }
	// return map;
	// }

	// @RequestMapping("search-product")
	// public String searchAndPageProduct(Model model, @RequestParam("keywords")
	// Optional<String> kw,
	// @RequestParam("p") Optional<Integer> p) {
	// // Init Product
	// Product item = new Product();
	// model.addAttribute("item", item);
	// // Remove a kwordk search session
	// session.remove("isAvaiable");
	// String kwords = kw.orElse(session.get("keywords"));
	// session.set("keywords", kwords);
	// Pageable pageable = PageRequest.of(p.orElse(0), 5);
	// Page<Product> page = productDAO.findByNameLike("%" + kwords + "%", pageable);
	// model.addAttribute("page", page);
	// // Lst Category
	// List<Category> category = categoryDAO.findAll();
	// model.addAttribute("lst_category", category);
	// return "admin/productManager";
	// }

	// @RequestMapping("filter-product-by-available")
	// public String FilterAvailableAndPageProduct(Model model, @RequestParam(value
	// = "available") String available,
	// @RequestParam("p") Optional<Integer> p) {
	// // Init Product
	// Product item = new Product();
	// model.addAttribute("item", item);
	// // Pagination
	// Pageable pageable = PageRequest.of(p.orElse(0), 5);
	// // Remove a kwordk search session
	// session.remove("keywords");
	// Boolean avaiAbleStore = true;
	// if (available.equals("all")) {
	// Page<Product> page = productDAO.findAll(pageable);
	// model.addAttribute("page", page);
	// } else {
	// if (available.equals("1")) {
	// avaiAbleStore = true;
	// } else {
	// avaiAbleStore = false;
	// }
	// Page<Product> page = productDAO.findProductsByAvailability(avaiAbleStore,
	// pageable);
	// model.addAttribute("page", page);
	// }
	// System.out.println(available);
	// // Lst Category
	// List<Category> category = categoryDAO.findAll();
	// model.addAttribute("lst_category", category);
	// return "admin/productManager";
	// }

	// @RequestMapping("filter-product-by-category")
	// public String FilterCategoryAndPageProduct(Model model,
	// @RequestParam("category") String categoryId,
	// @RequestParam("p") Optional<Integer> p) {
	// System.out.println(categoryId + "Category");
	// model.addAttribute("categoryId", categoryId);
	// // Init Product
	// Product item = new Product();
	// model.addAttribute("item", item);
	// // Remove a kwordk search session
	// session.remove("isAvaiable");
	// // Remove a kwordk search session
	// session.remove("keywords");
	// Category category_kw = categoryDAO.findById(categoryId).get();
	// System.out.println(category_kw.getName());
	// Pageable pageable = PageRequest.of(p.orElse(0), 15);
	// Page<Product> page = productDAO.findByCategoryNameLike("%" +
	// category_kw.getName() + "%", pageable);
	// model.addAttribute("page", page);
	// // Lst Category
	// List<Category> category = categoryDAO.findAll();
	// model.addAttribute("lst_category", category);
	// return "admin/productManager";
	// }
}
