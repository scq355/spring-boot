package com.wowjoy.boot.cache.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface MethodlockMapper {
    @Insert("insert into methodlock (method_name, description, update_time) values (#{method_name}, #{description}, #{update_time})")
    public int addMethodLock(String methodName, String description, Date updateTime);

    @Delete("delete from methodlock where method_name = #{methodName}")
    public int deleteMethodLock(String methodName);
}
