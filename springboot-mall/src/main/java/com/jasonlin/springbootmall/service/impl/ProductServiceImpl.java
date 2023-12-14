package com.jasonlin.springbootmall.service.impl;

import com.jasonlin.springbootmall.dao.ProductDao;
import com.jasonlin.springbootmall.model.Product;
import com.jasonlin.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
