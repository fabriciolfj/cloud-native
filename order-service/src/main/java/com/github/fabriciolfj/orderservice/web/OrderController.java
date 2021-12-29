package com.github.fabriciolfj.orderservice.web;

import com.github.fabriciolfj.orderservice.domain.Order;
import com.github.fabriciolfj.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Flux<Order> getAllOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getAllOrders(jwt.getSubject());
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrderById(@PathVariable final Long id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public Mono<Order> submitOrder(@RequestBody @Valid final OrderRequest request) {
        return orderService.submitOrder(request.getIsbn(), request.getQuantity());
    }
}
