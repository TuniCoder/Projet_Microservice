package com.espirt.microservice.productscategories.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
