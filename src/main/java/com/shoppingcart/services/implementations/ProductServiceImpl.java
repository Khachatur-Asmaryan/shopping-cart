package com.shoppingcart.services.implementations;

import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.models.Product;
import com.shoppingcart.repositories.ProductRepository;
import com.shoppingcart.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for managing products.
 * <p>
 * This class provides methods for retrieving, creating, updating, and deleting products.
 * It interacts with the underlying persistence layer through the {@link ProductRepository} interface.
 * The service methods handle the business logic and validation related to products.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the product with the specified ID
     * @throws NotFoundException if the product with the given ID does not exist
     */
    @Override
    public Product getById(int id) throws NotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find Product with current id: " + id));
    }

    /**
     * Retrieves a product by its type.
     *
     * @param type the type of the product to retrieve
     * @return the product with the specified type
     * @throws NotFoundException if the product with the given type does not exist
     */
    @Override
    public Product getByType(String type) throws NotFoundException {
        return productRepository.getByType(type)
                .orElseThrow(() -> new NotFoundException("Could not find Product with current type: " + type));
    }

    /**
     * Retrieves a list of products based on the provided name and description.
     *
     * @param name        The name of the product to search for.
     * @param description The description of the product to search for.
     * @return A list of products that match the given name and description.
     * @throws NotFoundException If no product is found with the given name and description.
     */
    @Override
    public List<Product> getByNameAndDescription(String name, String description) throws NotFoundException {
        List<Product> productList = productRepository.getByNameAndDescription(name, description);
        if (productList.isEmpty()) {
            throw new NotFoundException
                    ("Could not find Product with current name : " + name
                            + " and description: " + description);
        }
        return productList;
    }

    /**
     * Retrieves all products sorted by lower price.
     *
     * @return a list of products sorted by lower price
     */
    @Override
    public List<Product> findAllSortedByLowerPrice() {
        return productRepository.findAllSortedByLowerPrice();
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Saves a new product.
     *
     * @param product the product to be saved
     */
    @Transactional
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    /**
     * Updates an existing product.
     *
     * @param product the product to be updated
     */
    @Transactional
    @Override
    public void update(Product product) {
        product.setUpdatedDate(LocalDate.now());
        productRepository.save(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     */
    @Transactional
    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
