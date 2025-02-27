package me.sridharpatil.ecom.productservice.mappers;

import me.sridharpatil.ecom.productservice.controllers.dtos.CategoryResponseDto;
import me.sridharpatil.ecom.productservice.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDto mapToCategoryResponseDto(Category from) {
        if (from == null) {return null;}
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(from.getId());
        dto.setTitle(from.getTitle());

        return dto;
    }
}
