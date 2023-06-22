package com.shoppingcart.services.implementations;

import com.shoppingcart.dto.request.OrdersUpdateRequest;
import com.shoppingcart.enums.OrdersStatus;
import com.shoppingcart.entities.Orders;
import com.shoppingcart.repositories.OrdersRepository;
import com.shoppingcart.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing orders.
 * <p>
 * This class provides methods for saving and updating orders. It interacts with the underlying
 * persistence layer through the {@link OrdersRepository} interface. The service methods handle the
 * business logic and validation related to orders.
 */
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    /**
     * Saves a new order.
     *
     * <p>This method sets the status of the order to {@link OrdersStatus#PENDING} before saving it
     * using the {@link OrdersRepository}. The order is persisted in the underlying database.</p>
     *
     * @param orders the order to be saved
     */
    @Transactional
    @Override
    public void save(Orders orders) {
        orders.setStatus(OrdersStatus.PENDING);

        String orderNumber = this.createOrderNumber();
        orders.setOrderNumber(orderNumber);

        ordersRepository.save(orders);
    }

    /**
     * Updates the status of an order using a data transfer object (DTO).
     *
     * <p>This method retrieves the order with the specified ID from the {@link OrdersRepository},
     * updates its status with the value from the DTO, and saves the updated order. The order's
     * status is updated in the underlying database.</p>
     *
     * @param id        the ID of the order to be updated
     * @param ordersDTO the data transfer object containing the new status value
     */
    @Transactional
    @Override
    public void updateOrdersByDTO(int id, OrdersUpdateRequest ordersDTO) {
        ordersRepository.updateDTO(id, ordersDTO.getStatus());
    }

    private String createOrderNumber() {
        String orderNumber;

        do {
            orderNumber = RandomStringUtils.randomAlphanumeric(5);
        } while (ordersRepository.findByOrderNumber(orderNumber) != null);

        return orderNumber;
    }
}
