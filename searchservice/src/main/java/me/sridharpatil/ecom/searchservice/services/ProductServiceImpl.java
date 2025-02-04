package me.sridharpatil.ecom.searchservice.services;

import me.sridharpatil.ecom.searchservice.controllers.dtos.AdvancedSearchDto;
import me.sridharpatil.ecom.searchservice.documents.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public Page<Product> basicProductSearch(String query, int page, int size) {
        return null;
    }

    @Override
    public Page<Product> advancedProductSearch(String query, List<AdvancedSearchDto.FilterCriteria> filters, List<AdvancedSearchDto.SortCriteria> sortByList, int page, int size) {
        return null;
    }
}
