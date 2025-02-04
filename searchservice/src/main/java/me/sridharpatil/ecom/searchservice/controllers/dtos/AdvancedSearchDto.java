package me.sridharpatil.ecom.searchservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AdvancedSearchDto {
    private String query; // Full-text search query
    private List<FilterCriteria> filters; // Filters with operators
    private List<SortCriteria> sort; // Sorting
    private int pageNumber = 0; // Pagination - Default to page 0
    private int pageSize = 10;  // Pagination - Default to 10 results per page

    // Inner class for filter criteria
    @Getter @Setter
    public static class FilterCriteria {
        private String field;    // Field name
        private FilterCriteriaOperator operator; // eq, neq, gte, gt, lte, lt
        private String value;    // Value for comparison
    }

    // Inner class for sort criteria
    @Getter @Setter
    public static class SortCriteria {
        private String field;
        private String order; // "asc" or "desc"
    }
}