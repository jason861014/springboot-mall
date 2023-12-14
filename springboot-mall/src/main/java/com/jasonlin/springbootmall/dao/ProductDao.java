package com.jasonlin.springbootmall.dao;

import com.jasonlin.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
