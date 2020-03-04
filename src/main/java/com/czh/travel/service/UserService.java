package com.czh.travel.service;

import com.czh.travel.domain.User;

public interface UserService {
    boolean register(User user) throws Exception;

    boolean active(String code);

    User login(User user);
}
