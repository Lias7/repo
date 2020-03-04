package com.czh.travel.dao;

import com.czh.travel.domain.Seller;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SellerDao {
    @Select("select * from seller where sid = #{sid}")
    Seller findById(int sid);
}
