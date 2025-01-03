package me.sridharpatil.ecom.productservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.productservice.models.Category;

@Getter @Setter
public class ControllerCategoryResDto {
    private Long id;
    private String title;

    public static ControllerCategoryResDto of(Category category) {
        ControllerCategoryResDto dto = new ControllerCategoryResDto();
        dto.setId(category.getId());
        dto.setTitle(category.getTitle());

        return dto;
    }
}
