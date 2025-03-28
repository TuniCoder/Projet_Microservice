package com.espirt.microservice.productscategories.services.serv;


import com.espirt.microservice.productscategories.entity.Category;
import com.espirt.microservice.productscategories.repository.CategoriesRepo;
import com.espirt.microservice.productscategories.repository.ProductsRepo;
import com.espirt.microservice.productscategories.services.Interfaces.IserviceCategory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceCategoryImpl implements IserviceCategory {
    ProductsRepo productsRepo;
    CategoriesRepo categoriesRepo;


    @Override
    public Category addCategory(Category category) {
        return categoriesRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category , Long id) {
        Category category1 = categoriesRepo.findById(id).orElse(null);
        if (category1 == null) {
            log.info("Category with ID {} not found", id);
            return null;
        }
        category1.setName(category.getName());
        category1.setDescription(category.getDescription());
        return categoriesRepo.save(category1);

    }

    @Override
    public void deleteCategory(Long id) {
        categoriesRepo.deleteById(id);

    }

    @Override
    public Category getCategoryById(Long id) {
        return categoriesRepo.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoriesRepo.findAll();
    }
}
