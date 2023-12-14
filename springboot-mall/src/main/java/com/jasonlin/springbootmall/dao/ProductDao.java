package com.jasonlin.springbootmall.dao;

import com.jasonlin.springbootmall.dto.ProductRequest;
import com.jasonlin.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);


    Integer createProduct(ProductRequest productRequest);
}
