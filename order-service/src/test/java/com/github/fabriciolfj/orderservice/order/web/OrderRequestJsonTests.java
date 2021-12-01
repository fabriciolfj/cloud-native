package com.github.fabriciolfj.orderservice.order.web;

import com.github.fabriciolfj.orderservice.web.OrderRequest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class OrderRequestJsonTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JacksonTester<OrderRequest> json;

    @Test
    void testDeserialize() throws Exception {
        String content = "{\"isbn\":\"1234567890\", \"quantity\":\"1\"}";
        assertThat(this.json.parse(content))
                .usingRecursiveComparison().isEqualTo(new OrderRequest("1234567890", 1));
    }
}
