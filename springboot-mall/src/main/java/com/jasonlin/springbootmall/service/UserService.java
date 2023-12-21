package com.jasonlin.springbootmall.service;

import com.jasonlin.springbootmall.dto.UserLoginRequest;
import com.jasonlin.springbootmall.dto.UserRegisterRequest;
import com.jasonlin.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);

}
