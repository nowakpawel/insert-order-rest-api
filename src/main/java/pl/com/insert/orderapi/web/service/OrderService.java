package pl.com.insert.orderapi.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.com.insert.orderapi.web.entity.Order;
import pl.com.insert.orderapi.web.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }

        log.error("Order with id %d does not exists".formatted(id));
        return null;
    }

    public Order putOrder(Order order) {
        return orderRepository.save(order);
    }

}
