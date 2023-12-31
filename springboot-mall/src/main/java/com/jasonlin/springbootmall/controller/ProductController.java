package com.jasonlin.springbootmall.controller;

import com.jasonlin.springbootmall.constant.ProductCategory;
import com.jasonlin.springbootmall.dto.ProductQueryParams;
import com.jasonlin.springbootmall.dto.ProductRequest;
import com.jasonlin.springbootmall.model.Product;
import com.jasonlin.springbootmall.service.ProductService;
import com.jasonlin.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated//加上此註解 max min才會生效
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
@GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件 Filtering
            //前端可以透過這個參數去查詢哪個類別的商品
            //required=false 代表前端不一定只能搜尋category這個參數 而是可以搜尋全部而不會出現錯誤
            //@Requestparam表示從url路徑去請求參數
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            //排序 Sorting
            //orderBy表示根據什麼欄位來排序,ex:商品價格，時間等等
            //created_date表示若前端沒有傳遞要求參數，則顯示預設值(可能是商品最新上架日期)
            @RequestParam(defaultValue = "created_date") String orderBy,
            //sort 用升序或是降序來排序由小到大或反之
            //desc預設使用降序 (對商品進行大到小排序)
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 pagination
            @RequestParam(defaultValue="5") @Max(1000)@Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
) {
    ProductQueryParams productQueryParams = new ProductQueryParams();
    productQueryParams.setCategory(category);
    productQueryParams.setSearch(search);
    productQueryParams.setOrderBy(orderBy);
    productQueryParams.setSort(sort);
    productQueryParams.setLimit(limit);
    productQueryParams.setOffset(offset);
//取得 product List
  List<Product> productList = productService.getProducts(productQueryParams);
//取得 Product 總數
  Integer total = productService.countProduct(productQueryParams);
//分頁
    Page<Product> page = new Page<>();
       page.setLimit(limit);
       page.setOffset(offset);
       page.setTotal(total);
       page.setResults(productList);

       return ResponseEntity.status(HttpStatus.OK).body(page);
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
