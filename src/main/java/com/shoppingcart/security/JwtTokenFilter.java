package com.shoppingcart.security;

import com.shoppingcart.enums.JwtTokenType;
import com.shoppingcart.exceptions.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Component that filters and handles JWT token authentication for each request.
 * <p>
 * This class extends the {@link OncePerRequestFilter} class, which ensures that the filter
 * is only applied once per request. It intercepts the request, validates and processes the JWT token,
 * and sets the authentication in the security context if the token is valid.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value(value = "${re.login.url}")
    private String reLoginPath;

    /**
     * Component that filters and handles JWT token authentication for each request.
     * <p>
     * This class extends the {@link OncePerRequestFilter} class, which ensures that the filter
     * is only applied once per request. It intercepts the request, validates and processes the JWT token,
     * and sets the authentication in the security context if the token is valid.
     */
    @SneakyThrows
    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) {
        String token = jwtTokenProvider.resolveToken(servletRequest);

        try {
            if (token != null && jwtTokenProvider.validateToken(token, servletRequest)) {

                String tokenType = jwtTokenProvider.getClaimFromToken(token, key -> key.get("tokenType", String.class));

                if (tokenType.equals(JwtTokenType.REFRESH_TOKEN.name())) {
                    String servletPath = (servletRequest).getServletPath();
                    if (!servletPath.equals(reLoginPath))
                        throw new JwtAuthenticationException("Unauthorized", HttpStatus.UNAUTHORIZED);
                }

                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            servletRequest.setAttribute("expired", e.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
