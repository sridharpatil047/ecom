package me.sridharpatil.ecom.productservice.services;

import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.repositories.ProductRepository;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductRequestDto requestDto) {
        return null;
    }

    @Override
    public Product getProductById(Long productId) {
        return null;
    }

    @Override
    public Product updateProduct(Long productId, ProductRequestDto requestDto) {
        return null;
    }

    @Override
    public Product updateProductPartial(Long productId, ProductRequestDto requestDto) {
        return null;
    }

    @Override
    public Product deleteProductById(Long productId) {
        return null;
    }
}
