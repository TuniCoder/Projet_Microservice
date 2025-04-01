package com.espirt.microservice.productscategories.services.Interfaces;

import com.espirt.microservice.productscategories.entity.Products;

import java.util.List;

public interface IserviceProduts {

    Products addProduct(Products product);

    Products updateProduct(Products product, String id);

    void deleteProduct(String id);

    Products getProductById(String id);

    List<Products> getAllProducts();

    List<Products> getProductsByCategory(String id);


}
