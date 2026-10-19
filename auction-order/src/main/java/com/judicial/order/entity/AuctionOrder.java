package com.judicial.order.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AuctionOrder {
    private Long id;
    private String orderNo;
    private Long itemId;
    private Long buyerId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createTime;
}
