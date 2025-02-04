package me.sridharpatil.ecom.searchservice.services;

import me.sridharpatil.ecom.searchservice.controllers.dtos.AdvancedSearchDto;
import me.sridharpatil.ecom.searchservice.documents.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Page<Product> basicProductSearch(String query, int page, int size);
    Page<Product> advancedProductSearch(String query, List<AdvancedSearchDto.FilterCriteria> filters, List<AdvancedSearchDto.SortCriteria> sortByList, int page, int size);
}
