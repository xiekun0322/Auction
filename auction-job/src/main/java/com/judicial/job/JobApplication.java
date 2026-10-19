package com.judicial.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@MapperScan("com.judicial.job.mapper")
public class JobApplication {
    public static void main(String[] args) { SpringApplication.run(JobApplication.class, args); }
}
