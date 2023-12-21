package com.jasonlin.springbootmall.controller;

import com.jasonlin.springbootmall.dto.UserLoginRequest;
import com.jasonlin.springbootmall.dto.UserRegisterRequest;
import com.jasonlin.springbootmall.model.User;
import com.jasonlin.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
//選擇Post的理由 RESTful中,創建資源是對應到post方法
//另一個為資安考量，需使用request body 傳遞參數
@PostMapping ("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){

   Integer userId = userService.register(userRegisterRequest);

   User user = userService.getUserById(userId);

   return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
//登入功能
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
   User user = userService.login(userLoginRequest);

   return ResponseEntity.status(HttpStatus.OK).body(user);
    }


}
