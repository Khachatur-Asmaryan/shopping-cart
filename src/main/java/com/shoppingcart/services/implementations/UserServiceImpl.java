package com.shoppingcart.services.implementations;

import com.shoppingcart.exceptions.DuplicateDataException;
import com.shoppingcart.models.Authority;
import com.shoppingcart.models.User;
import com.shoppingcart.repositories.UserRepository;
import com.shoppingcart.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for managing user-related operations.
 * <p>
 * This service class is responsible for managing user data and performing various operations
 * such as retrieving users by email and saving new users. It interacts with the UserRepository
 * for data access and uses a PasswordEncoder for password encryption and comparison.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the Optional containing the retrieved user, or an empty Optional if the user is not found
     */
    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }


    /**
     * Saves a new user.
     * <p>
     * Please create Authority before saving
     * First Authority must be Admin
     * Second Authority must be Customer
     *
     * @param user the user to be saved
     * @throws DuplicateDataException if a user with the same email already exists
     */
    @Transactional
    @Override
    public void save(User user, boolean isAdmin) throws DuplicateDataException {
        String email = user.getEmail();

        if (userRepository.findByEmail(email) != null) {
            throw new DuplicateDataException(email + " email already exist");
        }

        Authority authority;
        if (isAdmin) {
            authority = new Authority(1);
        } else {
            authority = new Authority(2);
        }
        user.setAuthority(authority);

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        userRepository.save(user);
    }
}
