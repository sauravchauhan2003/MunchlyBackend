package com.example.Food.Delivery.App.Backend.OrderManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUsername(String username);
    List<Order> findByOrderStatus(OrderStatus orderStatus); // Changed from findByStatus to findByOrderStatus

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.id = :orderId") // Changed o.status to o.orderStatus and :status to :orderStatus
    int updateOrderStatusById(Long orderId, OrderStatus orderStatus); // Changed parameter name to match query

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.username = :username") // Changed o.status to o.orderStatus and :status to :orderStatus
    int updateOrderStatusByUsername(String username, OrderStatus orderStatus); // Changed parameter name to match query
}