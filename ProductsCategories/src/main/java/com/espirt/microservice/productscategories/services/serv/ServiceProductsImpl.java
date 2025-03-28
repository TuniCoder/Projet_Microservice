package com.espirt.microservice.productscategories.services.serv;

import com.espirt.microservice.productscategories.entity.Category;
import com.espirt.microservice.productscategories.entity.Products;
import com.espirt.microservice.productscategories.repository.CategoriesRepo;
import com.espirt.microservice.productscategories.repository.ProductsRepo;
import com.espirt.microservice.productscategories.services.Interfaces.IserviceProduts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceProductsImpl implements IserviceProduts {

    ProductsRepo productsRepo;
    CategoriesRepo categoriesRepo;
    @Override
    public Products addProduct(Products product) {
        return productsRepo.save(product);
    }

    @Override
    public Products updateProduct(Products product, Long id) {
        Products existingProduct = productsRepo.findById(id).orElse(null);

        if (existingProduct == null) {
            log.info("Product with ID {} not found", id);
            return null;
        }
        Category category = categoriesRepo.findById(product.getCategory().getId()).orElse(null);
        log.info("Category: {}", category);
        if (category != null) {
            existingProduct.setCategory(category);
        }

        // Mettre à jour uniquement les champs nécessaires
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setSku(product.getSku());
        existingProduct.setDiscountPercentage(product.getDiscountPercentage());
        existingProduct.setIsActive(product.getIsActive());


        // Sauvegarder et retourner l'objet mis à jour
        return productsRepo.save(existingProduct);
    }


    @Override
    public void deleteProduct(Long id) {
        productsRepo.deleteById(id);

    }

    @Override
    public Products getProductById(Long id) {
        return productsRepo.findById(id).orElse(null);
    }

    @Override
    public List<Products> getAllProducts() {
        return productsRepo.findAll();
    }

    @Override
    public List<Products> getProductsByCategory(Long id) {
        return productsRepo.findByCategoryId(id);
    }
}
