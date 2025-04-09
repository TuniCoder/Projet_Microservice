package com.espirt.microservice.productscategories.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @ManyToOne(cascade = CascadeType.ALL)
    Category category;
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private int stockQuantity;
    private String imageUrl;
    private String brand;
    private String sku;
    private Double discountPercentage;
    private Boolean isActive = true;

    @PrePersist
    public void generateId() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }

}
