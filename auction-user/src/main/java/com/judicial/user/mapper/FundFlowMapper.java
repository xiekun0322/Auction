package com.judicial.user.mapper;

import com.judicial.user.entity.FundFlow;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;

@Mapper
public interface FundFlowMapper {
    @Select("SELECT * FROM fund_flow WHERE flow_no=#{flowNo}")
    FundFlow findByFlowNo(String flowNo);

    @Insert("INSERT INTO fund_flow(flow_no,user_id,amount,type,create_time) " +
            "VALUES(#{flowNo},#{userId},#{amount},#{type},#{createTime})")
    int insert(FundFlow f);

    @Select("SELECT COALESCE(SUM(CASE WHEN type='DEDUCT' THEN -amount ELSE 0 END),0) " +
            "FROM fund_flow WHERE user_id=#{userId}")
    BigDecimal sumPaidByUser(Long userId);
}
