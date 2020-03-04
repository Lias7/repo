package com.czh.travel.service;

import com.czh.travel.domain.PageBean;
import com.czh.travel.domain.Route;

public interface FavoriteService {

    boolean isFavorite(int uid, int rid);

    PageBean<Route> myFavorite(int uid, int currentPage, int pageSize);

    void addFavorite(int uid, int rid);

    PageBean<Route> orderFavorite(int cid, int currentPage, int pageSize, String rname, int lPrice, int hPrice);
}
