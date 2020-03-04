package com.czh.travel.controller;

import com.czh.travel.domain.Category;
import com.czh.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAll.do")
    public @ResponseBody
    List<Category> findAll()throws Exception{
        List<Category> list = categoryService.findAll();
        return list;
    }

}
