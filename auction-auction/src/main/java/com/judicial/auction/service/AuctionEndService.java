package com.judicial.auction.service;

import com.judicial.auction.entity.AuctionItem;
import com.judicial.auction.feign.FundFeignClient;
import com.judicial.auction.mapper.AuctionItemMapper;
import com.judicial.auction.mq.LocalEventPublisher;
import com.judicial.common.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionEndService {

    private final AuctionItemMapper itemMapper;
    private final FundFeignClient fundFeign;
    private final AuctionRedisService redisService;
    private final LocalEventPublisher publisher;

    @Transactional
    public void end(Long itemId) {
        AuctionItem item = itemMapper.findById(itemId);
        if (item == null) throw new BizException("标的物不存在");
        if (Set.of("SOLD","UNSOLD","ENDED").contains(item.getStatus())) {
            log.info("标的物 {} 已结束，幂等", itemId);
            return;
        }

        itemMapper.updateStatus(itemId, "ENDED");
        redisService.markEnded(itemId);

        if (item.getLeaderId() == null) {
            itemMapper.updateStatus(itemId, "UNSOLD");
            publisher.publish("AUCTION_UNSOLD", Map.of("itemId", itemId));
            log.info("标的物 {} 流拍", itemId);
            return;
        }

        // 抵扣保证金（作为成交款的一部分）
        Map<String, Object> d = new HashMap<>();
        d.put("userId", item.getLeaderId());
        d.put("amount", item.getCurrentPrice());
        d.put("flowNo", "D-" + itemId);
        try { fundFeign.deduct(d); }
        catch (Exception e) { log.error("扣款失败，需对账补偿 item={}", itemId, e); }

        itemMapper.updateStatus(itemId, "SOLD");
        publisher.publish("AUCTION_SOLD", Map.of(
                "itemId", itemId, "buyerId", item.getLeaderId(),
                "amount", item.getCurrentPrice()));
        log.info("标的物 {} 成交，赢家={} 金额={}", itemId, item.getLeaderId(), item.getCurrentPrice());
    }
}
