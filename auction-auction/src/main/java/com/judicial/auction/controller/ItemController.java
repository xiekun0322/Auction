package com.judicial.auction.controller;

import com.judicial.auction.entity.AuctionItem;
import com.judicial.auction.mapper.AuctionItemMapper;
import com.judicial.auction.service.AuctionEndService;
import com.judicial.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auction")
@RequiredArgsConstructor
public class ItemController {
    private final AuctionItemMapper itemMapper;
    private final AuctionEndService endService;

    @GetMapping("/items") public ApiResponse<List<AuctionItem>> list() { return ApiResponse.ok(itemMapper.findAll()); }
    @GetMapping("/item/{id}") public ApiResponse<AuctionItem> item(@PathVariable Long id) { return ApiResponse.ok(itemMapper.findById(id)); }
    @PostMapping("/end/{id}") public ApiResponse<Void> end(@PathVariable Long id) { endService.end(id); return ApiResponse.ok(); }
}
