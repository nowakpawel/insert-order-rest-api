package pl.com.insert.orderapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/")
    @Operation(summary = "Find all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find orders",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Page not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
    })
    public ResponseEntity<List<Order>> test() {
        List<Order> orders = orderService.findAll();

        return ResponseEntity.ok(orders);
    }
}
