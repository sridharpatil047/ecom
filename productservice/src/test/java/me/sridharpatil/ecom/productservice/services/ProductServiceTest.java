package me.sridharpatil.ecom.productservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.repositories.ProductRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void test() throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException {

        // Assign - Mocking
        Product expectedProduct = new Product();
        expectedProduct.setId(191L);
        when(productRepository.findById(191L))
                .thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.getProductById(191L);

        // Assert
        assertEquals(expectedProduct.getId(), actualProduct.getId());

    }
}