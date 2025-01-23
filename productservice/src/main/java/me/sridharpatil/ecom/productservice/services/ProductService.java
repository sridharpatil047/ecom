package me.sridharpatil.ecom.productservice.services;

import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import me.sridharpatil.ecom.productservice.models.Product;

import java.util.List;

public interface ProductService {
    // Create
    Product createProduct(ProductRequestDto requestDto) throws CategoryNotFoundException, ProductNotFoundException;

    // Read
    Product getProductById(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    // Update
    Product updateProduct(Long productId, ProductRequestDto requestDto) throws ProductNotFoundException, CategoryNotFoundException;
    Product updateProductPartial(Long productId, ProductRequestDto requestDto) throws ProductNotFoundException, CategoryNotFoundException;

    // Delete
    Product deleteProductById(Long productId) throws ProductNotFoundException;
}
