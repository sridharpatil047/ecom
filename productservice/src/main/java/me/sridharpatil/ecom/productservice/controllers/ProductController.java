package me.sridharpatil.ecom.productservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductReqDto;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductResDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.services.ProductService;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@Log4j2
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. GET /products
    @GetMapping()
    public ResponseEntity<List<ControllerProductResDto>> getAllProducts() {

        log.debug("Received request to get all products");
        List<ControllerProductResDto> resDtoList = new ArrayList<>();

        List<Product> productList = productService.getAllProducts();
        for (Product product : productList) {
            resDtoList.add(ControllerProductResDto.of(product));
        }

        log.info("Returning {} products", resDtoList.size());
        // Return response
        return ResponseEntity.ok(resDtoList);
    }

    // 2. GET /products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ControllerProductResDto> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {

        log.debug("Received request to get product by id : {}", id);

        // Get product by id
        ControllerProductResDto responseDto =
                ControllerProductResDto.of(
                        productService.getProductById(id)
                );

        // Return response
        log.info("Returning product with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    // 3. POST /products
    @PostMapping()
    public ResponseEntity<ControllerProductResDto> createProduct(
            @RequestBody @Valid ControllerProductReqDto requestDto
    ) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException {

        log.debug("Received request to create a new product : {}", requestDto.getTitle());

        // Map request to service dto
        ProductRequestDto serviceRequestDto = new ProductRequestDto();
        serviceRequestDto.setProductTitle(requestDto.getTitle());
        serviceRequestDto.setProductDescription(requestDto.getDescription());
        serviceRequestDto.setPrice(requestDto.getPrice());
        serviceRequestDto.setCategoryId(requestDto.getCategoryId());

        // Save product to database
        ControllerProductResDto responseDto =
                ControllerProductResDto.of(
                        productService.createProduct(serviceRequestDto)
                );


        // Return response
        log.info("Product created with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    // 4. PATCH /products/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<ControllerProductResDto> updateProductById(
            @PathVariable("id") Long id,
            @RequestBody @Valid ControllerProductReqDto requestDto
    ) throws ProductNotFoundException, CategoryNotFoundException, JsonProcessingException {

        log.debug("Received request to update product by id : {}", id);

        // Map request to service dto
        ProductRequestDto serviceRequestDto = new ProductRequestDto();
        serviceRequestDto.setProductTitle(requestDto.getTitle());
        serviceRequestDto.setProductDescription(requestDto.getDescription());
        serviceRequestDto.setPrice(requestDto.getPrice());
        serviceRequestDto.setCategoryId(requestDto.getCategoryId());

        // Update product by id
        ControllerProductResDto responseDto =
                ControllerProductResDto.of(
                        productService.updateProductPartial(id, serviceRequestDto)
                );

        // Return response
        log.info("Product updated with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    // 5. DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ControllerProductResDto> deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {

        log.debug("Received request to delete product by id : {}", id);

        // Delete product by id
        ControllerProductResDto responseDto =
                ControllerProductResDto.of(
                        productService.deleteProductById(id)
                );

        // Return response
        log.info("Product deleted with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }
}
