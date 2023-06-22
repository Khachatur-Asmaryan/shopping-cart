package com.shoppingcart.services;

import com.shoppingcart.dto.request.OrdersUpdateRequest;
import com.shoppingcart.entities.Orders;

/**
 * @author Khachatur Asmaryan
 * @date 2023-06-21
 * <p>
 * The OrdersService interface provides methods for managing orders.
 */
public interface OrdersService {

    /**
     * Saves a new orders.
     *
     * @param orders the orders to be saved
     */
    void save(Orders orders);

    /**
     * Updates an existing orders based on the provided update request.
     *
     * @param id        the ID of the orders to be updated
     * @param ordersDTO the orders update request containing the updated data
     */
    void updateOrdersByDTO(int id, OrdersUpdateRequest ordersDTO);
}
