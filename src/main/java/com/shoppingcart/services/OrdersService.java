package com.shoppingcart.services;

import com.shoppingcart.dto.request.OrdersUpdateRequest;
import com.shoppingcart.entities.Orders;

public interface OrdersService {
    void save(Orders orders);

    void updateOrdersByDTO(int id, OrdersUpdateRequest ordersDTO);
}
