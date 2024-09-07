package pl.com.insert.orderapi.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.insert.orderapi.web.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
