package com.czh.travel.dao;

import com.czh.travel.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Insert("insert into user (username,password,name,birthday,sex,telephone,email,status,code) values(#{username},#{password},#{name},#{birthday},#{sex},#{telephone},#{email},#{status},#{code})")
    void save(User user) throws Exception;

    @Select("select * from user where username = #{username}")
    User findByUsername(String username)throws Exception;

    @Select("select * from user where code = #{code}")
    User findByCode(String code) throws Exception;

    @Update("update user set status='Y' where uid = #{uid}")
    void updateStatus(User user) throws Exception;

    @Select("select * from user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password) ;
}
