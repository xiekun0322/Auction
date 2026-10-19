package com.judicial.order.mapper;

import com.judicial.order.entity.AuctionOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM auction_order WHERE item_id=#{itemId}")
    AuctionOrder findByItemId(Long itemId);

    @Insert("INSERT INTO auction_order(order_no,item_id,buyer_id,amount,status,create_time) " +
            "VALUES(#{orderNo},#{itemId},#{buyerId},#{amount},#{status},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AuctionOrder o);
}
