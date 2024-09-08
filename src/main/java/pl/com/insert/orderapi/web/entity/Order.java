package pl.com.insert.orderapi.web.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.insert.orderapi.web.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private  String customerName;
    @Nullable
    private LocalDateTime orderCreatedDate;
    @Nullable
    private LocalDateTime orderUpdatedDate;
    private BigDecimal totalAmount;
    @Nullable
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
