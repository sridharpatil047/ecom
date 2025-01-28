package me.sridharpatil.ecom.orderservice.repositories;

import me.sridharpatil.ecom.orderservice.models.Order;
import me.sridharpatil.ecom.orderservice.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserIdAndStatus(Long userId, OrderStatus status);
}
