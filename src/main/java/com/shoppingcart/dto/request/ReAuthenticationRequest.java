package com.shoppingcart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReAuthenticationRequest {

    @NotNull
    private String refreshToken;
}
