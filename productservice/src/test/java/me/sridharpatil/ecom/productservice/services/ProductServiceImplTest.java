package me.sridharpatil.ecom.productservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.producers.ProductProducer;
import me.sridharpatil.ecom.productservice.producers.dtos.ProductProducerDto;
import me.sridharpatil.ecom.productservice.repositories.ProductRepository;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @Mock private CategoryService categoryService;
    @Mock private ProductProducer productProducer;

    @InjectMocks private ProductServiceImpl productService;

    private ProductRequestDto requestDto;
    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        requestDto = ProductRequestDto.builder()
                .productTitle("title")
                .productDescription("description")
                .price(123.0)
                .categoryId(1L)
                .build();

        category = new Category();
        category.setId(1L);
        category.setTitle("title");

        product = new Product();
        product.setId(1L);
        product.setTitle("title");
        product.setDescription("description");
        product.setCategory(category);

    }

    @Test
    void whenValidRequestThenReturnProduct() throws CategoryNotFoundException, JsonProcessingException {
        // Arrange and Mock
        when(categoryService.getCategoryById(requestDto.getCategoryId())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = productService.createProduct(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(product.getTitle(), result.getTitle());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getCategory().getId(), result.getCategory().getId());
    }

    @Test
    void whenInvalidCategoryIdThenThrowCategoryNotFoundException() throws CategoryNotFoundException, JsonProcessingException {
        // Arrange and Mock
        when(categoryService.getCategoryById(requestDto.getCategoryId())).thenThrow(CategoryNotFoundException.class);

        // Act and Assert
        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(requestDto));
    }

    @Test
    void whenKafkaMessageFailsThenThrowJsonProcessingException() throws CategoryNotFoundException, JsonProcessingException {
        // Arrange and Mock
        when(categoryService.getCategoryById(requestDto.getCategoryId())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        doThrow(JsonProcessingException.class).when(productProducer).productCreated(any(ProductProducerDto.class));

        assertThrows(JsonProcessingException.class, () -> productService.createProduct(requestDto));

    }

}