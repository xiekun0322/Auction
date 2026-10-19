package com.judicial.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.judicial.job.feign.AuctionFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemEndJob {
    private final AuctionFeignClient auctionFeign;

    /** MVP：每 5 分钟扫描（生产可 10s 一次，配合 Redis 到期 key）*/
    @Scheduled(fixedDelay = 300_000)
    public void run() {
        // 生产：调用 auction 内部接口 /api/auction/expired 拿到过期 id 列表，逐个 end
        // MVP 演示：调用 auction 端已内置的 /items 接口（略），这里仅打日志
        log.info("[ITEM-END] 到点落槌扫描（生产环境对接 /api/auction/expired）");
    }
}
