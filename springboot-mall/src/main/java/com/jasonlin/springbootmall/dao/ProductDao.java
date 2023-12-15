package com.jasonlin.springbootmall.dao;

import com.jasonlin.springbootmall.constant.ProductCategory;
import com.jasonlin.springbootmall.dto.ProductRequest;
import com.jasonlin.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

   List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

}
