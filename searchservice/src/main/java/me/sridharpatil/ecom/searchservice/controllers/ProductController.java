package me.sridharpatil.ecom.searchservice.controllers;

import me.sridharpatil.ecom.searchservice.search.Defaults;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;
import me.sridharpatil.ecom.searchservice.search.searches.BasicSearch;
import me.sridharpatil.ecom.searchservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/search")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @GetMapping("")
    public Object basicSearch(@RequestParam String query,
                              @RequestParam(defaultValue = Defaults.page) int page,
                              @RequestParam(defaultValue = Defaults.size) int size) {

        return productService.basicProductSearch(query, new Pagination(page, size));

    }

    //    @PostMapping("/advanced")
//    public Object advancedSearch(@RequestBody Object advancedSearchDto) {
//        return productService.advancedProductSearch(advancedSearchDto);
//    }
}
