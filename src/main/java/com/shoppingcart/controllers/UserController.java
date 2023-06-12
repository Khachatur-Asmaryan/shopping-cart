package com.shoppingcart.controllers;

import com.shoppingcart.exceptions.DuplicateDataException;
import com.shoppingcart.models.User;
import com.shoppingcart.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createAdmin")
    public ResponseEntity<Void> createAdmin(@RequestBody User user) throws DuplicateDataException {
        userService.save(user, true);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<Void> createCustomer(@RequestBody User user) throws DuplicateDataException {
        userService.save(user, false);
        return ResponseEntity.ok().build();
    }
}
