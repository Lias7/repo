package com.czh.travel.service.impl;

import com.czh.travel.dao.FavoriteDao;
import com.czh.travel.dao.RouteDao;
import com.czh.travel.dao.RouteImgDao;
import com.czh.travel.dao.SellerDao;
import com.czh.travel.domain.PageBean;
import com.czh.travel.domain.Route;
import com.czh.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RouteServiceImpl implements RouteService
{

    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteImgDao routeImgDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private FavoriteDao favoriteDao;


    @Override
    public PageBean<Route> findAll(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页的数据
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findAll(cid,start,pageSize,rname);
        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : ((totalCount / pageSize) + 1);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public Route findOne(int rid) {
        //1.根据rid去route查询出route对象
        Route route = routeDao.findOne(rid);
        return route;
    }
}
