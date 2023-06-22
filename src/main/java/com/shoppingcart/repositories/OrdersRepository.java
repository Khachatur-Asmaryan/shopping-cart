package com.shoppingcart.repositories;

import com.shoppingcart.enums.OrdersStatus;
import com.shoppingcart.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing orders.
 * <p>
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on the orders in the system. It extends the {@link JpaRepository} interface, which provides
 * basic CRUD operations' implementation.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    /**
     * Update the status of an order.
     *
     * @param ordersId the ID of the order
     * @param status   the new status for the order
     */
    @Modifying
    @Query("UPDATE Orders o SET o.status = ?2 WHERE o.id = ?1")
    void updateDTO(int ordersId, OrdersStatus status);

    /**
     * Retrieve an order by its order number.
     *
     * @param orderNumber the order number
     * @return the order with the specified order number, or null if not found
     */
    Orders findByOrderNumber(String orderNumber);
}
