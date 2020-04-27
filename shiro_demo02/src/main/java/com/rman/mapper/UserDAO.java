package com.rman.mapper;

import com.rman.entity.UserBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description :
 * @Author : ziming
 * @Date: 2020-04-22 11:16
 */
public interface UserDAO {
    /**
     * fetch UserBean by username
     * @param username
     * @return UserBean
     */
    @Select("select * from shiro_demo where username = #{username}")
    UserBean getUserBean(@Param("username")String username);
}
