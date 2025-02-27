package me.sridharpatil.ecom.productservice.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryRequestDto {
    @NotNull @NotBlank
    private String title;
}
