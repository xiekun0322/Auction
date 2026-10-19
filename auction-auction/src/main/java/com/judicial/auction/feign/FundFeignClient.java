package com.judicial.auction.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.judicial.common.ApiResponse;

@FeignClient(name = "fundFeign", url = "${feign.fund-url}")
public interface FundFeignClient {
    @PostMapping("/api/fund/freeze")
    ApiResponse<Void> freeze(@RequestBody Map<String, Object> body);

    @PostMapping("/api/fund/unfreeze")
    ApiResponse<Void> unfreeze(@RequestBody Map<String, Object> body);

    @PostMapping("/api/fund/deduct")
    ApiResponse<Void> deduct(@RequestBody Map<String, Object> body);
}
