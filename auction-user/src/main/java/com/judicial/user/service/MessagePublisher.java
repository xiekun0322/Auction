package com.judicial.user.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.judicial.user.entity.LocalMessage;
import com.judicial.user.mapper.LocalMessageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessagePublisher {
    private final LocalMessageMapper messageMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public void publish(String topic, String msgKey, Object payload) {
        if (messageMapper.findByKey(msgKey) != null) return;
        LocalMessage m = new LocalMessage();
        m.setMsgKey(msgKey);
        m.setTopic(topic);
        try { m.setPayload(objectMapper.writeValueAsString(payload)); }
        catch (Exception e) { m.setPayload(String.valueOf(payload)); }
        m.setStatus("PENDING");
        m.setCreateTime(LocalDateTime.now());
        messageMapper.insert(m);
    }
}
