package com.judicial.auction.init;

import com.judicial.auction.entity.AuctionItem;
import com.judicial.auction.mapper.AuctionItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final AuctionItemMapper itemMapper;

    @Override
    public void run(String... args) {
        AuctionItem it = new AuctionItem();
        it.setCaseNo("(2025)京01执1234号");
        it.setTitle("朝阳区某小区 A-101 房产");
        it.setStartPrice(new BigDecimal("1000"));
        it.setDeposit(new BigDecimal("500"));
        it.setCurrentPrice(new BigDecimal("1000"));
        it.setEndTime(LocalDateTime.now().plusMinutes(30));
        it.setStatus("ONGOING");
        itemMapper.insert(it);
    }
}
