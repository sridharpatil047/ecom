package me.sridharpatil.ecom.productservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequest;
import me.sridharpatil.ecom.productservice.models.Product;

import java.util.List;

public interface ProductService {
    // Create
    Product createProduct(ProductRequest requestDto) throws CategoryNotFoundException, ProductNotFoundException, JsonProcessingException;

    // Read
    Product getProductById(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    // Update
    Product updateProduct(Long productId, ProductRequest requestDto) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException;
    Product updateProductPartial(Long productId, ProductRequest requestDto) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException;

    // Delete
    Product deleteProductById(Long productId) throws ProductNotFoundException;
}
