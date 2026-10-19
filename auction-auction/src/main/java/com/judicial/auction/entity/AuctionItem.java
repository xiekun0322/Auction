package com.judicial.auction.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AuctionItem {
    private Long id;
    private String caseNo;
    private String title;
    private BigDecimal startPrice;
    private BigDecimal deposit;
    private BigDecimal currentPrice;
    private Long leaderId;
    private LocalDateTime endTime;
    private String status;
    private Integer version;
}
