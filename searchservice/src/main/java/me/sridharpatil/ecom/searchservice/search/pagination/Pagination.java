package me.sridharpatil.ecom.searchservice.search.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pagination {
    private int page;
    private int size;

    public Pagination(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
