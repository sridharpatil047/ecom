package me.sridharpatil.ecom.searchservice.consumners.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductConsumerEventReqDto {
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
}
