package me.sridharpatil.ecom.searchservice.services;

import me.sridharpatil.ecom.searchservice.search.filters.Filter;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.search.sorting.SortBy;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Page<Product> basicProductSearch(String query, Pagination pagination);
    Page<Product> advancedProductSearch(String query, List<Filter> filters, List<SortBy> sortBys, Pagination pagination);
}
