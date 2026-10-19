package com.judicial.user.mapper;

import com.judicial.user.entity.LocalMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LocalMessageMapper {
    @Select("SELECT * FROM local_message WHERE msg_key=#{key}")
    LocalMessage findByKey(String key);

    @Insert("INSERT INTO local_message(msg_key,topic,payload,status,retry,create_time) " +
            "VALUES(#{msgKey},#{topic},#{payload},#{status},0,#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LocalMessage m);

    @Select("SELECT * FROM local_message WHERE status='PENDING' ORDER BY id LIMIT #{limit}")
    List<LocalMessage> findPending(int limit);

    @Update("UPDATE local_message SET status=#{status}, retry=retry+1 WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
