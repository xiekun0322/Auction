package com.judicial.record.service;

import com.judicial.record.entity.BidRecord;
import com.judicial.record.mapper.BidRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidBuffer {
    private final BidRecordMapper mapper;
    private final ConcurrentLinkedQueue<BidRecord> queue = new ConcurrentLinkedQueue<>();

    public void offer(BidRecord r) { queue.offer(r); }

    @Scheduled(fixedDelay = 1000)
    public void flush() {
        List<BidRecord> batch = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            BidRecord b = queue.poll();
            if (b == null) break;
            batch.add(b);
        }
        if (batch.isEmpty()) return;
        try { mapper.batchInsert(batch); log.info("批量落库 {} 条", batch.size()); }
        catch (Exception e) { log.error("落库失败回滚队列", e); batch.forEach(queue::offer); }
    }
}
