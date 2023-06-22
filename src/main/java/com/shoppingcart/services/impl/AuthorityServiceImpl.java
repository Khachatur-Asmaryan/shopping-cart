package com.shoppingcart.services.impl;

import com.shoppingcart.exceptions.BadRequestException;
import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.entities.Authority;
import com.shoppingcart.repositories.AuthorityRepository;
import com.shoppingcart.services.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing authorities.
 * <p>
 * This service class provides functionality to retrieve, save, update, and delete authorities.
 * for managing authorities. It interacts with the AuthorityRepository to perform CRUD operations on authorities.
 */
@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    /**
     * Retrieves an authority by its ID.
     *
     * @param id the ID of the authority to retrieve
     * @return the authority with the specified ID
     * @throws NotFoundException if the authority with the given ID does not exist
     */
    @Override
    public Authority getById(int id) throws NotFoundException {
        return authorityRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find Authority with current ID: " + id));
    }

    /**
     * Retrieves all authorities.
     *
     * @return a list of all authorities
     */
    @Override
    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }

    /**
     * Saves a new authority.
     *
     * @param authority the authority to be saved
     */
    @Transactional
    @Override
    public void save(Authority authority) throws BadRequestException {
        authorityRepository.save(authority);
    }

    /**
     * Updates an existing authority.
     *
     * @param authority the authority to be updated
     */
    @Transactional
    @Override
    public void update(Authority authority) {
        authorityRepository.save(authority);
    }

    /**
     * Deletes an authority by its ID.
     *
     * @param id the ID of the authority to be deleted
     */
    @Transactional
    @Override
    public void delete(int id) {
        authorityRepository.deleteById(id);
    }
}
