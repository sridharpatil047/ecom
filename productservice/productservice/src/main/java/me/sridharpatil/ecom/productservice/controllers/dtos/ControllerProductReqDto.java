package me.sridharpatil.ecom.productservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ControllerProductReqDto {
    private String title;
    private String description;
    private Double price;
    private Long categoryId;
}
