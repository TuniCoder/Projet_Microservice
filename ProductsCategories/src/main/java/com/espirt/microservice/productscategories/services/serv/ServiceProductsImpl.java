package com.espirt.microservice.productscategories.services.serv;

import com.espirt.microservice.productscategories.entity.Category;
import com.espirt.microservice.productscategories.entity.Products;
import com.espirt.microservice.productscategories.repository.CategoriesRepo;
import com.espirt.microservice.productscategories.repository.ProductsRepo;
import com.espirt.microservice.productscategories.services.Interfaces.IserviceProduts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceProductsImpl implements IserviceProduts {

    ProductsRepo productsRepo;
    CategoriesRepo categoriesRepo;
    private EmailService emailService;

    @Override
    public Products addProduct(Products product) {
        Category category ;
        if(product.getCategory() != null) {
            category = categoriesRepo.findById(product.getCategory().getId()).orElse(null);
            log.info("Category: {}", category);
            if (category != null) {
                product.setCategory(category);
            }
        } else {
            log.warn("Product category is null");
        }
        return productsRepo.save(product);
    }

    @Override
    public Products updateProduct(Products product, String id) {
        Products existingProduct = productsRepo.findById(id).orElse(null);

        if (existingProduct == null) {
            log.info("Product with ID {} not found", id);
            return null;
        }

        // Update category if provided and exists
        if (product.getCategory() != null) {
            Category category = categoriesRepo.findById(product.getCategory().getId()).orElse(null);
            log.info("Category: {}", category);
            if (category != null) {
                existingProduct.setCategory(category);
            }
        }

        // Update fields only if they are not null
        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }

        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }

        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }

        if (product.getStockQuantity() != 0) {  // Assuming stockQuantity should never be 0, or you can adjust the check accordingly
            existingProduct.setStockQuantity(product.getStockQuantity());
        }

        if (product.getImageUrl() != null) {
            existingProduct.setImageUrl(product.getImageUrl());
        }

        if (product.getBrand() != null) {
            existingProduct.setBrand(product.getBrand());
        }

        if (product.getSku() != null) {
            existingProduct.setSku(product.getSku());
        }

        if (product.getDiscountPercentage() != null) {
            existingProduct.setDiscountPercentage(product.getDiscountPercentage());
        }

        if (product.getIsActive() != null) {
            existingProduct.setIsActive(product.getIsActive());
        }

        // Save and return the updated product
        return productsRepo.save(existingProduct);
    }



    @Override
    public void deleteProduct(String id) {
        productsRepo.deleteById(id);

    }

    @Override
    public Products getProductById(String id) {
        return productsRepo.findById(id).orElse(null);
    }

    @Override
    public List<Products> getAllProducts() {
        return productsRepo.findAll();
    }

    @Override
    public List<Products> getProductsByCategory(String id) {
        return productsRepo.findByCategoryId(id);
    }

    @Scheduled(cron = "0 15 10 * * ?", zone = "Africa/Tunis")

    public  void checkStockAndAlert () {
        log.info("Checking stock levels and sending alerts if necessary");
        List<Products> products = productsRepo.findAll();
        String s= "";
        boolean test = false;
        for (Products product : products) {
            if (product.getStockQuantity() < 5) {
                test = true;
                log.warn("Product {} is running low on stock. Current quantity: {}", product.getName(), product.getStockQuantity());
                 s += "Product " + product.getName() + " is running low on stock. Current quantity: " + product.getStockQuantity() + "\n";
            }
        }
        if (test) {
            emailService.sendEmail("saker.hajji@esprit.tn", "Stock Alert", s);
        }

    }
}
