package com.judicial.user.service;

import com.judicial.common.BizException;
import com.judicial.user.entity.FundFlow;
import com.judicial.user.mapper.AccountMapper;
import com.judicial.user.mapper.FundFlowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FundService {
    private final AccountMapper accountMapper;
    private final FundFlowMapper flowMapper;
    private final MessagePublisher publisher;

    @Transactional
    public void freeze(Long userId, BigDecimal amount, String flowNo) {
        if (flowMapper.findByFlowNo(flowNo) != null) return;
        if (accountMapper.freeze(userId, amount) == 0) throw new BizException("可用余额不足");
        saveFlow(flowNo, userId, amount, "FREEZE");
        publisher.publish("FUND_FROZEN", "fund:freeze:" + flowNo,
                Map.of("userId", userId, "amount", amount, "flowNo", flowNo));
    }

    @Transactional
    public void unfreeze(Long userId, BigDecimal amount, String flowNo) {
        if (flowMapper.findByFlowNo(flowNo) != null) return;
        if (accountMapper.unfreeze(userId, amount) == 0) throw new BizException("解冻失败：冻结不足");
        saveFlow(flowNo, userId, amount, "UNFREEZE");
        publisher.publish("FUND_UNFROZEN", "fund:unfreeze:" + flowNo,
                Map.of("userId", userId, "amount", amount, "flowNo", flowNo));
    }

    @Transactional
    public void deduct(Long userId, BigDecimal amount, String flowNo) {
        if (flowMapper.findByFlowNo(flowNo) != null) return;
        if (accountMapper.deductFrozen(userId, amount) == 0) throw new BizException("扣除失败：冻结不足");
        saveFlow(flowNo, userId, amount, "DEDUCT");
        publisher.publish("FUND_DEDUCTED", "fund:deduct:" + flowNo,
                Map.of("userId", userId, "amount", amount, "flowNo", flowNo));
    }

    private void saveFlow(String flowNo, Long userId, BigDecimal amount, String type) {
        FundFlow f = new FundFlow();
        f.setFlowNo(flowNo); f.setUserId(userId); f.setAmount(amount);
        f.setType(type); f.setCreateTime(LocalDateTime.now());
        flowMapper.insert(f);
    }
}
