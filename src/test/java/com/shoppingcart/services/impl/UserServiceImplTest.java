package com.shoppingcart.services.impl;

import com.shoppingcart.exceptions.DuplicateDataException;
import com.shoppingcart.entities.User;
import com.shoppingcart.repositories.UserRepository;
import com.shoppingcart.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void getByEmail_existingEmail_shouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User();
        when(userRepository.getByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userService.getByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository, times(1)).getByEmail(email);
    }

    @Test
    void getByEmail_nonExistingEmail_shouldReturnEmptyOptional() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.getByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getByEmail(email);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).getByEmail(email);
    }

    @Test
    public void save_validUserAndIsAdmin_shouldSaveUser() throws DuplicateDataException {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.save(user, true);

        // Assert
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void save_userWithEmailAlreadyExists_shouldThrowDuplicateDataException() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Act and Assert
        assertThrows(DuplicateDataException.class, () -> userService.save(user, false));
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}