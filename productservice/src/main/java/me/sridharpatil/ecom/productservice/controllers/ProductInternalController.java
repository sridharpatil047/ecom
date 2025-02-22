package me.sridharpatil.ecom.productservice.controllers;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductResDto;
import me.sridharpatil.ecom.productservice.exceptions.ProductNotFoundException;
import me.sridharpatil.ecom.productservice.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/private/products")
public class ProductInternalController {

    private final ProductService productService;

    public ProductInternalController(ProductService productService) {
        this.productService = productService;
    }

    // 1. GET /products/{id}
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

}
