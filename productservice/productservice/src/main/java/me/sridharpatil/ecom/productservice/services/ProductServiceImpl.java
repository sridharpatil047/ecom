package me.sridharpatil.ecom.productservice.services;

import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.repositories.ProductRepository;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;
    CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product createProduct(ProductRequestDto requestDto) throws CategoryNotFoundException {
        // Check if category for the product exists
        Category category = categoryService.getCategoryById(requestDto.getCategoryId());

        // Create a new product
        Product product = new Product();
        product.setTitle(requestDto.getProductTitle());
        product.setDescription(requestDto.getProductDescription());
        product.setPrice(requestDto.getPrice());
        product.setCategory(category);

        // Save and return the product
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {

        // Check if product with given id exists, if not throw exception
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty() || optionalProduct.get().isDeleted()) throw new ProductNotFoundException("Product with id " + productId + " not found");

        // Since product with given id exists, return the product
        return optionalProduct.get();
    }

    @Override
    public Product updateProduct(Long productId, ProductRequestDto requestDto) throws ProductNotFoundException, CategoryNotFoundException {
        // Check if product with given id exists, if not throw exception
        Product product = getProductById(productId);
        Category category = categoryService.getCategoryById(requestDto.getCategoryId());

        // Since product with given id exists, update the product
        product.setTitle(requestDto.getProductTitle());
        product.setDescription(requestDto.getProductDescription());
        product.setPrice(requestDto.getPrice());
        product.setCategory(category);

        // Save and return the product
        return productRepository.save(product);
    }

    @Override
    public Product updateProductPartial(Long productId, ProductRequestDto requestDto) throws ProductNotFoundException, CategoryNotFoundException {
        // Check if product with given id exists, if not throw exception
        Product product = getProductById(productId);


        // Since product with given id exists, update the product partially
        if (requestDto.getProductTitle() != null) product.setTitle(requestDto.getProductTitle());
        if (requestDto.getProductDescription() != null) product.setDescription(requestDto.getProductDescription());
        if (requestDto.getPrice() != null) product.setPrice(requestDto.getPrice());
        if (requestDto.getCategoryId() != null){
            Category category = categoryService.getCategoryById(requestDto.getCategoryId());
            product.setCategory(category);
        }

        // Save and return the product
        return productRepository.save(product);
    }

    @Override
    public Product deleteProductById(Long productId) throws ProductNotFoundException {
        // Check if product with given id exists, if not throw exception
        Product product = getProductById(productId);

        // Since product with given id exists, delete the product
        product.setDeleted(true);

        // Save and return the product
        return productRepository.save(product);
    }
}
