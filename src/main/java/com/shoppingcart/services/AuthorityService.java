package com.shoppingcart.services;

import com.shoppingcart.entities.Authority;
import com.shoppingcart.exceptions.BadRequestException;
import com.shoppingcart.exceptions.NotFoundException;

import java.util.List;

/**
 * @author Khachatur Asmaryan
 * @date 2023-06-21
 * <p>
 * The AuthorityService interface provides methods for managing authorities.
 */
public interface AuthorityService {

    /**
     * Retrieves the authority with the specified ID.
     *
     * @param id the ID of the authority
     * @return the authority object
     * @throws NotFoundException if the authority with the given ID is not found
     */
    Authority getById(int id) throws NotFoundException;

    /**
     * Retrieves a list of all authorities.
     *
     * @return a list of authorities
     */
    List<Authority> getAll();

    /**
     * Saves a new authority.
     *
     * @param authority the authority to be saved
     * @throws BadRequestException if the authority is invalid or already exists
     */
    void save(Authority authority) throws BadRequestException;

    /**
     * Updates an existing authority.
     *
     * @param authority the updated authority
     */
    void update(Authority authority);

    /**
     * Deletes the authority with the specified ID.
     *
     * @param id the ID of the authority to be deleted
     */
    void delete(int id);
}
