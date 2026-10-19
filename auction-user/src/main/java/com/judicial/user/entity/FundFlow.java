package com.judicial.user.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FundFlow {
    private Long id;
    private String flowNo;
    private Long userId;
    private BigDecimal amount;
    private String type;
    private LocalDateTime createTime;
}
