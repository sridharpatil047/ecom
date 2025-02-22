package me.sridharpatil.ecom.cartservice.services.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor @NoArgsConstructor
@Data
public class Category {
    private Long id;
    private String title;
}
