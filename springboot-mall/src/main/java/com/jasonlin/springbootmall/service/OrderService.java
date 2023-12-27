package com.jasonlin.springbootmall.service;

import com.jasonlin.springbootmall.dto.CreateOrderRequest;
import com.jasonlin.springbootmall.dto.OrderQueryParams;
import com.jasonlin.springbootmall.model.Order;

import java.util.List;

public interface OrderService {


    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
   Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
