package com.judicial.record.Controller;

import com.judicial.common.ApiResponse;
import com.judicial.record.entity.BidRecord;
import com.judicial.record.mapper.BidRecordMapper;
import com.judicial.record.service.BidBuffer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class BidRecordController {
    private final BidBuffer buffer;
    private final BidRecordMapper mapper;

    @PostMapping("/push")
    public ApiResponse<Void> push(@RequestBody BidRecord r) {
        if (mapper.findByIdemKey(r.getIdemKey()) == null) {
            if (r.getCreateTime() == null) r.setCreateTime(LocalDateTime.now());
            buffer.offer(r);
        }
        return ApiResponse.ok();
    }

    @GetMapping("/recent") public ApiResponse<List<BidRecord>> recent() { return ApiResponse.ok(mapper.recent()); }
}
