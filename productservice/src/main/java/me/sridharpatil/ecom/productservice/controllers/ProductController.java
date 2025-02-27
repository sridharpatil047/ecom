package me.sridharpatil.ecom.productservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.controllers.dtos.ProductRequestDto;
import me.sridharpatil.ecom.productservice.controllers.dtos.ProductResponseDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.mappers.ProductMapper;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.services.ProductService;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@Log4j2
public class ProductController {

    ProductService productService;
    ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // 1. GET /products
    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {

        log.debug("Received request to get all products");
        List<ProductResponseDto> resDtoList = new ArrayList<>();

        List<Product> productList = productService.getAllProducts();
        for (Product product : productList) {
            resDtoList.add(ProductResponseDto.of(product));
        }

        log.info("Returning {} products", resDtoList.size());
        // Return response
        return ResponseEntity.ok(resDtoList);
    }

    // 3. POST /products
    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody @Valid ProductRequestDto requestDto
    ) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException {

        log.debug("Received request to create a new product : {}", requestDto.getTitle());


        // Map request to service dto and call product service
        ProductResponseDto responseDto =
                productMapper.mapToProductResponseDto(
                        productService.createProduct(
                                productMapper.mapToProductRequest(requestDto)));

        // Return response
        log.info("Product created with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    // 4. PATCH /products/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductRequestDto requestDto
    ) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException {

        log.debug("Received request to update product by id : {}", id);

        // Map request to service dto
        ProductRequest serviceRequestDto = new ProductRequest();
        serviceRequestDto.setProductTitle(requestDto.getTitle());
        serviceRequestDto.setProductDescription(requestDto.getDescription());
        serviceRequestDto.setPrice(requestDto.getPrice());
        serviceRequestDto.setCategoryId(requestDto.getCategoryId());

        // Update product by id
        ProductResponseDto responseDto =
                ProductResponseDto.of(
                        productService.updateProductPartial(id, serviceRequestDto)
                );

        // Return response
        log.info("Product updated with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    // 5. DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDto> deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {

        log.debug("Received request to delete product by id : {}", id);

        // Delete product by id
        ProductResponseDto responseDto =
                ProductResponseDto.of(
                        productService.deleteProductById(id)
                );

        // Return response
        log.info("Product deleted with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }
}
