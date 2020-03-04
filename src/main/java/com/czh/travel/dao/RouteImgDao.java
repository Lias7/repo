package com.czh.travel.dao;

import com.czh.travel.domain.RouteImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RouteImgDao {
    @Select("select * from route_img where rid = #{rid}")
    public List<RouteImg> findByRid(int rid);
}
