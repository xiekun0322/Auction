package com.judicial.auction.controller;

import com.judicial.auction.service.BidService;
import com.judicial.common.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/auction")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @Data
    public static class BidRequest {
        @NotNull private Long itemId;
        @NotNull private Long userId;
        @NotNull @DecimalMin("0.01") private BigDecimal amount;
        @NotBlank private String idemKey;
    }

    @PostMapping("/bid")
    public ApiResponse<Void> bid(@RequestBody @Valid BidRequest req) {
        bidService.bid(req.getItemId(), req.getUserId(), req.getAmount(), req.getIdemKey());
        return ApiResponse.ok();
    }
}
