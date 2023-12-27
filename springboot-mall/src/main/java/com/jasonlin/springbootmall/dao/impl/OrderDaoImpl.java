package com.jasonlin.springbootmall.dao.impl;

import com.jasonlin.springbootmall.dao.OrderDao;
import com.jasonlin.springbootmall.dto.OrderQueryParams;
import com.jasonlin.springbootmall.model.Order;
import com.jasonlin.springbootmall.model.OrderItem;
import com.jasonlin.springbootmall.rowmapper.OrderItemRowMapper;
import com.jasonlin.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "SELECT COUNT(*) FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // search condition
        sql = addFilterSql(sql, map, orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // search condition查詢條件
        sql = addFilterSql(sql, map, orderQueryParams);

        // sorting排序
        sql = sql + " ORDER BY created_date DESC";

        // pagination分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
    }



    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url FROM order_item as oi LEFT JOIN product as p ON oi.product_id = p.product_id WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        return orderItemList;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();
        return  orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        // 使用 batchUpdate 一次性加入數據 效率更高
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) VALUES (:orderId, :productId, :quantity, :amount)";
        MapSqlParameterSource[] parameterSource = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSource[i] = new MapSqlParameterSource();
            parameterSource[i].addValue("orderId", orderId);
            parameterSource[i].addValue("productId", orderItem.getProductId());
            parameterSource[i].addValue("quantity", orderItem.getQuantity());
            parameterSource[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSource);
    }

    private String addFilterSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
        if (Objects.nonNull(orderQueryParams.getUserId())) {
            sql += " AND user_id = :userId";
            map.put("userId", orderQueryParams.getUserId());
        }

        return sql;
    }
}
