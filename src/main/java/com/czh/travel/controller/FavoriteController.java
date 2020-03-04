package com.czh.travel.controller;

import com.czh.travel.domain.PageBean;
import com.czh.travel.domain.Route;
import com.czh.travel.domain.User;
import com.czh.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/isFavorite.do")
    @ResponseBody
    public String findOne(@RequestParam(name = "rid",required = true) String ridstr){
        String flag;
        int rid;
        if ("null" != ridstr && "" != ridstr && !"null".equals(ridstr)) {
            rid = Integer.parseInt(ridstr);
        } else {
            flag = "0";
            return flag;
        }
        //2.获取session的user对象的uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户尚未登录
            uid = 0;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //3.调用FavoriteService查询是否收藏过该线路
        boolean f = favoriteService.isFavorite(uid, rid);
        if(f == true){
            return "1";
        }else{
            return "0";
        }
    }

    @RequestMapping("/myFavorite.do")
    public @ResponseBody
    PageBean myFavorite(){
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        //String ridstr = request.getParameter("rid");
        //获取session的user对象的uid
        User user = (User) request.getSession().getAttribute("user");

        int uid;
        if (null != user && !"".equals(user) && !"null".equals(user)) {
            uid = user.getUid();
        } else {
            uid = 0;
        }

        int currentPage = 0;
        if (currentPageStr != null && currentPageStr != "") {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr != "") {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 12;
        }

        //3.调用service查询pageBean对象
        PageBean<Route> pb = favoriteService.myFavorite(uid, currentPage, pageSize);
        return pb;
    }

    @RequestMapping("/addFavorite.do")
    public void addFavorite(@RequestParam(name = "rid",required = true) String ridstr){
        int rid = Integer.parseInt(ridstr);
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user == null){
            return;
        }else {
            uid = user.getUid();
        }
        favoriteService.addFavorite(uid,rid);
    }

    @RequestMapping("/favoriteOrder.do")
    public @ResponseBody
    PageBean favoriteOrder(String rname, @RequestParam(name = "lPrice") String lPricestr, @RequestParam(name = "hPrice") String hPricestr) throws UnsupportedEncodingException {
        //1.获取参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidstr = request.getParameter("cid");
        /*String lPricestr = request.getParameter("lPrice");
        String hPricestr = request.getParameter("hPrice");*/

        //接收线路名称
        /*String rname ;
        rname = request.getParameter("rname");*/
        /*if(null != rname && "" != rname && !"null".equals(rname)){
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }else {
            rname = null;
        }*/
        //2.处理参数
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr != "") {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr != "") {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 8;
        }

        int cid = 5;
        if (cidstr != null && cidstr != "" && !"null".equals(cidstr)) {
            cid = Integer.parseInt(cidstr);
        }

        int lPrice = 0;
        if(null != lPricestr && "" != lPricestr && !"null".equals(lPricestr)){
            lPrice = Integer.parseInt(lPricestr);
        }

        int hPrice = 0;
        if(null != hPricestr && "" != hPricestr && !"null".equals(hPricestr)){
            hPrice = Integer.parseInt(hPricestr);
        }
        //3.调用service查询pageBean对象
        PageBean<Route> pb = favoriteService.orderFavorite(cid, currentPage, pageSize, rname,lPrice,hPrice);
        return pb;
    }
}
