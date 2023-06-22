package com.shoppingcart.services;

import com.shoppingcart.entities.Product;
import com.shoppingcart.exceptions.NotFoundException;

import java.util.List;

/**
 * @Author Khachatur Asmaryan
 * @date 2023-06-21
 * <p>
 * The ProductService interface provides methods for managing products.
 */
public interface ProductService {

    /**
     * Retrieves the product with the specified ID.
     *
     * @param id the ID of the product
     * @return the product object
     * @throws NotFoundException if the product with the given ID is not found
     */
    Product getById(int id) throws NotFoundException;

    /**
     * Retrieves the product with the specified type.
     *
     * @param type the type of the product
     * @return the product object
     * @throws NotFoundException if the product with the given type is not found
     */
    Product getByType(String type) throws NotFoundException;

    /**
     * Retrieves a list of products that match the given name and description.
     *
     * @param name        the name of the product
     * @param description the description of the product
     * @return a list of products
     * @throws NotFoundException if no products matching the name and description are found
     */
    List<Product> getByNameAndDescription(String name, String description) throws NotFoundException;

    /**
     * Retrieves a list of all products, sorted by lower price.
     *
     * @return a list of products sorted by lower price
     */
    List<Product> findAllSortedByLowerPrice();

    /**
     * Retrieves a list of all products.
     *
     * @return a list of all products
     */
    List<Product> getAll();

    /**
     * Saves a new product.
     *
     * @param product the product to be saved
     */
    void save(Product product);

    /**
     * Updates an existing product.
     *
     * @param product the updated product
     */
    void update(Product product);

    /**
     * Deletes the product with the specified ID.
     *
     * @param id the ID of the product to be deleted
     */
    void delete(int id);
}
