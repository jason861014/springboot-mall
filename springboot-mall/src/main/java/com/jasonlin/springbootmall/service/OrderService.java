package com.jasonlin.springbootmall.service;

import com.jasonlin.springbootmall.dto.CreateOrderRequest;
import com.jasonlin.springbootmall.model.Order;

public interface OrderService {

   Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
