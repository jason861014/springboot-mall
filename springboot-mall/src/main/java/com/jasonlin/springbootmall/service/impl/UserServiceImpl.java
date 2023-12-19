package com.jasonlin.springbootmall.service.impl;

import com.jasonlin.springbootmall.dao.UserDao;
import com.jasonlin.springbootmall.dto.UserRegisterRequest;
import com.jasonlin.springbootmall.model.User;
import com.jasonlin.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    public Integer register(UserRegisterRequest userRegisterRequest){
        return userDao.createUser(userRegisterRequest);
    }
}
