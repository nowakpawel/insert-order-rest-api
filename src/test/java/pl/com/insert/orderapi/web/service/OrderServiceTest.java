package pl.com.insert.orderapi.web.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService service;

    private Order order;
    private LocalDateTime timeWhenCreated;

    @BeforeEach
    void setup() {
        timeWhenCreated = LocalDateTime.now();

        order = Order.builder()
                .id(1)
                .customerName("Pawel")
                .orderCreatedDate(timeWhenCreated)
                .totalAmount(new BigDecimal(12345678.90))
                .status(OrderStatus.valueOf("PENDING"))
                .build();
    }

    @AfterEach
    void tearDown() {
        order = null;
    }

    @Test
    void shouldReturnAllOrders() {
        List<Order> orders = List.of(
                new Order(1, "Pawel", LocalDateTime.now(), null, new BigDecimal("12345678.33"), OrderStatus.CANCELLED),
                new Order(2, "insERT", LocalDateTime.now(), null, new BigDecimal("4561283.55"), OrderStatus.CANCELLED)
        );

        when(orderRepository.findAll()).thenReturn(orders);
        List<Order> foundOrders = service.findAll();

        assertAll(
                () -> assertEquals(2, foundOrders.size())
        );
    }

    @Test
    void shouldFindOrderById() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order foundOrder = service.findOrderById(1);

        assertEquals("Pawel", foundOrder.getCustomerName());
        assertEquals(timeWhenCreated, foundOrder.getOrderCreatedDate());
        assertEquals(OrderStatus.PENDING, foundOrder.getStatus());
    }

    @Test
    void shouldNotFindOrderByNotExistedId() {
        when(orderRepository.findById(10)).thenReturn(Optional.empty());

        Order foundOrder = service.findOrderById(10);

        assertNull(foundOrder);
    }

    @Test
    void shouldSaveProperOrder() {

        when(orderRepository.save(order)).thenReturn(order);
        Order savedOrder = service.putOrder(order);

        assertEquals("Pawel", savedOrder.getCustomerName());
//        assertEquals(new BigDecimal("12345678.90"), savedOrder.getTotalAmount()); //TODO: refactor needed
        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenOrderStatusIsIncorrect() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                this::creteOrderWithInappropriateOrderStatus
        );

        assertNotNull(exception);
    }

    @Test
    void shouldConfirmedOrder() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        Order confirmedOrder = service.confirmOrder(1);

        assertEquals(OrderStatus.CONFIRMED, confirmedOrder.getStatus());
    }

    private void creteOrderWithInappropriateOrderStatus() {
        Order.builder()
                .id(1)
                .customerName("Pawel")
                .orderCreatedDate(LocalDateTime.now())
                .totalAmount(new BigDecimal(12345678.90))
                .status(OrderStatus.valueOf("Test"))
                .build();
    }
}