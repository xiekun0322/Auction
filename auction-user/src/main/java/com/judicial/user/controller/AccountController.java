package com.judicial.user.controller;

import com.judicial.common.ApiResponse;
import com.judicial.user.entity.Account;
import com.judicial.user.mapper.AccountMapper;
import com.judicial.user.service.FundService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/fund")
@RequiredArgsConstructor
public class AccountController {

    private final AccountMapper accountMapper;
    private final FundService fundService;

    @GetMapping("/account/{userId}")
    public ApiResponse<Account> account(@PathVariable Long userId) {
        return ApiResponse.ok(accountMapper.findByUserId(userId));
    }

    @Data
    public static class OpReq {
        private Long userId;
        private BigDecimal amount;
        private String flowNo;
    }

    @PostMapping("/freeze")   public ApiResponse<Void> freeze(@RequestBody OpReq r)   { fundService.freeze(r.getUserId(), r.getAmount(), r.getFlowNo()); return ApiResponse.ok(); }
    @PostMapping("/unfreeze") public ApiResponse<Void> unfreeze(@RequestBody OpReq r) { fundService.unfreeze(r.getUserId(), r.getAmount(), r.getFlowNo()); return ApiResponse.ok(); }
    @PostMapping("/deduct")   public ApiResponse<Void> deduct(@RequestBody OpReq r)   { fundService.deduct(r.getUserId(), r.getAmount(), r.getFlowNo()); return ApiResponse.ok(); }

    @GetMapping("/all")
    public ApiResponse<java.util.List<Account>> all() { return ApiResponse.ok(accountMapper.findAll()); }
}
