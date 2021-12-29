package com.github.fabriciolfj.orderservice.repository;

import com.github.fabriciolfj.orderservice.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    Flux<Order> findAllByCreatedBy(final String id);

}
