package com.poly.service;

import java.util.List;

import java.util.Date;

import org.springframework.data.repository.query.Param;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.model.Account;
import com.poly.model.Order;

public interface OrderService {
    List<Order> findAll();

    Order findById(Long id);

    List<Order> findAllCancel();

    Account findAllAccount(Long id);

    List<Order> findByUsername(String username);

    List<Order> findAllCancelByUsername(String username);

    Order save(Order order);

    Double getTotalRevenue();

    List<Order> findByStatus(String status);

    Long countProductsSoldByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Order create(JsonNode orderData);

    List<Order> findByAccountName(String name);

    List<Order> findByCreatedDate(Date ngayDat);

    List<Order> findByStatusAndUser(String status, String username);
}
