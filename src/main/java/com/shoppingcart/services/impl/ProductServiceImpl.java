package com.shoppingcart.services.impl;

import com.shoppingcart.entities.Product;
import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.repositories.ProductRepository;
import com.shoppingcart.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getById(int id) throws NotFoundException {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find Product with current id: " + id));
    }

    @Override
    public Product getByType(String type) throws NotFoundException {
        return productRepository.getByType(type)
                .orElseThrow(() -> new NotFoundException("Could not find Product with current type: " + type));
    }

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

    @Override
    public List<Product> findAllSortedByLowerPrice() {
        return productRepository.findAllSortedByLowerPrice();
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }


    @Transactional
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void update(Product product) {
        product.setUpdatedDate(LocalDate.now());
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
