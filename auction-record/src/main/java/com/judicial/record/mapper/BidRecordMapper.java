package com.judicial.record.mapper;

import com.judicial.record.entity.BidRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BidRecordMapper {
    @Select("SELECT * FROM bid_record WHERE idem_key=#{key}")
    BidRecord findByIdemKey(String key);

    @Insert("<script>" +
            "INSERT INTO bid_record(item_id,user_id,amount,idem_key,create_time) VALUES " +
            "<foreach collection='list' item='b' separator=','>" +
            "(#{b.itemId},#{b.userId},#{b.amount},#{b.idemKey},#{b.createTime})" +
            "</foreach></script>")
    int batchInsert(@Param("list") List<BidRecord> list);

    @Select("SELECT * FROM bid_record ORDER BY id DESC LIMIT 50")
    List<BidRecord> recent();
}
