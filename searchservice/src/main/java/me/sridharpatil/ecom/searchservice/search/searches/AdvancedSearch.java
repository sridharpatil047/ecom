package me.sridharpatil.ecom.searchservice.search.searches;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.searchservice.search.filters.Filter;
import me.sridharpatil.ecom.searchservice.search.sorting.SortBy;

import java.util.List;

@Getter @Setter
public class AdvancedSearch extends Search {
    private List<Filter> filters; // Filters
    private List<SortBy> sortBys; // Sorting
}