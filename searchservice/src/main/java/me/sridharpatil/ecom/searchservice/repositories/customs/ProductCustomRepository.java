package me.sridharpatil.ecom.searchservice.repositories.customs;

import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;

import java.io.IOException;
import java.util.List;

public interface ProductCustomRepository {
    List<Product> advancedSearch(AdvancedSearch advancedSearch) throws IOException;
}
