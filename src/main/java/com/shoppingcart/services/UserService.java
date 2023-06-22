package com.shoppingcart.services;

import com.shoppingcart.entities.User;
import com.shoppingcart.exceptions.DuplicateDataException;

import java.util.Optional;

/**
 * The UserService interface provides methods for managing users.
 *
 * @author Khachatur Asmaryan
 * @date 2023-06-21
 */
public interface UserService {

    /**
     * Saves a new user with the specified details.
     *
     * @param user    the user to be saved
     * @param isAdmin true if the user is an admin, false otherwise
     * @throws DuplicateDataException if a user with the same email already exists
     */
    void save(User user, boolean isAdmin) throws DuplicateDataException;

    /**
     * Retrieves the user with the specified email.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, or an empty Optional if not found
     */
    Optional<User> getByEmail(String email);
}
