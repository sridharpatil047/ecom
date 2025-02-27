package me.sridharpatil.ecom.productservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.productservice.models.Category;

@Getter @Setter
public class CategoryResponseDto {
    private Long id;
    private String title;

    public static CategoryResponseDto of(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setTitle(category.getTitle());

        return dto;
    }
}
