package com.github.fabriciolfj.dispacherservice;

import com.github.fabriciolfj.dispacherservice.dto.OrderAcceptedMessage;
import com.github.fabriciolfj.dispacherservice.dto.OrderDispatchedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
@Configuration
public class DispatchingFunctions {

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {
        return orderAcceptedMessage -> {
            log.info("The order with id {}", orderAcceptedMessage.getOrderId());
            return orderAcceptedMessage.getOrderId();
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return orderFlux -> orderFlux.map(id -> {
            log.info("The order whith id {}, is labeled", id);
            return new OrderDispatchedMessage(id);
        });
    }
}
