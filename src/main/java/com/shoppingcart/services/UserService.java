package com.shoppingcart.services;

import com.shoppingcart.exceptions.DuplicateDataException;
import com.shoppingcart.entities.User;

import java.util.Optional;

public interface UserService {
    void save(User user, boolean isAdmin) throws DuplicateDataException;

    Optional<User> getByEmail(String email);
}
