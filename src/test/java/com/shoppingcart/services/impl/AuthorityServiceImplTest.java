package com.shoppingcart.services.impl;

import com.shoppingcart.exceptions.BadRequestException;
import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.entities.Authority;
import com.shoppingcart.repositories.AuthorityRepository;
import com.shoppingcart.services.AuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorityServiceImplTest {
    @Mock
    private AuthorityRepository authorityRepository;

    private AuthorityService authorityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorityService = new AuthorityServiceImpl(authorityRepository);
    }

    @Test
    void getById_existingAuthority_shouldReturnAuthority() throws NotFoundException {
        // Arrange
        int authorityId = 1;
        Authority authority = new Authority();
        authority.setId(authorityId);

        when(authorityRepository.findById(authorityId)).thenReturn(Optional.of(authority));

        // Act
        Authority result = authorityService.getById(authorityId);

        // Assert
        assertNotNull(result);
        assertEquals(authorityId, result.getId());
        verify(authorityRepository, times(1)).findById(authorityId);
    }

    @Test
    void getById_nonExistingAuthority_shouldThrowNotFoundException() {
        // Arrange
        int authorityId = 1;

        when(authorityRepository.findById(authorityId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> authorityService.getById(authorityId));
        verify(authorityRepository, times(1)).findById(authorityId);
    }

    @Test
    void getAll_multipleAuthoritiesExist_shouldReturnListOfAuthorities() {
        // Arrange
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(1));
        authorities.add(new Authority(2));

        when(authorityRepository.findAll()).thenReturn(authorities);

        // Act
        List<Authority> result = authorityService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(authorities.size(), result.size());
        verify(authorityRepository, times(1)).findAll();
    }

    @Test
    void getAll_noAuthoritiesExist_shouldReturnEmptyList() {
        // Arrange
        List<Authority> authorities = new ArrayList<>();

        when(authorityRepository.findAll()).thenReturn(authorities);

        // Act
        List<Authority> result = authorityService.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(authorityRepository, times(1)).findAll();
    }

    @Test
    void save_validAuthority_shouldSaveAuthority() throws BadRequestException {
        // Arrange
        Authority authority = new Authority();

        // Act
        authorityService.save(authority);

        // Assert
        verify(authorityRepository, times(1)).save(authority);
    }

    @Test
    void update_validAuthority_shouldUpdateAuthority() {
        // Arrange
        Authority authority = new Authority();

        // Act
        authorityService.update(authority);

        // Assert
        verify(authorityRepository, times(1)).save(authority);
    }

    @Test
    void delete_existingAuthority_shouldDeleteAuthority() {
        // Arrange
        int authorityId = 1;

        // Act
        authorityService.delete(authorityId);

        // Assert
        verify(authorityRepository, times(1)).deleteById(authorityId);
    }
}