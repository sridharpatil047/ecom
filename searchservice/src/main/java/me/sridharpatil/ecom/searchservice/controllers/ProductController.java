package me.sridharpatil.ecom.searchservice.controllers;

import me.sridharpatil.ecom.searchservice.search.Defaults;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;
import me.sridharpatil.ecom.searchservice.search.searches.BasicSearch;
import me.sridharpatil.ecom.searchservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/products/search")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping
//    public Product createProduct(@RequestBody Product product) {
//        return productService.createProduct(product);
//    }
//
//    @PutMapping
//    public Product updateProduct(@RequestBody Product product) {
//        return productService.updateProduct(product);
//    }
    @PostMapping
    public Object advancedSearch(@RequestBody AdvancedSearch advancedSearch) throws IOException {
        return productService.advancedProductSearch(advancedSearch.getQuery(),
                advancedSearch.getFilters(),
                advancedSearch.getSortBys(),
                advancedSearch.getPagination());
    }
}
