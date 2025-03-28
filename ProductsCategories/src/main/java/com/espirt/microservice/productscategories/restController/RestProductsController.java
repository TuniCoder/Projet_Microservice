package com.espirt.microservice.productscategories.restController;

import com.espirt.microservice.productscategories.entity.Products;
import com.espirt.microservice.productscategories.services.serv.ServiceProductsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/prod/products")
public class RestProductsController {
    ServiceProductsImpl serviceProducts;
    @PostMapping("/")
    public Products addProduct(@RequestBody Products product){
       return serviceProducts.addProduct(product);
    }
    @GetMapping("/")
    public List<Products> getAllProducts(){
        return serviceProducts.getAllProducts();
    }
    @GetMapping("/{id}")
    public Products getProductById(@PathVariable Long id){
        return serviceProducts.getProductById(id);
    }
    @PutMapping("/{id}")
    public Products updateProduct(@RequestBody Products product , @PathVariable Long id){
        return serviceProducts.updateProduct(product , id);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        serviceProducts.deleteProduct(id);
    }
    @GetMapping("/category/{id}")
    public List<Products> getProductsByCategory(@PathVariable Long id){
        return serviceProducts.getProductsByCategory(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProductImage(@RequestParam("file") MultipartFile file) {
        try {
            // Save to local folder
            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            // Create folder if it doesn't exist
            Files.createDirectories(path.getParent());

            // Save file
            Files.write(path, file.getBytes());

            return ResponseEntity.ok("Image uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }



}
