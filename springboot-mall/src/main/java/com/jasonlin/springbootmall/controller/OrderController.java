package com.jasonlin.springbootmall.controller;

import com.jasonlin.springbootmall.dto.CreateOrderRequest;
import com.jasonlin.springbootmall.dto.OrderQueryParams;
import com.jasonlin.springbootmall.model.Order;
import com.jasonlin.springbootmall.service.OrderService;
import com.jasonlin.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // get order list
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        // get order total count
        Integer total = orderService.countOrder(orderQueryParams);

        // pagination
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

//創建訂單的功能
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){


       Integer orderId = orderService.createOrder(userId, createOrderRequest);

       Order order = orderService.getOrderById(orderId);
       return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }


}
