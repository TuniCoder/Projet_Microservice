package com.espirt.microservice.productscategories.services.Interfaces;

import com.espirt.microservice.productscategories.entity.Category;

import java.util.List;

public interface IserviceCategory {
    Category addCategory(Category category);
    Category updateCategory(Category category , Long id);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();

}
