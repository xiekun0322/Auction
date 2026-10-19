package com.judicial.record.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BidRecord {
    private Long id;
    private Long itemId;
    private Long userId;
    private BigDecimal amount;
    private String idemKey;
    private LocalDateTime createTime;
}
