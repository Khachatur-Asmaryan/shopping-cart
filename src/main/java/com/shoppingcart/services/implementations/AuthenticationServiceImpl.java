package com.shoppingcart.services.implementations;

import com.shoppingcart.dto.request.AuthenticationRequest;
import com.shoppingcart.enums.JwtTokenType;
import com.shoppingcart.exceptions.JwtAuthenticationException;
import com.shoppingcart.models.User;
import com.shoppingcart.security.JwtTokenProvider;
import com.shoppingcart.services.interfaces.AuthenticationService;
import com.shoppingcart.services.interfaces.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service implementation for handling authentication.
 * Implements the {@link AuthenticationService} interface.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates the user based on the provided authentication request and token.
     *
     * @param request            The authentication request containing the user's credentials.
     * @param token              The token for the authenticated user, or null if not authenticated.
     * @param httpServletRequest The HTTP servlet request.
     * @return A map containing the access token and refresh token for the authenticated user.
     * @throws JwtAuthenticationException If the authentication fails or the token is invalid.
     */
    @Override
    public Map<String, Object> authenticate(AuthenticationRequest request, String token, HttpServletRequest httpServletRequest) {
        int userId, authorityId;
        String email, role;

        if (token == null) {
            email = request.getEmail();
            User user = userService
                    .getByEmail(email)
                    .orElseThrow(() -> new JwtAuthenticationException("Incorrect email or password", HttpStatus.UNAUTHORIZED));

            this.checkUserCredentials(user, request.getPassword());

            userId = user.getId();
            authorityId = user.getAuthority().getId();
            role = user.getAuthority().getRole();

        } else if (!jwtTokenProvider.validateToken(token, httpServletRequest) ||
                !jwtTokenProvider.getClaimFromToken(token, key -> key.get("tokenType", String.class)).equals(JwtTokenType.REFRESH_TOKEN.name())) {
            throw new JwtAuthenticationException("Unauthorized", HttpStatus.UNAUTHORIZED);
        } else {
            email = jwtTokenProvider.getClaimFromToken(token, Claims::getSubject);
            userId = jwtTokenProvider.getClaimFromToken(token, key -> key.get("userId", Integer.class));
            role = jwtTokenProvider.getClaimFromToken(token, key -> key.get("role", String.class));
            authorityId = jwtTokenProvider.getClaimFromToken(token, key -> key.get("authorityId", Integer.class));
        }

        String accessToken = jwtTokenProvider.createJwt(userId, email, authorityId, role, JwtTokenType.ACCESS_TOKEN);
        String refreshToken = jwtTokenProvider.createJwt(userId, email, authorityId, role, JwtTokenType.REFRESH_TOKEN);

        return this.createAuthResponse(accessToken, refreshToken);
    }

    /**
     * Checks the user's credentials for validity.
     *
     * @param user     the User object containing the user's details
     * @param password the password to check against the user's stored password
     * @throws JwtAuthenticationException if the user credentials are incorrect
     */
    private void checkUserCredentials(User user, String password) {

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new JwtAuthenticationException("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Creates an authentication response with the provided access token and refresh token.
     *
     * @param accessToken  The access token to include in the response.
     * @param refreshToken The refresh token to include in the response.
     * @return A map containing the access token and refresh token.
     */
    private Map<String, Object> createAuthResponse(String accessToken, String refreshToken) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        return response;
    }
}
