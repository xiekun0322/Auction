package com.judicial.order.controller;

import com.judicial.common.ApiResponse;
import com.judicial.order.entity.AuctionOrder;
import com.judicial.order.mapper.OrderMapper;
import com.judicial.order.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    @Data
    public static class CreateReq {
        private Long itemId; private Long buyerId; private BigDecimal amount;
    }

    @PostMapping("/create") public ApiResponse<AuctionOrder> create(@RequestBody CreateReq r) {
        return ApiResponse.ok(service.create(r.getItemId(), r.getBuyerId(), r.getAmount()));
    }

    @GetMapping("/by-item/{itemId}") public ApiResponse<AuctionOrder> byItem(@PathVariable Long itemId) {
        return ApiResponse.ok(mapper.findByItemId(itemId));
    }
}
