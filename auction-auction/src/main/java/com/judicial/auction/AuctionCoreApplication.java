package com.judicial.auction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@MapperScan("com.judicial.auction.mapper")
public class AuctionCoreApplication {
    public static void main(String[] args) { SpringApplication.run(AuctionCoreApplication.class, args); }
}
