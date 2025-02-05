package me.sridharpatil.ecom.productservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.producers.ProductProducer;
import me.sridharpatil.ecom.productservice.producers.dtos.ProductProducerDto;
import me.sridharpatil.ecom.productservice.repositories.ProductRepository;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;
    CategoryService categoryService;
    ProductProducer productProducer;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ProductProducer productProducer) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productProducer = productProducer;
    }

    @Override
    public Product createProduct(ProductRequestDto requestDto) throws CategoryNotFoundException, JsonProcessingException {
        // Check if category for the product exists
        log.debug("Checking if category with id {} exists", requestDto.getCategoryId());
        Category category = categoryService.getCategoryById(requestDto.getCategoryId());

        // Create a new product
        Product product = new Product();
        product.setTitle(requestDto.getProductTitle());
        product.setDescription(requestDto.getProductDescription());
        product.setPrice(requestDto.getPrice());
        product.setCategory(category);

        // Save and return the product
        log.info("Saving product with title : {}", product.getTitle());
        Product product1 = productRepository.save(product);

        // Produce message to Kafka
        log.info("Producing message to Kafka");
        productProducer.productCreated(ProductProducerDto.of(product1));

        return product1;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {

        // Check if product with given id exists, if not throw exception
        log.debug("Checking if product with id {} exists", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty() || optionalProduct.get().isDeleted()) {
            log.error("Product with id {} not found", productId);
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }

        // Since product with given id exists, return the product
        log.info("Returning product with id : {}", productId);

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        log.debug("Getting all products");
        return productRepository.findByDeletedFalse();
    }

    @Override
    public Product updateProduct(Long productId, ProductRequestDto requestDto) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException {
        // Check if product with given id exists, if not throw exception
        log.debug("Checking if product with id {} exists", productId);
        Product product = getProductById(productId);
        Category category = categoryService.getCategoryById(requestDto.getCategoryId());

        // Since product with given id exists, update the product
        log.debug("Updating product with id : {}", productId);
        product.setTitle(requestDto.getProductTitle());
        product.setDescription(requestDto.getProductDescription());
        product.setPrice(requestDto.getPrice());
        product.setCategory(category);

        // Save and return the product
        log.info("Saving product with id : {}", productId);
        Product product1 = productRepository.save(product);

        // Produce message to Kafka
        log.info("Producing message to Kafka");
        productProducer.productUpdated(ProductProducerDto.of(product1));

        return product1;
    }

    @Override
    public Product updateProductPartial(Long productId, ProductRequestDto requestDto) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException {
        // Check if product with given id exists, if not throw exception
        log.debug("Checking if product with id {} exists", productId);
        Product product = getProductById(productId);


        // Since product with given id exists, update the product partially
        log.debug("Updating product with id : {}", productId);
        if (requestDto.getProductTitle() != null) product.setTitle(requestDto.getProductTitle());
        if (requestDto.getProductDescription() != null) product.setDescription(requestDto.getProductDescription());
        if (requestDto.getPrice() != null) product.setPrice(requestDto.getPrice());
        if (requestDto.getCategoryId() != null){
            Category category = categoryService.getCategoryById(requestDto.getCategoryId());
            product.setCategory(category);
        }

        // Save and return the product
        log.info("Saving product with id : {}", productId);
        Product product1 = productRepository.save(product);

        // Produce message to Kafka
        log.info("Producing message to Kafka");
        productProducer.productUpdated(ProductProducerDto.of(product1));

        return product1;
    }

    @Override
    public Product deleteProductById(Long productId) throws ProductNotFoundException {
        // Check if product with given id exists, if not throw exception
        log.debug("Checking if product with id {} exists", productId);
        Product product = getProductById(productId);

        // Since product with given id exists, delete the product
        log.debug("Deleting product with id : {}", productId);
        product.setDeleted(true);

        // Save and return the product
        log.info("Saving product with id : {}", productId);
        return productRepository.save(product);
    }
}
