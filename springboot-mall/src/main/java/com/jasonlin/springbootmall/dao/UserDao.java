package com.jasonlin.springbootmall.dao;

import com.jasonlin.springbootmall.dto.UserRegisterRequest;
import com.jasonlin.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
