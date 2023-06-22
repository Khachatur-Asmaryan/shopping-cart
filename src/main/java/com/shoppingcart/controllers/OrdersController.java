package com.shoppingcart.controllers;

import com.shoppingcart.dto.request.OrdersUpdateRequest;
import com.shoppingcart.entities.Orders;
import com.shoppingcart.services.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/placeOrder")
    public ResponseEntity<Void> save(@RequestBody Orders orders) {
        ordersService.save(orders);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Void> update(@PathVariable(value = "id") int id,
                                       @RequestBody OrdersUpdateRequest ordersUpdateRequest) {
        ordersService.updateOrdersByDTO(id, ordersUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
