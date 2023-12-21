package com.poly.rest.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.model.Product;
import com.poly.service.ParamService;
import com.poly.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/admin/products")
@CrossOrigin("*")
public class ProductManagerRestController {
	@Autowired
	ProductServiceImpl productService;
	@Autowired
	ParamService paramService;

	// @GetMapping
	// public List<Product> getAllList() {
	// return productService.findAll();
	// }

	// // GET a single product by ID
	// @GetMapping("/{id}")
	// public ResponseEntity<Product> getProductById(@PathVariable(value = "id")
	// Integer id) {
	// Product product = productService.findById(id);
	// if (product != null) {
	// return ResponseEntity.ok().body(product);
	// } else {
	// return ResponseEntity.notFound().build();
	// }
	// }

	// // POST create a new product
	// @PostMapping
	// @ResponseBody
	// public Product createProduct(@RequestBody Product product) {
	// System.out.println("Hello");
	// System.out.println(product);
	// return productService.save(product);
	// }

	// // PUT update an existing product
	// @PutMapping("/update/{id}")
	// public ResponseEntity<Product> updateProduct(
	// @PathVariable(value = "id") Integer id,
	// @RequestBody Product productDetails) {
	// Product product = productService.findById(id);
	// if (product != null) {
	// Product existingProduct = product;
	// existingProduct.setName(productDetails.getName());
	// existingProduct.setImage(productDetails.getImage());
	// existingProduct.setPrice(productDetails.getPrice());
	// existingProduct.setQuantity(productDetails.getQuantity());
	// existingProduct.setCategory(productDetails.getCategory());
	// // Update other fields and relationships as needed

	// return ResponseEntity.ok().body(productService.save(existingProduct));
	// } else {
	// return ResponseEntity.notFound().build();
	// }
	// }

	@GetMapping("")
	@ResponseBody
	// @CrossOrigin(origins = "http://localhost:3000/volt-react-dashboard#/product")
	public List<Product> getAll() {
		return productService.findAll();
	}

	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<Product> add(@RequestPart("product") String productStr,
			@RequestPart("file") MultipartFile file) {
		ObjectMapper mapper = new ObjectMapper();
		File newFile = null;
		if (!file.isEmpty()) {
			newFile = paramService.save(file, "/img/product");
		}
		Product newProduct = new Product();
		try {
			newProduct = mapper.readValue(productStr, Product.class);

			System.out.println(newProduct.getCategory().getName());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.noContent().build();

		}
		// return ResponseEntity.ok(new Product());
		return ResponseEntity.ok(productService.save(newProduct));
	}

	@PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<Product> update(@RequestPart("product") String productStr,
			@RequestPart("file") MultipartFile file) {
		ObjectMapper mapper = new ObjectMapper();
		File newFile = null;
		if (!file.isEmpty()) {
			newFile = paramService.save(file, "/img/product");
		}
		Product newProduct = new Product();
		try {
			newProduct = mapper.readValue(productStr, Product.class);

			System.out.println(newProduct.getCategory().getName());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.noContent().build();

		}
		// return ResponseEntity.ok(new Product());
		return ResponseEntity.ok(productService.save(newProduct));
	}

	@PutMapping(value = "/noneMultipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<Product> updateNoneMultipartFile(@RequestPart("product") String productStr,
			@RequestPart("file") MultipartFile oldFile) {
		ObjectMapper mapper = new ObjectMapper();

		File newFile = null;
		if (!oldFile.isEmpty()) {
			newFile = paramService.save(oldFile, "/img/product");
		}
		Product newProduct = new Product();
		try {
			newProduct = mapper.readValue(productStr, Product.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.noContent().build();

		}
		// return ResponseEntity.ok(new Product());
		return ResponseEntity.ok(productService.save(newProduct));
	}

//	@PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	@ResponseBody
//	public ResponseEntity<Product> updateProduct(@RequestPart("product") String productStr,
//			@RequestPart("file") MultipartFile file) {
//		
//		System.out.println(productStr);
//		
//		ObjectMapper mapper = new ObjectMapper();
//		File newFile = null;
//		if (!file.isEmpty()) {
//			newFile = paramService.save(file, "/img/product");
//		}
//		Product newProduct = new Product();
//		try {
//			newProduct = mapper.readValue(productStr, Product.class);
//
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//			return ResponseEntity.noContent().build();
//		}
//		return ResponseEntity.ok(productService.save(newProduct));
//	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
		Product product = productService.findById(id);
		try {
			product.setAvailable(updatedProduct.getAvailable());
			productService.save(product);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().build();
	}
}
