package com.shoppingcart.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * Component that handles the entry point for JWT authentication.
 * <p>
 * This class implements the {@link AuthenticationEntryPoint} interface, which is invoked when
 * an authentication exception occurs during JWT authentication. It sends an unauthorized response
 * with the appropriate error message based on the authentication exception.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Invoked when an authentication exception occurs during JWT authentication.
     *
     * @param request       the HTTP servlet request
     * @param response      the HTTP servlet response
     * @param authException the authentication exception
     * @throws IOException if an I/O error occurs while handling the request
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        final String expired = request.getAttribute("expired") != null ? request.getAttribute("expired").toString() : "";
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Objects.requireNonNullElse(expired, "Unauthorized"));
    }
}
