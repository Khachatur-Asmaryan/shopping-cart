package com.shoppingcart.services.implementations;

import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.entities.Product;
import com.shoppingcart.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getById_ValidId_ReturnsProduct() throws NotFoundException, ExecutionException, InterruptedException {
        // Arrange
        int productId = 1;
        Product expectedProduct = new Product(productId, "Test Product");

        CompletableFuture<Optional<Product>> future = new CompletableFuture<>();
        future.complete(Optional.of(expectedProduct));

        when(productRepository.findById(productId)).thenReturn(future.get());

        // Act
        Product actualProduct = productService.getById(productId);

        // Assert
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void getById_InvalidId_ThrowsNotFoundException() throws ExecutionException, InterruptedException {
        // Arrange
        int productId = 1;

        CompletableFuture<Optional<Product>> future = new CompletableFuture<>();
        future.complete(Optional.empty());

        when(productRepository.findById(productId)).thenReturn(future.get());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> productService.getById(productId));
    }

    @Test
    void getByType_existingType_shouldReturnProduct() throws NotFoundException {
        String type = "TypeA";
        Product product = new Product();
        product.setType(type);

        when(productRepository.getByType(type)).thenReturn(Optional.of(product));

        Product result = productService.getByType(type);

        assertEquals(product, result);
        verify(productRepository, times(1)).getByType(type);
    }

    @Test
    void getByType_nonExistingType_shouldThrowNotFoundException() {
        String type = "NonExistingType";

        when(productRepository.getByType(type)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getByType(type));
        verify(productRepository, times(1)).getByType(type);
    }

    @Test
    void getByNameAndDescription_existingNameAndDescription_shouldReturnProductList() throws NotFoundException {
        String name = "ProductA";
        String description = "DescriptionA";
        Product productA = new Product();
        productA.setName(name);
        productA.setDescription(description);
        List<Product> productList = List.of(productA);

        when(productRepository.getByNameAndDescription(name, description)).thenReturn(productList);

        List<Product> result = productService.getByNameAndDescription(name, description);

        assertEquals(productList, result);
        verify(productRepository, times(1)).getByNameAndDescription(name, description);
    }

    @Test
    void getByNameAndDescription_nonExistingNameAndDescription_shouldThrowNotFoundException() {
        String name = "NonExistingName";
        String description = "NonExistingDescription";

        when(productRepository.getByNameAndDescription(name, description)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> productService.getByNameAndDescription(name, description));
        verify(productRepository, times(1)).getByNameAndDescription(name, description);
    }

    @Test
    void findAllSortedByLowerPrice_shouldReturnProductList() {
        List<Product> productList = Arrays.asList(new Product(), new Product());

        when(productRepository.findAllSortedByLowerPrice()).thenReturn(productList);

        List<Product> result = productService.findAllSortedByLowerPrice();

        assertEquals(productList, result);
        verify(productRepository, times(1)).findAllSortedByLowerPrice();
    }

    @Test
    void getAll_shouldReturnProductList() {
        List<Product> productList = Arrays.asList(new Product(), new Product());

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAll();

        assertEquals(productList, result);
        verify(productRepository, times(1)).findAll();
    }


    @Test
    void save_validProduct_shouldSaveProduct() {
        Product product = new Product();

        productService.save(product);

        verify(productRepository, times(1)).save(product);
    }

    @Transactional
    @Test
    void update_existingProduct_shouldUpdateProduct() {
        int productId = 1;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("ProductA");
        existingProduct.setDescription("DescriptionA");

        lenient().when(productRepository.getById(productId)).thenReturn(Optional.of(existingProduct));


        productService.update(existingProduct);

        verify(productRepository, times(1)).save(existingProduct);
        assertEquals(existingProduct.getUpdatedDate(), LocalDate.now()); // Verify updatedDate is set to current time
    }

    @Test
    void delete_existingProductId_shouldDeleteProduct() {
        int productId = 1;

        productService.delete(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}