package com.shoppingcart.repositories;

import com.shoppingcart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing users.
 * <p>
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on the users in the system. It extends the {@link JpaRepository} interface, which provides
 * basic CRUD operations' implementation.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieve a user by their email.
     *
     * @param email the email of the user
     * @return an optional containing the user with the specified email, or empty if not found
     */
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> getByEmail(String email);

    /**
     * Retrieve a user by their email.
     *
     * @param email the email of the user
     * @return the user with the specified email, or null if not found
     */
    User findByEmail(String email);
}
