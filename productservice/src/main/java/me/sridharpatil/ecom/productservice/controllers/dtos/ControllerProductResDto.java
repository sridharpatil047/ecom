package me.sridharpatil.ecom.productservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.productservice.models.Product;

@Getter @Setter
public class ControllerProductResDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private ControllerCategoryResDto category;

    public static ControllerProductResDto of(Product product) {
        ControllerProductResDto dto = new ControllerProductResDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(
                ControllerCategoryResDto.of(
                        product.getCategory()
                )
        );

        return dto;
    }
}
