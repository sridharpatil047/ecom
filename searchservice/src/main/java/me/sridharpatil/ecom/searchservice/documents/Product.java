package me.sridharpatil.ecom.searchservice.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter @Setter
@Document(indexName = "products")
public class Product {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Object)
    private Category category;

    @Getter @Setter
    public static class Category {
        private Long id;

        @Field(type = FieldType.Text)
        private String title;
    }
}
