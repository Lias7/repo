package com.czh.travel.controller;

import com.czh.travel.domain.PageBean;
import com.czh.travel.domain.Route;
import com.czh.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/pageQuery.do")
    public @ResponseBody
    PageBean pageQuery()throws Exception{
        //1.获取参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidstr = request.getParameter("cid");

        //接收线路名称
        String rname = "null";
        rname = request.getParameter("rname");
        /*if(null != rname && "" != rname && !"null".equals(rname)){
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
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
            pageSize = 5;
        }

        int cid = 5;
        if (cidstr != null && cidstr != "" && !"null".equals(cidstr)) {
            cid = Integer.parseInt(cidstr);
        }
        //3.调用service查询pageBean对象
        PageBean<Route> pb =  routeService.findAll(cid, currentPage, pageSize, rname);
        return pb;
    }

    @RequestMapping("/findOne.do")
    public @ResponseBody
    Route findOne(@RequestParam(name = "rid",required = true) String ridstr){
        int rid;
        if ("".equals(ridstr) || "null".equals(ridstr)) {
            rid = 1;
        } else {
            rid = Integer.parseInt(ridstr);
        }
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        return route;
    }
}
