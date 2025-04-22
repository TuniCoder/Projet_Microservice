package com.espirt.microservice.productscategories.repository;

import com.espirt.microservice.productscategories.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<Category, String> {
}
