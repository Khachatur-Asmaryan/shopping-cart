package com.shoppingcart.services.impl;

import com.shoppingcart.entities.Authority;
import com.shoppingcart.entities.User;
import com.shoppingcart.exceptions.DuplicateDataException;
import com.shoppingcart.repositories.UserRepository;
import com.shoppingcart.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

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
