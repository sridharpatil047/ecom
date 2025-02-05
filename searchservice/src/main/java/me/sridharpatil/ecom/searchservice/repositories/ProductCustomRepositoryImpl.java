package me.sridharpatil.ecom.searchservice.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.search.Field;
import me.sridharpatil.ecom.searchservice.search.filters.Filter;
import me.sridharpatil.ecom.searchservice.search.filters.Operator;
import me.sridharpatil.ecom.searchservice.search.searches.AdvancedSearch;
import me.sridharpatil.ecom.searchservice.search.sorting.SortBy;
import me.sridharpatil.ecom.searchservice.search.sorting.SortingOrder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository{

    private final ElasticsearchClient elasticsearchClient;

    public ProductCustomRepositoryImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public List<Product> advancedSearch(AdvancedSearch advancedSearch) throws IOException {

        // Build the query
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        // Full text search for title and description
        if (advancedSearch.getQuery() != null && !advancedSearch.getQuery().isEmpty()){
            boolQueryBuilder.must(
                    QueryBuilders.multiMatch(mm -> mm
                            .query(advancedSearch.getQuery())
                            .fields(Field.TITLE.getValue(), Field.DESCRIPTION.getValue())
                    )
            );
        }

        // Applying filters
        if (advancedSearch.getFilters() != null && !advancedSearch.getFilters().isEmpty()){
            for (Filter filter : advancedSearch.getFilters()){
                Query filterQuery = buildFilterQuery(filter.getField(), filter.getOperator(), filter.getValue());
                boolQueryBuilder.filter(filterQuery);
            }
        }


        // Applying sorting
        List<SortOptions> sortOptions = new ArrayList<>();
        // Default sort by ID
        sortOptions.add(new SortOptions.Builder()
                        .field(f -> f.field(Field.ID.getValue()).order(SortOrder.Asc))
                .build()
        );
        // Add additional sort options if any provided
        if (advancedSearch.getSortBys() != null && !advancedSearch.getSortBys().isEmpty()){
            for (SortBy sortBy : advancedSearch.getSortBys()){
                SortOrder sortOrder = sortBy.getOrder().equals(SortingOrder.ASC) ? SortOrder.Asc : SortOrder.Desc;
                sortOptions.add(new SortOptions.Builder()
                        .field(f -> f.field(sortBy.getField().getValue()).order(sortOrder))
                        .build());
            }
        }


        // Build the search request
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("products")
                .query(b -> b.bool(boolQueryBuilder.build()))
                .from(advancedSearch.getPagination().getPage() * advancedSearch.getPagination().getSize())
                .sort(sortOptions)
                .size(advancedSearch.getPagination().getSize())
        );

        // Execute the search request
        SearchResponse<Product> searchResponse = elasticsearchClient.search(searchRequest, Product.class);

        // Return the search results
        return searchResponse.hits().hits().stream()
                .map(hit -> hit.source())
                .collect(Collectors.toList());
    }

    private Query buildFilterQuery(Field field, Operator operator, Object value){
        return switch (operator) {
            case EQ  -> QueryBuilders.match(m -> m.field(field.getValue()).query(String.valueOf(value)));
            case NEQ -> QueryBuilders.bool(b -> b.mustNot(QueryBuilders.match(m -> m.field(field.getValue()).query(String.valueOf(value)))));
            case GT  -> QueryBuilders.range(r  -> r.number(n -> n.field(field.getValue()).gt((Double) value)));
            case GTE -> QueryBuilders.range(r  -> r.number(n -> n.field(field.getValue()).gte((Double) value)));
            case LT  -> QueryBuilders.range(r  -> r.number(n -> n.field(field.getValue()).lt((Double) value)));
            case LTE -> QueryBuilders.range(r  -> r.number(n -> n.field(field.getValue()).lte((Double) value)));
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }
}
