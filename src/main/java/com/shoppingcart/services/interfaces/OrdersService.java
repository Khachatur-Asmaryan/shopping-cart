package com.shoppingcart.services.interfaces;

import com.shoppingcart.dto.request.OrdersUpdateRequest;
import com.shoppingcart.models.Orders;

public interface OrdersService {
    void save(Orders orders);

    void updateOrdersByDTO(int id, OrdersUpdateRequest ordersDTO);
}
