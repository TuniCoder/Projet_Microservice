package com.espirt.microservice.productscategories.restController;

import com.espirt.microservice.productscategories.entity.Category;
import com.espirt.microservice.productscategories.services.serv.ServiceCategoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/prod/Categories")
public class RestCategoryController {
    ServiceCategoryImpl serviceCategory;
    @PostMapping("/")
    public Category addCategory(@RequestBody Category category){
        return serviceCategory.addCategory(category);
    }
    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody Category category , @PathVariable Long id){
        return serviceCategory.updateCategory(category , id);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        serviceCategory.deleteCategory(id);
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return serviceCategory.getCategoryById(id);
    }
    @GetMapping("/")
    public List<Category> getAllCategories(){
        return serviceCategory.getAllCategories();
    }
}
