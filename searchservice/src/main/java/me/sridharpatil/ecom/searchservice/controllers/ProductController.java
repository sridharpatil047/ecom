package me.sridharpatil.ecom.searchservice.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import me.sridharpatil.ecom.searchservice.search.Defaults;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;
import me.sridharpatil.ecom.searchservice.search.searches.BasicSearch;
import me.sridharpatil.ecom.searchservice.services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/products/search")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object basicSearch(@RequestParam @NotNull String query,
                              @RequestParam(defaultValue = "0", required = false) Integer page,
                              @RequestParam(defaultValue = "5", required = false) Integer size) throws IOException {

        AdvancedSearch advancedSearch = new AdvancedSearch();
        advancedSearch.setQuery(query);
        advancedSearch.setPagination(new Pagination(page, size));

        return productService.advancedProductSearch(advancedSearch.getQuery(),
                null,
                null,
                advancedSearch.getPagination());
    }

    @PostMapping
    public Object advancedSearch(@RequestBody AdvancedSearch advancedSearch) throws IOException {
        return productService.advancedProductSearch(advancedSearch.getQuery(),
                advancedSearch.getFilters(),
                advancedSearch.getSortBys(),
                advancedSearch.getPagination());
    }
}
