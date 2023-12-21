package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.Cart;
import com.poly.model.CartDetail;
import com.poly.model.Product;
import com.poly.model.Review;
import com.poly.repository.CartDAO;
import com.poly.repository.CartDetailDAO;
import com.poly.repository.ProductDAO;
import com.poly.repository.ReviewDAO;
import com.poly.service.ProductService;
import com.poly.service.SessionService;

@Controller
@RequestMapping("shop/product-detail")
public class ShopDetailController {
	@Autowired
	ProductDAO productDao;
	@Autowired
	SessionService sessionService;
	CartDetailDAO cartDetailDAO;
	@Autowired
	CartDAO cartDAO;

	@Autowired
	ProductService productService;

	@GetMapping("")
	public String index(Model model, @RequestParam("id") Integer productId) {
		Product product = productService.findById(productId);
		List<Review> reviews = product.getReviews();
		List<Product> product_similars = productService.findByProductCategogy(product.getCategory().getId(),
				product.getId());
		float sum_Rating = 0;
		
		for (Review review : reviews) {
			sum_Rating += review.getRating();
		}
		float count_Rating = sum_Rating / reviews.size();
		float rounded_rating = Math.round(count_Rating);
		model.addAttribute("product", product);
		model.addAttribute("productQuanity", sessionService.get("selectedQuantity"));
		sessionService.remove("selectedQuantity");
		model.addAttribute("count_rating", rounded_rating);
		model.addAttribute("reviews", reviews);
		model.addAttribute("product_similars", product_similars);
		model.addAttribute("pageActive", "shop");

		return "client/shop-details";
	}
}
