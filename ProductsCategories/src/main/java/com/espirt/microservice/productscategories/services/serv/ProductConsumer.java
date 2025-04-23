package com.espirt.microservice.productscategories.services.serv;

import com.espirt.microservice.productscategories.entity.Products;
import com.espirt.microservice.productscategories.repository.ProductsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductConsumer {

    @Autowired
    private ProductsRepo productsRepo;

    @RabbitListener(queues = "panierQueue")
    public String receiveProductId(String productId) {
        System.out.println("Received product ID from panier: " + productId);

        Products product = productsRepo.findById(productId).orElse(null);

        if (product != null) {
            try {
                Map<String, String> productMap = new HashMap<>();
                productMap.put("id", product.getId());
                productMap.put("name", product.getName());
                productMap.put("price", product.getPrice().toString());

                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(productMap); // 🔁 JSON
            } catch (Exception e) {
                e.printStackTrace();
                return "{\"error\":\"Failed to serialize product\"}";
            }
        } else {
            return "{\"error\":\"Product not found\"}";
        }
    }
}
