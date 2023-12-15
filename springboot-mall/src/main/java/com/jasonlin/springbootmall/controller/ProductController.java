package com.jasonlin.springbootmall.controller;

import com.jasonlin.springbootmall.constant.ProductCategory;
import com.jasonlin.springbootmall.dto.ProductRequest;
import com.jasonlin.springbootmall.model.Product;
import com.jasonlin.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
@GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //前端可以透過這個參數去查詢哪個類別的商品
            //required=false 代表前端不一定只能搜尋category這個參數 而是可以搜尋全部而不會出現錯誤
            //@Requestparam表示從url路徑去請求參數
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search
) {

       List<Product> productList =  productService.getProducts(category, search);

       return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

    if (product != null) {
        return ResponseEntity.status(HttpStatus.OK).body(product);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
    }

@PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

       Integer productId = productService.createProduct(productRequest);

     Product product = productService.getProductById(productId);

     return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


@PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        //檢查product是否存在
        Product product = productService.getProductById(productId);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


        //修改商品數據
        productService.updateProduct(productId, productRequest);

         Product updateProduct = productService.getProductById(productId);

         return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

@DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable  Integer productId) {
        productService.deleteProductById(productId);

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
