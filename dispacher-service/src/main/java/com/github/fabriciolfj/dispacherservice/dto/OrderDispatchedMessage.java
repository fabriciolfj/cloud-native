package com.github.fabriciolfj.dispacherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDispatchedMessage {

    private Long orderId;
}
