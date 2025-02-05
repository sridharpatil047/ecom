package me.sridharpatil.ecom.productservice.producers.dtos;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.productservice.models.Product;

@Getter @Setter
public class ProductProducerDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Category category;

    @Getter @Setter
    public static class Category {
        public Long id;
        public String title;
    }
    public static ProductProducerDto of(Product product) {
        ProductProducerDto dto = new ProductProducerDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(
                new Category()
        );
        dto.getCategory().setId(product.getCategory().getId());
        dto.getCategory().setTitle(product.getCategory().getTitle());

        return dto;
    }
}
