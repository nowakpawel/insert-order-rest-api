package pl.com.insert.orderapi.web.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.com.insert.orderapi.web.domain.OrderStatus;
import pl.com.insert.orderapi.web.entity.Order;
import pl.com.insert.orderapi.web.repository.OrderRepository;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService service;

    @Test
    void shouldReturnAllOrders() {
        List<Order> orders = List.of(
                new Order(1, "Pawel", LocalDateTime.now(), new BigDecimal("12345678.33"), OrderStatus.CANCELLED),
                new Order(2, "insERT", LocalDateTime.now(), new BigDecimal("4561283.55"), OrderStatus.CANCELLED)
        );

        when(orderRepository.findAll()).thenReturn(orders);
        List<Order> foundOrders = service.findAll();

        assertAll(
                () -> assertEquals(2, foundOrders.size())
        );
    }

    @Test
    void shouldSaveProperOrder() {
        Order newOrder = Order.builder()
                .id(1)
                .customerName("Pawel")
                .orderDate(LocalDateTime.now())
                .totalAmount(new BigDecimal(12345678.90))
                .status(OrderStatus.valueOf("PENDING"))
                .build();

        when(orderRepository.save(newOrder)).thenReturn(newOrder);
        Order savedOrder = service.putOrder(newOrder);

        assertEquals("Pawel", savedOrder.getCustomerName());
//        assertEquals(new BigDecimal("12345678.90"), savedOrder.getTotalAmount()); //TODO: refactor needed
        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenOrderStatusIsIncorrect() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> creteOrder()
        );

        assertNotNull(exception);
    }

    private Order creteOrder() {
        return Order.builder()
                .id(1)
                .customerName("Pawel")
                .orderDate(LocalDateTime.now())
                .totalAmount(new BigDecimal(12345678.90))
                .status(OrderStatus.valueOf("Test"))
                .build();
    }
}