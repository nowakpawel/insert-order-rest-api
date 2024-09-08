package pl.com.insert.orderapi.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.com.insert.orderapi.web.domain.OrderStatus;
import pl.com.insert.orderapi.web.entity.Order;
import pl.com.insert.orderapi.web.repository.OrderRepository;

import java.time.LocalDateTime;
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
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }

        order.setOrderCreatedDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order confirmOrder(Integer id) {
        Order order = findOrderById(id);

        if (order != null) {
            order.setOrderUpdatedDate(LocalDateTime.now());
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);

            return order;
        }

        return null;
    }

    public Order cancelOrder(Integer id) {
        Order order = findOrderById(id);

        if (order != null) {
            order.setOrderUpdatedDate(LocalDateTime.now());
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            return order;
        }

        return null;
    }

    public Order deliverOrder(Integer id) {
        Order order = findOrderById(id);

        if (order != null) {
            order.setOrderUpdatedDate(LocalDateTime.now());
            order.setStatus(OrderStatus.DELIVERED);
            orderRepository.save(order);

            return order;
        }

        return null;
    }
}
