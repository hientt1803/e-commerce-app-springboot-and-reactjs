package com.poly.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Category;

public interface CategoryDAO extends JpaRepository<Category, String> {
	@Query("SELECT a FROM Category a WHERE a.id =?1 and a.name LIKE ?2")
	 List<Category> getCategory(String id, String name);
}
