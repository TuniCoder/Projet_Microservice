package com.espirt.microservice.productscategories.services.Interfaces;

import com.espirt.microservice.productscategories.entity.Products;

import java.util.List;

public interface IserviceProduts {

    Products addProduct(Products product);
    Products updateProduct(Products product, Long id);
    void deleteProduct(Long id);
    Products getProductById(Long id);
    List<Products> getAllProducts();
    List<Products> getProductsByCategory(Long id);



}
