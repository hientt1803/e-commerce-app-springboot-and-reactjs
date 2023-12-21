package com.poly.service;

import java.util.List;

import com.poly.model.Category;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    Category findById(String categoryId);
    
    List<Category> getCategory(String id, String name);
}
