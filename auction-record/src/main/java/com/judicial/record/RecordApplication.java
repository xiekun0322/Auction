package com.judicial.record;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.judicial.record.mapper")
public class RecordApplication {
    public static void main(String[] args) { SpringApplication.run(RecordApplication.class, args); }
}
