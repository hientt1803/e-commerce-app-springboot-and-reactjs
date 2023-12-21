package com.poly.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.model.Account;
import com.poly.model.Cart;
import com.poly.model.CartDetail;
import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.repository.AccountDAO;
import com.poly.repository.CartDAO;
import com.poly.repository.CartDetailDAO;
import com.poly.repository.CategoryDAO;
import com.poly.repository.OrderDetailDAO;
import com.poly.repository.ProductDAO;
import com.poly.service.SessionService;

@Controller
@RequestMapping("shop")
public class ShopController {
	@Autowired
	AccountDAO accountDAO;
	@Autowired
	SessionService sessionService;
	@Autowired
	CartDetailDAO cartDetailDAO;
	@Autowired
	CartDAO cartDAO;
	@Autowired
	OrderDetailDAO dao;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	SessionService session;

	@GetMapping("cart-detail")
	public String getCartDetailView(Model model, RedirectAttributes rdAtr) {

		// if (session.get("username") == null) {
		// session.set("messageShop", "Đăng nhập trước khi xem giỏ hàng");
		// rdAtr.addAttribute("isMessageShop", true);
		// return "redirect:/account/login";
		// }
		// // ! mockup get client form session
		// Account account = sessionService.get("account");
		// model.addAttribute("pageActive", "shop");

		// // * get client form session
		// Cart cart = cartDAO.findByUserName(account.getUsername());
		// List<CartDetail> cartDetails = cart.getCartDetails();
		// Double totalPrice = 0.0;
		// int totalQuantity = 0;
		// for (CartDetail cartDetail : cartDetails) {
		// totalPrice += (cartDetail.getProduct().getPrice() *
		// cartDetail.getQuantity());
		// totalQuantity += cartDetail.getQuantity();
		// }
		// sessionService.set("totalCart", totalQuantity);
		// model.addAttribute("cartDetails", cartDetails);
		// model.addAttribute("totalPrice", totalPrice);
		// model.addAttribute("cart", cart);
		return "/client/cart-detail";
	}

	@GetMapping("")
	public String index(Model model, @RequestParam("p") Optional<Integer> p) {
		// if (session.get("username") != null) {
		// Account account = accountDAO.findById(session.get("username")).orElse(null);
		// model.addAttribute("isAdmin", account.getAdmin());
		// }
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = productDAO.findByAvailable(true, pageable);
		model.addAttribute("page", page);
		model.addAttribute("pageActive", "shop");
		// Category
		List<Category> categoryLst = categoryDAO.findAll();
		model.addAttribute("categoryLst", categoryLst);
		return "/client/shop";
	}

	// @GetMapping("cart-detail")
	// public String getCartDetailView(Model model, RedirectAttributes rdAtr) {
	// if (session.get("username") == null) {
	// session.set("messageShop", "Đăng nhập trước khi xem giỏ hàng");
	// rdAtr.addAttribute("isMessageShop", true);
	// return "redirect:/account/login";
	// }
	// // ! mockup get client form session
	// Account account = sessionService.get("account");
	// model.addAttribute("pageActive", "shop");
	//
	// // * get client form session
	// Cart cart = cartDAO.findByUserName(account.getUsername());
	// List<CartDetail> cartDetails = cart.getCartDetails();
	// Double totalPrice = 0.0;
	// int totalQuantity = 0;
	// for (CartDetail cartDetail : cartDetails) {
	// totalPrice += (cartDetail.getProduct().getPrice() *
	// cartDetail.getQuantity());
	// totalQuantity += cartDetail.getQuantity();
	// }
	// sessionService.set("totalCart", totalQuantity);
	// model.addAttribute("cartDetails", cartDetails);
	// model.addAttribute("totalPrice", totalPrice);
	// model.addAttribute("cart", cart);
	// return "/client/cart-detail";
	// }
	//
	// @GetMapping("/cart-detail/add/{productId}")
	// public String addCartDetail(@PathVariable("productId") Integer productId,
	// Model model, RedirectAttributes rdAtr) {
	// if (session.get("username") == null) {
	// session.set("messageShop", "Đăng nhập trước khi thêm sản phẩm vào giỏ hàng");
	// rdAtr.addAttribute("isMessageShop", true);
	// return "redirect:/account/login";
	// }
	// // mock client form session
	// Account account = sessionService.get("account");
	// Cart cart = cartDAO.findByUserName(account.getUsername());
	// CartDetail cartDetail = new CartDetail();
	// cartDetail.setProduct(productDAO.findById(productId).get());
	// CartDetail testCarDetail =
	// cartDetailDAO.findByProduct(productDAO.findById(productId).get());
	// int totalQuantity = 0;
	// if (testCarDetail == null) {
	// cartDetail.setQuantity(1);
	// } else {
	// cartDetail = testCarDetail;
	// cartDetail.setQuantity(cartDetail.getQuantity() + 1);
	//
	// }
	// for (CartDetail cd : cart.getCartDetails()) {
	// totalQuantity += cd.getQuantity();
	// }
	// cart.setQuantity(totalQuantity);
	// cartDetail.setCart(cart);
	// cartDAO.save(cart);
	// cartDetailDAO.save(cartDetail);
	// sessionService.set("totalCart", cart.getQuantity());
	// model.addAttribute("pageActive", "shop");
	//
	// sessionService.set("isUpdated", true);
	// return "redirect:/shop";
	// }
	//
	// @PostMapping("/cart-detail/add")
	// public String addCartDetailFromProductDetail(@RequestParam("productId")
	// Integer productId, RedirectAttributes rdAtr,
	// @RequestParam("quantity") Integer quantity) {
	//
	// if (session.get("username") == null) {
	// session.set("selectedProductId", productId);
	// session.set("selectedQuantity", quantity);
	// session.set("state", "productDetail");
	// session.set("messageShop", "Đăng nhập trước khi thêm sản phẩm vào giỏ hàng");
	// rdAtr.addAttribute("isMessageShop", true);
	// return "redirect:/account/login";
	// }
	//
	// // mock client form session
	// Account account = sessionService.get("account");
	// Cart cart = cartDAO.findByUserName(account.getUsername());
	// CartDetail cartDetail = new CartDetail();
	// cartDetail.setProduct(productDAO.findById(productId).get());
	// CartDetail testCarDetail =
	// cartDetailDAO.findByProduct(productDAO.findById(productId).get());
	// int totalQuantity = 0;
	// if (testCarDetail == null) {
	// cartDetail.setQuantity(quantity);
	// } else {
	// cartDetail = testCarDetail;
	// cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
	// }
	// for (CartDetail cd : cart.getCartDetails()) {
	// totalQuantity += cd.getQuantity();
	//
	// }
	// cart.setQuantity(totalQuantity);
	// cartDetail.setCart(cart);
	// cartDAO.save(cart);
	// cartDetailDAO.save(cartDetail);
	// sessionService.set("totalCart", cart.getQuantity());
	// sessionService.set("isUpdated", true);
	// return "redirect:/shop";
	// }
	//
	// @PostMapping("cart-detail/update")
	// public String updateCartDetail(@RequestParam("cartdetailId") Integer
	// cartdetailId,
	// @RequestParam("quantity") Integer quantity) {
	// System.out.println("cartdetailId " + cartdetailId + " " + quantity);
	// CartDetail cartDetail = cartDetailDAO.findById(cartdetailId).get();
	// cartDetail.setQuantity(quantity);
	// cartDetailDAO.save(cartDetail);
	// System.out.println(cartDetail.getQuantity());
	// return "redirect:/shop/cart-detail";
	// }
	//
	// @GetMapping("cart-detail/delete/{id}")
	// public String deleteCartDetail(@PathVariable("id") Integer cartdetailId) {
	// cartDetailDAO.deleteById(cartdetailId);
	// return "redirect:/shop/cart-detail";
	// }
	//
	// // @GetMapping("add-to-cart")
	// // public String AddToCart(Model model, RedirectAttributes rdAtr) {
	// // if (session.get("username") == null) {
	// // session.set("messageShop", "Đăng nhập trước khi thêm sản phẩm vào giỏ
	// hàng");
	// // rdAtr.addAttribute("isMessageShop", true);
	// // return "redirect:/account/login";
	// // }
	//
	// // return "redirect:/shop";
	// // }

	@RequestMapping("shop-search-product")
	public String searchAndPageProduct(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		// Init Product
		Product item = new Product();
		model.addAttribute("item", item);
		String kwords = kw.orElse(sessionService.get("keywords"));
		sessionService.set("keywords", kwords);
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productDAO.findByNameLike("%" + kwords + "%", pageable);
		model.addAttribute("page", page);
		// Category
		List<Category> categoryLst = categoryDAO.findAll();
		model.addAttribute("categoryLst", categoryLst);
		// Page Active
		model.addAttribute("pageActive", "shop");
		return "/client/shop";
	}

	@RequestMapping("sort-by-category/{name}")
	public String sortByCategory(@ModelAttribute("item") Category item, @PathVariable("name") String name,
			@RequestParam("p") Optional<Integer> p, Model model) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productDAO.findByCategoryNameLike("%" + name + "%", pageable);
		System.out.println(page);
		model.addAttribute("page", page);
		// Category
		List<Category> categoryLst = categoryDAO.findAll();
		model.addAttribute("categoryLst", categoryLst);
		// Page Active
		model.addAttribute("pageActive", "shop");
		return "/client/shop";
	}

	@RequestMapping("sort-by-price-product")
	public String sortByPrice(Model model, @RequestParam("product-header-sort") String sortValue,
			@RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productDAO.findByPriceSortASC(pageable);
		if (sortValue.equalsIgnoreCase("ASC")) {
			System.out.println("ASC");
			page = productDAO.findByPriceSortASC(pageable);
		} else if (sortValue.equalsIgnoreCase("DESC")) {
			System.out.println("DESC");
			page = productDAO.findByPriceSortDESC(pageable);
		}
		for (Product product : page) {
			System.out.println(product.getName());
		}
		model.addAttribute("page", page);
		// Category
		List<Category> categoryLst = categoryDAO.findAll();
		model.addAttribute("categoryLst", categoryLst);
		// Page Active
		model.addAttribute("pageActive", "shop");
		return ("client/shop");
	}

	@RequestMapping("shop-search-product-by-price")
	public String searchProductByPrice(Model model, @RequestParam("startPrice") Double startPrice,
			@RequestParam("endPrice") Double endPrice, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productDAO.findByPriceBetween(startPrice, endPrice, pageable);
		model.addAttribute("page", page);
		// Add category list to model for display
		List<Category> categoryLst = categoryDAO.findAll();
		model.addAttribute("categoryLst", categoryLst);
		// Page Active
		model.addAttribute("pageActive", "shop");
		return "/client/shop";
	}

	@RequestMapping("shop-search-product-by-tab")
	public String searchAndPageProductByTab(Model model, @RequestParam("keywords") String kw,
			@RequestParam("p") Optional<Integer> p) {
		// Init Product
		Product item = new Product();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productDAO.findByNameLike("%" + kw + "%", pageable);
		model.addAttribute("page", page);
		// Category
		List<Category> categoryLst = categoryDAO.findAll();
		model.addAttribute("categoryLst", categoryLst);
		// Page Active
		model.addAttribute("pageActive", "shop");
		return "/client/shop";
	}
}
