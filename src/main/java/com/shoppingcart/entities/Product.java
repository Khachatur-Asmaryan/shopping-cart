package com.shoppingcart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int countInStock;

    @JsonIgnore
    private LocalDate createdDate;

    @JsonIgnore
    private LocalDate updatedDate;

    public Product() {
        createdDate = LocalDate.now();
    }

    public Product(int productId, String test_product) {
    }
}
