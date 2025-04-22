    package com.espirt.microservice.productscategories.restController;

    import com.espirt.microservice.productscategories.entity.Products;
    import com.espirt.microservice.productscategories.services.serv.ServiceProductsImpl;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import lombok.AllArgsConstructor;
    import org.springframework.http.*;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.client.RestTemplate;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.List;
    import java.util.Map;
    import java.util.UUID;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/api/prod/products")
    @CrossOrigin(origins = "http://localhost:4200")
    public class RestProductsController {
        private final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";
        private final String API_KEY = "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";

        ServiceProductsImpl serviceProducts;

        @PostMapping("/")
        public Products addProduct(@RequestBody Products product) {
            return serviceProducts.addProduct(product);
        }

        @GetMapping("/")
        public List<Products> getAllProducts() {
            return serviceProducts.getAllProducts();
        }

        @GetMapping("/{id}")
        public Products getProductById(@PathVariable String id) {
            return serviceProducts.getProductById(id);
        }

        @PutMapping("/{id}")
        public Products updateProduct(@RequestBody Products product, @PathVariable String id) {
            return serviceProducts.updateProduct(product, id);
        }

        @DeleteMapping("/{id}")
        public void deleteProduct(@PathVariable String id) {
            serviceProducts.deleteProduct(id);
        }

        @GetMapping("/category/{id}")
        public List<Products> getProductsByCategory(@PathVariable String id) {
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
            @PostMapping("/message")
        public ResponseEntity<String> forwardToOpenRouter(@RequestBody Map<String, Object> body) {
            // Optional: Extract content manually if needed
            var messages = (java.util.List<Map<String, String>>) body.get("messages");
            String content = messages.get(0).get("content");

            // Print or log extracted content
            System.out.println("User said: " + content);

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_KEY);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            // Send to OpenRouter
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(OPENROUTER_URL, entity, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }

        @PostMapping("/with-image")
        public ResponseEntity<Products> addProductWithImage(
                @RequestParam("product") String productJson,
                @RequestParam("file") MultipartFile file) {
            try {
                // Convertir JSON en objet Products
                ObjectMapper objectMapper = new ObjectMapper();
                Products product = objectMapper.readValue(productJson, Products.class);

                // Sauvegarder l'image
                String uploadDir = "uploads/";
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());

                // Enregistrer le nom de fichier dans le produit
                product.setImageUrl(fileName); // tu dois avoir un champ `imagePath` dans l'entité Products

                // Enregistrer le produit
                Products savedProduct = serviceProducts.addProduct(product);
                return ResponseEntity.ok(savedProduct);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
        }
        @PutMapping("/with-image/{id}")
        public ResponseEntity<Products> updateProductWithImage(
                @PathVariable("id") String id,
                @RequestParam("product") String productJson,
                @RequestParam(value = "file", required = false) MultipartFile file) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Products updatedProduct = objectMapper.readValue(productJson, Products.class);

                // Retrieve existing product
                Products existingProduct = serviceProducts.getProductById(id);
                if (existingProduct == null) {
                    return ResponseEntity.notFound().build();
                }

                // Handle image upload if a new image is provided
                if (file != null && !file.isEmpty()) {
                    String uploadDir = "uploads/";
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path path = Paths.get(uploadDir + fileName);
                    Files.createDirectories(path.getParent());
                    Files.write(path, file.getBytes());

                    // Set the new image URL
                    updatedProduct.setImageUrl(fileName);
                } else {
                    // Retain the existing image URL if no new file provided
                    updatedProduct.setImageUrl(existingProduct.getImageUrl());
                }

                // Ensure the product ID remains consistent
                updatedProduct.setId(id);

                Products savedProduct = serviceProducts.updateProduct(updatedProduct, id);
                return ResponseEntity.ok(savedProduct);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

    }
