package com.czh.travel.dao;

import com.czh.travel.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryDao {

    @Select("select * from category")
    List<Category> findAll();
}
