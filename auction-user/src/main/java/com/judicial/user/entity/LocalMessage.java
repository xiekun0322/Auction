package com.judicial.user.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LocalMessage {
    private Long id;
    private String msgKey;
    private String topic;
    private String payload;
    private String status;
    private Integer retry;
    private LocalDateTime createTime;
}
