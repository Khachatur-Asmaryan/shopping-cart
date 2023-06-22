package com.shoppingcart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingcart.enums.OrdersStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    private OrdersStatus status;

    @JsonIgnore
    private String orderNumber;

    @JsonIgnore
    private LocalDateTime orderData;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product products;

    public Orders() {
        orderData = LocalDateTime.now();
    }
}
