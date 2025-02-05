package me.sridharpatil.ecom.searchservice.search;

import lombok.Getter;

@Getter
public enum Field {
    ID("id"),
    TITLE("title"),
    DESCRIPTION("description"),
    PRICE("price"),
    CATEGORY_ID("category.id"),
    CATEGORY_TITLE("category.title");

    private final String value;

    Field(String value) {
        this.value = value;
    }

}
