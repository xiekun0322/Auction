package com.judicial.auction.mapper;

import com.judicial.auction.entity.AuctionItem;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AuctionItemMapper {
    @Select("SELECT * FROM auction_item WHERE id=#{id}")
    AuctionItem findById(Long id);

    @Insert("INSERT INTO auction_item(case_no,title,start_price,deposit,current_price,leader_id,end_time,status,version) " +
            "VALUES(#{caseNo},#{title},#{startPrice},#{deposit},#{currentPrice},#{leaderId},#{endTime},#{status},0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AuctionItem a);

    @Select("SELECT * FROM auction_item")
    List<AuctionItem> findAll();

    @Update("UPDATE auction_item SET current_price=#{newPrice}, leader_id=#{userId}, version=version+1 " +
            "WHERE id=#{id} AND version=#{version} AND status='ONGOING' AND current_price<#{newPrice}")
    int updatePriceOptimistic(@Param("id") Long id, @Param("version") Integer version,
                              @Param("newPrice") BigDecimal newPrice, @Param("userId") Long userId);

    @Update("UPDATE auction_item SET end_time=#{endTime} WHERE id=#{id}")
    int updateEndTime(@Param("id") Long id, @Param("endTime") LocalDateTime endTime);

    @Update("UPDATE auction_item SET status=#{status} WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Select("SELECT * FROM auction_item WHERE status='ONGOING' AND end_time<=#{now}")
    List<AuctionItem> findExpired(@Param("now") LocalDateTime now);
}
