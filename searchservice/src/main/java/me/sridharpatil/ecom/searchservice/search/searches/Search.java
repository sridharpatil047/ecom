package me.sridharpatil.ecom.searchservice.search.searches;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.sridharpatil.ecom.searchservice.search.pagination.Pagination;

@Getter @Setter
@SuperBuilder
public abstract class Search {
    private String query; // Full-text search query
    private Pagination pagination; // Pagination
}
