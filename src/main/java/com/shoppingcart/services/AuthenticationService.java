package com.shoppingcart.services;

import com.shoppingcart.dto.request.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Khachatur Asmaryan
 * @date 2023-06-21
 * <p>
 * The AuthenticationService interface provides methods for authentication.
 */
public interface AuthenticationService {

    /**
     * Authenticates the user based on the provided authentication request.
     *
     * @param request            the authentication request containing user credentials
     * @param token              the authentication token
     * @param httpServletRequest the HTTP servlet request object
     * @return a map containing authentication-related information
     */
    Map<String, Object> authenticate(AuthenticationRequest request, String token, HttpServletRequest httpServletRequest);
}
