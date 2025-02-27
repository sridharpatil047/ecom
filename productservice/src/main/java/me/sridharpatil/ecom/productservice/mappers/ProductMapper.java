package me.sridharpatil.ecom.productservice.mappers;


import me.sridharpatil.ecom.productservice.controllers.dtos.ProductRequestDto;
import me.sridharpatil.ecom.productservice.controllers.dtos.ProductResponseDto;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ProductRequest mapToProductRequest(ProductRequestDto from) {
        if (from == null) {return null;}
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductTitle(from.getTitle());
        productRequest.setProductDescription(from.getDescription());
        productRequest.setPrice(from.getPrice());
        productRequest.setCategoryId(from.getCategoryId());

        return productRequest;
    }

    public ProductResponseDto mapToProductResponseDto(Product from) {
        if (from == null) {return null;}
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(from.getId());
        productResponseDto.setTitle(from.getTitle());
        productResponseDto.setDescription(from.getDescription());
        productResponseDto.setPrice(from.getPrice());
        productResponseDto.setCategory(categoryMapper.mapToCategoryResponseDto(from.getCategory()));

        return productResponseDto;
    }
}
