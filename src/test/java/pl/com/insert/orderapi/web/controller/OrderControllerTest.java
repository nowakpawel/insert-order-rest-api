package pl.com.insert.orderapi.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pl.com.insert.orderapi.web.domain.OrderStatus;
import pl.com.insert.orderapi.web.entity.Order;
import pl.com.insert.orderapi.web.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @InjectMocks
    private OrderController controller;
    @Mock
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;
    private LocalDateTime timeWhenCreated;
    private Order order;

    @BeforeEach
    void setup() {
        timeWhenCreated = LocalDateTime.now();

        order = Order.builder()
                .id(1)
                .customerName("Pawel")
                .orderCreatedDate(timeWhenCreated)
                .totalAmount(new BigDecimal(12345678.90))
                .status(OrderStatus.PENDING)
                .build();
    }

    @Test
    void shouldGetAllOrders() {
        List<Order> orders = List.of(
                new Order(1, "Pawel", LocalDateTime.now(), null, new BigDecimal("1234568.90"), OrderStatus.PENDING),
                new Order(2, "InsERT", LocalDateTime.now(), null, new BigDecimal("1234568.90"), OrderStatus.PENDING)
        );

        when(orderService.findAll()).thenReturn(orders);
        ResponseEntity<List<Order>> response = controller.findAllOrders();

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldReturnCreatedOrder() {
        when(orderService.putOrder(order)).thenReturn(order);
        ResponseEntity<Order> response = controller.addNewOrder(order);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldUpdateOrder() {
        Order confirmedOrder = Order.builder()
                .id(1)
                .customerName("Pawel")
                .orderCreatedDate(timeWhenCreated)
                .totalAmount(new BigDecimal(12345678.90))
                .status(OrderStatus.CONFIRMED)
                .build();

        when(orderService.confirmOrder(1)).thenReturn(confirmedOrder);

        ResponseEntity<Order> response = controller.confirmOrder(1);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}