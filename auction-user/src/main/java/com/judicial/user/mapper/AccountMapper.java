package com.judicial.user.mapper;

import com.judicial.user.entity.Account;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AccountMapper {
    @Select("SELECT * FROM account WHERE user_id=#{userId}")
    Account findByUserId(Long userId);

    @Insert("INSERT INTO account(user_id,balance,frozen,version) VALUES(#{userId},#{balance},#{frozen},#{version})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Account a);

    @Update("UPDATE account SET frozen=frozen+#{amount}, version=version+1 " +
            "WHERE user_id=#{userId} AND balance-frozen>=#{amount}")
    int freeze(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    @Update("UPDATE account SET frozen=frozen-#{amount}, version=version+1 " +
            "WHERE user_id=#{userId} AND frozen>=#{amount}")
    int unfreeze(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    @Update("UPDATE account SET frozen=frozen-#{amount}, balance=balance-#{amount}, version=version+1 " +
            "WHERE user_id=#{userId} AND frozen>=#{amount}")
    int deductFrozen(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    @Select("SELECT * FROM account")
    List<Account> findAll();
}
