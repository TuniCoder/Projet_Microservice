package com.espirt.microservice.productscategories.services.Interfaces;

import com.espirt.microservice.productscategories.entity.Category;

import java.util.List;

public interface IserviceCategory {
    Category addCategory(Category category);

    Category updateCategory(Category category, String id);

    void deleteCategory(String id);

    Category getCategoryById(String id);

    List<Category> getAllCategories();

}
