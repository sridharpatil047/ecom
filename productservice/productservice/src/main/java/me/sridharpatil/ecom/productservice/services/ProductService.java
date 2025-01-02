package me.sridharpatil.ecom.productservice.services;

import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import me.sridharpatil.ecom.productservice.models.Product;

public interface ProductService {
    // Create
    Product createProduct(ProductRequestDto requestDto);

    // Read
    Product getProductById(Long productId);

    // Update
    Product updateProduct(Long productId, ProductRequestDto requestDto);
    Product updateProductPartial(Long productId, ProductRequestDto requestDto);

    // Delete
    Product deleteProductById(Long productId);
}
