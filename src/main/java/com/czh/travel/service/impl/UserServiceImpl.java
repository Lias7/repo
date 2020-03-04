package com.czh.travel.service.impl;

import com.czh.travel.dao.UserDao;
import com.czh.travel.domain.User;
import com.czh.travel.service.UserService;
import com.czh.travel.utils.MailUtils;
import com.czh.travel.utils.Md5Util;
import com.czh.travel.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean register(User user) throws Exception {
        User u = userDao.findByUsername(user.getUsername());
        if (u != null) {
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        //2.3加密密码
        user.setPassword(Md5Util.MD5(user.getPassword()));
        userDao.save(user);
        //3.发送激活邮箱
        String context = "<a href='http://localhost/travel/user/active.do?code=" + user.getCode() + "' > 点击激活爽翻天旅游网</a>";
        MailUtils.sendMail(user.getEmail(), context, "激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        User user = null;
        try {
            user = userDao.findByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null) {
            try {
                userDao.updateStatus(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), Md5Util.JM(Md5Util.KL(Md5Util.MD5(user.getPassword()))));
    }
}
