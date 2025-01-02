package me.sridharpatil.ecom.productservice.services.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequestDto {
    private String productTitle;
    private String productDescription;
    private Double price;
    private Long categoryId;
}
