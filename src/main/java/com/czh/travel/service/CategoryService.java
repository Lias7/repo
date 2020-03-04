package com.czh.travel.service;

import com.czh.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll() throws Exception;
}
