package com.judicial.user.job;

import com.judicial.user.entity.LocalMessage;
import com.judicial.user.mapper.LocalMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageRelayJob {
    private final LocalMessageMapper mapper;

    @Scheduled(fixedDelay = 1000)
    public void relay() {
        List<LocalMessage> list = mapper.findPending(100);
        for (LocalMessage m : list) {
            log.info("[MQ-PUB] svc=user topic={} key={} payload={}",
                    m.getTopic(), m.getMsgKey(), m.getPayload());
            mapper.updateStatus(m.getId(), "SENT");
        }
    }
}
