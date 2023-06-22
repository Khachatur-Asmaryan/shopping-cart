package com.shoppingcart.services.impl;

import com.shoppingcart.dto.request.OrdersUpdateRequest;
import com.shoppingcart.entities.Orders;
import com.shoppingcart.enums.OrdersStatus;
import com.shoppingcart.repositories.OrdersRepository;
import com.shoppingcart.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    @Transactional
    @Override
    public void save(Orders orders) {
        orders.setStatus(OrdersStatus.PENDING);

        String orderNumber = this.createOrderNumber();
        orders.setOrderNumber(orderNumber);

        ordersRepository.save(orders);
    }

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
