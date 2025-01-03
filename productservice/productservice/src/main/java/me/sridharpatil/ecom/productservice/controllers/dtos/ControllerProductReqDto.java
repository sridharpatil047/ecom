package me.sridharpatil.ecom.productservice.controllers.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ControllerProductReqDto {

    @NotNull @NotBlank
    private String title;
    private String description;
    private Double price;

    @NotNull
    private Long categoryId;
}
