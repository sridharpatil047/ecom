package me.sridharpatil.ecom.productservice.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductReqDto;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductResDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.services.ProductService;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. GET /products
    @GetMapping()
    public ResponseEntity<List<ControllerProductResDto>> getAllProducts() {

        List<ControllerProductResDto> resDtoList = new ArrayList<>();

        List<Product> productList = productService.getAllProducts();
        for (Product product : productList) {
            resDtoList.add(ControllerProductResDto.of(product));
        }

        // Return response
        return ResponseEntity.ok(resDtoList);
    }


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
            @RequestBody @Valid ControllerProductReqDto requestDto
    ) throws ProductNotFoundException, CategoryNotFoundException {


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
            @RequestBody @Valid ControllerProductReqDto requestDto
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
