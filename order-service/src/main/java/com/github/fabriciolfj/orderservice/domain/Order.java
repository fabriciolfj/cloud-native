package com.github.fabriciolfj.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends PersistableEntity {

    private String bookIsbn;
    private String bookName;
    private Double bookPrice;
    private Integer quantity;
    private OrderStatus status;
    @CreatedDate
    private Long createdDate;
    @LastModifiedDate
    private Long lastModifiedDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;

    public Order(final String bookIsbn, final int quantity, final OrderStatus status) {
        this.bookIsbn = bookIsbn;
        this.quantity = quantity;
        this.status = status;
    }
}
