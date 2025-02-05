package me.sridharpatil.ecom.searchservice.services;

import me.sridharpatil.ecom.searchservice.search.filters.Filter;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.repositories.ProductRepository;
import me.sridharpatil.ecom.searchservice.search.sorting.SortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return createProduct(product);
    }

    @Override
    public Page<Product> basicProductSearch(String query, Pagination pagination) {
        return productRepository.findAllByTitleContainingOrDescriptionContaining(query, query, PageRequest.of(pagination.getPageNumber(), pagination.getPageSize()));
    }

    @Override
    public Page<Product> advancedProductSearch(String query, List<Filter> filters, List<SortBy> sortBys, Pagination pagination) {
        return null;
    }
}
