package me.sridharpatil.ecom.searchservice.search.filters;

import lombok.Getter;
import lombok.Setter;
import me.sridharpatil.ecom.searchservice.search.Field;

@Getter
@Setter
public class Filter {
    private Field field;    // Field name
    private Operator operator; // eq, neq, gte, gt, lte, lt
    private Object value;    // Value for comparison
}
