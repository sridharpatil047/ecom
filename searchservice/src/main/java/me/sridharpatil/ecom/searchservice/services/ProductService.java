package me.sridharpatil.ecom.searchservice.services;

import me.sridharpatil.ecom.searchservice.search.filters.Filter;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.search.sorting.SortBy;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    List<Product> advancedProductSearch(String query, List<Filter> filters, List<SortBy> sortBys, Pagination pagination) throws IOException;
}
