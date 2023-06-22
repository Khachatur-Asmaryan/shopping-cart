package com.shoppingcart.repositories;

import com.shoppingcart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing products.
 * <p>
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on the products in the system. It extends the {@link JpaRepository} interface, which provides
 * basic CRUD operations' implementation.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Retrieve a product by its type.
     *
     * @param type the type of the product
     * @return an optional containing the product with the specified type, or empty if not found
     */
    @Query("SELECT p FROM Product p WHERE p.type = ?1")
    Optional<Product> getByType(String type);

    /**
     * Retrieve a product by its ID.
     *
     * @param id the ID of the product
     * @return an optional containing the product with the specified ID, or empty if not found
     */
    @Query("SELECT p FROM Product p WHERE p.id = ?1")
    Optional<Product> getById(int id);

    /**
     * Retrieve products by name and description.
     *
     * @param name        the name of the product
     * @param description the description of the product
     * @return a list of products matching the specified name and description
     */
    @Query("SELECT p FROM Product p WHERE p.name =?1 and p.description = ?2")
    List<Product> getByNameAndDescription(String name, String description);

    /**
     * Retrieve all products sorted by lower price.
     *
     * @return a list of all products sorted by lower price
     */
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> findAllSortedByLowerPrice();
}
