package com.czh.travel.service.impl;

import com.czh.travel.dao.FavoriteDao;
import com.czh.travel.dao.RouteDao;
import com.czh.travel.domain.Favorite;
import com.czh.travel.domain.PageBean;
import com.czh.travel.domain.Route;
import com.czh.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private RouteDao routeDao;

    @Override
    public boolean isFavorite(int uid, int rid) {
        Favorite favorite = favoriteDao.findByUidAndRid(uid, rid);
        return favorite != null;    //如果对象有值，返回true
    }

    @Override
    public PageBean<Route> myFavorite(int uid, int currentPage, int pageSize) {
        //封装pageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页展示数据数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = favoriteDao.findByUid(uid);
        pb.setTotalCount(totalCount);
        //设置每页展示的数据集合
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByFavoritePage(uid,start,pageSize);
        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount /pageSize) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public void addFavorite(int uid, int rid) {
        Date date = new Date();
        Favorite favorite = new Favorite();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        favorite.setDate(sdf.format(date));
        favorite.setUid(uid);
        favorite.setRid(rid);
        favoriteDao.addFavorite(favorite);
        routeDao.updateCount(rid);
    }

    @Override
    public PageBean<Route> orderFavorite(int cid, int currentPage, int pageSize, String rname, int lPrice, int hPrice) {
        int order=1;
        PageBean<Route> pageBean=new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        int start=(currentPage-1)*pageSize;
        List<Route> list = routeDao.findRouteByRnameAndPrice(cid,rname,lPrice,hPrice,start,pageSize);
        pageBean.setList(list);

        int totalCount = routeDao.findTotalCountByRnameAndPrice(cid,rname,lPrice,hPrice);
        pageBean.setTotalCount(totalCount);
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : ((totalCount/pageSize) + 1);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }
}
