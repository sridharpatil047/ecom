package me.sridharpatil.ecom.productservice.controllers;

import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductReqDto;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductResDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.services.ProductService;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. GET /products


    // 2. GET /products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ControllerProductResDto> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {

        // validate id
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        // Get product by id
        ControllerProductResDto responseDto =
                ControllerProductResDto.of(
                        productService.getProductById(id)
                );

        // Return response
        return ResponseEntity.ok(responseDto);
    }

    // 3. POST /products
    @PostMapping()
    public ResponseEntity<ControllerProductResDto> createProduct(
            @RequestBody ControllerProductReqDto requestDto
    ) throws ProductNotFoundException, CategoryNotFoundException {

        // Validate request
        if (requestDto.getTitle() == null
                || requestDto.getTitle().isEmpty()
                || requestDto.getCategoryId() == null) {
            return ResponseEntity.badRequest().build();
        }

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
        return ResponseEntity.ok(responseDto);
    }

    // 4. PATCH /products/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<ControllerProductResDto> updateProductById(
            @PathVariable("id") Long id,
            @RequestBody ControllerProductReqDto requestDto
    ) throws ProductNotFoundException, CategoryNotFoundException {

        // Validate request
        if (id == null){
            return ResponseEntity.badRequest().build();
        }

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
        return ResponseEntity.ok(responseDto);
    }

    // 5. DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ControllerProductResDto> deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {

        // validate id
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        // Delete product by id
        ControllerProductResDto responseDto =
                ControllerProductResDto.of(
                        productService.deleteProductById(id)
                );

        // Return response
        return ResponseEntity.ok(responseDto);
    }
}
