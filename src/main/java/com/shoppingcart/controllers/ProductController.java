package com.shoppingcart.controllers;

import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.entities.Product;
import com.shoppingcart.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/byType")
    public ResponseEntity<Product> getByType(@RequestParam String type) throws NotFoundException {
        return ResponseEntity.ok(productService.getByType(type));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/ByNameAndDescription")
    public ResponseEntity<List<Product>> getByNameAndDescription(@RequestParam String name, @RequestParam String description) throws NotFoundException {
        return ResponseEntity.ok(productService.getByNameAndDescription(name, description));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/sortedPrice")
    public ResponseEntity<List<Product>> findAllSortedByLowerPrice() {
        return ResponseEntity.ok(productService.findAllSortedByLowerPrice());
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Product product) {
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Product product) {
        productService.update(product);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
