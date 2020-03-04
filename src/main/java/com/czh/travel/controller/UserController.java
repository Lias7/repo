package com.czh.travel.controller;

import com.czh.travel.domain.ResultInfo;
import com.czh.travel.domain.User;
import com.czh.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/register.do")
    public @ResponseBody
    ResultInfo register(@ModelAttribute("user") User user) throws Exception {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("RANDOMCODEKEY");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            return info;
        }
        boolean flag = userService.register(user);
        //4.响应结果
        ResultInfo info = new ResultInfo();       //用于封装后端返回前端数据对象
        if (flag) {
            info.setFlag(flag);
        } else {
            info.setFlag(flag);
            info.setErrorMsg("注册失败！");
        }
        return info;
    }

    @RequestMapping("/active.do")
    @ResponseBody
    public String active(@RequestParam(name = "code", required = true) String code) throws Exception {
        String msg = null;
        if (code != null && !code.equals("")) {
            //调用service完成激活
            boolean flag = userService.active(code);
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href= '../pages/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败,联系管理员";
            }
        }
        return msg;
    }

    @RequestMapping("/login.do")
    public @ResponseBody
    ResultInfo login(@ModelAttribute("user") User user){
        //校验验证码
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("RANDOMCODEKEY");
        if (!check.equalsIgnoreCase(checkcode_server) || check == null || check == "") {
            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            return info;
        }
        User u = userService.login(user);
        //4.判断用户是否存在和用户是否激活
        ResultInfo info = new ResultInfo();
        //4.1用户或密码错误
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //4.2用户未激活
        if (u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户未激活");
        }
        //4.3用户信息正确
        if (u != null && "Y".equals(u.getStatus())) {
            info.setFlag(true);
            request.getSession().setAttribute("user", u);
        }
        return info;
    }

    @RequestMapping("/findOne.do")
    public @ResponseBody
    User findOne(){
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }

    @RequestMapping("/exit.do")
    public String exit(){
        request.getSession().invalidate();
        //2.重定向到登录页面
        return "redirect:/pages/login.html";
    }

}
