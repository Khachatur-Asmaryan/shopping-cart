package com.shoppingcart.repositories;

import com.shoppingcart.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing authorities.
 * <p>
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on the authorities in the system. It extends the {@link JpaRepository} interface, which provides
 * basic CRUD operations' implementation.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
