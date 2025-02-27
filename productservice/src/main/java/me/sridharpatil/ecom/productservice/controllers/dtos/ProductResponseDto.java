package me.sridharpatil.ecom.productservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.productservice.models.Product;

@Getter @Setter
public class ProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private CategoryResponseDto category;

    public static ProductResponseDto of(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(
                CategoryResponseDto.of(
                        product.getCategory()
                )
        );

        return dto;
    }
}
