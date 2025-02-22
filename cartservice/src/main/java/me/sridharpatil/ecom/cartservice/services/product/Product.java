package me.sridharpatil.ecom.cartservice.services.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor @NoArgsConstructor
@Data
public class Product {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Category category;
}
