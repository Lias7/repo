package com.czh.travel.dao;

import com.czh.travel.domain.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FavoriteDao {

    @Select("select count(*) from favorite where rid = #{rid}")
    int findByRid(int rid);

    @Select("select * from favorite where uid = #{uid} and rid = #{rid}")
    Favorite findByUidAndRid(@Param("uid") int uid, @Param("rid") int rid);

    @Select("select count(*) from favorite where uid = #{uid}")
    int findByUid(int uid);

    @Insert("insert into favorite (rid,date,uid)values(#{rid,jdbcType=INTEGER},#{date,jdbcType=DATE},#{uid,jdbcType=INTEGER})")
    void addFavorite(Favorite favorite);
}
