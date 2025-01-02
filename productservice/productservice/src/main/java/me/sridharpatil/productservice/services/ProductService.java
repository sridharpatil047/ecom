package me.sridharpatil.productservice.services;

import me.sridharpatil.productservice.models.Product;
import me.sridharpatil.productservice.services.dtos.ProductRequestDto;

public interface ProductService {
    // Create
    Product createProduct(ProductRequestDto productRequestDto);

    // Read
    Product getProductById(Long productId);

    // Update
    Product updateProduct(Long productId, ProductRequestDto productRequestDto);
    Product updateProductPartial(Long productId, ProductRequestDto productRequestDto);

    // Delete
    Product deleteProductById(Long productId);
}
