package com.judicial.user.mapper;

import com.judicial.user.entity.SysUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SysUserMapper {
    @Select("SELECT * FROM sys_user WHERE username=#{u} AND password=#{p}")
    SysUser login(@Param("u") String username, @Param("p") String password);

    @Insert("INSERT INTO sys_user(username,password,role) VALUES(#{username},#{password},#{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SysUser u);
}
