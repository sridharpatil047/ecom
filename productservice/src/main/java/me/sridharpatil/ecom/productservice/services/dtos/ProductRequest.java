package me.sridharpatil.ecom.productservice.services.dtos;

import lombok.*;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ProductRequest {
    private String productTitle;
    private String productDescription;
    private Double price;
    private Long categoryId;
}
