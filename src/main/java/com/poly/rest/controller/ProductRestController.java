package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Product;
import com.poly.service.ProductService;


@CrossOrigin("*")
@RestController
@RequestMapping("/admin/products")
public class ProductRestController {
    @Autowired
    ProductService productService;

    @RequestMapping
    public List<Product> getAllCategories() {
        return productService.findAll();
    }
}