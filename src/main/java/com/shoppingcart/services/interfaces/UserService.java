package com.shoppingcart.services.interfaces;

import com.shoppingcart.exceptions.DuplicateDataException;
import com.shoppingcart.models.User;

import java.util.Optional;

public interface UserService {
    void save(User user, boolean isAdmin) throws DuplicateDataException;

    Optional<User> getByEmail(String email);
}
