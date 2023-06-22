package com.shoppingcart.services.impl;

import com.shoppingcart.dto.request.OrdersUpdateRequest;
import com.shoppingcart.enums.OrdersStatus;
import com.shoppingcart.entities.Orders;
import com.shoppingcart.repositories.OrdersRepository;
import com.shoppingcart.services.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrdersServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    private OrdersService ordersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ordersService = new OrdersServiceImpl(ordersRepository);
    }

    @Test
    void save_newOrder_shouldSetStatusToPendingAndSaveOrder() {
        // Arrange
        Orders orders = new Orders();

        // Act
        ordersService.save(orders);

        // Assert
        assertEquals(OrdersStatus.PENDING, orders.getStatus());
        verify(ordersRepository, times(1)).save(orders);
    }

    @Test
    void updateOrdersByDTO_validOrderId_shouldUpdateOrderStatus() {
        // Arrange
        int orderId = 1;
        OrdersUpdateRequest ordersDTO = new OrdersUpdateRequest();
        ordersDTO.setStatus(OrdersStatus.PROCESSING);

        // Act
        ordersService.updateOrdersByDTO(orderId, ordersDTO);

        // Assert
        verify(ordersRepository, times(1)).updateDTO(orderId, ordersDTO.getStatus());
    }
}