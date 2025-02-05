package me.sridharpatil.ecom.searchservice.services;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.searchservice.search.filters.Filter;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.repositories.ProductRepository;
import me.sridharpatil.ecom.searchservice.search.sorting.SortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService{

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        log.debug("Saving product with id : {}", product.getId());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        log.debug("Updating product with id : {}", product.getId());
        return createProduct(product);
    }

    @Override
    public List<Product> advancedProductSearch(String query, List<Filter> filters, List<SortBy> sortBys, Pagination pagination) throws IOException {
        log.debug("Performing advanced search with query : {}, filters : {}, sortBys : {}, pagination : {}", query, filters, sortBys, pagination);
        AdvancedSearch advancedSearch = new AdvancedSearch();
        if (query == null && filters == null){
            throw new IllegalArgumentException("Invalid search parameters");
        }
        advancedSearch.setQuery(query);
        advancedSearch.setFilters(filters);
        advancedSearch.setSortBys(sortBys);
        advancedSearch.setPagination(pagination);
        
        return productRepository.advancedSearch(advancedSearch);
    }
}
