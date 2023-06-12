package com.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && countInStock == product.countInStock && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(type, product.type) && Objects.equals(createdDate, product.createdDate) && Objects.equals(updatedDate, product.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, type, price, countInStock, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", countInStock=" + countInStock +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
