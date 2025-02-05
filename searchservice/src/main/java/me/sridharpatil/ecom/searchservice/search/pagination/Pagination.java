package me.sridharpatil.ecom.searchservice.search.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pagination {
    private int pageNumber;
    private int pageSize;

    public Pagination(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
