package com.czh.travel.service;

import com.czh.travel.domain.PageBean;
import com.czh.travel.domain.Route;

public interface RouteService {
    PageBean<Route> findAll(int cid, int currentPage, int pageSize, String rname);

    Route findOne(int rid);
}
