package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Category;
import com.poly.repository.CategoryDAO;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/categories")
public class CategoriesRestController {
    @Autowired
    CategoryDAO dao;

    @RequestMapping
    public List<Category> getAllCategories() {
        return dao.findAll();
    }
}
