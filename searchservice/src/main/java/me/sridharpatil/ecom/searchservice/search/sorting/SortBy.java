package me.sridharpatil.ecom.searchservice.search.sorting;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.searchservice.search.Field;

@Getter @Setter
public class SortBy {
    private Field field;
    private SortingOrder order; // "asc" or "desc"
}
