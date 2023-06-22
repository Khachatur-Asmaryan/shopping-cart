package com.shoppingcart.controllers;

import com.shoppingcart.dto.request.AuthenticationRequest;
import com.shoppingcart.dto.request.ReAuthenticationRequest;
import com.shoppingcart.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-cart/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request,
                                          HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(request, null, httpServletRequest));

    }

    @PostMapping("/re-login")
    public ResponseEntity<?> reAuthenticate(@Valid @RequestBody ReAuthenticationRequest request,
                                            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(null, request.getRefreshToken(), httpServletRequest));

    }
}
