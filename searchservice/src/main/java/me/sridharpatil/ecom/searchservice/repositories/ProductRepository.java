package me.sridharpatil.ecom.searchservice.repositories;

import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.repositories.customs.ProductCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, Long>, ProductCustomRepository {
}
