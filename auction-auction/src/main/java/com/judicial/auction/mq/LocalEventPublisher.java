package com.judicial.auction.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * MVP：本地消息表由 auction-core 自身表维护，此处退化为打印日志。
 * 生产环境替换为 RocketMQ 生产者。
 */
@Component
@Slf4j
public class LocalEventPublisher {
    public void publish(String topic, Object payload) {
        log.info("[MQ-PUB] svc=auction topic={} payload={}", topic, payload);
    }
}
