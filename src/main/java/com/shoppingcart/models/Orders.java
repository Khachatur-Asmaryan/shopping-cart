package com.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingcart.enums.OrdersStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id && status == orders.status && Objects.equals(orderNumber, orders.orderNumber) && Objects.equals(orderData, orders.orderData) && Objects.equals(user, orders.user) && Objects.equals(products, orders.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, orderNumber, orderData, user, products);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", status=" + status +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderData=" + orderData +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
