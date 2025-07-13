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
    List<Order> findByStatus(OrderStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    int updateOrderStatusById(Long orderId, OrderStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.username = :username")
    int updateOrderStatusByUsername(String username, OrderStatus status);
}
