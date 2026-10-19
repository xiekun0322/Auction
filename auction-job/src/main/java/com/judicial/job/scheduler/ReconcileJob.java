package com.judicial.job.scheduler;

import com.judicial.job.entity.ReconcileDiff;
import com.judicial.job.feign.UserFeignClient;
import com.judicial.job.mapper.ReconcileDiffMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReconcileJob {
    private final UserFeignClient userFeign;
    private final ReconcileDiffMapper diffMapper;

    /** MVP：每 2 分钟跑一次；生产：凌晨 3 点 cron */
    @Scheduled(fixedDelay = 120_000)
    public void run() {
        try {
            var rsp = userFeign.allAccounts();
            if (rsp.getCode() != 0 || rsp.getData() == null) return;
            List<Map<String, Object>> accounts = rsp.getData();
            int diffCount = 0;
            for (Map<String, Object> a : accounts) {
                BigDecimal bal = new BigDecimal(String.valueOf(a.get("balance")));
                BigDecimal base = new BigDecimal("10000");
                BigDecimal expected = base; // 无 deduction 情况下应与初始一致
                if (bal.compareTo(expected) != 0) {
                    ReconcileDiff d = new ReconcileDiff();
                    d.setUserId(Long.valueOf(String.valueOf(a.get("userId"))));
                    d.setDbBalance(bal);
                    d.setFlowBalance(expected);
                    d.setDiff(bal.subtract(expected));
                    d.setStatus("PENDING");
                    d.setCreateTime(LocalDateTime.now());
                    diffMapper.insert(d);
                    diffCount++;
                }
            }
            log.info("[RECONCILE] 完成，差异 {} 条", diffCount);
        } catch (Exception e) {
            log.warn("[RECONCILE] 跳过：{}", e.getMessage());
        }
    }
}
