package pl.com.insert.orderapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.insert.orderapi.web.entity.Order;
import pl.com.insert.orderapi.web.service.OrderService;

import java.util.List;


@RestController
@RequestMapping("/insertapi")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    @Operation(summary = "Find all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find orders",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
    })
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> orders = orderService.findAll();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Find Order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find orders",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
    })
    public ResponseEntity<Order> findOrderById(@Parameter(description = "Id of the order we wabt to findf", example = "1") @PathVariable("id") Integer id) {
        Order foundOrder = orderService.findOrderById(id);

        return ResponseEntity.ok(foundOrder);
    }

    @PostMapping("/add")
    @Operation(summary = "Ad order to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added new order",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
    })
    public ResponseEntity<Order> addNewOrder(@RequestBody Order order) {
        Order createdOrder = orderService.putOrder(order);
        return ResponseEntity.ok(createdOrder);
    }
}
