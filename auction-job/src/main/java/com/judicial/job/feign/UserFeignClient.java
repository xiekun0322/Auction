package com.judicial.job.feign;

import com.judicial.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "userFeign", url = "${feign.user-url}")
public interface UserFeignClient {
    @GetMapping("/api/fund/all")
    ApiResponse<List<Map<String, Object>>> allAccounts();
}
