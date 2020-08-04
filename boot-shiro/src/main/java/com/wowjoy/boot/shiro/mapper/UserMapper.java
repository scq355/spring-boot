package com.wowjoy.boot.shiro.mapper;

import com.wowjoy.boot.shiro.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findByName(@Param("name") String name);

    User findById(@Param("id") Integer id);

}
