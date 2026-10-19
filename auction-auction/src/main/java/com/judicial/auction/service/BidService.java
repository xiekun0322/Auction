package com.judicial.auction.service;

import com.judicial.auction.entity.AuctionItem;
import com.judicial.auction.feign.FundFeignClient;
import com.judicial.auction.mapper.AuctionItemMapper;
import com.judicial.auction.mq.LocalEventPublisher;
import com.judicial.common.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidService {

    private final AuctionItemMapper itemMapper;
    private final AuctionRedisService redisService;
    private final FundFeignClient fundFeign;
    private final LocalEventPublisher publisher;

    @Value("${auction.use-redis:false}")
    private boolean useRedis;

    public void bid(Long itemId, Long userId, BigDecimal amount, String idemKey) {
        AuctionItem item = itemMapper.findById(itemId);
        if (item == null) throw new BizException("标的物不存在");
        if (!"ONGOING".equals(item.getStatus())) throw new BizException("竞拍未进行中");

        // 1. 冻结保证金
        Map<String, Object> freeze = new HashMap<>();
        freeze.put("userId", userId);
        freeze.put("amount", amount);
        freeze.put("flowNo", "F-" + idemKey);
        var fRsp = fundFeign.freeze(freeze);
        if (fRsp.getCode() != 0) throw new BizException("冻结失败：" + fRsp.getMsg());

        Long oldLeader = item.getLeaderId();
        BigDecimal oldPrice = item.getCurrentPrice();
        boolean extended = false;
        java.time.LocalDateTime newEnd = null;

        try {
            if (useRedis) {
                newEnd = redisService.tryBid(itemId, userId, amount, idemKey);
                extended = true;
            } else {
                int rows = itemMapper.updatePriceOptimistic(itemId, item.getVersion(), amount, userId);
                if (rows == 0) throw new BizException("出价冲突，请重试");
            }
        } catch (RuntimeException e) {
            // 补偿：解冻
            Map<String, Object> uf = new HashMap<>();
            uf.put("userId", userId); uf.put("amount", amount); uf.put("flowNo", "UF-" + idemKey);
            fundFeign.unfreeze(uf);
            throw e;
        }

        // 2. 前一位领先者解冻
        if (oldLeader != null && !oldLeader.equals(userId)) {
            Map<String, Object> uf = new HashMap<>();
            uf.put("userId", oldLeader);
            uf.put("amount", oldPrice);
            uf.put("flowNo", "UF-" + idemKey + "-" + oldLeader);
            try { fundFeign.unfreeze(uf); } catch (Exception ignored) { }
        }

        if (extended && newEnd != null) itemMapper.updateEndTime(itemId, newEnd);

        // 3. 事件（异步落库 + 前端通知）
        publisher.publish("BID_SUCCESS", Map.of(
                "itemId", itemId, "userId", userId, "amount", amount, "idemKey", idemKey));

        log.info("出价成功 item={} user={} amount={} extended={}", itemId, userId, amount, extended);
    }
}
