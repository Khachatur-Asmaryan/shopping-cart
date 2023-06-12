package com.shoppingcart.services.interfaces;

import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.models.Product;

import java.util.List;

public interface ProductService {

    Product getById(int id) throws NotFoundException;

    Product getByType(String type) throws NotFoundException;

    List<Product> getByNameAndDescription(String name, String description) throws NotFoundException;

    List<Product> findAllSortedByLowerPrice();

    List<Product> getAll();

    void save(Product product);

    void update(Product product);

    void delete(int id);
}
