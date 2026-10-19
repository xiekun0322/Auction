package com.judicial.job.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.judicial.job.entity.ReconcileDiff;

@Mapper
public interface ReconcileDiffMapper {
    @Insert("INSERT INTO reconcile_diff(user_id,db_balance,flow_balance,diff,status,create_time) " +
            "VALUES(#{userId},#{dbBalance},#{flowBalance},#{diff},#{status},#{createTime})")
    int insert(ReconcileDiff d);

    @Select("SELECT * FROM reconcile_diff ORDER BY id DESC LIMIT 100")
    List<ReconcileDiff> findRecent();
}
