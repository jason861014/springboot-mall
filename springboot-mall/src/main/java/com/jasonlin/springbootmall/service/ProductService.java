package com.jasonlin.springbootmall.service;

import com.jasonlin.springbootmall.dto.ProductRequest;
import com.jasonlin.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
