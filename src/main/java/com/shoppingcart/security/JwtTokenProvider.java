package com.shoppingcart.security;

import com.shoppingcart.enums.JwtTokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

/**
 * Component that provides JWT token handling and operations.
 * <p>
 * This class implements the Serializable interface to support serialization of the JwtTokenProvider instances.
 * It is responsible for creating JWT tokens, extracting claims from tokens, validating tokens,
 * and resolving tokens from the HTTP request.
 */
@Component
public class JwtTokenProvider implements Serializable {

    @Value("${jwt.access.token.expired}")
    private long ACCESS_TOKEN_VALIDITY;

    @Value("${jwt.refresh.token.expired}")
    private long REFRESH_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String JWT_TOKEN_SECRET;

    /**
     * Initializes the JwtTokenProvider by encoding the JWT_TOKEN_SECRET using Base64 encoding.
     * This method is annotated with @PostConstruct to ensure it is executed after the dependency injection is done.
     */
    @PostConstruct
    protected void init() {
        JWT_TOKEN_SECRET = Base64.getEncoder().encodeToString(JWT_TOKEN_SECRET.getBytes());
    }

    /**
     * Creates a JWT token with the specified user information and token type.
     *
     * @param userId      the ID of the user
     * @param username    the username of the user
     * @param authorityId the ID of the authority
     * @param role        the role of the user
     * @param tokenType   the type of the token (ACCESS_TOKEN or REFRESH_TOKEN)
     * @return the created JWT token
     */
    public String createJwt(int userId, String username, int authorityId, String role, JwtTokenType tokenType) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.put("userId", userId);
        claims.put("authorityId", authorityId);

        Date now = new Date();
        Date validity;

        if (tokenType == JwtTokenType.ACCESS_TOKEN) {
            validity = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY);
        } else {
            validity = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY);
        }

        claims.put("tokenType", tokenType.name());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SECRET)
                .compact();
    }

    /**
     * Retrieves the authentication object from the JWT token.
     *
     * @param token the JWT token
     * @return the authentication object
     */
    public Authentication getAuthentication(String token) {

        String email = getClaimFromToken(token, Claims::getSubject);
        String role = this.getClaimFromToken(token, key -> key.get("role", String.class));

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        grantedAuthorities.add(grantedAuthority);

        UserDetails userDetails = new User(email, "[PROTECTED]", grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Validates the JWT token by checking its expiration.
     *
     * @param token          the JWT token
     * @param servletRequest the HTTP servlet request
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, HttpServletRequest servletRequest) {
        try {
            return this.getClaimFromToken(token, Claims::getExpiration).after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            servletRequest.setAttribute("expired", e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a specific claim from the JWT token.
     *
     * @param token          the JWT token
     * @param claimsResolver the function to resolve the desired claim from the Claims object
     * @param <T>            the type of the claim value
     * @return the value of the specified claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        // Parse the token and retrieve the claims
        final Claims claims =
                Jwts
                        .parser()
                        .setSigningKey(JWT_TOKEN_SECRET)
                        .parseClaimsJws(token)
                        .getBody();
        // Resolve and return the desired claim value using the claimsResolver function
        return claimsResolver.apply(claims);
    }

    /**
     * Resolves the JWT token from the HTTP request.
     *
     * @param request the HTTP servlet request
     * @return the resolved JWT token, or null if not found in the request
     */
    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header == null ? null : header.replaceAll("Bearer", "").strip();
    }
}
