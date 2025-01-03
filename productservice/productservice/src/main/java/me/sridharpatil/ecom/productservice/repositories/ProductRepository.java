package me.sridharpatil.ecom.productservice.repositories;

import me.sridharpatil.ecom.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}