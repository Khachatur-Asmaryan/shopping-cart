package com.shoppingcart.services.interfaces;

import com.shoppingcart.dto.request.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface AuthenticationService {

    Map<String, Object> authenticate(AuthenticationRequest request, String token, HttpServletRequest httpServletRequest);
}
