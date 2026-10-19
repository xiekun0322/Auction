package com.judicial.order.service;

import com.judicial.order.entity.AuctionOrder;
import com.judicial.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderMapper mapper;

    @Transactional
    public AuctionOrder create(Long itemId, Long buyerId, BigDecimal amount) {
        AuctionOrder exist = mapper.findByItemId(itemId);
        if (exist != null) return exist;  // 幂等
        AuctionOrder o = new AuctionOrder();
        o.setOrderNo("O" + itemId + "-" + System.currentTimeMillis());
        o.setItemId(itemId);
        o.setBuyerId(buyerId);
        o.setAmount(amount);
        o.setStatus("PENDING_PAY");
        o.setCreateTime(LocalDateTime.now());
        mapper.insert(o);
        log.info("订单创建 item={} order={}", itemId, o.getOrderNo());
        return o;
    }
}
