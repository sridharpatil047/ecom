package me.sridharpatil.ecom.productservice.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ControllerProductReqDto {

    @NotNull @NotBlank
    private String title;
    private String description;
    private Double price;

    @NotNull
    private Long categoryId;
}
