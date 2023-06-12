package com.shoppingcart.dto.request;

import com.shoppingcart.enums.OrdersStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrdersUpdateRequest {

    @NotNull
    private OrdersStatus status;
}
